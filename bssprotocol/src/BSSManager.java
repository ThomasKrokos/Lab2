import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.rmi.Naming;

public class BSSManager extends UnicastRemoteObject implements BSSManagerInterface {

    private int tokenHolder;
    private MessageBuffer[] messageBuffers;


    public BSSManager(int n) throws RemoteException{
        super();
        tokenHolder = -1;
        messageBuffers = new MessageBuffer[n];
        for (int i = 0; i < n; i++) {
            messageBuffers[i] = new MessageBuffer();
        }
    }

    
    public void send(Message message) throws RemoteException{
        int senderID = message.getSender();
        messageBuffers[senderID].addMessage(message);
        if(tokenHolder == -1){
            tokenHolder = senderID;
        }
        if(senderID == tokenHolder){
            broadcastMessages();
        }
    }

    public void releaseToken() throws RemoteException{
        if (messageBuffers.length > 0) {
            System.out.println("Transferring token from " + tokenHolder + " to " + (tokenHolder + 1)%messageBuffers.length );
            tokenHolder = (tokenHolder + 1) % messageBuffers.length;
        }
        broadcastMessages();
    }

    private void broadcastMessages() throws RemoteException {
        if (tokenHolder >= 0 && tokenHolder < messageBuffers.length) {
            MessageBuffer buffer = messageBuffers[tokenHolder];
            while (!buffer.isEmpty()) {
                Message message = buffer.getNextMessage();
                for (int i = 0; i < messageBuffers.length; i++) {
                    if (i != tokenHolder){
                        try {
                            ProcessInterface targetProcess = (ProcessInterface) Naming.lookup("rmi://localhost/ProcessService" + i);
                            System.out.println("Broadcasting message " + message + " to p" + targetProcess.getPID());
                            System.out.println("Broadcasted message's clock is "+Arrays.toString(message.getClock()));

                            targetProcess.deliver(message);

                        } catch (Exception e) {
                            System.err.println("Exception: " + e.toString());
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }


}