package AST;

import TYPES.TYPE;

public abstract class AST_FUNC_CALL extends AST_Node
{
    public AST_FUNC_CALL(int line){
        super(line);
    }
    public TYPE SemantMe(){
        return null;
    }
}