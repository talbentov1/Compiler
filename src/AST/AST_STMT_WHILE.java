package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond,AST_STMT_LIST body, int line)
	{
        super(line);
		/******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== stmt -> WHILE LPAREN exp RPAREN LBRACE stmtList RBRACE");

		this.cond = cond;
		this.body = body;
	}

	/***************************************************/
    /* The printing message for a while statement AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = WHILE STATEMENT */
        /*********************************/
        System.out.println("AST NODE WHILE");

        /******************************************/
        /* RECURSIVELY PRINT cond and body ... */
        /******************************************/
        if (cond != null) cond.PrintMe();
        if (body != null) body.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "WHILE");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
    }

    public TYPE SemantMe() {
        if(cond == null){
            System.out.println(">> ERROR: Condition expression missing in while statement");
            print_error_and_exit();
        }

        TYPE typeOfCond = cond.SemantMe();
        if (!(typeOfCond instanceof TYPE_INT)) {
            System.out.format(">> ERROR [%d] condition inside while loop must be of type int\n", line);
            print_error_and_exit();
        }

        SYMBOL_TABLE.getInstance().beginScope();

        if(body != null){

            body.SemantMe();
        }

        SYMBOL_TABLE.getInstance().endScope();

        return null;
    }
}