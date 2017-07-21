package fr.epita.mapper.models;

public class DrawingString {

	private String src;
	private String dest;
	private int port;
	private String protocol;
	
	public DrawingString(String src, String dest, int port, String protocol) {
		
		this.src = src;
		this.dest = dest;
		this.port = port;
		this.protocol = protocol;
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
	
	public String toString(){
		return "[ " + src + " -> " + dest + " : " + port + " - " + protocol + " ]";
	}
}
