import java.io.Serializable;
import java.util.PriorityQueue;

public class MessageBuffer implements Serializable {
    private static final long serialVersionUID = 1L;

    private PriorityQueue<Message> messageQueue;

    public MessageBuffer() {
        this.messageQueue = new PriorityQueue<>();
    }

    public void addMessage(Message message) {
        messageQueue.add(message);
    }

    public Message getNextMessage() {
        return messageQueue.poll();
    }

    public boolean isEmpty() {
        return messageQueue.isEmpty();
    }

    public Message peekNextMessage() {
        return messageQueue.peek();
    }
}
