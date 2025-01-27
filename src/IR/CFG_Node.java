package IR;

import java.util.HashSet;
import java.util.ArrayList;

public class CFG_Node {
    private static int idCounter = 0; 
    private int uniqueId; 
    private IRcommand command;
    private ArrayList<CFG_Node> inNodes;
    private ArrayList<CFG_Node> outNodes;
    private HashSet<Pair> analysisIn;
    private HashSet<Pair> analysisOut;

    public CFG_Node(IRcommand command) {
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

    public void addToInNodes(CFG_Node node){
        this.inNodes.add(node);
    }

    public void addToOutNodes(CFG_Node node){
        this.outNodes.add(node);
    }

    public ArrayList<CFG_Node> getInNodes(){
        return this.inNodes;
    }

    public ArrayList<CFG_Node> getOutNodes(){
        return this.outNodes;
    }

    public HashSet<Pair> getAnalysisOut(){
        return this.analysisOut;
    }

    public void setAnalysisOut(HashSet<Pair> output){
        this.analysisOut = output;
    }

    public HashSet<Pair> getAnalysisIn(){
        return this.analysisIn;
    }

    public void setAnalysisIn(HashSet<Pair> input){
        this.analysisIn = input;
    }
}