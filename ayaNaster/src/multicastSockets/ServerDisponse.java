package multicastSockets;
import remoteMethodInvocation.ClientRMI;
import java.util.LinkedList;
public class ServerDisponse extends Thread{
    public static String nameFile=null;
    private static LinkedList <Response> responses=new LinkedList<Response>();
    private String ip;
    private int port,second=6;
    public ServerDisponse(int port,String ip){
        this.port=port;
        this.ip=ip;
    }
    @Override
    public void run(){
        synchronized(this){
            ClientRMI crmi=new ClientRMI(ip);
            LinkedList<String> response=crmi.questionFile(nameFile);//receive list that containst file that containts files that concordaron with name of file entered for user
            if(response==null)
                return;
            int i,size=response.size();
            for(i=0;i<size;i++){
                if(!response.equals("-1")){
                    String []split=response.get(i).split(",");
                    existResponse(split[0],split[2],Integer.parseInt(split[1]));
                }
            }
            notify();
        }
    }
    public void existResponse(String nameFile,String md5File,int sizeofFile){
        int i;
        for(i=0;i<responses.size();i++){
            if(responses.get(i).getnameFile().equals(nameFile)&&responses.get(i).getmd5File().equals(md5File))
                responses.get(i).addserver(this);
        }
        responses.add(new Response(nameFile, md5File, sizeofFile));
        responses.get(responses.size()-1).addserver(this);
    }
    public static LinkedList<Response> getresponses(){
        return responses;
    }
    public String getip(){
        return ip;
    }
    public int getport(){
        return port;
    }
    public int getsecond(){
        return second;
    }
    public void decrementsecond(){
        second--;
    }
    public void resetsecond(){
        second=6;
    }
}
