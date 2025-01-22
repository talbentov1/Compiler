package AST;

import TYPES.TYPE;

public abstract class AST_CFIELD extends AST_Node
{
    public AST_CFIELD(int line){
        super(line);
    }
    public TYPE SemantMe() {
        return null;
    }
}