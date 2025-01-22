package AST;

import TYPES.TYPE;

public class AST_FUNC_DEC extends AST_DEC
{
	public AST_FUNCDEC_TYPE functionDec;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_FUNC_DEC(AST_FUNCDEC_TYPE functionDec, int line)
	{
		super(line);
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== dec -> functionDec");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.functionDec = functionDec;
	}

	/***************************************************/
    /* The printing message for a function declaration AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION DECLARATION */
        /*********************************/
        System.out.println("AST NODE FUNCTION DECLARATION");

        /******************************************/
        /* RECURSIVELY PRINT functionDec ... */
        /******************************************/
        if (functionDec != null) functionDec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION DECLARATION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (functionDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, functionDec.SerialNumber);
    }

    public TYPE SemantMe() {
        if (functionDec != null) {
            /***************************************/
            /* [1] Semantically analyze the function declaration */
            /***************************************/
            return functionDec.SemantMe();
        }
        return null;
    }
}