package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public abstract class AST_PROGRAM extends AST_Node {
    
    public AST_PROGRAM(int line) {
        super(line);
    }
    
    public TYPE SemantMe() {
        return null;
    }

    public TEMP IRme() {
        return null;
    }
}
