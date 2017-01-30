package cs310;

// One user's record on one line: how many times
// this user has been seen on this line
public class Usage{
    private String username;
    private int count;

    public Usage(String x){
        username = x;
        count = 1;
    }

    // The following methods are getters and setters for
    // private fields username and count. Nothing more,
    // nothing less. (We could have an increment method.)

    public void setUser(String x){username = x;}

    public void setCount(int x){count = x;}

    public String getUser(){return username;}

    public int getCount(){return count;}
}
