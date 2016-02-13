package multicastSockets;
import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.LinkedList;
public class MulticastClient {
    private final int numberMaxThread=11;//is number of thread that create for download file
    private MulticastSocket socket;
    private String ip="230.1.1.1";
    private int port=5000;
    private boolean isReading=true;
    private LinkedList <ServerDisponse>serverDisponse=new LinkedList<ServerDisponse>();
    private Thread threadReadDatagrams=null;
    private Thread threadTimerDecrementador=null;
    private Runnable timerDecrementador=new Runnable(){
        public void run(){
            try{
                while(isReading){
                    Thread.sleep(1000);
                    decrementa();
                    //printList();
                }
                emptyList(serverDisponse);
            }catch(Exception e){
                System.out.print("\nerror in timer decrementador "+e.getMessage());
            }
        }
    };
    private Runnable readDatagrams=new Runnable(){
        public void run(){
            String message;
            byte []buffer=new byte[5];
            DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
            new Thread(timerDecrementador).start();//start thread timer
            try{
                while(isReading){
                    socket.receive(packet);//recivo the package 
                    message=new String(packet.getData()).trim();//remove all espacios in blanco
                    addOrUpdateListServerDisponse(Integer.parseInt(message),packet.getAddress().getHostAddress());//sedn port(message contains port) and ip of server
                    //System.out.print("\n client multicast receive "+message+" from ip "+packet.getAddress().getHostAddress());
                    Thread.sleep(137);
                }
            }catch(Exception e){
                javax.swing.JOptionPane.showMessageDialog(null,"error in read datagrams "+e.getMessage());
            }            
        }
    };
    public MulticastClient(int port,String ip,int ttl){
        this.port=port;
        this.ip=ip;
        startMulticastSocket(ttl);
    }
    public void startMulticastSocket(int ttl){
        try{
            socket=new MulticastSocket(port);//create multicast socket
            socket.setReuseAddress(true);//put socket reusable for que se pueda usar por mas aplicaciones
            socket.setTimeToLive(ttl);//pone el tiempo de vida de los packets
            socket.joinGroup(InetAddress.getByName(ip));//se une al grupo 
            System.out.print("\nmulticast client running in port: "+port+" ip: "+ip);
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"error starting client multicast "+e.getMessage());
        }
    }
    public void stopThreadReadDatagrams(){
        isReading=false;
    }
    public void startThreadReadDatagrams(){
        if(threadReadDatagrams!=null){
            javax.swing.JOptionPane.showMessageDialog(null,"the client is reading packege firsth stop thread if wanna start other thread");
            return;
        }
        isReading=true;
        threadReadDatagrams=new Thread(readDatagrams);
        threadReadDatagrams.start();
    }
    public void decrementa(){
        int i;
        for(i=0;i<serverDisponse.size();i++){//recorre la lista de servidores disponibles
            serverDisponse.get(i).decrementsecond();//decrementa en uno los segundos de cada servidor disponible 
            if(serverDisponse.get(i).getsecond()==0)//if los segundos son 0 
                serverDisponse.remove(i--);//el servidor ya no esta en funcionamiento asi que lo remuevo
        }
    }
    public void addOrUpdateListServerDisponse(int port,String ip){
        int i;
        for(i=0;i<serverDisponse.size();i++){//recorro list of server disponse
            if(serverDisponse.get(i).getip().equals(ip)&&serverDisponse.get(i).getport()==port){//if a ip and port coincide with ip and port received
                serverDisponse.get(i).resetsecond();//restart seconds a 6seconds
                return;
            }
        }
        serverDisponse.add(new ServerDisponse(port,ip));//en la lista no estava el servidor (ip y puerto) entonces lo agrego
    }
    public void printList(){//print in consola all servers disponse
        int i;
        for(i=0;i<serverDisponse.size();i++)
            System.out.print("\nip="+serverDisponse.get(i).getip()+"\tport="+serverDisponse.get(i).getport()+"\tsecond="+serverDisponse.get(i).getsecond());
    }
    public void emptyList(LinkedList list){
        while(list.size()>0)
            list.remove(0);
    }
    public void showServerDisponse(){//show in JOptionPane all servers disponse
        LinkedList <ServerDisponse> temp=serverDisponse;
        int i;
        String aux="";
        for(i=0;i<temp.size();i++)
            aux+="ip: "+temp.get(i).getip()+" port: "+temp.get(i).getport()+"\n";
        javax.swing.JOptionPane.showMessageDialog(null,aux);
    }
    public LinkedList<Response> askByFile(String nameFile){
        LinkedList <ServerDisponse> temp=serverDisponse;
        int i,j=0;
        ServerDisponse.nameFile=nameFile;//pongo el nombre del archivo a buscar es variable estatica para que todos los objetos Server Disponse lo busquen
        for(i=0;i<temp.size();i++,j++){
            temp.get(i).start();//this start thread the class ServerDisponse
            synchronized(temp.get(i)){
                try{
                    if(j>=numberMaxThread){//for para wait threads
                        for(j=0;j<i;j++)
                            temp.get(j).wait();//for wait the thread
                        j=0;
                    }
                }catch(Exception e){
                    javax.swing.JOptionPane.showMessageDialog(null,"erron in askByFile "+e.toString());
                }
            }
        }
        for(j=i-j;j<temp.size();j++){//for para wait threads 
            try{
                synchronized(temp.get(j)){
                    temp.get(j).wait();//for wait the thread
                }
            }catch(Exception e){
                javax.swing.JOptionPane.showMessageDialog(null,"erron in askByFile two"+e.toString());
            }
        }
        //ServerDisponse.nameFile=null;
        return ServerDisponse.getresponses();//this is a method static and list response same all objects have same list response
    }
}