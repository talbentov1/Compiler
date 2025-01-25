package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public class AST_PROGRAM_LIST extends AST_PROGRAM
{
	public AST_DEC dec1;
	public AST_PROGRAM optionalDec2;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_PROGRAM_LIST(AST_DEC dec1, AST_PROGRAM optionalDec2, int line)
	{
		super(line);
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== program -> dec+\n");

		this.dec1 = dec1;
		this.optionalDec2 = optionalDec2;
	}

	/***************************************************/
    /* The printing message for a program list AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = PROGRAM LIST */
        /*********************************/
        System.out.println("AST NODE PROGRAM LIST");

        /******************************************/
        /* RECURSIVELY PRINT dec1, then optionalDec2 ... */
        /******************************************/
        if (dec1 != null) dec1.PrintMe();
        if (optionalDec2 != null) optionalDec2.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "PROGRAM LIST");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (dec1 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, dec1.SerialNumber);
        if (optionalDec2 != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, optionalDec2.SerialNumber);
    }

    public TYPE SemantMe() {
        /*********************************/
        /* [1] Semantically analyze dec1 */
        /*********************************/
        if(dec1 != null){dec1.SemantMe();}
    
        /****************************************************/
        /* [2] Semantically analyze optionalDec2 recursively*/
        /****************************************************/
        if (optionalDec2 != null) {optionalDec2.SemantMe();}
    
        /******************************************/
        /* [3] doesn't matter what to return here */
        /******************************************/
        return null;
    }

    public TEMP IRme() {
		if(dec1 != null){dec1.IRme();}
        if (optionalDec2 != null) {optionalDec2.IRme();}
        return null;
    }

}