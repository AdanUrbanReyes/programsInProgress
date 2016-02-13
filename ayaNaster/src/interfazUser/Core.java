package interfazUser;

import remoteMethodInvocation.ServerRMI;
import streamSockets.StreamServer;

public class Core {
    public static int askPort(String titleOfJPane){
        int port=0;
        while((port<1024 || port>65536)){
            try{
                port=Integer.parseInt(javax.swing.JOptionPane.showInputDialog(null,titleOfJPane));
                if(port<1024||port>65536)
                    javax.swing.JOptionPane.showMessageDialog(null,"enter port menor of 65536 and mayor that 1024","error port",javax.swing.JOptionPane.ERROR_MESSAGE);
            }catch(NumberFormatException e){
                javax.swing.JOptionPane.showMessageDialog(null,"enter a number integer please","error",javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
        return port;
    }
    public static String askIpGroup(String titleOfJPane){
        String ip=null;
        java.net.MulticastSocket multicast=null;
        boolean ipOk=false;
        while(!ipOk){
            ip=javax.swing.JOptionPane.showInputDialog(titleOfJPane);
            try{
                multicast=new java.net.MulticastSocket(5000);
                multicast.joinGroup(java.net.InetAddress.getByName(ip));
                multicast.close();
                ipOk=true;
            }catch(Exception e){
                javax.swing.JOptionPane.showMessageDialog(null,"enter ip of group valida please "+e.toString(),"error ip",javax.swing.JOptionPane.ERROR_MESSAGE);
                ipOk=false;
            }
        }
        return ip;
    }    
    public static void main(String []args){
        //int streamPort=askPort("enter stream port");
        
        //streamSockets.StreamServer ss=new StreamServer(4000,"/home/ayan/adoo/imagenesPharmacy/");
        
        //int portMulticast=askPort("please enter port multicast Server");
        //String ipGroup=askIpGroup("please enter ip of group to join");
        multicastSockets.MulticastServer multicastServer=new multicastSockets.MulticastServer(5000,"230.0.0.1",7);
        multicastServer.startThreadAnnounce(4000);
        
        multicastSockets.MulticastClient multicastClient=new multicastSockets.MulticastClient(5000,"230.0.0.1",7);
        multicastClient.startThreadReadDatagrams();
        
        //String shareDirectory=Interfaz.selectDirectory("select directory to share");
        ServerRMI srmi=new ServerRMI("/home/ayan/adoo/imagenesPharmacy/");
        
        DriverInterfaz di=new DriverInterfaz(multicastClient);
    }
}
