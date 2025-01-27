package IR;
import java.util.ArrayList;
import java.util.HashSet;


public abstract class Analysis {
    public String name;
    public boolean directionIsForward; // false means that the analysis direction is backwards
    public boolean joinIsUnion; // false means that join operation is intersection
    public boolean chaoticMode; // modes can be: chaotic / fixpoint, false means fixpoint
    public HashSet<Pair> initialValue;
    public CFG cfg;

    public Analysis(String name, boolean forward, boolean union, boolean chaoticMode, HashSet<Pair> initialValue, CFG cfg){
        System.out.println("initializing Analysis: " + name);
        this.name = name;
        this.directionIsForward = forward;
        this.joinIsUnion = union;
        this.chaoticMode = chaoticMode;
        this.initialValue = initialValue;
        this.cfg = cfg;
    }

    public abstract HashSet<Pair> kill(CFG_Node node);
    public abstract HashSet<Pair> gen(CFG_Node node);

    public void transfer(CFG_Node node){
        calcNodeInput(node);
        HashSet<Pair> in_i = node.getAnalysisIn();
        HashSet<Pair> kill_i = kill(node);
        HashSet<Pair> gen_i = gen(node);
        HashSet<Pair> out = new HashSet<>(in_i);
        out.removeAll(kill_i);
        out.addAll(gen_i);

        node.setAnalysisOut(out);
    }

    public void calcNodeInput(CFG_Node node){
        ArrayList<CFG_Node> inNodes = node.getInNodes();
        HashSet<Pair> result = new HashSet<>();
        
        if (inNodes.isEmpty()) {
            result = this.initialValue;
        }
        else if (this.joinIsUnion) {
            for (CFG_Node inNode : inNodes) {
                result.addAll(inNode.getAnalysisOut());
            }
        } else {
            result.addAll(inNodes.get(0).getAnalysisOut());
            for (int i = 1; i < inNodes.size(); i++) {
                result.retainAll(inNodes.get(i).getAnalysisOut());
            }
        }
        node.setAnalysisIn(result);
    }

    public void runAnalysis(){
        System.out.println("Running analysis: " + this.name);
        if (!this.directionIsForward){
            reverseCfg();
        }
        if (this.chaoticMode){
            startChaoticIterations();
        }
        else{
            startFixpoint();
        }
    }

    public void startChaoticIterations(){
        System.out.println("Starting chaotic iterations...");
        ArrayList<Integer> workList = new ArrayList<>();
        ArrayList<CFG_Node> nodes = cfg.getNodes();
        workList.add(0);

        // still need to implement rest of chaotic iterations

    }

    public void reverseCfg(){
        System.out.println("Don't need to implement backwards analysis yet...");
    }

    public void startFixpoint(){
        System.out.println("Don't need to implement fixpoint analysis yet...");
    }
    
}
