package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import IR.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_VAR_DEC extends AST_DEC
{
	public AST_VARDEC_TYPE variableDec;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_DEC(AST_VARDEC_TYPE variableDec, int line)
	{
        super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== dec -> variableDec");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.variableDec = variableDec;
	}

	/***************************************************/
    /* The printing message for a var declaration AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = VAR DECLARATION */
        /*********************************/
        System.out.println("AST NODE VAR DEC");

        /******************************************/
        /* RECURSIVELY PRINT varDec ... */
        /******************************************/
        if (variableDec != null) variableDec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "VAR DEC");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (variableDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, variableDec.SerialNumber);
    }

    public TYPE SemantMe() {
        if (variableDec != null) {
            return variableDec.SemantMe();
        }
        return null;
    }

    public TEMP IRme() {
		this.variableDec.IRme();
		return null;
	}
}