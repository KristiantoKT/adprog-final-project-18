package zonkbot;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import zonkbot.controller.ZonkbotController;

public class Zonkbot {
    private Question question;
    private boolean addQuestionSection;
    private int giveAnswerCount;

    public Zonkbot() {
        addQuestionSection = false;
        giveAnswerCount = 0;
        question = null;
    }

    public Question getPresentQuestion() {
        return question;
    }

    public String responseMessage(String textContent) {
        String replyText;
        if (textContent.equals("/add_question") && !addQuestionSection) {
            replyText = gettingToAddQuestionSection();
        } else if (addQuestionSection) {
            replyText = addQuestionSection(textContent);
        } else if (textContent.equals("/change_answer")) {
            replyText = gettingToChangeAnswerSection();
        } else if (textContent.length() > 10
                && textContent.substring(0,9).equals("/Question")) {
            replyText = changeAnswerSection(textContent);
        } else if (textContent.length() >= 15
                && textContent.substring(0,15).equals("/Correct answer")) {
            replyText = chooseCorrectAnswer(textContent);
        } else if (textContent.length() > 5
                && textContent.substring(0,5).equals("/echo")) {
            replyText = echo(textContent);
        } else {
            replyText = textContent + " is not a command";
        }
        return replyText;
    }

    @NotNull
    private String echo(String textContent) {
        String replyText;
        replyText =  textContent.replace("/echo","");
        replyText = replyText.substring(1);
        return replyText;
    }

    private String chooseCorrectAnswer(String textContent) {
        String replyText = "";
        int correctAnswerIndex = Integer.parseInt(textContent.substring(17)) - 1;
        if (question != null) {
            question.setCorrectAnswer(correctAnswerIndex);
            replyText = question.toString();
        }
        ZonkbotController.writeToJson(question);
        question = null;
        return replyText;
    }

    @NotNull
    private String changeAnswerSection(String textContent) {
        String replyText;
        int questionIndex = Integer.parseInt(textContent.substring(11)) - 1;
        List<Question> questions = ZonkbotController.readFromJson();
        question = questions.get(questionIndex);
        replyText = "/Choose correct answer";
        return replyText;
    }

    @NotNull
    private String gettingToChangeAnswerSection() {
        String replyText;
        List<Question> questions = ZonkbotController.readFromJson();
        if (questions.isEmpty()) {
            replyText = "There is no question";
        } else {
            replyText = "/Choose question";
        }
        return replyText;
    }

    @NotNull
    private String gettingToAddQuestionSection() {
        addQuestionSection = true;
        return "Please input your question";
    }

    public String addQuestionSection(String textContent) {
        String result = "";
        if (giveAnswerCount == 0) {
            question = new Question(textContent);
            giveAnswerCount++;
            result = "Answer 1:";
        } else if (giveAnswerCount < 4) {
            question.addAnswer(textContent);
            giveAnswerCount++;
            result = "Answer " + giveAnswerCount + ":";
        } else if (giveAnswerCount >= 4) {
            question.addAnswer(textContent);
            result = "/Choose correct answer";
            addQuestionReset();
        }
        return result;
    }

    private void addQuestionReset() {
        giveAnswerCount = 0;
        addQuestionSection = false;
    }

    @Override
    public String toString() {
        String result = "";
        List<Question> questions = ZonkbotController.readFromJson();
        for (Question element: questions) {
            result += element.toString();
        }
        return result;
    }
}
