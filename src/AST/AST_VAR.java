package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import IR.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public abstract class AST_VAR extends AST_Node
{
    public AST_VAR(int line){
        super(line);
    }
    
    public TYPE SemantMe() {
        return null;
    }

    public TEMP IRme() {
		return null;
	}
}
