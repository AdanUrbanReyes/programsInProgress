package remoteMethodInvocation;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
public class ServerRMI {
    public static void setCodeBase(Class <?> c){
        String path="",route=c.getProtectionDomain().getCodeSource().getLocation().toString();
        path=System.getProperty("java.rmi.server.codebase");
        if(path!=null&&!path.isEmpty())
            route=path+" "+route;
        System.setProperty("java.rmi.server.codebase",route);
    }
    public static void startRmiregister(){
        try{
            java.rmi.registry.LocateRegistry.createRegistry(1099);//puerto default rmiregistry
            System.out.print("\nrmiregister ready");
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"error starting rmiregister "+e.toString());
        }
    }
    public ServerRMI(String fileShared){
        startRmiregister();
        setCodeBase(InterfaceRemota.class);
        try{
            InterfaceRemota ir=new RemoteObject(fileShared);
            Registry registry=LocateRegistry.getRegistry();
            registry.bind("haveIFile", ir);
            System.out.print("\nserver rmi ready waiting for clients...");
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"error in constructor server rmi "+e.toString());
        }
    }
}
