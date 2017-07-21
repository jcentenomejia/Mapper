package fr.epita.mapper.tests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.epita.mapper.models.Rule;
import fr.epita.mapper.models.DrawingString;
import fr.epita.mapper.services.RuleDao;
import fr.epita.mapper.services.RulesFile;

public class GetFile {

	@Test
	public void GetFileTest() throws SQLException, IOException{
		RulesFile file = new RulesFile("10.41.179.11","jorge","1234");
		//file.getFile();
		List<Rule> rules = file.getRules();

		List<DrawingString> drawing = new ArrayList<DrawingString>();
		RuleDao dao = new RuleDao();
		for(Rule rule:rules){
			System.out.println(rule);
			
			drawing.addAll(dao.getDrawingStrings(rule));
			dao.insertRule(rule);
			
		}
		
		System.out.println(drawing);
	}
}
