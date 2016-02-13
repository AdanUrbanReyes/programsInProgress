package handler;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import view.SelectServer;
import multicast.MulticastClient;
import model.ServerDisponse;
public class HandlerSelectServer implements KeyListener{
	private SelectServer selectServer;
	private MulticastClient multicastClient;
	public HandlerSelectServer(){
		multicastClient=new MulticastClient("230.0.0.1",5000,7);
		multicastClient.startThreadReadDatagrams();
		selectServer=new SelectServer("romper wifi",1024,800);
		selectServer.servers.addKeyListener(this);
		updateServersOnTable();
	}
	public void updateServersOnTable(){//this method can was a thread
		int i;
		ServerDisponse auxiliary;
		try{
			while(true){//check this is best while(multicastClient.isReading)
				Thread.sleep(7000);
				javax.swing.table.DefaultTableModel model=(javax.swing.table.DefaultTableModel)selectServer.servers.getModel();
				//clean table
				i=model.getRowCount()-1;
				while(i>=0){
					model.removeRow(i--);	
				}
				//end clean table shuld set in a method 
				for(i=0;i<multicastClient.serverDisponse.size();i++){
					auxiliary=multicastClient.serverDisponse.get(i);
					model.addRow(new Object[]{auxiliary.getPort(),auxiliary});
				}
			}
		}catch(Exception e){
			System.out.print("error actualizando la table de servidores disponibles \t"+e.toString()+"\n");
		}
	}
	@Override
	public void keyPressed(KeyEvent e){
		int code=e.getKeyCode();
		if(code==KeyEvent.VK_ENTER){
			
			javax.swing.JOptionPane.showMessageDialog(null,"press enter");
		}
	}
	@Override
	public void keyTyped(KeyEvent e){;}
	@Override
	public void keyReleased(KeyEvent e){;}

	public static void main(String []args){
		HandlerSelectServer hi=new HandlerSelectServer();
	//MulticastServer ms=new MulticastServer("230.0.0.1",5000,7);
	//ms.startAnnounce("4000",5000);
	}
}
