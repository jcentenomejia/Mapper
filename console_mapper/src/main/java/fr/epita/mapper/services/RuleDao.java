package fr.epita.mapper.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import fr.epita.mapper.models.DrawingString;
import fr.epita.mapper.models.Rule;

public class RuleDao {

	private Connection currentConnection;
	
	public RuleDao() throws SQLException, IOException{
		getConnection();
	}
	
	//Tries current connection if it disconnected, it reconnects again
	private Connection getConnection() throws SQLException, IOException {
		
		Properties props = new Properties();
		
		props.load(this.getClass().getResourceAsStream("/db.properties"));
			
		String cnx = props.getProperty("jdbc.connection.string");
		String user = props.getProperty("jdbc.connection.user");
		String pwd = props.getProperty("jdbc.connection.pwd");

		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		this.currentConnection = DriverManager.getConnection(cnx, user, pwd);
		
		return this.currentConnection;
	}
	
	public void insertRule(Rule rule) throws SQLException, IOException{
		Connection connection = getConnection();

		String sqlInstruction = "INSERT INTO rules(firewall_ip, port, protocol, "
				+ "direction, action) VALUES(?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sqlInstruction);
		statement.setString(1, rule.getIp());
		statement.setString(2, String.valueOf(rule.getPort()));
		statement.setString(3, rule.getProtocol());
		statement.setString(4, rule.getDirection());
		statement.setString(5, rule.getAccept()?"ACCEPT":"DROP");
		statement.execute();
		
		releaseResources();
		statement.close();
	}
	
	public List<DrawingString> getDrawingStrings(Rule rule) throws SQLException, IOException {
		List<DrawingString> rules = new ArrayList<DrawingString>();

		Connection connection = getConnection();

		int port = rule.getPort();
		String protocol = rule.getProtocol();
		boolean accept = rule.getAccept();
		
		PreparedStatement statement = connection.prepareStatement("select * from port_info where port = ? and direction = ?");
		
		statement.setString(1, String.valueOf(port));
		statement.setString(2, rule.getDirection());
		
		ResultSet rs = statement.executeQuery();
		
		while (rs.next()) {
			String src = rs.getString("src_layer");
			String dest = rs.getString("dest_layer");
			
			DrawingString ds = new DrawingString(src,dest,port,protocol,accept);
			rules.add(ds);
		}
		if(rules.isEmpty()){
			if("INPUT".equals(rule.getDirection())){
				rules.add(new DrawingString("INTERNET","INTERNAL_NETWORK",port,protocol+"(Assuming)",accept));
			}else{
				rules.add(new DrawingString("INTERNAL_NETWORK","INTERNET",port,protocol+"(Assuming)",accept));
			}
		}
		releaseResources();
		statement.close();
		return rules;
	}
	
	private void releaseResources() {
		try {
			this.currentConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
