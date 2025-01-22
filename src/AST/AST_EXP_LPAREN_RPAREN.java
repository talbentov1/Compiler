package AST;

import TYPES.TYPE;

public class AST_EXP_LPAREN_RPAREN extends AST_EXP
{
	public AST_EXP exp;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_LPAREN_RPAREN(AST_EXP exp, int line)
	{
		super(line);
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> (exp)\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.exp = exp;
	}
	
	/***************************************************/
    /* The printing message for an expression in parenthesis AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = EXPRESSION IN PARENTHESIS = "(EXPRESSION)" " */
        /*********************************/
        System.out.println("AST NODE (EXPRESSION)");

        /******************************************/
        /* RECURSIVELY PRINT exp ... */
        /******************************************/
        if (exp != null) exp.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "(EXPRESSION)");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }
	
    public TYPE SemantMe() {
        /***************************************/
        /* [1] Semantically analyze the expression inside the parenthesis */
        /***************************************/
        TYPE t = exp.SemantMe();
        
        /***************************************/
        /* [2] Return the type of the expression inside the parenthesis */
        /***************************************/
        return t;
    }

    public boolean isExpConst() {
		return false;
	}
}
