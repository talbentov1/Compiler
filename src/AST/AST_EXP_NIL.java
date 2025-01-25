package AST;

import IR.IR;
import IR.IRcommandConstInt;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TYPES.*;

public class AST_EXP_NIL extends AST_EXP
{
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_NIL(int line)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== exp -> NIL\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
	}

	/************************************************/
	/* The printing message for a NIL EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST NIL EXP */
		/*******************************/
		System.out.format("AST NODE NIL\n");

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"NIL");
	}

	public TYPE SemantMe() {
        /***************************************/
        /* [1] Return the type of nil, which is NIL_TYPE */
        /***************************************/
        return TYPE_NIL.getInstance();
    }

	public TEMP IRme() {
		TEMP ret = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(ret, 0)); // 0 is the default value of nil
		return ret;
	}

	public boolean isExpConst() {
		return true;
	}
}
