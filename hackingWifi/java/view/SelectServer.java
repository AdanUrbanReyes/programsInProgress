package view;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
public class SelectServer extends JFrame{
	public JTable servers;
	public SelectServer(String title,int width,int height){
		super(title);
		setSize(width,height);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTable(width,height);
		setVisible(true);
	}
	public void setTable(int width,int height){
		String []columns={"puerto","ip"};
		DefaultTableModel model=new DefaultTableModel(null,columns);
		servers=new JTable(model);
		JScrollPane scroll=new JScrollPane(servers);
		scroll.setBounds(0,0,width,height-50);
		add(scroll);
	}
}
