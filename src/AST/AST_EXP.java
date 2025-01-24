package AST;

import TEMP.TEMP;
import TYPES.*;

public abstract class AST_EXP extends AST_Node
{
	public AST_EXP(int line) {
        super(line);
    }
	
	public TYPE SemantMe()
	{
		return null;
	}

	public TEMP IRme(){
		return null;
	}

	public abstract boolean isExpConst();
}