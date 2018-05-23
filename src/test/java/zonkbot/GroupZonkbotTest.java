package zonkbot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupZonkbotTest {

    private User user = new User("userId");
    private GroupZonkbot group = new GroupZonkbot("id", user);

    @Test
    void getGroupIdTest() {
        assertEquals(group.getGroupId(), group.getGroupId());
    }

    @Test
    void getUsersTest() {
        assertEquals(group.getUsers(), group.getUsers());
    }

    @Test
    void responseMessageTest() {
        assertEquals("/Random question",
                group.responseMessage("test","userId"));
    }

    @Test
    void getUserTest() {
        assertEquals(group.getUser("userId"),
                group.getUser("userId"));
    }

    @Test
    void addUserTest() {
        group.addUser(user);
    }

    @Test
    void resetChanceTest() {
        group.resetChance();
    }

    @Test
    void stopZonkTest() {
        assertEquals("show leaderboard", group.stopZonk());
    }

    @Test
    void isZeroTest() {
        group.isAllChanceIsZero();
    }

    @Test
    void QandATest() {
        group.QandA("/Q2A1", "userId", "memeng", user);
    }

    @Test
    void startZonkTest() {
        assertEquals(group.startZonk(), group.startZonk());
    }

}