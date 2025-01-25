package AST;

import TYPES.TYPE;
import TEMP.TEMP;

public abstract class AST_FUNCDEC_TYPE extends AST_Node
{
    public AST_FUNCDEC_TYPE(int line) {
        super(line);
    }
    
    public TYPE SemantMe() {
        return null;
    }

    public TEMP IRme() {
        return null;
    }
}