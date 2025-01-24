package AST;

import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_LIST;
import TYPES.TYPE_VOID;

public class AST_EXP_LIST extends AST_Node {

    public AST_EXP exp;
    public AST_EXP_LIST arguments;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_EXP_LIST(AST_EXP exp, AST_EXP_LIST arguments, int line) {
        
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if(arguments != null) System.out.println("====================== expList -> exp COMMA expList");
        else System.out.println("====================== expList -> exp");
        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.exp = exp;
        this.arguments = arguments;
    }

    /***************************************************/
    /* The printing message for an expression argument AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = EXPRESSION ARGUMENT */
        /*********************************/
        System.out.println("AST NODE EXPRESSION ARGUMENT");

        /******************************************/
        /* RECURSIVELY PRINT exp and expArgs */
        /******************************************/
        if (exp != null) exp.PrintMe();
        if (arguments != null) arguments.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "EXPRESSION ARGUMENT");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
        if (arguments != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, arguments.SerialNumber);
    }

    public TYPE_LIST SemantMe() {
        /***************************************/
        /* [1] Semantically analyze exp type */
        /***************************************/
        TYPE exp_t = exp.SemantMe();
        
        /***********************/
        /* [2] check void type */
        /***********************/
        if (exp_t instanceof TYPE_VOID) {
            System.out.format(">> ERROR: [%d] void type for variable is illegal\n",line);
            print_error_and_exit();
        }

        /***********************************************************/
        /* [3] Semantically analyze remaining exps (recursively) */
        /***********************************************************/
        TYPE_LIST tail;
        if (arguments == null) {tail = null;}
		else {tail = arguments.SemantMe();}
        return new TYPE_LIST(exp_t, tail);
    }

    public TEMP IRme()
    {
        if (exp != null) {exp.IRme();}
        if (arguments != null) {arguments.IRme();}
        return null;
    }
}