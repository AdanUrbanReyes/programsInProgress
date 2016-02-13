package streamSockets;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class StreamServer {
    private String fileShared=null;
    private ServerSocket server;
    private int port;
    private Socket client;
    private Runnable atiende=new Runnable(){
        public void run(){
            try{
                Socket cliente=client;
                String nameFile=String.valueOf(receiveObject(cliente));
                int upLimit=receiveInteger(cliente);
                int downLimit=receiveInteger(cliente);
                        
                        
                        
            }catch(Exception e){
                System.out.print("\nerror in thread atiende "+e.getMessage());
            }
        }  
    };
    public StreamServer(int port,String fileShared){
        this.port=port;
        this.fileShared=fileShared;
        startServerSocket();
    }
    public void startServerSocket(){
        try{
            server=new ServerSocket(port);
            server.setReuseAddress(true);
            System.out.print("\nserver of flujo wake up in port "+port);
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"error starting server socket "+e.toString());
        }
    }
    public void sendObject(Socket client,Object object){
        try{
            ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());
            oos.writeObject(object);
            oos.flush();
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"error sending object "+e.toString());
        }
    }
    public Object receiveObject(Socket client) {
        Object object = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            object = ois.readObject();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "error receiving object " + e.toString());
        }
        return object;
    }   
    public int receiveInteger(Socket client) {
        int integer=0;
        try {
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            integer= ois.readInt();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "error receiving object " + e.toString());
        }
        return integer;
    }       
    public void atiende(Socket client){
        this.client=client;
        Thread thread=new Thread(atiende);
        thread.start();    
    }
    public void receiveClients(){
        Socket client;
        try{
            while(true){
                System.out.print("\nwaiting for client");
                client=server.accept();
                System.out.print("\nclient connected in port: "+client.getPort()+" ip:"+client.getInetAddress().getHostName());
                atiende(client);
                Thread.sleep(137);
            }   
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"error in receive client "+e.getMessage());
        }
    }
    public static void sendFile(File file,int upLimit,int downLimit) {
        byte[]bufer=new byte[1024];
        int leidos=0;
        ObjectOutputStream oos=new ObjectOutputStream(null);
        try{
            BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
            //BufferedOutputStream bos=new BufferedOutputStream(cliente.getOutputStream());
            while((leidos=bis.read(bufer,0,bufer.length))!=-1){
                oos.write(bufer,0,leidos); oos.flush();
            }
            area.append("\nse envio correctamete "+file.getName());
            bis.close();
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"error al enviar "+file.getName()+" "+e.getMessage());
        }
    }
}
