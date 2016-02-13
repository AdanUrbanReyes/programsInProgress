package stream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class StreamServer{
	private ServerSocket socket;
	private Socket client;
	private Runnable threadHandlerClient=new Runnable(){
		public void run(){
			receiveCommands();
		}
	};
	public StreamServer(int port){
		try{
			socket=new ServerSocket(port);
			socket.setReuseAddress(true);
			System.out.print("servidor iniciado en puerto:"+port+"\n");
		}catch(Exception e){
			System.out.print("error iniciando servidor en puerto: "+port+"\t"+e.toString()+"\n");
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
	public void receiveCommands(){
		Socket client=this.client;
		String string;
		do{
			string=(String)receiveObject(client);
			System.out.print("ip:"+client.getInetAddress().getHostAddress()+" port:"+client.getPort()+" dice -> "+string+"\n");
		}while(!string.equals("-1"));
	}
	public void waitClients(){
		try{
			while(true){
				System.out.print("esperando cliente...\n");
				client=socket.accept();
				System.out.print("cliente conectado desde ip:"+client.getInetAddress().getHostAddress()+" puerto:"+client.getPort()+"\n");
				Thread thread=new Thread(threadHandlerClient);
				thread.start();
				Thread.sleep(500);
			}
		}catch(Exception e){
			System.out.print("error esperando clientes\t"+e.toString()+"\n");
		}
	}
}
