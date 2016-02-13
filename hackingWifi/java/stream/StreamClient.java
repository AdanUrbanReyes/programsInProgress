package stream;
import java.net.Socket;
import java.net.InetAddress;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
public class StreamClient{
	private Socket socket;
	public StreamClient(String ip,int port){
		try{
			socket=new Socket(InetAddress.getByName(ip),port);
			System.out.print("cliente conectado en ip:"+ip+" puerto:"+port+"\n");
		}catch(Exception e){
			System.out.print("error conectando cliente con el servidor ip:"+ip+" puerto: "+port+"\t"+e.toString()+"\n");
		}
	}
	public boolean sendObject(Socket socket,Object object){
		try{
			ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(object);
		}catch(Exception e){
			System.out.print("error enviando objecto del socket ip:"+socket.getInetAddress().getHostAddress()+" puerto:"+socket.getPort()+"\t"+e.toString()+"\n");
			return false;
		}
		return true;
	}
	public Object receiveObject(Socket socket){
		Object object=null;
		try{
			ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
			object=ois.readObject();
		}catch(Exception e){
			System.out.print("error reciviendo objecto del socket ip:"+socket.getInetAddress().getHostAddress()+" puerto:"+socket.getPort()+"\t"+e.toString()+"\n");
		}
		return object;
	}
	public void sendCommands(){
		String string;
		do{
			string=javax.swing.JOptionPane.showInputDialog(null,"ingrese comando a enviar o -1 para finalizar");
			sendObject(this.socket,string);
		}while(!string.equals("-1"));
	}
}
