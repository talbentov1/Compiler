package AST;

import IR.IR;
import IR.IRcommand_PrintInt;
import TEMP.TEMP;
import TYPES.TYPE;

public class AST_EXP_FUNC_CALL extends AST_EXP
{
	public AST_FUNC_CALL f;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_FUNC_CALL(AST_FUNC_CALL f, int line)
	{
		super(line);
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		//System.out.print("====================== exp ->  (var DOT)? ID LPAREN (exp (COMMA exp)*)? RPAREN\n");
        System.out.print("====================== exp ->  funcCall\n");
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.f = f;
	}

	/***************************************************/
    /* The printing message for a function call expression AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = EXPRESSION FUNCTION CALL */
        /*********************************/
        System.out.println("AST NODE EXPRESSION FUNCTION CALL");

        /******************************************/
        /* RECURSIVELY PRINT f ... */
        /******************************************/
        if (f != null) f.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "EXPRESSION\nFUNCTION CALL");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (f != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, f.SerialNumber);
    }

    public TYPE SemantMe() {
        if (f != null) {
            /***************************************/
            /* [1] Semantically analyze the function call */
            /***************************************/
            TYPE t = f.SemantMe();
            
            /**************************************************/
            /* [2] Return the type of the function call */
            /**************************************************/
            return t;
        }
        else {
            System.out.format(">> ERROR($d) EXPRESSION FUNCTION CALL is null", line);
            print_error_and_exit();
            return null;
        }
    }

    public TEMP IRme()
	{
		// if (f != null) { f.IRme(); }
		// return null;

        if (f != null) { return f.IRme(); }
		return null;
	}

    public boolean isExpConst() {
		return false;
	}
}
