package IR;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
            for (int i=0; i<cfg.size(); i++) {
                killSet.add(new Dom(varName, i));
            }
            killSet.add(new Dom(varName, null));
        }
        if (command instanceof IRcommand_Load){
            // might want to modify based on scope
            IRcommand_Load loadCommand = (IRcommand_Load) command;
            String tempName = "t" + loadCommand.dst.serial;
            for (int i=0; i<cfg.size(); i++) {
                killSet.add(new Dom(tempName, i));
            }
            killSet.add(new Dom(tempName, null));
        }

        if(command instanceof IRcommand_Binop_Sub_Integers 
            || command instanceof IRcommand_Binop_Add_Integers
            || command instanceof IRcommand_Binop_Div_Integers
            || command instanceof IRcommand_Binop_Mul_Integers
            || command instanceof IRcommand_Binop_EQ_Integer
            || command instanceof IRcommand_Binop_GT_Integers
            || command instanceof IRcommand_Binop_LT_Integers
        ) {
            // might not work without casting, if so, we would need to seperate this if to 7 different if :(
            String tempName = "t" + command.dst.serial;
            for (int i=0; i<cfg.size(); i++) {
                killSet.add(new Dom(tempName, i));
            }
            killSet.add(new Dom(tempName, null));
        }
        return killSet;
    }

    @Override
    public HashSet<Dom> gen(CFG_Node node){
        HashSet<Dom> genSet = new HashSet<>();
        IRcommand command = node.getCommand();
        HashSet<Dom> inSet = node.getAnalysisIn();

        if(command instanceof IRcommand_Binop_Sub_Integers 
            || command instanceof IRcommand_Binop_Add_Integers
            || command instanceof IRcommand_Binop_Div_Integers
            || command instanceof IRcommand_Binop_Mul_Integers
            || command instanceof IRcommand_Binop_EQ_Integer
            || command instanceof IRcommand_Binop_GT_Integers
            || command instanceof IRcommand_Binop_LT_Integers
        ) {
            // might not work without casting, if so, we would need to seperate this if to 7 different if :(
            if(this.isLabelNull(inSet, "t" + command.t1.serial) || if(this.isLabelNull(inSet, "t" + command.t2.serial))) {
                genSet.add(new Dom("t" + command.dst.serial, null));
            } else {
                genSet.add(new Dom("t" + command.dst.serial, node.getIndex()));
            }
        }

        if (command instanceof IRcommandConstInt) {
            IRcommandConstInt constIntCommand = (IRcommandConstInt) command;
            genSet.add(new Dom("t" + constIntCommand.t.serial, node.getIndex()));
        }
        
        if (command instanceof IRcommand_load) {
            IRcommand_load loadCommand = (IRcommand_load) command;
            // might want to modify based on scope
            if(this.isLabelNull(inSet, loadCommand.var_name)) {
                genSet.add(new Dom("t" + loadCommand.dst.serial, null));
            }
            else {
                genSet.add(new Dom("t" + loadCommand.dst.serial, node.getIndex()));
            }
        }

        if (command instanceof IRcommand_Store) {
            IRcommand_Store storeCommand = (IRcommand_Store) command;
            // might want to modify based on scope
            if(this.isLabelNull(inSet, "t" + storeCommand.src.serial)) {
                genSet.add(new Dom(storeCommand.var_name, null));
            }
            else {
                genSet.add(new Dom(storeCommand.var_name, node.getIndex()));
            }
        }

        return genSet;
    }
    
    @Override
    public void analyzeResult() {
        ArrayList<CFG_Node> nodes = cfg.getNodes();
        TreeSet<String> foundVars = new TreeSet<>();
    
        for (CFG_Node node : nodes) {
            String usedVar = node.usedVarInNode();

            if (usedVar == null) {
                continue;
            }

            HashSet<Dom> inSet = node.getAnalysisIn();
            boolean found = inSet.stream().anyMatch(dom -> usedVar.equals(dom.getVarName()) && dom.getLabel() == null);
    
            if (found) {
                foundVars.add(usedVar);
            }
        }
        
        if (foundVars.size() == 0){
            file_writer.println("!OK");
        }
        else{
            for (String var : foundVars) {
                file_writer.println(var);
            }
        }
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

    public boolean isLabelNull(HashSet<Dom> inSet, String name) {
        return inSet.stream()
                .filter(dom -> name.equals(dom.getVarName()))
                .findFirst()
                .map(dom -> dom.getLabel() == null)
                .orElse(false);
    }
}
