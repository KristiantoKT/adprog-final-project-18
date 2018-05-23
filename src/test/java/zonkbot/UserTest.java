package zonkbot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserTest {

    private User user = new User("id");

    @Test
    void getUserId() {
        assertEquals("id", user.getUserId());
    }

    @Test
    void getScore() {
        assertEquals(0, user.getScore());
    }

    @Test
    void getTakenChance() {
        assertEquals(0, user.getTakenChance());
    }

    @Test
    void setScore() {
        user.setScore(2);
        assertEquals(2, user.getScore());
    }

    @Test
    void setTakenChance() {
        user.setTakenChance(2);
        assertEquals(2, user.getTakenChance());
    }

    @Test
    void compareTo() {
        User user2 = new User("id2");
        assertEquals(0, user.compareTo(user2));
    }

}