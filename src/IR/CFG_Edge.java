package IR;

public class CFG_Edge {
    public CFG_Node_Temp from;
    public CFG_Node_Temp to;

    public CFG_Edge(CFG_Node_Temp from, CFG_Node_Temp to){
        this.from = from;
        this.to = to;
    }

    public CFG_Node_Temp getFrom() {
        return from;
    }

    public CFG_Node_Temp getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Edge from Node " + from.getId() + " to Node " + to.getId();
    }
    
}
