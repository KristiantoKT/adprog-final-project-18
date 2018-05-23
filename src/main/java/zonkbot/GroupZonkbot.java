package zonkbot;

import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import zonkbot.controller.ZonkbotController;


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
        if (chance == 0) {
            replyText =  isAllChanceIsZero();
        } else if (textContent.equals("start zonk")) {
            replyText = startZonk();
        } else if (textContent.length() == 5 && textContent.substring(0,2).equals("/Q")
                && textContent.substring(3,4).equals("A")) {
            replyText = questionAndAnswer(textContent, userId, replyText, user);
        } else if (textContent.length() == 9 && textContent.equals("stop zonk")) {
            replyText = stopZonk();
        }
        return replyText;
    }

    @NotNull
    public String stopZonk() {
        String replyText;
        isZonk = false;
        replyText =  "show leaderboard";
        return replyText;
    }

    public String questionAndAnswer(String textContent, String userId,
                                    String replyText, User user) {
        int questionIndex = Integer.parseInt(textContent.substring(2, 3)) - 1;
        int answerIndex = Integer.parseInt(textContent.substring(4, 5)) - 1;
        ArrayList<Question> questions = ZonkbotController.readFromJson();
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
    public String startZonk() {
        ArrayList<Question> questions = ZonkbotController.readFromJson();
        if (questions.isEmpty()) {
            return "Sorry but no question available";
        }
        isZonk = true;
        return "/Random question";
    }

    @NotNull
    public String isAllChanceIsZero() {
        if (isAllUserChanceIsZero()) {
            resetChance();
            return "/Random question";
        }
        return "";
    }

    public User getUser(String userId) {
        for (User user: users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private boolean isAllUserChanceIsZero() {
        for (User user: users) {
            if (user.getTakenChance() > 0) {
                return false;
            }
        }
        return true;
    }

}
