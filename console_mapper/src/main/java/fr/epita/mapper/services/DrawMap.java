package fr.epita.mapper.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.epita.mapper.models.DrawingString;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class DrawMap {

	private List<DrawingString> drawingStrings;

	public DrawMap(List<DrawingString> drawingStrings) {
		this.drawingStrings = drawingStrings;
	}
	
	public void generateMap() throws IOException{
		int size = drawingStrings.size();
		boolean[] toPrint = new boolean[size];
		String[] arrows = new String[size];
		
		Arrays.fill(toPrint, true);
		Arrays.fill(arrows, " -> ");
		
		String drawingString = "@startuml \n"
				+ "skinparam monochrome true \n"
				+ "participant Internet as INTERNET \n"
				+ "participant \"Public DMZ\" as PUBLIC_DMZ \n"
				+ "participant \"Extranet DMZ\" as EXTRANET_DMZ \n"
				+ "participant \"DB Network\" as DB_NETWORK \n"
				+ "participant \"Internal Network\" as INTERNAL_NETWORK \n";


		for(int i =0; i < size; i++){
			for(int j =0; j < size; j++){
				DrawingString first = drawingStrings.get(i);
				DrawingString second = drawingStrings.get(j);
				if(toPrint[i]){
					if(first.getPort() == second.getPort() && first.getSrc().equals(second.getDest())
							&& first.getDest().equals(second.getSrc()) && !first.getDest().equals(first.getSrc())){
						toPrint[j] = false;
						arrows[i] = " <-> ";
					}
				}
			}
		}
		
		
		for(int i = 0; i < size; i++){
			
			if(toPrint[i]){
				drawingString += drawingStrings.get(i).getSrc() + arrows[i] + drawingStrings.get(i).getDest() + " : " + drawingStrings.get(i).getPort() + " " + drawingStrings.get(i).getProtocol() + " \n";
				if(!drawingStrings.get(i).getAccept()){
					drawingString += "destroy " + drawingStrings.get(i).getDest() + " \n";
				}
			}
		}
		
		drawingString += "@enduml ";
		
		SourceStringReader reader = new SourceStringReader(drawingString);
		
		FileOutputStream output = new FileOutputStream(new File("test.svg"));
		reader.generateImage(output, new FileFormatOption(FileFormat.SVG, false));
	}
}
