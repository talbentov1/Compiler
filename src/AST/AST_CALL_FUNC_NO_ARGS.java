package AST;
import TYPES.*;
import IR.IR;
import IR.IRcommand;
import IR.IRcommand_Jump_Label;
import IR.IRcommand_PrintInt;
import SYMBOL_TABLE.*;
import TEMP.TEMP;

public class AST_CALL_FUNC_NO_ARGS extends AST_FUNC_CALL
{
	public String name;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CALL_FUNC_NO_ARGS(String name, int line)
	{
        super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== callFunc ->  ID LPAREN RPAREN\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
	}

	public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION CALL NO ARGUMENTS */
        /*********************************/
        System.out.println("AST NODE FUNCTION CALL NO ARGUMENTS");

        /******************************************/
        /* PRINT name */
        /******************************************/
		System.out.format("FUNCTION NAME: ( %s )\n",name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION CALL NO ARGUMENTS\nFunction Name: " + name);

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
		
        // check that the function has no args
        TYPE_LIST args = null;
        TYPE_LIST expectedArgs = func.params;

        if (!TYPE_LIST.checkCompatibleLists(args, expectedArgs)){
            System.out.format(">> ERROR: function call: arguments mismatch!\n");
			print_error_and_exit();
        }

        return func.returnType;
    }

    public TEMP IRme()
    {
        IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(name));
		return null;
    }
}
