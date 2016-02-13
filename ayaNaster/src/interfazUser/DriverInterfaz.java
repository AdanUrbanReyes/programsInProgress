package interfazUser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import multicastSockets.MulticastClient;
import multicastSockets.Response;
public class DriverInterfaz implements ActionListener{
    private Interfaz interfaz;
    private MulticastClient multicastClient;
    public DriverInterfaz(MulticastClient multicastClient){
        this.multicastClient=multicastClient;
        interfaz=new Interfaz();
        setActionListeners();
    }
    public void setActionListeners(){
        interfaz.search.addActionListener(this);
        interfaz.seeServersFree.addActionListener(this);
    }
    public void fillfilesFound(LinkedList<Response> list){
        javax.swing.table.DefaultTableModel model=(javax.swing.table.DefaultTableModel)interfaz.filesFounded.getModel();
        int i,size=list.size();
        for(i=0;i<size;i++){
            Response response=list.get(i);
            model.addRow(new Object[]{response.getnameFile(),response.getsizeofFile(),response.getmd5File(),response.getServersThatResponse()});
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        javax.swing.JButton temp=(javax.swing.JButton)e.getSource();
        if(temp==interfaz.search){
            String nameFileToDownload=interfaz.stringFileSearch.getText();
            if(nameFileToDownload!=null&&!nameFileToDownload.isEmpty()){
                fillfilesFound(multicastClient.askByFile(nameFileToDownload));
            }else{
                javax.swing.JOptionPane.showMessageDialog(null,"please enter a string for search file");
            }
        }else{
            if(temp==interfaz.seeServersFree){
                multicastClient.showServerDisponse();
            }else{
                javax.swing.JOptionPane.showMessageDialog(null,"button not config :S","error with a button",javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
