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

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import zonkbot.GroupZonkbot;
import zonkbot.Question;
import zonkbot.User;
import zonkbot.Zonkbot;

@LineMessageHandler
public class ZonkbotController {
    private static final Logger LOGGER = Logger.getLogger(ZonkbotController.class.getName());

    public Zonkbot zonkbot = new Zonkbot();
    private List<GroupZonkbot> groupZonkbots = new ArrayList<>();

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @EventMapping
    void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event)
            throws ExecutionException,
            InterruptedException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent messageContent = event.getMessage();
        String textContent = messageContent.getText();
        String replyToken = event.getReplyToken();
        if (event.getSource() instanceof UserSource) {
            responseMessageForPersonal(event.getSource().getUserId(),
                    textContent, replyToken);
        } else if (event.getSource() instanceof GroupSource) {
            groupResponseMessage(event, replyToken);
        }
    }

    public void groupResponseMessage(MessageEvent<TextMessageContent> event, String replyToken)
            throws ExecutionException, InterruptedException {
        String replyText;
        replyText = responseMessageForGroup(event);

        if (replyText.equals("/Random question")) {
            replyWithRandomQuestion(replyToken);
        } else if (replyText.equals("show leaderboard")) {
            String groupId = ((GroupSource) event.getSource()).getGroupId();
            replyText = showLeaderboard(groupId);
            groupZonkbots.remove(getGroup(groupId));
            this.replyText(replyToken, replyText);
        } else if (!replyText.isEmpty()) {
            this.replyText(replyToken, replyText);
        }
    }

    public void responseMessageForPersonal(String userId,
                                            String textContent, String replyToken)
            throws ExecutionException, InterruptedException {
        String replyText;
        replyText = zonkbot.responseMessage(textContent);
        if (replyText.equals("/Choose correct answer")) {
            Question question = null;
            if (zonkbot.getPresentQuestion() != null) {
                question = zonkbot.getPresentQuestion();
            }
            chooseCorrectAnswerWithCarousel(replyToken, question);
        } else if (replyText.equals("/Choose question")) {
            chooseQuestion(replyToken);
        } else if (replyText.equals("/name")) {
            replyText = getProfileName(userId);
            this.replyText(replyToken, replyText);
        } else if (!replyText.isEmpty()) {
            this.replyText(replyToken, replyText);
        }
    }

    public String responseMessageForGroup(MessageEvent<TextMessageContent> event) {
        String replyText = "";
        String groupId = "";
        String userId = "";
        String textContent = "";
        if (event != null) {
            groupId = ((GroupSource) event.getSource()).getGroupId();
            userId = event.getSource().getUserId();
            textContent = event.getMessage().getText();
        }
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

    public String showLeaderboard(String groupId) throws ExecutionException, InterruptedException {
        GroupZonkbot group = getGroup(groupId);
        return getLeaderboard(group);
    }

    public String getLeaderboard(GroupZonkbot group) throws ExecutionException, InterruptedException {
        String reply = "";
        if (group != null) {
            List<User> users = group.getUsers();
            Collections.sort(users);
            for (User user : users) {
                String userId = user.getUserId();
                String name = getProfileName(userId);
                reply += name +": " + user.getScore() + "\n";
            }
        }
        return reply;
    }

    public GroupZonkbot getGroup(String groupId) {
        if (!groupZonkbots.isEmpty()) {
            for (GroupZonkbot group : groupZonkbots) {
                if (group.getGroupId().equals(groupId)) {
                    return group;
                }
            }
        }
        return null;
    }


    public void replyWithRandomQuestion(String replyToken) {
        ArrayList<Question> questions = readFromJson();
        Random rand = new Random();
        int questionIndex = rand.nextInt(questions.size());
        randomQuestionCarousel(replyToken, questions.get(questionIndex),
                questionIndex);
    }

    public void randomQuestionCarousel(String replyToken, Question question,
                                       int questionIndex) {
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

    //CHOOSE QUESTIONS WITH CAROUSEL
    public void chooseQuestion(String replyToken) {
        List<Question> questions = readFromJson();
        List<CarouselColumn> columns = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            List<Action> actions = new ArrayList<>();
            actions.add(new MessageAction("Select",
                    String.format("/Question: %s", i + 1)));
            columns.add(new CarouselColumn(null,
                    "Choose Question", questions.get(i).getQuestion(), actions));
        }
        Template carouselTemplate = new CarouselTemplate(columns);
        TemplateMessage templateMessage = new TemplateMessage("Questions", carouselTemplate);
        reply(replyToken, templateMessage);
    }

    //CHOOSE CORRECT ANSWER WITH CAROUSEL
    public void chooseCorrectAnswerWithCarousel(String replyToken, Question question) {
        List<String> answers = question.getAnswers();
        List<CarouselColumn> columns = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            List<Action> actions = new ArrayList<>();
            actions.add(new MessageAction("Select",
                    String.format("/Correct answer: %s", i + 1)));
            columns.add(new CarouselColumn(null,
                    question.getQuestion(), answers.get(i), actions));
        }
        Template carouselTemplate = new CarouselTemplate(columns);
        TemplateMessage templateMessage = new TemplateMessage("Answers", carouselTemplate);
        this.reply(replyToken, templateMessage);
    }

    public String getProfileName(String userId) throws ExecutionException, InterruptedException {
        return lineMessagingClient.getProfile(userId).get().getDisplayName();
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void replyText(@NonNull String replyToken, @NonNull String message) {
        reply(replyToken, new TextMessage(message));
    }

    public static void writeToJson(Question question) {
        Gson gson = new Gson();
        ArrayList<Question> questions = readFromJson();
        questions.add(question);
        try (FileWriter writer = new FileWriter("src/file.json")) {
            gson.toJson(questions,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Question> readFromJson() {
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
}
