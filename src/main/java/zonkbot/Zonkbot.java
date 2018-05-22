package zonkbot;

import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.Template;
import zonkbot.controller.ZonkbotController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Zonkbot {
    ArrayList<Question> questions;
    private Question question;
    private boolean add_question_section;
    private boolean change_answer_section;
    private int giveAnswerCount;

    public Zonkbot() {
        questions = new ArrayList<Question>();
        add_question_section = false;
        change_answer_section = false;
        giveAnswerCount = 0;
    }

    public int getQuestionIndex(String question) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestion().equals(question)) {
                return i;
            }
        }
        return -1;
    }

    public Question getPresentQuestion() {
        return question;
    }

    public String responseMessage(String textContent, String replyToken, ZonkbotController zonkbotController) throws IOException {
        String replyText = "";
        //ADD_QUESTION
        if (textContent.equals("/add_question") && !add_question_section) {
            replyText = "Please input your question";
            add_question_section = true;
        }
        //ADD_QUESTION_SECTION
        else if (add_question_section) {
            replyText = add_question(textContent, replyToken);
        }
//        //CHANGE_ANSWER
//        else if (textContent.equals("/change_answer")) {
//            if(questions.isEmpty())
//                replyText = "There is no question";
//            else
//                zonkbotController.chooseQuestion(replyToken);
//        }
//        //CHANGE_ANSWER_SECTION
//        else if (this != null && textContent.length() > 10
//                && textContent.substring(0,9).equals("/Question")) {
//            int questionIndex = Integer.parseInt(textContent.substring(11));
//            zonkbotController.question = chooseQuestion(questionIndex);
//            zonkbotController.chooseCorrectAnswer(zonkbotController.question, replyToken);
//        }
        //CHOOSE CORRECT ANSWER
        else if (textContent.length() >= 15
                && textContent.substring(0,15).equals("/Correct answer")){
            int correctAnswerIndex = Integer.parseInt(textContent.substring(17));
            question.setCorrectAnswer(correctAnswerIndex);
            replyText = question.toString();
            question = null;
        }
        //ECHO
        else if (textContent.length() > 5
                && textContent.substring(0,5).equals("/echo")) {
            replyText =  textContent.replace("/echo","");
            replyText = replyText.substring(1);
        }
        //OTHERS
        else {
            replyText = textContent + " is not a command";
        }
        return replyText;
    }

    public String add_question(String textContent, String replyToken) throws IOException {
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
            result = "Carousel";
            addQuestionReset();
            ZonkbotController.writeToJson(question);
        }
        return result;
    }

    private void addQuestionReset() {
        giveAnswerCount = 0;
        add_question_section = false;
    }

    

    @Override
    public String toString() {
        String result = "";
        for (Question element: questions) {
            result += element.toString();
        }
        return result;
    }
}
