package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import IR.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(AST_EXP cond,AST_STMT_LIST body, int line)
	{
        super(line);
		/******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== stmt -> IF LPAREN exp RPAREN LBRACE stmtList RBRACE");
		
		/*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
		this.cond = cond;
		this.body = body;
	}

	/***************************************************/
    /* The printing message for an if statement AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = IF STATEMENT */
        /*********************************/
        System.out.println("AST NODE IF");

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
            "IF");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
    }

    public TYPE SemantMe() {
        if(cond == null){
            System.out.println(">> ERROR: Condition expression missing in if statement");
            print_error_and_exit();
            return null;
        }
        TYPE condType = cond.SemantMe();

        if (!(condType instanceof TYPE_INT)) {
            System.out.format(">> ERROR [%d] condition inside while loop must be of type int\n", line);
            print_error_and_exit();
        }
    
        SYMBOL_TABLE.getInstance().beginScope();

        if(body != null) {

            body.SemantMe();
        }

        SYMBOL_TABLE.getInstance().endScope();
    
        return null;
    }

    public TEMP IRme()
	{
		String end = IRcommand.getFreshLabel("end");

		IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(cond.IRme(), end));
		//IR.getInstance().Add_IRcommand(new IRcommandScope());

		body.IRme();

		//IR.getInstance().Add_IRcommand(new IRcommandScopeDestroy());
		IR.getInstance().Add_IRcommand(new IRcommand_Label(end));

		return null;
	}
}