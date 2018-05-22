package zonkbot;

import org.jetbrains.annotations.NotNull;
import zonkbot.controller.ZonkbotController;

import java.util.ArrayList;

public class GroupZonkbot {
    ArrayList<User> users;
    String groupId;
    boolean isZonk;


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

    public String responseMessage(String textContent, String userId) {
        String replyText = "";

        User user = getUser(userId);
        int chance = user.getTakenChance();
        //ALL CHANCE IS 0
        if (chance == 0) {
            replyText =  isAllChanceIsZero();
        }
        //INITIAL START ZONK
        else if (textContent.equals("start zonk")) {
            replyText = startZonk();
        }
        //ANSWER QUESTION
        else if (textContent.length() == 5 && textContent.substring(0,2).equals("/Q")
                && textContent.substring(3,4).equals("A")) {
            replyText = QandA(textContent, userId, replyText, user);
        }
        //STOP ZONK
        else if (textContent.length() == 9 && textContent.equals("stop zonk")) {
            replyText = stopZonk();
        }
        return replyText;
    }

    @NotNull
    private String stopZonk() {
        String replyText;
        isZonk = false;
        replyText =  "show leaderboard";
        return replyText;
    }

    private String QandA(String textContent, String userId, String replyText, User user) {
        int questionIndex = Integer.parseInt(textContent.substring(2, 3)) - 1;
        int answerIndex = Integer.parseInt(textContent.substring(4, 5)) - 1 ;
        ArrayList<Question> questions = ZonkbotController.readFromJSON();
        Question question = questions.get(questionIndex);
        boolean answerCorrect = answerIndex == question.getCorrectAnswerIndex();
        if (answerCorrect && user.getTakenChance() > 0) {
            user.setScore(user.getScore() + 1);
            replyText =  responseMessage("start zonk", userId);
        } else if (!answerCorrect && user.getTakenChance() > 0) {
            user.setTakenChance(user.getTakenChance() - 1);
            replyText = "";
        }
        return replyText;
    }

    @NotNull
    private String startZonk() {
        ArrayList<Question> questions = ZonkbotController.readFromJSON();
        if(questions.isEmpty())
            return "Sorry but no question available";
        isZonk = true;
        return "/Random question";
    }

    @NotNull
    private String isAllChanceIsZero() {
        if (isAllUserChanceIsZero()) {
            resetChance();
            return "/Random question";
        }
        return "";
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

}