package view;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
public class Console{
	private JButton sendFile;
	private JButton send;
	private JLabel server;
	public Console(String title,int width,int height){
		super(title);
		setSize(width,height);
		setLayout(null);
		setDefaultCloseOperation(WindowCOnstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	public initComponents(String server,int width,int height){
		this.server=new JLabel(server);
		this.server.setBounds(0,0,width,30);
		this.
	}
	
}
