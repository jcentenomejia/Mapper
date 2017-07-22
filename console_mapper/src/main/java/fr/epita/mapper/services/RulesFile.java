package fr.epita.mapper.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import fr.epita.mapper.models.Rule;

public class RulesFile {

	private String username;
	private String host;
	private String password;
	
	public RulesFile(String host, String username, String password) {
		
		this.username = username;
		this.host = host;
		this.password = password;
	}
	
	public boolean getFile(){
		boolean result = false;
		String hostname = this.host;
		String user = this.username;
		String pass = this.password;
		String copyFrom = "/etc/iptables/rules.v4";
		String copyTo = "/"; 
		JSch jsch = new JSch();
		Session session = null;
		System.out.println("Trying to connect.....");
		try {
			session = jsch.getSession(user, hostname, 22);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(pass);
			session.connect(); 
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel; 
			sftpChannel.get(copyFrom, copyTo);
			sftpChannel.exit();
			session.disconnect();
			System.out.println("Done copying!!");
			result = true;
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Rule> getRules(){
		List<Rule> rules = new ArrayList<Rule>();
		int port;
		String dir;
		boolean accept;
		String protocol;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("rules.v4"));
			while (scanner.hasNextLine()){
				
				String[] line = (scanner.nextLine()).split(" ");
				if("-A".equals(line[0])){
					dir = line[1];
					protocol = line[3];
					port = Integer.parseInt(line[7]);
					accept = "ACCEPT".equals(line[9])?true:false;
					
					Rule readRule = new Rule(host,port,protocol,dir,accept);
					rules.add(readRule);
				}
				
			}
			
			scanner.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rules;
	}
}
