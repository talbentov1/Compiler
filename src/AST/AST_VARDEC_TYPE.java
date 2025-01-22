package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public abstract class AST_VARDEC_TYPE extends AST_Node
{
    public AST_VARDEC_TYPE(int line){
        super(line);
    }
    public TYPE SemantMe() {
        return null;
    }
}