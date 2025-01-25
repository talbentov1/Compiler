package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_FUNC_CALL extends AST_STMT
{
	public AST_FUNC_CALL f;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_FUNC_CALL(AST_FUNC_CALL f, int line)
	{
        super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt ->  (var DOT)? ID LPAREN (exp (COMMA exp)*)? RPAREN SEMICOLON\n");
		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.f = f;
	}

	/***************************************************/
    /* The printing message for a function call statement AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION CALL STATEMENT */
        /*********************************/
        System.out.println("AST NODE FUNCTION CALL");

        /******************************************/
        /* RECURSIVELY PRINT f ... */
        /******************************************/
        if (f != null) f.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION CALL");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (f != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, f.SerialNumber);
    }

    public TYPE SemantMe() {
        if (f != null) {
            return f.SemantMe();
        } else {
            System.out.format(">> ERROR: [%d] Function call expression is null", line);
            print_error_and_exit();
            return null;
        }
    }

    public TEMP IRme() {
        this.f.IRme();
        return null;
    }
}
