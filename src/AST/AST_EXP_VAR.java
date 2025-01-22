package AST;

import TYPES.TYPE;

public class AST_EXP_VAR extends AST_EXP
{
	public AST_VAR var;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_VAR(AST_VAR var, int line)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> var\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
	}
	
	/***********************************************/
	/* The default message for an exp var AST node */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = VAR EXP AST NODE */
		/************************************/
		System.out.print("AST NODE VAR EXP\n");

		/*****************************/
		/* RECURSIVELY PRINT var ... */
		/*****************************/
		if (var != null) var.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"VAR EXP");	

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
			
	}

	public TYPE SemantMe() {
		/***************************************/
		/* [1] Get the type of the variable */
		/***************************************/
		TYPE t = var.SemantMe();
	
		/***************************************/
		/* [2] Return the type of the variable */
		/***************************************/
		return t;
	}

	public boolean isExpConst() {
		return false;
	}	
}
