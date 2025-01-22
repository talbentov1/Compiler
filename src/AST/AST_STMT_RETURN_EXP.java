package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_RETURN_EXP extends AST_STMT
{
	public AST_EXP exp;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN_EXP(AST_EXP exp, int line)
	{
        super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> RETURN exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.exp = exp;
	}

	/***************************************************/
    /* The printing message for a return statement with expression AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = STATEMENT RETURN WITH EXPRESSION */
        /*********************************/
        System.out.println("AST NODE RETURN EXP");

        /******************************************/
        /* RECURSIVELY PRINT exp ... */
        /******************************************/
        if (exp != null) exp.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "RETURN EXP");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }

    public TYPE SemantMe() {
        TYPE_FUNCTION function = SYMBOL_TABLE.getInstance().getCurrentFunction();
        if (function == null) {
            System.out.format(">> ERROR:[%d] Return statement outside of function scope", line);
            print_error_and_exit();
        }
    
        if (exp == null) {
            if (!(function.returnType instanceof TYPE_VOID)) {
                System.out.format(">> ERROR:[%d] Return statement with missing expression in non-void function", line);
                print_error_and_exit();
            }
    
            return TYPE_VOID.getInstance();
        }
    
        TYPE expType = exp.SemantMe();

        if (expType instanceof TYPE_FUNCTION){expType = ((TYPE_FUNCTION)expType).returnType;} // in case expType is a function, compare to its return type instead of its name

        if (!function.returnType.equals(expType)) {
            System.out.format(">> ERROR: [%d] Return type mismatch", line);
            print_error_and_exit();
        }
    
        return expType;
    }
}
