package zonkbot.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.Template;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import zonkbot.Question;
import zonkbot.Zonkbot;

@LineMessageHandler
public class ZonkbotController {

    private static final Logger LOGGER = Logger.getLogger(ZonkbotController.class.getName());
    private Zonkbot zonkbot = null;
    private Question question = null;


    @Autowired
    private LineMessagingClient lineMessagingClient;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent messageContent = event.getMessage();
        String textContent = messageContent.getText();
        String replyText = responseMessage(textContent, event.getReplyToken());
        if(!replyText.isEmpty())
            replyText(event.getReplyToken(),replyText);
    }

    public String responseMessage(String textContent, String replyToken) {
        String replyText = "";
        //ACTIVATE ZONKBOT
        if (textContent.equals("/zonkbot") && zonkbot == null) {
            replyText = activateZonkbot();
        }
        //ZONKBOT ALREADY ACTIVATED
        else if (textContent.equals("/zonkbot") && zonkbot != null) {
            replyText = "zonkbot has already activated";
        }
        //DEACTIVATE ZONKBOT
        else if (textContent.equals("/deactivate") && zonkbot != null) {
            deactivateZonkbot();
            replyText = "zonkbot deactivated, all question will be deleted";
        }
        //ADD_QUESTION
        else if (textContent.equals("/add_question") && zonkbot != null
                && !zonkbot.isAdd_question_section()) {
            replyText = "Please input your question";
            zonkbot.setAdd_question_section(true);
        }
        //ADD_QUESTION_SECTION
        else if (zonkbot != null && zonkbot.isAdd_question_section()) {
            replyText = add_question(textContent, replyToken);
        }
        //CHANGE_ANSWER
        else if (zonkbot != null && textContent.equals("/change_answer")) {
            if(zonkbot.getQuestions() == null)
                replyText = "There is no question";
            else
                chooseQuestion(replyToken);
        }
        //CHANGE_ANSWER_SECTION
        else if (zonkbot != null && textContent.length() > 10
                && textContent.substring(0,9).equals("/Question")) {
            int questionIndex = Integer.parseInt(textContent.substring(11));
            question = zonkbot.chooseQuestion(questionIndex);
            chooseCorrectAnswer(question, replyToken);
        }
        //CHOOSE CORRECT ANSWER
        else if (textContent.length() >= 15
                && textContent.substring(0,15).equals("/Correct answer")){
            String correctAnswer = textContent.substring(17);
            question.setCorrectAnswer(correctAnswer);
            replyText = question.toString();
            question = null;
        }
        //ECHO
        else if (textContent.length() > 5
                && textContent.substring(0,5).equals("/echo")) {
            replyText =  textContent.replace("/echo","");
            replyText = replyText.substring(1);
        }
        //ZONKBOT NOT AVAILABLE
        else if (zonkbot == null) {
            replyText = "zonkbot are not available."
                    + "To activate zonkbot please type \"/zonkbot\"";
        }
        //OTHERS
        else {
            replyText = textContent + " is not a command";
        }
        return replyText;
    }

    private void deactivateZonkbot() {
        zonkbot = null;
        question = null;
    }

    @NotNull
    private String activateZonkbot() {
        String replyText;
        zonkbot = new Zonkbot();
        replyText = "zonkbot activated!";
        return replyText;
    }


    public String add_question(String textContent, String replyToken) {
        String result = "";
        int answerNumber = zonkbot.getAnswer_number();
        if (answerNumber == 0) {
            question = new Question(textContent);
            zonkbot.setAnswer_number(++answerNumber);
            result = "Answer 1:";
        } else if (answerNumber < 4) {
            question.addAnswer(textContent);
            zonkbot.setAnswer_number(++answerNumber);
            result = "Answer " + answerNumber + ":";
        } else if (answerNumber >= 4) {
            question.addAnswer(textContent);
            zonkbot.add_question(question);
            chooseCorrectAnswer(question, replyToken);
            addQuestionReset();
        }
        return result;
    }

    private void addQuestionReset() {
        zonkbot.setAnswer_number(0);
        zonkbot.setAdd_question_section(false);
    }

    private void chooseCorrectAnswer(Question question, String replyToken) {
        List<String> answers = question.getAnswers();
        List<CarouselColumn> columns = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            List<Action> actions = new ArrayList<>();
            actions.add(new MessageAction("Select",
                    String.format("/Correct answer: %s", i)));
            columns.add(new CarouselColumn(null,
                    "Choose The Correct Answer", answers.get(i), actions));
        }
        Template carouselTemplate = new CarouselTemplate(columns);
        TemplateMessage templateMessage = new TemplateMessage("Answers", carouselTemplate);
        this.reply(replyToken, templateMessage);
    }

    private void chooseQuestion(String replyToken) {
        List<Question> questions = zonkbot.getQuestions();
        List<CarouselColumn> columns = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            List<Action> actions = new ArrayList<>();
            actions.add(new MessageAction("Select",
                    String.format("/Question: %s", i)));
            columns.add(new CarouselColumn(null,
                    "Choose Question", questions.get(i).getQuestion(), actions));
        }
        Template carouselTemplate = new CarouselTemplate(columns);
        TemplateMessage templateMessage = new TemplateMessage("Questions", carouselTemplate);
        this.reply(replyToken, templateMessage);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
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

    private void replyText(@NonNull String replyToken, @NonNull String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken must not be empty");
        }
        if (message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "……";
        }
        this.reply(replyToken, new TextMessage(message));
    }
}
