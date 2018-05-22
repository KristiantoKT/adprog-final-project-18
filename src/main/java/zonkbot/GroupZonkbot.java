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

    ZonkbotController zonkbotController;


    public GroupZonkbot(String groupId, User user) {
        users = new ArrayList<User>();
        this.groupId = groupId;
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

    public void resetScore() {
        for (User user: users) {
            user.setScore(0);
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String responseMessage(String textContent, UserSource userSource, String replyToken) throws IOException {
        String replyText = "";
        User user = getUser(userSource);
        replyText =  "nyampe sini";
//        int chance = user.getTakenChance();
//        if (chance == 0) {
//            if (isAllUserChanceIsZero()) {
//                resetChance();
//                responseMessage("start zonk", userSource, replyToken);
//            }
//            return "";
//        }
//        else if (textContent.equals("start zonk")) {
//            ArrayList<Question> questions = ZonkbotController.readFromJSON();
//            if(questions.isEmpty())
//                return "Sorry but no question available";
//             return "/Random question";
//        }
//        else if (textContent.length() == 4 && textContent.substring(0,2).equals("/Q")
//                && textContent.substring(3,4).equals("A")) {
//            int questionIndex = Integer.parseInt(textContent.substring(2, 3));
//            int answerIndex = Integer.parseInt(textContent.substring(4, 5));
//            ArrayList<Question> questions = ZonkbotController.readFromJSON();
//            Question question = questions.get(questionIndex);
//            boolean answerCorrect = answerIndex == question.getCorrectAnswerIndex();
//            if (answerCorrect && user.getTakenChance() > 0) {
//                user.setScore(user.getScore() + 1);
//                responseMessage("start zonk", userSource, replyToken);
//                return "";
//            } else if (!answerCorrect && user.getTakenChance() > 0) {
//                user.setTakenChance(user.getTakenChance() - 1);
//            }
//        }
//        else if (textContent.length() == 9 && textContent.equals("stop zonk") ) {
//            replyText = showLeaderBoard();
//            deactivate();
//        }


        return replyText;
    }

    public void deactivate() {
        resetChance();
        resetScore();
    }

//    public String showLeaderBoard() {
//        Collections.sort(users);
//        String reply ="";
//        for (User user: users) {
//            String userId = user.getUserSource().getUserId();
//            UserProfileResponse upr = zonkbotController.getProfile(userId);
//            reply += upr.getDisplayName() + ": " + user.getScore() + "\n";
//        }
//        return reply;
//    }


    public User getUser(UserSource userSource) {
        for (User user: users) {
            if (user.getUserSource() == userSource)
                return user;
        }
        return null;
    }

    public int getQuestionIndex (String question) throws IOException {
        ArrayList<Question> questions = ZonkbotController.readFromJSON();
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestion().equals(question))
                return i;
        }
        return -1;
    }

    private boolean isAllUserChanceIsZero() {
        for (User user: users) {
            if (user.getTakenChance() > 0)
                return false;
        }
        return true;
    }

    public Question getQuestion() throws IOException {
        ArrayList<Question> questions = ZonkbotController.readFromJSON();
        Random rand = new Random();
        if (questions.isEmpty())
            return null;
        int i = rand.nextInt(questions.size());
        return questions.get(i);
    }
}