package zonkbot;

import org.jetbrains.annotations.NotNull;
import zonkbot.controller.ZonkbotController;

import java.util.List;

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
        //ADD_QUESTION
        if (textContent.equals("/add_question") && !addQuestionSection) {
            replyText = gettingToAddQuestionSection();
        }
        //ADD_QUESTION_SECTION
        else if (addQuestionSection) {
            replyText = addQuestionSection(textContent);
        }
        //CHANGE_ANSWER
        else if (textContent.equals("/change_answer")) {
            replyText = gettingToChangeAnswerSection();
        }
        //CHANGE_ANSWER_SECTION
        else if (textContent.length() > 10
                && textContent.substring(0,9).equals("/Question")) {
            replyText = changeAnswerSection(textContent);
        }
        //CHOOSE CORRECT ANSWER
        else if (textContent.length() >= 15
                && textContent.substring(0,15).equals("/Correct answer")){
            replyText = chooseCorrectAnswer(textContent);
        }
        //ECHO
        else if (textContent.length() > 5
                && textContent.substring(0,5).equals("/echo")) {
            replyText = echo(textContent);
        }
        //OTHERS
        else {
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
        if(question != null) {
            question.setCorrectAnswer(correctAnswerIndex);
            replyText = question.toString();
        }
        question = null;
        return replyText;
    }

    @NotNull
    private String changeAnswerSection(String textContent) {
        String replyText;
        int questionIndex = Integer.parseInt(textContent.substring(11)) - 1;
        List<Question> questions = ZonkbotController.readFromJSON();
        question = questions.get(questionIndex);
        replyText = "/Choose correct answer";
        return replyText;
    }

    @NotNull
    private String gettingToChangeAnswerSection() {
        String replyText;List<Question> questions = ZonkbotController.readFromJSON();
        if(questions.isEmpty())
            replyText = "There is no question";
        else
            replyText = "/Choose question";
        return replyText;
    }

    @NotNull
    private String gettingToAddQuestionSection() {
        String replyText;
        replyText = "Please input your question";
        addQuestionSection = true;
        return replyText;
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
            ZonkbotController.writeToJson(question);
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
        List<Question> questions = ZonkbotController.readFromJSON();
        for (Question element: questions) {
            result += element.toString();
        }
        return result;
    }
}
