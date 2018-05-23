package zonkbot;

public class User implements Comparable<User>{
    private String userId;
    private int score;
    private int takenChance;

    public User(String userId) {
        this.userId = userId;
        score = 0;
        takenChance = 0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTakenChance(int takenChance) {
        this.takenChance = takenChance;
    }

    public String getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public int getTakenChance() {
        return takenChance;
    }


    @Override
    public int compareTo(User other) {
        return Integer.compare(score, other.score);
    }
}
