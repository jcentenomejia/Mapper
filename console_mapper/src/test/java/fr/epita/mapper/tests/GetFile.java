package fr.epita.mapper.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.epita.mapper.models.Rule;
import fr.epita.mapper.models.DrawingString;
import fr.epita.mapper.services.RuleDao;
import fr.epita.mapper.services.RulesFile;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class GetFile {

	@Test
	public void GetFileTest() throws SQLException, IOException{
		RulesFile file = new RulesFile("192.168.0.11","jorge","1234");
		file.getFile();
		List<Rule> rules = file.getRules();

		List<DrawingString> drawing = new ArrayList<DrawingString>();
		RuleDao dao = new RuleDao();
		for(Rule rule:rules){
			System.out.println(rule);
			
			List<DrawingString> temp = dao.getDrawingStrings(rule);
			drawing.addAll(temp);
			dao.insertRule(rule);
			
		}
		Draw(drawing);
		System.out.println(drawing);
	}
	
	public void Draw(List<DrawingString> list) throws IOException{
		String drawingString = "@startuml \n"
				+ "participant Internet as INTERNET \n"
				+ "participant \"Public DMZ\" as PUBLIC_DMZ \n"
				+ "participant \"Extranet DMZ\" as EXTRANET_DMZ \n"
				+ "participant \"DB Network\" as DB_NETWORK \n"
				+ "participant \"Internal Network\" as INTERNAL_NETWORK \n";
				
		for(DrawingString drawing:list){ 
			
			drawingString += drawing.getSrc() + " -> " + drawing.getDest() + " : " + drawing.getPort() + " " + drawing.getProtocol() + " \n";
			
		}
		
		drawingString += "@enduml ";
		
		SourceStringReader reader = new SourceStringReader(drawingString);
		
		FileOutputStream output = new FileOutputStream(new File("test.svg"));
		reader.generateImage(output, new FileFormatOption(FileFormat.SVG, false));
	}
}
