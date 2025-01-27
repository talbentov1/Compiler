package IR;

import java.util.HashSet;
import java.util.ArrayList;

public class CFG_Node {
    private static int idCounter = 0; 
    private int uniqueIndex; 
    private int scope;
    private IRcommand command;
    private ArrayList<CFG_Node> inNodes;
    private ArrayList<CFG_Node> outNodes;
    private HashSet<Dom> analysisIn;
    private HashSet<Dom> analysisOut;

    public CFG_Node(IRcommand command, int currScope) {
        this.uniqueIndex = idCounter;
        this.command = command;
        idCounter++;
        scope = currScope;
    }

    public int getIndex() {
        return uniqueIndex; 
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

    public HashSet<Dom> getAnalysisOut(){
        return this.analysisOut;
    }

    public void setAnalysisOut(HashSet<Dom> output){
        this.analysisOut = output;
    }

    public HashSet<Dom> getAnalysisIn(){
        return this.analysisIn;
    }

    public void setAnalysisIn(HashSet<Dom> input){
        this.analysisIn = input;
    }

    public int getScope(){
        return scope;
    }
}