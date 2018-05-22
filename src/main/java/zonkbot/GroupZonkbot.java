package zonkbot;

import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.Template;
import com.linecorp.bot.model.profile.UserProfileResponse;
import zonkbot.controller.ZonkbotController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GroupZonkbot {
    ArrayList<User> users;
    String groupId;
    Question currentQuestion;
    boolean isZonk;

    ZonkbotController zonkbotController;


    public GroupZonkbot(String groupId, User user) {
        users = new ArrayList<User>();
        this.groupId = groupId;
        isZonk = false;
        addUser(user);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void resetChance() {
        for (User user: users) {
            user.setTakenChance(3);
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String responseMessage(String textContent, String userId, String replyToken) throws IOException {
        String replyText = "";

        User user = getUser(userId);
        int chance = user.getTakenChance();
        //ALL CHANCE IS 0
        if (chance == 0) {
            if (isAllUserChanceIsZero()) {
                resetChance();
                return "/Random question";
            }
            return "";
        }
        //INITIAL START ZONK
        else if (textContent.equals("start zonk")) {
            ArrayList<Question> questions = ZonkbotController.readFromJSON();
            if(questions.isEmpty())
                return "Sorry but no question available";
            isZonk = true;
            return "/Random question";
        }
        //ANSWER QUESTION
        else if (textContent.length() == 5 && textContent.substring(0,2).equals("/Q")
                && textContent.substring(3,4).equals("A")) {
            int questionIndex = Integer.parseInt(textContent.substring(2, 3)) - 1;
            int answerIndex = Integer.parseInt(textContent.substring(4, 5)) - 1 ;
            ArrayList<Question> questions = ZonkbotController.readFromJSON();
            Question question = questions.get(questionIndex);
            boolean answerCorrect = answerIndex == question.getCorrectAnswerIndex();
            if (answerCorrect && user.getTakenChance() > 0) {
                user.setScore(user.getScore() + 1);
                return responseMessage("start zonk", userId, replyToken);
            } else if (!answerCorrect && user.getTakenChance() > 0) {
                user.setTakenChance(user.getTakenChance() - 1);
                return "";
            }
        }
        //ALL ID
        else if (textContent.length() == 4 && textContent.equals("/All")) {
            return getAllUserId();
        }
        //STOP ZONK
        else if (textContent.length() == 9 && textContent.equals("stop zonk")) {
            isZonk = false;
            return "show leaderboard";
        }


        return replyText;
    }


    public User getUser(String userId) {
        for (User user: users) {
            if (user.getUserId().equals(userId))
                return user;
        }
        return null;
    }


    private boolean isAllUserChanceIsZero() {
        for (User user: users) {
            if (user.getTakenChance() > 0)
                return false;
        }
        return true;
    }

    public String getAllUserId() {
        String result = "";
        for (User user: users) {
            result = user.getUserId() + " chance:" + user.getTakenChance() + " score: " + user.getScore() + " \n\n";
        }
        return result;
    }
}
