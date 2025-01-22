package AST;
import TYPES.TYPE;


public abstract class AST_CLASSDEC_TYPE extends AST_Node
{
    public AST_CLASSDEC_TYPE(int line){
        super(line);
    }
    public TYPE SemantMe() {
        return null;
    }
}