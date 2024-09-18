import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class Server {
    
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);

            System.out.println();
            System.out.println();
            System.out.println("RMI registry started.");

            int n = 3;
            BSSManagerInterface globalBSSManager = new BSSManager(n);
            Naming.rebind("BSSManagerService", globalBSSManager);
            
            ProcessInterface[] processes = new Process[n];
            for(int i = 0; i < n; i++){
                processes[i] = new Process(n, i, globalBSSManager);
                Naming.rebind("ProcessService"+i, processes[i]);
                System.out.println("Registered ProcessService" + i);

            }

            System.out.println("Remote objects registered in the RMI registry.");
            System.out.println();
            System.out.println();


        } catch (Exception e) {
            System.err.println("Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
