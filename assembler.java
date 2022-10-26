/*
 * 
 * 
 * 
 * Initialize:
Opens the input file (Prog.asm) and gets ready to process it
Constructs a symbol table, and adds to it all the predefined symbols
Second pass (main loop):
(starts again from the beginning of the file)
While there are more lines to process:
Gets the next instruction, and parses it
If the instruction is @ symbol
If symbol is not in the symbol table, adds it
Translates the symbol into its binary value
If the instruction is dest =comp ; jump
Translates each of the three fields into its binary value
Assembles the binary values into a string of sixteen 0’s and 1’s
Writes the string to the output file.
 */



import java.io.BufferedWriter;
import java.io.File;


import java.io.FileWriter;
import java.io.IOException;

public class assembler {

	public static int ramNext = 16;
	public static int count=0;
	public static String tComp;
	public static String tDest;
	public static String tJump;
	public static void main(String[] args) {

		//Copy name of OLD FILE
		String oldName = args[0].substring(0, args[0].indexOf('.'));	
		//ADD .hack at the end
		String outFileName = oldName+".hack";  
		
		//Make new SYMBOL TABLE and PARSE
		symboltable symTable = new symboltable(); 
		//CODE init
		code cota = new code();
		//new parse 
		parser parseNew = new parser(args[0]);  
	
		//hack file out
		File output = new File(outFileName); 
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(output.getAbsoluteFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter buffW = new BufferedWriter(fw); 
		//WE can now write to the file


		/* First pass:
		Reads the program lines, one by one
		focusing only on (label) declarations.
		Adds the found labels to the symbol table */
		//WHILE we have lines to process
		while(parseNew.hasMoreLines()) {  

		if(parseNew.typeCom()== parser.commandType.L_COMMAND) { 
			//ADD the found labels to the SYMBOL TABLE (symTable)
			symTable.addEntry(parseNew.symbol(),Integer.toString(count));
		else{
			count++; 
			//GO to the next line
			parseNew.goNextInstruction();  
		
		}

		//RESET THE COUNTER
		parseNew.counter = 0;   
		
		//second pass

		/*
		 * Second pass (main loop):
		(starts again from the beginning of the file)
		While there are more lines to process:
		Gets the next instruction, and parses it
		If the instruction is @ symbol
		If symbol is not in the symbol table, adds it
		Translates the symbol into its binary value
		If the instruction is dest =comp ; jump
		Translates each of the three fields into its binary value
		Assembles the binary values into a string of sixteen 0’s and 1’s
		Writes the string to the output file.
		 */

		while(parseNew.hasMoreLines())
		{
			if(parseNew.typeCom()== parser.commandType.A_COMMAND) 
			{
				//If the instruction is @ symbol
				if(parseNew.stringFileArr[parseNew.counter].startsWith("@"))
				{
				String tempor  = parseNew.symbol(); 	
					if(parseNew.numCheck(tempor)) 
					{
						
						int xS = Integer.parseInt(tempor);
						//bin = xS
						tempor = parseNew.zeroAdder(tempor);
						try {
							buffW.write(tempor + '\n');
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					//add
					else  
					{	
						//symTABLE has it in the SYMBOL TABLE
						 if(symTable.contains(tempor)) 
							{
							String tempor2 = symTable.getAddress(tempor);
							int xxx = Integer.parseInt(tempor2);
							tempor2 = parseNew.zeroAdder(tempor2);

							///WE CAN WRITE TO HACK
							try {
								buffW.write(tempor2+'\n');  
							} catch (IOException e) {
								e.printStackTrace();
							}
							//it is not in symTable
							if(!symTable.contains(tempor))  
							{
								symTable.addEntry(tempor,Integer.toString(ramNext));  
								ramNext++;
							}
						}
					}
				}
			} 
			if(parseNew.typeCom()== parser.commandType.C_COMMAND)

			//A stuff
			{	
				//for jump
				if(parseNew.stringFileArr[parseNew.counter].contains(";")) 
				{
					tDest = cota.forDest("NULL"); 
					tComp = cota.forComp(parseNew.comp());
					tJump = cota.forJump(parseNew.jump());
					
				}
				//dest comp
				else if(parseNew.stringFileArr[parseNew.counter].contains("="))
				{
					tJump = cota.forDest(parseNew.jump());
					tComp = cota.forComp(parseNew.comp());
					tDest = cota.forJump("NULL");  
				}
			}
			try {
				buffW.write("111" + tDest + tJump + tComp + '\n');
			} catch (IOException e) {
				e.printStackTrace();
			}
			//if C keep going
			parseNew.goNextInstruction();		
		}
}
}