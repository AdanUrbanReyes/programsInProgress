package model;
public class ServerDisponse{
    private String ip;
    private int port,second;
    public ServerDisponse(int port,String ip,int second){
        this.port=port;
        this.ip=ip;
		  this.second=second;
    }
    public String getIp(){
        return ip;
    }
    public int getPort(){
        return port;
    }
    public int getSecond(){
        return second;
    }
	 public void setIp(String ip){
	 	this.ip=ip;
	 }
	 public void setPort(int port){
	 	this.port=port;
	 }
	 public void setSecond(int second){
	 	this.second=second;
	 }
	 @Override
	 public String toString(){
	 	return ip;
	 }
}
