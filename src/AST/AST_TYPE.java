package AST;

import TYPES.TYPE;

public abstract class AST_TYPE extends AST_Node {
    
    public AST_TYPE(int line){
        super(line);
    }

    public TYPE SemantMe() {
        return null;
    }
    
}