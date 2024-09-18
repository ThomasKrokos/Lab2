import java.io.Serializable;
import java.util.Arrays;

public class Message implements Comparable<Message>, Serializable {
    private static final long serialVersionUID = 1L;

    private String message;
    private int[] clock;
    private int pID;

    public Message(String m, int[] clock, int pID){
        this.message = m;
        this.clock = clock;
        this.pID = pID;
    }

    public int[] getClock() {
        return clock;
    }

    public String getMessage(){
        return message;
    }
    public int getSender(){
        return pID;
    }

    public int compareTo(Message other) {
        int[] otherClock = other.getClock();
        return Arrays.compare(this.clock, otherClock);
    }

    public String toString() {
        return "\"" + message + "\" from p" + pID;
    }
}
