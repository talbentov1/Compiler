package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_RETURN_NIL extends AST_STMT
{
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN_NIL(int line)
	{
        super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> RETURN\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
	}

	/***************************************************/
    /* The printing message for a return statement AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = RETURN */
        /*********************************/
        System.out.println("AST NODE RETURN");

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "RETURN");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
    }

    public TYPE SemantMe() {
        TYPE_FUNCTION function = SYMBOL_TABLE.getInstance().getCurrentFunction();
        if (function == null) {
            System.out.format(">> ERROR:[%d] Return statement outside of function scope", line);
            print_error_and_exit();
        }
    
        if (!(function.returnType instanceof TYPE_VOID)) {
            System.out.format(">> ERROR:[%d] Return statement with missing expression in non-void function", line);
            print_error_and_exit();
        }
    
        return TYPE_VOID.getInstance();
    }
}
