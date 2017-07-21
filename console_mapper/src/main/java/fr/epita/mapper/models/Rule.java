package fr.epita.mapper.models;

public class Rule {
	
	private String ip;
	private int port;
	private String protocol;
	private String direction;
	private boolean accept;
	
	public Rule(String ip, int port, String protocol, String direction, boolean accept) {
		this.ip = ip;
		this.port = port;
		this.protocol = protocol;
		this.direction = direction;
		this.accept = accept;
	}

	public int getPort() {
		return port;
	}

	public String getDirection() {
		return direction;
	}
	
	public String getProtocol() {
		return protocol;
	}
	
	public boolean getAccept(){
		return accept;
	}
	
	public String getIp(){
		return ip;
	}
	
	public String toString(){
		return "[ " + port + " " + protocol + " " + direction + " " + accept + "]";
	}
}
