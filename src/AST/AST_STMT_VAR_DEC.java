package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_VAR_DEC extends AST_STMT
{
	public AST_VARDEC_TYPE var;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_VAR_DEC(AST_VARDEC_TYPE var, int line)
	{
        super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> varDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
	}

	/***************************************************/
    /* The printing message for a variable declaration statement AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = STATEMENT VARIABLE DECLARATION */
        /*********************************/
        System.out.println("AST NODE VARIABLE DECLARATION");

        /******************************************/
        /* RECURSIVELY PRINT varDec ... */
        /******************************************/
        if (var != null) var.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "VARIABLE\nDECLARATION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
    }

    public TYPE SemantMe() {
        return var.SemantMe();
    }
}
