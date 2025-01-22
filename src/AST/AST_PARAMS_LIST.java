package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_PARAMS_LIST extends AST_Node {

    public AST_TYPE type;
    public String name;
    public AST_PARAMS_LIST paramsList;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_PARAMS_LIST(AST_TYPE type, String name, AST_PARAMS_LIST paramsList, int line) {
        
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if(paramsList != null) System.out.println("====================== paramsList -> type ID COMMA paramsList");
        else System.out.println("====================== paramsList -> type ID");
        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.type = type;
        this.name = name;
        this.paramsList = paramsList;
    }

    /***************************************************/
    /* The printing message for a paramslist AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = PARAMS LIST */
        /*********************************/
        System.out.println("AST NODE PARAMETERS LIST");

        /******************************************/
        /* PRINT name ... */
        /******************************************/
        System.out.format("name: %s\n", name);

        /******************************************/
        /* RECURSIVELY PRINT type, paramsList */
        /******************************************/
        if (type != null) type.PrintMe();
        if (paramsList != null) paramsList.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "PARAMETERS\nLIST\n" + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (paramsList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, paramsList.SerialNumber);
    }

    public TYPE_LIST SemantMe() {
        /***************************************************/
        /* [1] Semantically analyze the type of the argument */
        /***************************************************/
        TYPE param_t = type.SemantMe();
        
        /***********************/
        /* [2] check void type */
        /***********************/
        if (param_t instanceof TYPE_VOID) {
            System.out.format(">> ERROR: [%d] void type for variable is illegal\n",line);
            print_error_and_exit();
        }

        /************************************************************************************************/
        /* [2] check param's name doesn't exist in scope (e.g. two params with same name in paramsList) */
        /************************************************************************************************/
        if (SYMBOL_TABLE.getInstance().findInLastScope(name) != null)
		{
			System.out.format(">> ERROR [%d] variable %s already exists in scope\n",line,name);
            print_error_and_exit();				
		}
        
        /*******************************************/
        /* [2] enter current param to symbol table */
        /*******************************************/
        SYMBOL_TABLE.getInstance().enter(name, param_t, false); // begin and end scope circiling the parameter are inserted in AST_FUNC_PARAMS
    
        /***********************************************************/
        /* [3] Semantically analyze remaining params (recursively) */
        /***********************************************************/
        TYPE_LIST tail;
        if (paramsList == null) {tail = null;}
		else {tail = paramsList.SemantMe();}
        return new TYPE_LIST(param_t, tail);
    }
}