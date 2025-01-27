package IR;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Uninitialized_Variable_Analysis extends Analysis {
    public static CFG cfg;
    public static HashSet<Dom> initialValue;
    public PrintWriter file_writer;

    public Uninitialized_Variable_Analysis(PrintWriter file_writer){
        super("uninitialized variables", true, true, true, initialValue, cfg);
        this.file_writer = file_writer;
    }

    @Override
    public HashSet<Dom> kill(CFG_Node node){
        HashSet<Dom> killSet = new HashSet<>();
        IRcommand command = node.getCommand();
        if (command instanceof IRcommand_Store){
            // might want to modify based on scope
            IRcommand_Store storeCommand = (IRcommand_Store) command;
            String varName = storeCommand.var_name;
            for (int i=0; i<cfg.size(); i++){
                killSet.add(new Dom(varName, i));
            }
        }

        return killSet;
    }

    @Override
    public HashSet<Dom> gen(CFG_Node node){
        HashSet<Dom> genSet = new HashSet<>();
        IRcommand command = node.getCommand();
        if (command instanceof IRcommand_Store){
            IRcommand_Store storeCommand = (IRcommand_Store) command;
            // might want to modify based on scope
            genSet.add(new Dom(storeCommand.var_name, node.getIndex()));
        }
        return genSet;
    }
    
    @Override
    public void analyzeResult(){

    }

    public static void prepare(){
        prepareCFG();
        prepareInitialValue();
    }

    public static void prepareCFG(){
        System.out.println("preparing the CFG for this analysis");
        cfg = CFG.getInstance();
        cfg.buildCFG();
    }

    public static void prepareInitialValue(){
        System.out.println("preparing the initial value of the analysis, based on the CFG");
        HashSet<Dom> initVal = new HashSet<>();
        Set<String> varNames = cfg.getVarNames();
        for (String varName : varNames) {
            initVal.add(new Dom(varName, null));   
        }

        initialValue = initVal;
    }
}
