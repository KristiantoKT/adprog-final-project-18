package zonkbot.controller;

import com.google.gson.Gson;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.Template;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import zonkbot.GroupZonkbot;
import zonkbot.Question;
import zonkbot.User;
import zonkbot.Zonkbot;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@LineMessageHandler
public class ZonkbotController {

    private static final Logger LOGGER = Logger.getLogger(ZonkbotController.class.getName());
    public Zonkbot zonkbot = new Zonkbot();
    private List<GroupZonkbot> groupZonkbots = new ArrayList<>();

    @Autowired
    private LineMessagingClient lineMessagingClient;


    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws ExecutionException, InterruptedException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent messageContent = event.getMessage();
        String textContent = messageContent.getText();
        String replyText;
        //USER SOURCE
        String replyToken = event.getReplyToken();
        if (event.getSource() instanceof UserSource) {
            replyText = zonkbot.responseMessage(textContent);
            if (replyText.equals("/Choose correct answer")) {
                chooseCorrectAnswerWithCarousel(replyToken);
            } else if (replyText.equals("/Choose question")) {
                chooseQuestion(replyToken);
            } else if (replyText.equals("/name")) {
                replyText = getProfileName(event.getSource().getUserId());
                this.replyText(replyToken, replyText);
            } else if (!replyText.isEmpty()) {
                this.replyText(replyToken, replyText);
            }
        }
        //GROUP SOURCE
        else if (event.getSource() instanceof GroupSource) {
            replyText = groupResponseMessage(event);

            if (replyText.equals("/Random question"))
                replyWithRandomQuestion(replyToken);
            else if (replyText.equals("show leaderboard")) {
                String groupId = ((GroupSource) event.getSource()).getGroupId();
                replyText = showLeaderboard(groupId);
                groupZonkbots.remove(getGroup(groupId));
                this.replyText(replyToken, replyText);
            }
            else if (!replyText.isEmpty())
                this.replyText(replyToken, replyText);
        }
    }

    public String showLeaderboard(String groupId) throws ExecutionException, InterruptedException {
        String reply = "";
        GroupZonkbot group = getGroup(groupId);
        List<User> users = group.getUsers();
        Collections.sort(users);
        for (User user: users) {
            String userId = user.getUserId();
            String name = getProfileName(userId);
            reply += name + ": " + user.getScore() + "\n";
        }
        return reply;
    }

    public String groupResponseMessage(MessageEvent<TextMessageContent> event) {
        String replyText = "";
        String groupId = ((GroupSource) event.getSource()).getGroupId();
        String userId = event.getSource().getUserId();
        String textContent = event.getMessage().getText();
        GroupZonkbot group = getGroup(groupId);
        boolean hasGroup = group != null;


        if (hasGroup) {
            replyText = group.responseMessage(textContent, userId);
        } else if (!hasGroup && textContent.equals("start zonk")) {
            User user = new User(userId);
            group = new GroupZonkbot(groupId, user);
            groupZonkbots.add(group);
            replyText = group.responseMessage(textContent, userId);
        }

        return replyText;



    }

    public void replyWithRandomQuestion(String replyToken) {
        ArrayList<Question> questions = readFromJSON();
        Random rand = new Random();
        int questionIndex = rand.nextInt(questions.size());
        Question question = questions.get(questionIndex);
        List<String> answers = question.getAnswers();
        List<CarouselColumn> columns = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            List<Action> actions = new ArrayList<>();
            actions.add(new MessageAction("Select",
                    String.format("/Q%sA%s",
                            questionIndex + 1, i + 1)));
            columns.add(new CarouselColumn(null,
                    question.getQuestion(), answers.get(i), actions));
        }
        Template carouselTemplate = new CarouselTemplate(columns);
        TemplateMessage templateMessage = new TemplateMessage("Answers", carouselTemplate);
        this.reply(replyToken, templateMessage);
    }


    private GroupZonkbot getGroup(String groupId) {
        if(!groupZonkbots.isEmpty()) {
            for (GroupZonkbot group : groupZonkbots) {
                if (group.getGroupId().equals(groupId))
                    return group;
            }
        }
        return null;
    }

    //CHOOSE QUESTIONS WITH CAROUSEL
    private void chooseQuestion(String replyToken) {
        List<Question> questions = readFromJSON();
        List<CarouselColumn> columns = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            List<Action> actions = new ArrayList<>();
            actions.add(new MessageAction("Select",
                    String.format("/Question: %s", i+1)));
            columns.add(new CarouselColumn(null,
                    "Choose Question", questions.get(i).getQuestion(), actions));
        }
        Template carouselTemplate = new CarouselTemplate(columns);
        TemplateMessage templateMessage = new TemplateMessage("Questions", carouselTemplate);
        reply(replyToken, templateMessage);
    }

    //CHOOSE CORRECT ANSWER WITH CAROUSEL
    public void chooseCorrectAnswerWithCarousel(String replyToken) {
        Question question = zonkbot.getPresentQuestion();
        List<String> answers = question.getAnswers();
        List<CarouselColumn> columns = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            List<Action> actions = new ArrayList<>();
            actions.add(new MessageAction("Select",
                    String.format("/Correct answer: %s", i+1)));
            columns.add(new CarouselColumn(null,
                    "Choose The Correct Answer", answers.get(i), actions));
        }
        Template carouselTemplate = new CarouselTemplate(columns);
        TemplateMessage templateMessage = new TemplateMessage("Answers", carouselTemplate);
        this.reply(replyToken, templateMessage);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }


    public void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    public void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void replyText(@NonNull String replyToken, @NonNull String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken must not be empty");
        }
        if (message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "……";
        }
        reply(replyToken, new TextMessage(message));
    }

    public static void writeToJson(Question question) {
        Gson gson = new Gson();
        ArrayList<Question> questions = readFromJSON();
        questions.add(question);
        try (FileWriter writer = new FileWriter("file.json")) {
            gson.toJson(questions,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Question> readFromJSON() {
        Gson gson = new Gson();
        Question[] result = null;

        try (Reader reader = new FileReader("src/file.json")) {
            result = gson.fromJson(reader, Question[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Question> resultList = new ArrayList<Question>(Arrays.asList(result));
        return resultList;
    }

    public String getProfileName (String userId) throws ExecutionException, InterruptedException {
        return lineMessagingClient.getProfile(userId).get().getDisplayName();

    }

    }
