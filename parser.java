
import java.io.BufferedReader;

import java.text.ParsePosition;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStreamReader;
import java.text.NumberFormat;

public class parser {

	public int counter; //count

	public BufferedReader buffRead;	
	//Read line by line from the file
	private String stringFile; 

	//ARRAY
	public  String stringFileArr[];  
	
	//LINE CUR
	public static String currentCommand;

	//SYMBOL VALUE
	public static int symVal  = 16; 
	//ACL
	public static commandType commandType;  

	
	
	//Input file is open parse
	parser(String nameFile)
	{	
		int needPro;
		//INIT
		counter = 0;
		FileInputStream fileIS = null; 
		try {
			fileIS = new FileInputStream(nameFile);
		} catch (FileNotFoundException err) {
			err.printStackTrace();
		}
	
        try {
			while ((needPro = fileIS.read()) != -1) {	//STRING CONVERSION
				//char conversion
			    stringFile += (char) needPro; 	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		    DataInputStream inp = new DataInputStream(fileIS);
		    buffRead = new BufferedReader(new InputStreamReader(inp));
		    
			//String is added to the stringFileARR
		   stringFileArr = stringFile.split("\n");
		   for(int i=0; i < stringFileArr.length; i++){
			   stringFileArr[i] =  stringFileArr[i].trim();
       }
		   
	}
	
	//Checks if there is more work to do (boolean)
	public boolean hasMoreLines() {
		if(counter != stringFileArr.length) {
			return true;
		} else {
			return false;
		}
	}
	//Gets the next instruction and makes it the current instruction (string)
	public  void goNextInstruction(){
		counter++;
	}

	public String zeroAdder(String intN)
	{
		StringBuilder sb = new StringBuilder();
		for (int i=16-intN.length(); i>0; i--) {
		    sb.append('0');
		}
		sb.append(intN);
		return sb.toString();
	}

	/*Returns the current instruction type (constant):

	A_INSTRUCTION for @ xxx, where xxx is either a decimal number or a symbol
	C_INSTRUCTION for dest = comp ; jump
	L_INSTRUCTION for (label)

	*/
	//Parsing the current instruction:
	public commandType typeCom()
	{
		
		if(stringFileArr[counter].startsWith("("))
		{
			return commandType = commandType.L_COMMAND;
		}
		if(stringFileArr[counter].startsWith("@"))
		{
			return commandType = commandType.A_COMMAND;
		} else {
			return commandType.C_COMMAND;
		}
	}
	

	
	public enum commandType{
		A_COMMAND,L_COMMAND,C_COMMAND
	}
	

	public boolean numCheck(String num)
	{
		NumberFormat formatter = NumberFormat.getInstance();
		  ParsePosition pos = new ParsePosition(0);
		  formatter.parse(num, pos);
		  return  num.length() == pos.getIndex();
		
	}
	 
	//Returns the instruction’s symbol (string)
	public String symbol()
	{
		String lable = "";
		if(stringFileArr[counter].startsWith("@"))
		{
			lable = stringFileArr[counter];
			lable = lable.replaceAll("@", "");
		}
		else 
			if(stringFileArr[counter].startsWith("("))
			{
				lable = stringFileArr[counter];
				lable = lable.replaceAll("\\((.*?)\\)", "$1");
			}
		return lable;
	}
	//Returns the instruction’s dest field (string)
	public String dest() {
		if(stringFileArr[counter].contains("="))
		{
		String rDest = stringFileArr[counter];
		
		int eIndex = rDest.lastIndexOf("=");
		rDest =  rDest.substring(0,eIndex);
		return rDest;
		}
		return null;
	}

	//Returns the instruction’s comp field (string)
	public String comp() {
		String rComp = stringFileArr[counter]; 
		if(stringFileArr[counter].contains("="))
		{
		int eIndex = rComp.lastIndexOf("=");
		rComp =  rComp.substring(eIndex+1,rComp.length());
		}
		else if(stringFileArr[counter].contains(";"))
		{
			//retComp =  retComp.substring(0,1) ;
			int eIndex = rComp.lastIndexOf(";");
			rComp =  rComp.substring(0,eIndex);
		}
		return rComp;
	}

	//Returns the instruction’s jump field (string)
	public String jump() {
		if(stringFileArr[counter].contains(";"))
		{
			String retJump = stringFileArr[counter]; 
			int endIndex = retJump.lastIndexOf(";");
			return retJump.substring(endIndex+1,retJump.length());
		}
		return null;
	}
	
}
