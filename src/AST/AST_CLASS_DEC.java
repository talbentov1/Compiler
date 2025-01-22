package AST;

import TYPES.TYPE;

public class AST_CLASS_DEC extends AST_DEC
{
	public AST_CLASSDEC_TYPE classDec;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CLASS_DEC(AST_CLASSDEC_TYPE classDec, int line)
	{
        super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== dec -> classDec");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.classDec = classDec;
	}

	/***************************************************/
    /* The printing message for a class declaration AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASS DECLARATION */
        /*********************************/
        System.out.println("AST NODE CLASS DECLARATION");

        /******************************************/
        /* RECURSIVELY PRINT classDec ... */
        /******************************************/
        if (classDec != null) classDec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "CLASS DECLARATION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (classDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, classDec.SerialNumber);
    }

    public TYPE SemantMe() {
        if (classDec != null){
            return classDec.SemantMe();
        }
        return null;
    }
}