package handler;
import view.Console;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class HandlerConsole implements ActionListener{
	private Console console;
	public HandlerConsole(){
		console=new Console("consola",800,600);
		
	}
	public void setWindowsListener(){//this method only is for set null handler serach table when user close view search table and can funcionar patron singlenton
		try{
			console.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e){
					System.out.print("en window listener\n");
				}
			});
		}catch(Exception e){
			System.out.print("error cerrando frame Console\t"+e.toString()+"\n");
		}
	}
}
