package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;
import TYPES.TYPE_CLASS_VAR_DEC;
import TYPES.TYPE_FUNCTION;
import TYPES.TYPE_LIST;

public class AST_FUNCDEC_PARAMS extends AST_FUNCDEC_TYPE
{
    public AST_TYPE type;
    public String name;
    public AST_PARAMS_LIST params;
    public AST_STMT_LIST body;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNCDEC_PARAMS(AST_TYPE type, String name, AST_PARAMS_LIST params, AST_STMT_LIST body, int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== funcDec -> type ID LPAREN paramsList RPAREN LBRACE stmtList RBRACE");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.type = type;
        this.name = name;
        this.params = params;
        this.body = body;
    }

    /***************************************************/
    /* The printing message for a function declaration with arguments AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCDEC WITH PARAMETERS */
        /*********************************/
        System.out.println("AST NODE FUNCDEC WITH PARAMETERS");

        /******************************************/
        /* PRINT name ... */
        /******************************************/
        System.out.format("Function name: %s\n", name);

        /******************************************/
        /* RECURSIVELY PRINT typAndId, args, and body ... */
        /******************************************/
        if (type != null) type.PrintMe();
        if (params != null) params.PrintMe();
        if (body != null) body.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION DECLARATION\nWITH PARAMETERS");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (params != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, params.SerialNumber);
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
    }

    public TYPE SemantMe() {
        
        /***************************************/
        /* Semantically analyze the return type */
        /***************************************/
        TYPE returnType = type.SemantMe();
        if (returnType == null) {
            System.out.format(">> ERROR [%d] function %s has an invalid return type\n", line, name);
            print_error_and_exit();
        }        
        
        /**********************************************/
        /* Semantically analyze the function name */
        /**********************************************/
        if (SYMBOL_TABLE.getInstance().findInLastScope(name) != null)
		{
			System.out.format(">> ERROR [%d] function %s already exists in scope\n",line,name);
            print_error_and_exit();				
		}

        /*****************************************************/
        /* check if function is shadowing a class field name */ 
        /*****************************************************/
        TYPE_CLASS funcs_class = SYMBOL_TABLE.getInstance().getCurrentClass();
        if (funcs_class != null){
            TYPE class_var_with_same_name = funcs_class.findDataMember(name);
            if(class_var_with_same_name!= null && !(class_var_with_same_name instanceof TYPE_FUNCTION)){
                System.out.format(">> ERROR [%d] shadowing %s is illegal\n", line, name);
                print_error_and_exit();
            }
        }

        /*****************************************************************************************************/
        /* check function name is not a reserved key word, a primitive type name or a library function name */
        /*****************************************************************************************************/
        String[] reservedKeywords = {
            "int", "string", "void", "if", "else", "while",
            "new", "nil", "class", "array", "return", "extends"
        };
        for (String keyword : reservedKeywords) {
            if (name.equals(keyword)) {
                System.out.format(">> ERROR [%d] function name (%s) is a reserved kew word\n",line,name);
                print_error_and_exit();		
            }
        }

        /**********************************************/
        /* check overriding a method in a derived class */
        /**********************************************/
        SYMBOL_TABLE.getInstance().beginScope(); // temporary scope for parameter analysis and overriding check. ends before the function itself is defined.
        
        TYPE_LIST f_params = (TYPE_LIST)params.SemantMe();
        TYPE_FUNCTION f = new TYPE_FUNCTION(returnType,name,f_params);

        boolean IsInClass = false;
        if(SYMBOL_TABLE.getInstance().getCurrentClass() != null){
            IsInClass = true;
            if(!AST_FUNCDEC_NO_PARAMS.overriding_check(SYMBOL_TABLE.getInstance().getCurrentClass(), f)){
                System.out.format(">> ERROR [%d] function %s overloading is illegal\n",line,name);
                print_error_and_exit();	
            }
        }

        SYMBOL_TABLE.getInstance().endScope(); // end temporary scope (deletes all parameters from symbol table)

        /***************************************************************************************/
        /* check function defined in the outermost (global) scope or inside a class (method) */
        /***************************************************************************************/
        if(!IsInClass && SYMBOL_TABLE.getInstance().getScope() > 0){
            System.out.format(">> ERROR: function definition not in global scope\n");
            print_error_and_exit(); 
        }

        /**************************************************/
        /*  Begin a new scope and enter parameters to scope */
        /*************************************************/
        SYMBOL_TABLE.getInstance().beginScope();
        f_params = (TYPE_LIST)params.SemantMe(); // enters parameters to scope

        /********************************************************************************************/
        /*  Enter the function to the symbol table - to allow the function to call itself (recursion) */
        /********************************************************************************************/
        f = new TYPE_FUNCTION(returnType,name,f_params);
        SYMBOL_TABLE.getInstance().setCurrentFunction(f);
        SYMBOL_TABLE.getInstance().enter(name, f, false, 0);

        /***************************************/
        /*  Semant the function body */
        /***************************************/
        if (body != null) body.SemantMe();

        /***************************************/
        /*  End the scope */
        /***************************************/
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().setCurrentFunction(null);

        /***************************************************/
		/*  Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name, f, false, 0);

        /***************************************/
        /*  Return the function type */
        /***************************************/
        if (IsInClass){
            return new TYPE_CLASS_VAR_DEC(f, name);
        }
        return f;
    }

    // IRme is not needed in ex4
}
