package remoteMethodInvocation;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
public interface InterfaceRemota extends Remote{//in this class writing all metodos remotos
    public LinkedList<String> haveIFile(String nameFile) throws RemoteException;//method remote this receive name file to search and if found file return nameFile,lengthFile,mdsFile else return null 
}
