package IR;

public class CFG_Edge {
    public CFG_Node in;
    public CFG_Node out;

    public CFG_Edge(CFG_Node in, CFG_Node out){
        this.in = in;
        this.out = out;
    }
    
}
