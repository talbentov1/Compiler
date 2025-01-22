package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_CALL_FUNC_ARGS extends AST_FUNC_CALL
{
	public String name;
	public AST_EXP_LIST expList;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CALL_FUNC_ARGS(String name, AST_EXP_LIST expList, int line)
	{
        super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== callFunc ->  ID LPAREN (exp (COMMA exp)*)? RPAREN\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.expList = expList;
	}

	/***************************************************/
    /* The printing message for a function call with arguments AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION CALL WITH ARGUMENTS */
        /*********************************/
        System.out.println("AST NODE FUNCTION CALL WITH ARGUMENTS");

        /******************************************/
        /* PRINT name */
        /******************************************/
		System.out.format("FUNCTION NAME: ( %s )\n",name);

        /******************************************/
        /* RECURSIVELY PRINT expList */
        /******************************************/
        if (expList != null) expList.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION CALL WITH ARGUMENTS\nFunction Name: " + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (expList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, expList.SerialNumber);
    }

    public TYPE SemantMe() {
        SYMBOL_TABLE st = SYMBOL_TABLE.getInstance();
        TYPE funcDeclared = st.find(name);

        // check that the function was declared earlier with type function
        if (funcDeclared == null || !(funcDeclared instanceof TYPE_FUNCTION)){
            System.out.format(">> ERROR: function call: function was not declaired!\n");
			print_error_and_exit();
        }

        TYPE_FUNCTION func = (TYPE_FUNCTION) funcDeclared;

        // check that the function's args have matching types
        TYPE_LIST args = null;
        if (expList != null){
            args = expList.SemantMe();
        }
        TYPE_LIST expectedArgs = func.params;

        if (!TYPE_LIST.checkCompatibleLists(args, expectedArgs)){
            System.out.format(">> ERROR: function call: arguments mismatch!\n");
			print_error_and_exit();
        }

        return func.returnType;
    }
}
