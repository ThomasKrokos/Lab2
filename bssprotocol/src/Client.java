import java.rmi.Naming;
import java.rmi.RemoteException;



public class Client {        
    public static void main(String[] args) {
        try {
            System.out.println("Looking up remote objects as a client...");


            int n = 3;

            ProcessInterface processesClient[] = new ProcessInterface[n];

            for(int i = 0; i < n; i++){             
                processesClient[i] = (ProcessInterface) Naming.lookup("rmi://localhost/ProcessService" + i);
                System.out.println("Looked up ProcessService" + i);
        }

            BSSManagerInterface globalBSSManager = (BSSManagerInterface) Naming.lookup("rmi://localhost/BSSManagerService");
            System.out.println("Looked up globalBSSManager");

            simulate(processesClient, globalBSSManager);
        } catch (Exception e) {
            System.err.println("Exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void simulate(ProcessInterface[] processesClient, BSSManagerInterface globalBSSManager) throws RemoteException{
     
        

        System.out.println("Running BSS Protocal simulation ...");
        processesClient[0].send("Hi everyone- p0"); 
        //with this first message since no process has the token p0 will get it
        processesClient[0].send("Hi again everyone - p0");
        processesClient[1].send("global message from p1");
        processesClient[2].send("p2 message");
        globalBSSManager.releaseToken(); // this passes the token from p0 to p1
        processesClient[2].send("p2 message part 2");
        processesClient[1].send("a second message from p1");
        processesClient[1].send("a third global message from p1");
        processesClient[0].send("another message from p0 - p0");
        globalBSSManager.releaseToken(); // this passes the token from p1 to p2
        globalBSSManager.releaseToken(); // this passes the token back to p0 from p2
        //by now all the buffers in the BSSManager are cleared


        System.out.println("Ending BSS Protocol simulation ...");

    }

}
        


