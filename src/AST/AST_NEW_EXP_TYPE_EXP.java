package AST;

import TYPES.TYPE;
import TYPES.TYPE_INT;
import TYPES.TYPE_VOID;

public class AST_NEW_EXP_TYPE_EXP extends AST_NEW_EXP
{
    public AST_TYPE t;
    public AST_EXP exp;

    public AST_NEW_EXP_TYPE_EXP (AST_TYPE t, AST_EXP exp, int line)
	{
		super(line);
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== newExp ->  NEW type LBRACK exp RBRACK\n");
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
        this.exp = exp;
	}

	/***************************************************/
    /* The printing message for a new expression with type and expression AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = NEW TYPE [EXP] */
        /*********************************/
        System.out.println("AST NODE NEW TYPE [EXP]");

        /******************************************/
        /* RECURSIVELY PRINT type and exp ... */
        /******************************************/
        if (t != null) t.PrintMe();
        if (exp != null) exp.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "NEW TYPE [EXP]");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }

    public TYPE SemantMe() {
        /***********************************************/
        /* [1] Semantically analyze the type */
        /***********************************************/
        TYPE new_t = t.SemantMe();

        /***********************/
        /* [2] check void type */
        /***********************/
        if (new_t instanceof TYPE_VOID) {
            System.out.format(">> ERROR: [%d] new void is an illegal expression\n",line);
            print_error_and_exit();
        }
    
        /*********************************************************************************/
        /* [2] Semantically analyze exp (size), asserting size is int and greater than 0 */
        /*********************************************************************************/
        TYPE size_t = exp.SemantMe();
        
        if (!(size_t instanceof TYPE_INT)) {
            System.out.format(">> ERROR:[%d] array size must be an integer", line);
            print_error_and_exit();
        }

        if( exp instanceof AST_EXP_INT && ((AST_EXP_INT)exp).value <= 0){
			System.out.format(">> ERROR [%d] array size must be greater than zero\n", line);
            print_error_and_exit();
		}
    
        /*******************/
        /* [4] Return type */
        /*******************/
        return new_t;
    }
}