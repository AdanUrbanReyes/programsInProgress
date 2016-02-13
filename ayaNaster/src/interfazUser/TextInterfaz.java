package interfazUser;
import multicastSockets.MulticastClient;
import multicastSockets.MulticastServer;
import multicastSockets.Response;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import remoteMethodInvocation.ServerRMI;
import streamSockets.StreamServer;
public class TextInterfaz extends JFrame implements ActionListener{
    private MulticastServer sm=null;
    private MulticastClient cm=null;
    private String fileDownloads=null,nameFileToDownload=null;
    private JButton search=null,seeServersFree=null,seeDownloads=null;
    private JTextField box=null;//box of text that content string name the file to search
    private JPanel filesFound=null;//panel that content all files founded 
    
    private DefaultTableModel modelTables;
    
    
    public TextInterfaz (String title,int width,int heigth){
        super(title);
        setSize(width,heigth);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);//put window in center of display
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startSockets();
        putButtons();
        putTextField();
        putPanel();
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        JButton temp=(JButton)e.getSource();
        if(temp==search){
            String nameFileToDownload=box.getText();
            if(nameFileToDownload!=null&&!nameFileToDownload.isEmpty()){
                fillfilesFound(cm.askByFile(nameFileToDownload));
            }
        }else{
            if(temp==seeServersFree){
                cm.showServerDisponse();
            }else{
                if(temp==seeDownloads){
                    JOptionPane.showMessageDialog(null,"downloads");
                }else
                    JOptionPane.showMessageDialog(null,"button not config :S","error with a button",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static String selectDirectoryToShared(String title){
        int error;
        String route=null;
        JFileChooser selector=new JFileChooser();
        selector.setDialogTitle(title);//put title of window del explorador de files
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//just cant select a carpetas no archivos
        do{
            error=selector.showOpenDialog(null);
            if(error==JFileChooser.APPROVE_OPTION){
                route=selector.getSelectedFile().getAbsolutePath();//selector.getCurrentDirectory().getAbsolutePath();
            }else{
                if(error==JFileChooser.CANCEL_OPTION){
                    JOptionPane.showMessageDialog(null,"is necesary that select a folder to share");
                }
            }
        }while(route==null);
        //System.out.print("\nrouteShare="+route);
        return route;
    }
    public void putButtons(){
        search=new JButton("search");
        search.setFont(new Font("Serif",Font.PLAIN,17));
        search.setBounds(200,35,100,35);
        search.addActionListener(this);
        add(search);
        seeServersFree=new JButton("servers free");
        seeServersFree.setFont(new Font("Serif",Font.PLAIN,17));
        seeServersFree.setBounds(0,0,150,35);
        seeServersFree.addActionListener(this);
        add(seeServersFree);
        seeDownloads=new JButton("downloads");
        seeDownloads.setFont(new Font("Serif",Font.PLAIN,17));
        seeDownloads.setBounds(150,0,150,35);
        seeDownloads.addActionListener(this);
        add(seeDownloads);
    }
    public void putTable(){
        ;
    }
    public void putPanel(){
        filesFound=new JPanel(new GridLayout(0,4));
        JScrollPane scroll=new JScrollPane(filesFound);
        scroll.setBounds(0,70,300,430);
        add(scroll);
    }
    public void putTextField(){
        box=new JTextField(30);
        box.setFont(new Font("Serif",Font.PLAIN,17));
        box.setBounds(0,35,200,35);
        add(box);
    }
    public void fillfilesFound(LinkedList<Response> list){
        //filesFound.paintAll(this.getGraphics());
        int i;
        filesFound.add(new JLabel("name"));
        filesFound.add(new JLabel("size"));
        filesFound.add(new JLabel("md5"));
        filesFound.add(new JLabel("download"));
        filesFound.revalidate();
        for(i=0;i<list.size();i++){
            filesFound.add(new JLabel(list.get(i).getnameFile()));
            filesFound.add(new JLabel(""+list.get(i).getsizeofFile()));
            filesFound.add(new JLabel(list.get(i).getmd5File()));
            filesFound.add(new JButton("download"));
            filesFound.revalidate();
        }
    }
    public int askPort(String titleOfJPane){
        int port=0;
        while((port<1024 || port>65536)){
            try{
                port=Integer.parseInt(JOptionPane.showInputDialog(null,titleOfJPane));
                if(port<1024||port>65536)
                    JOptionPane.showMessageDialog(null,"enter port menor of 65536 and mayor that 1024","error port",JOptionPane.ERROR_MESSAGE);
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"enter a number integer please","error",JOptionPane.ERROR_MESSAGE);
            }
        }
        return port;
    }
    public String askIpGroup(String titleOfJPane){
        String ip=null;
        java.net.MulticastSocket multicast=null;
        boolean ipOk=false;
        while(!ipOk){
            ip=JOptionPane.showInputDialog(titleOfJPane);
            try{
                multicast=new java.net.MulticastSocket(5000);
                multicast.joinGroup(InetAddress.getByName(ip));
                multicast.close();
                ipOk=true;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"enter ip of group valida please "+e.toString(),"error ip",JOptionPane.ERROR_MESSAGE);
                ipOk=false;
            }
        }
        return ip;
    }
    public void startServerSocket(){
        int port=askPort("enter port to server socket");
        String ip=askIpGroup("enter ip of group for server socket");
        System.out.print("\n"+ip+":"+port);
        //String ip=null;
        //do{
     //   }while((port<1024&&port>65536)||ip==null);
    }
    public void startSockets(){
        /*
        int portMulticast=Integer.parseInt(JOptionPane.showInputDialog(null,"enter port of socket multicast"));
        int portFlujo=Integer.parseInt(JOptionPane.showInputDialog(null,"enter port of socket flujo"));
        String ipGroup=JOptionPane.showInputDialog(null,"enter ip of group join");
        int ttl=Integer.parseInt(JOptionPane.showInputDialog(null,"enter ttl"));
        */
        String fileShared=selectDirectoryToShared("enter route of directory shared")+System.getProperty("file.separator");
        //fileDownloads=selectDirectory("enter route of directory for save downloads")+System.getProperty("file.separator");
        
        ServerRMI srmi=new ServerRMI(fileShared);
        /*StreamServer sf=new StreamServer(portFlujo,fileShared);
        sf.startServerSocket();
        sf.receiveClients();*/
        //startServerSocket();
        sm=new MulticastServer(5000,"228.1.1.1",3);//receive port, ipGroup and ttl
        sm.startThreadAnnounce(4000);//recive el puerto a anunciar 
        cm=new MulticastClient(5000,"228.1.1.1",3);//receive port, ipGroup and ttl
        cm.startThreadReadDatagrams();//start read datagramas
    }
}