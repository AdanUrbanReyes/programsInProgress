package multicast;
import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
public class MulticastServer {
	private MulticastSocket socket;
	private int port,tba;//tba= time betwen announce in miliseconds
	private String ip,announce;
	private boolean isAnnounce;
	private Runnable runnableAnnounce=new Runnable(){
		public void run(){
			try{
				byte []buffer=new byte[announce.length()];
				DatagramPacket packet=new java.net.DatagramPacket(buffer,buffer.length,InetAddress.getByName(ip),port);
				packet.setData(announce.getBytes());
				packet.setLength(announce.length());
				while(isAnnounce){
//					System.out.print("anunciando:"+announce+"\n");
					socket.send(packet);
					Thread.sleep(tba);
				}
			}catch(Exception e){
				System.out.print("error anuciando:"+announce+"\t"+e.getMessage());
			}
		}
	};
	public MulticastServer(String ip,int port,int ttl){
		this.port=port;
		this.ip=ip;
		startMulticastSocket(ttl);
	}
	public void startMulticastSocket(int ttl){
		try{
			socket=new MulticastSocket(port);
			socket.setReuseAddress(true);//para que se pueda reusar el socket
			socket.setTimeToLive(ttl);//pongo el tiempo de vida de los packetes
			socket.joinGroup(java.net.InetAddress.getByName(ip));//me uno al grupo
			System.out.print("servidor multicast iniciado en ip:"+ip+" puerto:"+port+" con tiempo de vida: "+ttl+"\n");
		}catch(Exception e){
			System.out.print("error al iniciar el servidor multicast en ip:"+ip+" puerto:"+port+" con tiempo de vida: "+ttl+"\t"+e.toString()+"\n");
		}
	}
	public void stopThreadAnnounce(){
		isAnnounce=false;
	}
	public void startAnnounce(String announce,int tba){//tba= time betwen announce in miliseconds
		if(isAnnounce==true){
			System.out.print("el servidor multicast ya esta anunciando\n");
			return;
		}
		isAnnounce=true;
		this.announce=announce;
		this.tba=tba;
		new Thread(runnableAnnounce).start();
	}
}
