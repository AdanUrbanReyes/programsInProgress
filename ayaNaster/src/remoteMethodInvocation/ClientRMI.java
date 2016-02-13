package remoteMethodInvocation;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
public class ClientRMI {
    private InterfaceRemota remote;
    public ClientRMI(String host){
        try{
            Registry registry=LocateRegistry.getRegistry(host);
            remote=(InterfaceRemota)registry.lookup("haveIFile");
        }catch(Exception e){System.out.print("\nerror in client rmi "+e.getMessage());}
    }
    public LinkedList<String> questionFile(String nameFile){
        try{
            return remote.haveIFile(nameFile);
        }catch(Exception e){System.out.print("\nerror in questionFile "+e.getMessage());}
        return null;
    }
}
