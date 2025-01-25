package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public abstract class AST_NEW_EXP extends AST_Node
{
    public AST_NEW_EXP(int line) {
        super(line);
    }
    
    public TYPE SemantMe() {
        return null;
    }

    public TEMP IRme(){
        return null;
    }
}