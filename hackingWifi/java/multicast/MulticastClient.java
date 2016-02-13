package multicast;
import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.LinkedList;
import model.ServerDisponse;
public class MulticastClient {
	private static final int twfs=6;//time wait for server in seconds
	private MulticastSocket socket;
	private String ip;
	private int port;
	private boolean isReading;
	public LinkedList <ServerDisponse> serverDisponse=new LinkedList<ServerDisponse>();
	private Runnable timerDecrementador=new Runnable(){
		public void run(){
			try{
				while(isReading){
					Thread.sleep(1000);
					decrementa();
					//printList();
				}
			}catch(Exception e){
				System.out.print("error decrementando el tiempo de los servidores\t"+e.getMessage()+"\n");
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
					updateListServerDisponse(Integer.parseInt(message),packet.getAddress().getHostAddress());//sedn port(message contains port) and ip of server
					//System.out.print("client multicast recivio:"+message+" from ip:"+packet.getAddress().getHostAddress()+"\n");
					Thread.sleep(137);
				}
			}catch(Exception e){
				javax.swing.JOptionPane.showMessageDialog(null,"error in read datagrams "+e.getMessage());
			}			
		}
	};
	public MulticastClient(String ip,int port,int ttl){
		this.port=port;
		this.ip=ip;
		startMulticastSocket(ttl);
	}
	public void startMulticastSocket(int ttl){
		try{
			socket=new MulticastSocket(port);//create multicast socket
			socket.setTimeToLive(ttl);//pone el tiempo de vida de los packets
			socket.joinGroup(InetAddress.getByName(ip));//se une al grupo 
			System.out.print("cliente multicast iniciado en ip:"+ip+" puerto:"+port+" con tiempo de vida:"+ttl+"\n");
		}catch(Exception e){
			System.out.print("error iniciando cliente multicast en ip:"+ip+" puerto:"+port+" con tiempo de vida:"+ttl+"\n");
		}
	}
	public void stopThreadReadDatagrams(){
		isReading=false;
	}
	public void startThreadReadDatagrams(){
		if(isReading==true){
			System.out.print("el cliente multicast ya esta leyendo paketes\n");
			return;
		}
		isReading=true;
		new Thread(readDatagrams).start();
	}
	public void decrementa(){
		int i;
		ServerDisponse auxiliary;
		for(i=0;i<serverDisponse.size();i++){//recorre la lista de servidores disponibles
			auxiliary=serverDisponse.get(i);
			auxiliary.setSecond(auxiliary.getSecond()-1);//decrementa en uno los segundos de cada servidor disponible 
			if(serverDisponse.get(i).getSecond()==0){//if los segundos son 0 
				serverDisponse.remove(i--);//el servidor ya no esta en funcionamiento asi que lo remuevo
			}
		}
	}
	public void updateListServerDisponse(int port,String ip){
		int i;
		ServerDisponse auxiliary;
		for(i=0;i<serverDisponse.size();i++){//recorro list of server disponse
			auxiliary=serverDisponse.get(i);
			if(auxiliary.getIp().equals(ip)&&auxiliary.getPort()==port){//if a ip and port coincide with ip and port received
				auxiliary.setSecond(twfs);//restart seconds a 6seconds
				return;
			}
		}
		serverDisponse.add(new ServerDisponse(port,ip,twfs));//en la lista no estava el servidor (ip y puerto) entonces lo agrego
	}
	public void printList(){//print in consola all servers disponse
		int i;
		ServerDisponse auxiliary;
		for(i=0;i<serverDisponse.size();i++){
			auxiliary=serverDisponse.get(i);
			System.out.print("ip="+auxiliary.getIp()+"\tport="+auxiliary.getPort()+"\tsecond="+auxiliary.getSecond()+"\n");
		}
	}
}
