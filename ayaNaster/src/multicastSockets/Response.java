package multicastSockets;
import java.util.LinkedList;
public class Response {
    private String nameFile,md5File;
    private int sizeofFile;
    private LinkedList<ServerDisponse> servers=new LinkedList<ServerDisponse>();
    public Response(String nameFile,String md5File,int sizeofFile){
        this.nameFile=nameFile;
        this.md5File=md5File;
        this.sizeofFile=sizeofFile;
    }
    public void addserver(ServerDisponse server){
        servers.add(server);
    }
    public int getServersThatResponse(){
        return servers.size();
    }
    public int getsizeofFile(){
        return sizeofFile;
    }
    public String getnameFile(){
        return nameFile;
    }
    public String getmd5File(){
        return md5File;
    }
}
