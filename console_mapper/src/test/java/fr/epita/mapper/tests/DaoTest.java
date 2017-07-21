package fr.epita.mapper.tests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import fr.epita.mapper.models.DrawingString;
import fr.epita.mapper.models.Rule;
import fr.epita.mapper.services.RuleDao;

public class DaoTest {

	@Test
	public void TestDao() throws SQLException, IOException{
		RuleDao dao = new RuleDao();  
		
		List<DrawingString> list = dao.getDrawingStrings(new Rule("",80,"tcp","INPUT",true));
		System.out.println(list.toString());
	}
}
