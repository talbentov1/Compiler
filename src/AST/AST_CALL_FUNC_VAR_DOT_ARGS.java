package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_CALL_FUNC_VAR_DOT_ARGS extends AST_FUNC_CALL
{
	public String name;
	public AST_VAR var;
	public AST_EXP_LIST expList;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CALL_FUNC_VAR_DOT_ARGS(String name, AST_VAR var, AST_EXP_LIST expList, int line)
	{
        super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== callFunc ->  var DOT ID LPAREN (exp (COMMA exp)*)? RPAREN\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.var = var;
		this.expList = expList;
	}


	/***************************************************/
    /* The printing message for a function call with var dot and arguments AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION CALL WITH VAR DOT AND ARGUMENTS */
        /*********************************/
        System.out.println("AST NODE FUNCTION CALL WITH VAR DOT AND ARGUMENTS");

        /******************************************/
        /* PRINT name */
        /******************************************/
		System.out.format("FUNCTION NAME: ( %s )\n",name);

        /******************************************/
        /* RECURSIVELY PRINT expList and var */
        /******************************************/
        if (var != null) var.PrintMe();
		if (expList != null) expList.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION CALL WITH VAR DOT AND ARGUMENTS\nFunction Name: " + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
		if (expList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, expList.SerialNumber);
    }

	public TYPE SemantMe() {
        TYPE vType = var.SemantMe();

        // check that var can have methods (var is a class)
        if (!(vType instanceof TYPE_CLASS)){
            System.out.format(">> ERROR: can't call var's function, var is not a class!\n");
			print_error_and_exit();
        }

        TYPE_CLASS classV = (TYPE_CLASS) vType;
        TYPE_FUNCTION funcInClass = classV.get_method(name);

        SYMBOL_TABLE st = SYMBOL_TABLE.getInstance();
        TYPE funcDeclared = st.findInLastScope(name);

        TYPE_FUNCTION func = null;

        // check that the function was declared earlier with type function
        if (funcDeclared != null && (funcDeclared instanceof TYPE_FUNCTION)){
            func = (TYPE_FUNCTION) funcDeclared;
        }
        else if(funcInClass != null && (funcInClass instanceof TYPE_FUNCTION)){
            func = (TYPE_FUNCTION) funcInClass;
        }
        else{
            System.out.format(">> ERROR: can't call var's function, function doesn't exist!\n");
			print_error_and_exit();
        }

        // check that the function's args have matching types
        TYPE_LIST args = null;
        if (expList != null){
            args = expList.SemantMe();
        }
        TYPE_LIST expectedArgs = func.params;

        if (!TYPE_LIST.checkCompatibleLists(args, expectedArgs)){
            System.out.format(">> ERROR: var dot function call: arguments mismatch!\n");
			print_error_and_exit();
        }

        return func.returnType;
    }
}
