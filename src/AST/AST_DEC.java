package AST;
import TYPES.TYPE;


public abstract class AST_DEC extends AST_Node
{
    public AST_DEC(int line){
        super(line);
    }
    public TYPE SemantMe() {
        return null;
    }
}