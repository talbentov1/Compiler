package IR;

public class CFG_Edge {
    public CFG_Node from;
    public CFG_Node to;

    public CFG_Edge(CFG_Node from, CFG_Node to){
        this.from = from;
        this.to = to;
    }

    public CFG_Node getFrom() {
        return from;
    }

    public CFG_Node getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Edge from Node " + from.getId() + " to Node " + to.getId();
    }
    
}
