package fr.epita.mapper.launcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.mapper.models.DrawingString;
import fr.epita.mapper.models.Rule;
import fr.epita.mapper.services.DrawMap;
import fr.epita.mapper.services.RuleDao;
import fr.epita.mapper.services.RulesFile;

public class ConsoleLauncher {

	List<Rule> rules;
	Scanner scanner;
	RulesFile file;
	
	public static void main(String[] args) throws SQLException, IOException {
		
		ConsoleLauncher launcher = new ConsoleLauncher();
		
		launcher.work();
		
	}

	public void work() throws SQLException, IOException{
		System.out.println("Welcome to the Mapper software");
		scanner = new Scanner(System.in);

		System.out.println("Please type the host:");
		String host = scanner.nextLine();
		
		System.out.println("Please type your username:");
		String user = scanner.nextLine();
		
		System.out.println("Please type your password:");
		String password = scanner.nextLine();
		
		file = new RulesFile(host,user,password);
		
		if( file.getFile()){
			rules = file.getRules();
			List<DrawingString> drawing = new ArrayList<DrawingString>();
			RuleDao dao = new RuleDao();
			for(Rule rule:rules){
				System.out.println(rule);
				
				List<DrawingString> temp = dao.getDrawingStrings(rule);
				drawing.addAll(temp);
				dao.insertRule(rule);
				
			}
			
			DrawMap mapper = new DrawMap(drawing);
			mapper.generateMap();
			System.out.println("Map ready!");
		}
		scanner.close();
	}
	
}
