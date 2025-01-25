package AST;

import TYPES.TYPE;
import TYPES.TYPE_VOID;

public class AST_NEW_EXP_TYPE extends AST_NEW_EXP
{
    public AST_TYPE t;

    public AST_NEW_EXP_TYPE(AST_TYPE t, int line)
	{
		super(line);
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== newExp ->  NEW type\n");
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
	}

	/***************************************************/
    /* The printing message for a "new" expression with type */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = NEW TYPE */
        /*********************************/
        System.out.println("AST NODE NEW TYPE");

        /******************************************/
        /* RECURSIVELY PRINT t ... */
        /******************************************/
        if (t != null) t.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "NEW TYPE");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
    }

    public TYPE SemantMe() {
        /*************************************/
        /* [1] Semantically analyze the type */
        /*************************************/
        TYPE new_t = t.SemantMe();

        /***********************/
        /* [2] check void type */
        /***********************/
        if (new_t instanceof TYPE_VOID) {
            System.out.format(">> ERROR: [%d] new void is an illegal expression\n",line);
            print_error_and_exit();
        }

        /*******************/
        /* [3] return type */
        /*******************/
        return new_t;
    }

    // IRme() not needed for ex4
}