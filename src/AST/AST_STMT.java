package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import IR.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public abstract class AST_STMT extends AST_Node
{
	public AST_STMT(int line){
		super(line);
	}
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE");
	}

	public TYPE SemantMe() {
		return null;		
    }

	public TEMP IRme() {
		return null;
	}
}
