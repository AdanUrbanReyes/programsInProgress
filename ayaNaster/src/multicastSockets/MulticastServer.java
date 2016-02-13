package multicastSockets;
public class MulticastServer {
    private java.net.MulticastSocket socket;
    private int port=5000,portAnnounce=4000;
    private String ip="230.1.1.1";
    private boolean isAnnounce=true;
    private Thread threadAnnounce=null;
    private Runnable announce=new Runnable(){
        public void run(){
            try{
                String message=""+portAnnounce;
                byte []buffer=new byte[message.length()];
                java.net.DatagramPacket packet=new java.net.DatagramPacket(buffer,buffer.length,java.net.InetAddress.getByName(ip),port);
                packet.setData(message.getBytes());
                packet.setLength(message.length());
                while(isAnnounce){
                    //System.out.print("\nanunciando el puerto "+portServerAnnounce);
                    socket.send(packet);
                    Thread.sleep(5000);
                }
            }catch(Exception e){
                javax.swing.JOptionPane.showMessageDialog(null,"error in announce thread "+e.getMessage());
            }
            threadAnnounce=null;
        }
    };
    public MulticastServer(int port,String ip,int ttl){
        this.port=port;
        this.ip=ip;
        startMulticastSocket(ttl);
    }
    public void startMulticastSocket(int ttl){
        try{
            socket=new java.net.MulticastSocket(port);
            socket.setReuseAddress(true);//para que se pueda reusar el socket
            socket.setTimeToLive(ttl);//pongo el tiempo de vida de los packetes
            socket.joinGroup(java.net.InetAddress.getByName(ip));//me uno al grupo
            System.out.print("\nserver multicast running in port: "+port+" ip: "+ip);
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"error starting server multicast "+e.getMessage());
        }
    }
    public void stopThreadAnnounce(){
        isAnnounce=false;
    }
    public void startThreadAnnounce(int port){
        if(threadAnnounce!=null){
            javax.swing.JOptionPane.showMessageDialog(null,"the server is announce firsth stop thread if wanna announce other port");
            return;
        }
        portAnnounce=port;
        isAnnounce=true;
        threadAnnounce=new Thread(announce);
        threadAnnounce.start();
    }
}
