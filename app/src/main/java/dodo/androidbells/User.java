package dodo.androidbells;

/**
 * Created by DoDo on 4/24/18.
 */

public class User {
    private int score;
    private String name;

    public User(String n) {
        this.score = 0;
        this.name = n;
    }

    public int getScore()   {
        return this.score;
    }

    public String getName()    {
        return this.name;
    }

    public void incrementScore()    {
        this.score++;
    }
}
