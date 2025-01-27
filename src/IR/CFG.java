package IR;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CFG {
    private int scope;
    private ArrayList<CFG_Node> nodes;
    private ArrayList<CFG_Edge> edges;
    private Set<String> varNames;

    private static CFG CFG_instance = null;

    private CFG() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        varNames = new TreeSet<>();
        scope = 0;
    }

    public static CFG getInstance() {
        if (CFG_instance == null) {
            CFG_instance = new CFG();
        }
        return CFG_instance;
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
        nodes.clear();
        edges.clear();

        HashMap<String, CFG_Node> labelToNodeMap = new HashMap<>();
        HashMap<CFG_Node, String> pendingJumps = new HashMap<>();

        // Iterate through the command list and create nodes
        IRcommand current = IR.getInstance().get_head();
        IRcommandList next = IR.getInstance().get_tail();
        
        while (current != null) {
            CFG_Node currentNode = new CFG_Node(current, scope);
            nodes.add(currentNode);

            advanceScope(current);

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

            addVarNames(current);

            if (next != null){
                current = next.get_head();
                next = next.get_tail();
            }
            else{
                current = null;
                next = null;
            }
        }

        // Connect nodes with edges
        for (int i = 0; i < nodes.size(); i++) {
            CFG_Node currentNode = nodes.get(i);
            IRcommand command = currentNode.getCommand();

            // Connect to the next command (if not a jump)
            if (!(command instanceof IRcommand_Jump_Label || command instanceof IRcommand_Jump_If_Eq_To_Zero)) {
                if (i + 1 < nodes.size()) {
                    CFG_Node nextNode = nodes.get(i + 1);
                    edges.add(new CFG_Edge(currentNode, nextNode));
                    currentNode.addToOutNodes(nextNode);
                    nextNode.addToInNodes(currentNode);
                }
            }

            // Resolve pending jumps
            if (pendingJumps.containsKey(currentNode)) {
                String targetLabel = pendingJumps.get(currentNode);
                if (labelToNodeMap.containsKey(targetLabel)) {
                    CFG_Node targetNode = labelToNodeMap.get(targetLabel);
                    edges.add(new CFG_Edge(currentNode, targetNode));
                    currentNode.addToOutNodes(targetNode);
                    targetNode.addToInNodes(currentNode);
                } else {
                    System.err.println("Warning: Label " + targetLabel + " not found for jump command.");
                }
            }
        }
    }

    public void advanceScope(IRcommand command){
        if (command instanceof IRcommandScopeStart){
            this.scope++;
        }
        if (command instanceof IRcommandScopeEnd){
            this.scope--;
        }
    }

    public void addVarNames(IRcommand command){
        if (command instanceof IRcommand_Store){
            varNames.add(((IRcommand_Store) command).var_name);
        }
        if (command instanceof IRcommand_Load){
            varNames.add(((IRcommand_Load) command).var_name);
        }
        if (command instanceof IRcommand_Allocate){
            varNames.add(((IRcommand_Allocate) command).var_name);
        }
    }

    public ArrayList<CFG_Node> getNodes(){
        return this.nodes;
    }

    public ArrayList<CFG_Edge> getEdges(){
        return this.edges;
    }

    public int size(){
        return this.nodes.size();
    }

    public Set<String> getVarNames(){
        return this.varNames;
    }

}
