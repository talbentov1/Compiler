package IR;

import IR.*;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class CFG {
    private ArrayList<CFG_Node_Temp> nodes;
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

    // creates a new CFG_Node_Temp, adds it to the nodes list and returns it
    public CFG_Node_Temp createAndAddNode() {
        CFG_Node_Temp newNode = new CFG_Node_Temp();
        nodes.add(newNode);
        return newNode;
    }

    // creates a new CFG_Edge, adds it to the edges list
    public void createAndAddEdge(CFG_Node_Temp in, CFG_Node_Temp out) {
        edges.add(new CFG_Edge(in, out));
    }

    // for debug
    public void printCFG() {
        for (CFG_Node_Temp node : nodes) {
            node.printNodeCommands(); // need to implement
        }
        for (CFG_Edge edge : edges) {
            System.out.println(edge.toString());
        }
    }

    public void buildCFG() {
        nodes.clear();
        edges.clear();

        HashMap<String, CFG_Node_Temp> labelToNodeMap = new HashMap<>();
        HashMap<CFG_Node_Temp, String> pendingJumps = new HashMap<>();

        // Iterate through the command list and create nodes
        IRcommand current = IR.getInstance().get_head();
        while (current != null) {
            CFG_Node_Temp currentNode = new CFG_Node_Temp(current);
            nodes.add(currentNode);

            if (current instanceof IRcommand_Label) {
                IRcommand_Label labelCommand = (IRcommand_Label) current;
                labelToNodeMap.put(labelCommand.label_name, currentNode);
            }

            if (current instanceof IRcommand_Jump_If_Eq_To_Zero || current instanceof IRcommand_Jump_Label) {
                String targetLabel;
                if (current instanceof IRcommand_Jump_If_Eq_To_Zero) {
                    targetLabel = ((IRcommand_Jump_If_Eq_To_Zero) current).label_name;
                } else {
                    targetLabel = ((IRcommand_Jump_Label) current).label_name;
                }
                pendingJumps.put(currentNode, targetLabel);
            }

            current = current.nextCommand;
        }

        // Connect nodes with edges
        for (int i = 0; i < nodes.size(); i++) {
            CFG_Node_Temp currentNode = nodes.get(i);
            IRcommand command = currentNode.getCommand();

            // Connect to the next command (if not a jump)
            if (!(command instanceof IRcommand_Jump_Label || command instanceof IRcommand_Jump_If_Eq_To_Zero)) {
                if (i + 1 < nodes.size()) {
                    CFG_Node_Temp nextNode = nodes.get(i + 1);
                    edges.add(new CFG_Edge(currentNode, nextNode));
                }
            }

            // Resolve pending jumps
            if (pendingJumps.containsKey(currentNode)) {
                String targetLabel = pendingJumps.get(currentNode);
                if (labelToNodeMap.containsKey(targetLabel)) {
                    CFG_Node_Temp targetNode = labelToNodeMap.get(targetLabel);
                    edges.add(new CFG_Edge(currentNode, targetNode));
                } else {
                    System.err.println("Warning: Label " + targetLabel + " not found for jump command.");
                }
            }
        }
    }

}
