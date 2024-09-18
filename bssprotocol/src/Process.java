import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;


public class Process  extends UnicastRemoteObject  implements ProcessInterface {

    private int[] localClock;
    private int pID;
    private MessageBuffer messageBuffer;
    private BSSManagerInterface BSSManager; 
    

    public Process(int n, int id, BSSManagerInterface bss) throws RemoteException {
        super();
        localClock = new int[n];
        pID = id;
        BSSManager = bss;
        messageBuffer = new MessageBuffer();
        
    }


    public void send(String messageDataString) throws RemoteException {
        localClock[pID]++;
        int[] messageClock = Arrays.copyOf(localClock, localClock.length);
        Message newMessage = new Message(messageDataString, messageClock, pID);
        System.out.println();
        System.out.println("Sending message " + newMessage + " with clock " + Arrays.toString(messageClock) + " to BSSManager");
        System.out.println();
        BSSManager.send(newMessage);
    }

    public void deliver(Message message) throws RemoteException {
        int senderID = message.getSender();
        messageBuffer.addMessage(message);
        handleMessageOrder(senderID);
    }

    public int getPID() throws RemoteException{
        return pID;
    }

    private void handleMessageOrder(int senderID) throws RemoteException{

            while((messageBuffer.isEmpty() != true) && (messageBuffer.peekNextMessage().getClock()[senderID] == localClock[senderID] + 1)){
            Message message = messageBuffer.getNextMessage();
            localClock[senderID]++;
            System.out.println("p" + pID + " has received message " + message);
            System.out.println("p" + pID + "'s vector clock is now " + Arrays.toString(localClock));
            System.out.println();
        }
    }
}
