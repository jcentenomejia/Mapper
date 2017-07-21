package fr.epita.mapper.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class MapTest {

	@Test
	public void drawTest() throws IOException, SQLException{
		Properties props = new Properties();
		
		props.load(this.getClass().getResourceAsStream("/db.properties"));
		
		String cnx = props.getProperty("jdbc.connection.string");
		String user = props.getProperty("jdbc.connection.user");
		String pwd = props.getProperty("jdbc.connection.pwd");
		
	    Connection con = DriverManager.getConnection(cnx, user, pwd);
		String drawingString = "@startuml \n"
				+ "participant Internet as INTERNET \n"
				+ "participant \"Public DMZ\" as PUBLIC_DMZ \n"
				+ "participant \"Extranet DMZ\" as EXTRANET_DMZ \n"
				+ "participant \"DB Network\" as DB_NETWORK \n"
				+ "participant \"Internal Network\" as INTERNAL_NETWORK \n";
				
	    String selectString = "SELECT * FROM rules_table";
		PreparedStatement selectStatement = con.prepareStatement(selectString);
		
		ResultSet rs = selectStatement.executeQuery();
		
		while(rs.next()){ 
			
			drawingString += rs.getString("LAYER_SOURCE") + " -> " + rs.getString("LAYER_DESTINATION") + " : " + rs.getString("PORT") + " " + rs.getString("PROTOCOL") + " \n";
			
		}
		
		drawingString += "@enduml ";
		
		rs.close();
		selectStatement.close();
		con.close();
		
		SourceStringReader reader = new SourceStringReader(drawingString);
		
		FileOutputStream output = new FileOutputStream(new File("test.svg"));
		reader.generateImage(output, new FileFormatOption(FileFormat.SVG, false));
	}
}
