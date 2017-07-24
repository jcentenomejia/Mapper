package fr.epita.mapper.models;

public class DrawingString {

	private String src;
	private String dest;
	private int port;
	private String protocol;
	private boolean accept;
	private String userSrc;
	private String userDest;
	
	public DrawingString(String src, String dest, int port, String protocol, boolean accept, String userSrc, String userDest) {
		
		this.src = src;
		this.dest = dest;
		this.port = port;
		this.protocol = protocol;
		this.accept = accept;
		this.userSrc = userSrc;
		this.userDest = userDest;
		
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public void setAccept(boolean accept){
		this.accept = accept;
	}
	
	public boolean getAccept(){
		return accept;
	}
	
	public String getUserSrc() {
		return userSrc;
	}

	public void setUserSrc(String userSrc) {
		this.userSrc = userSrc;
	}
	
	public String getUserDest() {
		return userDest;
	}

	public void setUserDest(String userDest) {
		this.userDest = userDest;
	}
	
	public String toString(){
		return "[ " + src + " -> " + dest + " : " + port + " - " + protocol + " ]";
	}
}
