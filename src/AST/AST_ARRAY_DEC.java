package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import IR.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_ARRAY_DEC extends AST_DEC
{
	public AST_ARRAYDEC_TYPE arrayDec;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_ARRAY_DEC(AST_ARRAYDEC_TYPE arrayDec, int line)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== dec -> arrayTypedef");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.arrayDec = arrayDec;
	}

	/*************************************************/
	/* The printing message for array Typedef declaration AST node */
	/*************************************************/
	
	public void PrintMe()
	{
		
		/*************************************/
		/* AST NODE TYPE = AST ARRAY TYPE DECLARATION */
		/*************************************/
		System.out.print("AST NODE ARRAY TYPE DECLARATION\n");

		/**************************************/
		/* PRINT ARRAY TYPEDEF DECLARATION */
		/**************************************/
		if (arrayDec != null) arrayDec.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("Array Type Declaration"));

		/****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (arrayDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, arrayDec.SerialNumber);
		
	}

	public TYPE SemantMe()
	{		
		if (arrayDec != null){
			return arrayDec.SemantMe();
		}
		
		return null;	
	}

	public TEMP IRme() {
		if (arrayDec != null){
			return arrayDec.IRme();
		}
		return null;
	}
}