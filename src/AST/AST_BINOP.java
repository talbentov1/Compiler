package AST;
import TYPES.*;

public abstract class AST_BINOP extends AST_Node
{
    public AST_BINOP(int line){
        super(line);
    }
    public TYPE SemantMe() {
        return null;
    }
}
