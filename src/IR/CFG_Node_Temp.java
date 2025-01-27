package IR;

import java.util.HashSet;
import java.util.ArrayList;

public class CFG_Node_Temp {
    private static int idCounter = 0; 
    private int uniqueId; 
    private IRcommand command;

    public CFG_Node_Temp(IRcommand command) {
        this.uniqueId = idCounter++; // Assign unique id and increment counter
        this.command = command;
    }

    public int getId() {
        return uniqueId; 
    }

    public IRcommand getCommand() {
        return this.command;
    }

    public void printNodeCommands(){
        this.command.printCommand();
    }
}