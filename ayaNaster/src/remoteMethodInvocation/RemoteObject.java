package remoteMethodInvocation;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
public class RemoteObject extends UnicastRemoteObject implements InterfaceRemota{ 
    private String fileShared=null;//this is carpeta or folder that user like compartir here searching all files that other clientes question
    public RemoteObject(String fileShared) throws RemoteException{
        super();
        this.fileShared=fileShared;
    }
    public static byte[] createChecksum(String filename){
        try{
            InputStream fis =  new FileInputStream(filename);
            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            int numRead;
            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);
            fis.close();
            return complete.digest();
        }catch(Exception e){
            System.out.print("\nerror in create createChecksum "+e.toString());
            e.printStackTrace();
        }
        return null;
    }
    public static String getMD5Checksum(String filename){
        byte[] b = createChecksum(filename);
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }    
    public LinkedList<String> haveIFile(String nameFile){
        File file=new File(fileShared);//get apuntador a la carpeta compartida
        LinkedList <String> filesFounded=new LinkedList<String>();
        if(!file.exists())//if the route dont exists
            return null;
        File []files=file.listFiles();//get all files that content the carpeta (file) 
        int i;
        for(i=0;i<files.length;i++){//recorro all files
            if(files[i].getName().contains(nameFile))
                filesFounded.add(files[i].getName()+","+files[i].length()+","+getMD5Checksum(files[i].getAbsolutePath()));
        }
        if(filesFounded.size()==0)
            return null;
        return filesFounded;
    }
}
