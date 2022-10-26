

/*  Symbol table class
Constructor / initializer: Creates and initializes a SymbolTable

addEntry(symbol (string), address (int)): Adds <symbol, address> to the table (void)

contains(symbol (string)): Checks if symbol exists in the table (boolean)

getAddress(symbol (string)): Returns the address (int) associated with symbol
*/
//HANDLES SYMBOLS

import java.util.Map;
import java.util.TreeMap;


public class symboltable {

   private static Map<String, String> symbols;
    //Symbol values got from table

   public symboltable()
    {
        symbols = new TreeMap<String,String>(); 

            //R0, R1, R2, - R15 == (0 - 15)
           symbols.put("R0", "0");
           symbols.put("R1", "1");
           symbols.put("R2", "2");
           symbols.put("R3", "3");
           symbols.put("R4", "4");
           symbols.put("R5", "5");
           symbols.put("R6", "6");
           symbols.put("R7", "7");
           symbols.put("R8", "8");
           symbols.put("R9", "9");
           symbols.put("R10", "10");
           symbols.put("R11", "11");
           symbols.put("R12", "12");
           symbols.put("R13", "13");
           symbols.put("R14", "14");
           symbols.put("R15", "15");

           //screen kbd, sp
           symbols.put("SCREEN", "16384");
           symbols.put("KBD", "24576");
           symbols.put("SP", "0");

            //LCL - sum
           symbols.put("LCL", "1");
           symbols.put("ARG", "2");
           symbols.put("THIS", "3");
           symbols.put("THAT", "4");
           symbols.put("LOOP", "4");
           symbols.put("STOP", "18");
           symbols.put("i", "16");
           symbols.put("sum", "17");
    }
   
  //Adds <symbol, address> to the table (void)
   public void addEntry(String symbol, String address) {
       symbols.put(symbol, address);	
   }
 //Checks if symbol exists in the table (boolean)
   public boolean contains(String symbol){
       return symbols.containsKey(symbol);
   }
   //Returns the address (int) associated with symbol
   public String getAddress(String symbol){
       return	symbols.get(symbol);	
   }
   
}
