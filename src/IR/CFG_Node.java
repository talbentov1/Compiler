package IR;

import java.util.HashSet;
import java.util.ArrayList;

public class CFG_Node {
    private static int idCounter = 0; 
    private int uniqueId; 

    public CFG_Node() {
        this.uniqueId = idCounter++; // Assign unique id and increment counter

    }

    public int getId() {
        return uniqueId; 
    }

    public void addIRcommandWithPrevNext(IRcommand_With_Prev_And_Next command) {
        
    }

}
