package IR;

import IR.*;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class CFG {
    private ArrayList<CFG_Node> nodes;
    private ArrayList<CFG_Edge> edges;
    
    private static CFG CFG_instance = null;

    private IRcommand currentCommand;
    private IRcommandList commandsList;

    private CFG() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        currentCommand = null;
        commandsList = null;
    }

    public static CFG getInstance() {
        if (CFG_instance == null) {
            CFG_instance = new CFG();
        }
        return CFG_instance;
    }

    // creates a new CFG_Node, adds it to the nodes list and returns it
    public CFG_Node createAndAddNode() {
        CFG_Node newNode = new CFG_Node();
        nodes.add(newNode);
        return newNode;
    }

    // creates a new CFG_Edge, adds it to the edges list
    public void createAndAddEdge(CFG_Node in, CFG_Node out) {
        edges.add(new CFG_Edge(in, out));
    }

    // for debug
    public void printCFG() {
        for (CFG_Node node : nodes) {
            node.printNodeCommands(); // need to implement
        }
        for (CFG_Edge edge : edges) {
            System.out.println(edge.toString());
        }
    }

    public void buildCFG() {

		currentCommand = IR.getInstance().get_head(); 
        commandsList = IR.getInstance().get_tail(); // IR commands list obtained by IRme() 

        CFG_Node currentNode = createAndAddNode(); // create the first CFG node
        IRcommand_With_Prev_And_Next currentCommandWithPrevNext = new IRcommand_With_Prev_And_Next(currentCommand);
        IRcommand_With_Prev_And_Next prevCommandWithPrevNext = null;
        currentNode.addIRcommandWithPrevNext(currentCommandWithPrevNext);

        if(commandsList == null){
            currentCommand = null;
            commandsList = null;
            prevCommandWithPrevNext = null;
        }
        else{
            prevCommandWithPrevNext = currentCommandWithPrevNext;
            currentCommand = commandsList.get_head();
            commandsList = commandsList.get_tail();
        }

    }

}
