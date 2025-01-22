package AST;

import TYPES.*;
import SYMBOL_TABLE.*;


public class AST_FUNCDEC_NO_PARAMS extends AST_FUNCDEC_TYPE
{
    public AST_TYPE type;
    public String name;
    public AST_STMT_LIST body;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNCDEC_NO_PARAMS(AST_TYPE type, String name, AST_STMT_LIST body, int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== funcDec -> type ID LPAREN RPAREN LBRACE stmtList RBRACE");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.type = type;
        this.name = name;
        this.body = body;
    }

    /***************************************************/
    /* The printing message for a function declaration with no arguments AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCDEC NO PARAMETERS */
        /*********************************/
        System.out.println("AST NODE FUNCDEC NO PARAMETERS");
        
        /******************************************/
        /* PRINT name ... */
        /******************************************/
        System.out.format("Function name: %s\n", name);
        
        /******************************************/
        /* RECURSIVELY PRINT type and body ... */
        /******************************************/
        if (type != null) type.PrintMe();
        if (body != null) body.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION DECLARATION\nNO PARAMETERS");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
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
        

        TYPE_FUNCTION func = new TYPE_FUNCTION(returnType,name,null);

        /**************************************************************/
        /* check overriding a method in a derived class and shadowing */
        /**************************************************************/
        boolean IsInClass = false;
        TYPE_CLASS funcs_class = SYMBOL_TABLE.getInstance().getCurrentClass();
        if(funcs_class != null){
            IsInClass = true;
            if(!overriding_check(funcs_class, func)){
                System.out.format(">> ERROR [%d] function %s overloading is illegal\n",line,name);
                print_error_and_exit();	
            }
            TYPE class_var_with_same_name = funcs_class.findDataMember(name);
            if(class_var_with_same_name!= null && !(class_var_with_same_name instanceof TYPE_FUNCTION)){
                System.out.format(">> ERROR [%d] shadowing %s is illegal\n", line, name);
                print_error_and_exit();
            }
        }

        /***************************************************************************************/
        /* check function defined in the outermost (global) scope or inside a class (method) */
        /***************************************************************************************/
        if(!IsInClass && SYMBOL_TABLE.getInstance().getScope() > 0){
            System.out.format(">> ERROR: function definition not in global scope\n");
            print_error_and_exit(); 
        }
        
        /***************************************/
        /*  Begin a new scope */
        /***************************************/
        SYMBOL_TABLE.getInstance().beginScope();
        SYMBOL_TABLE.getInstance().setCurrentFunction(func);

        /***************************************/
        /*  Enter the function type to the symbol table */
        /***************************************/
        
        SYMBOL_TABLE.getInstance().enter(name, func, false);

        /***************************************/
        /*  Semant the function body */
        /***************************************/
        if (body != null) body.SemantMe();

        /***************************************/
        /*  End the scope */
        /***************************************/
        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().setCurrentFunction(null);

        /***************************************/
        /*  Return the function type */
        /***************************************/
        if (IsInClass){
            return new TYPE_CLASS_VAR_DEC(func, name);
        }
        return func;
        
    }

    /*************************************************/
    /* Return true iff the overriding is done legally */
    /*************************************************/
    public static boolean overriding_check(TYPE_CLASS funcs_class, TYPE_FUNCTION func) {
        TYPE_CLASS ancestorClass = funcs_class.father;
        while (ancestorClass != null) {
            // Check if the ancestor class has a function with the same name
            TYPE_FUNCTION ancestor_method = ancestorClass.get_method_from_class(func.name);
            if (ancestor_method != null) {
                if (!same_signature(ancestor_method, func)) {
                    return false; 
                }
            }
            ancestorClass = ancestorClass.father;
        }
        return true;
    }

    /*************************************************************************************/
    /* Return true iff both functions have the same signature (return types, parameters) */
    /*************************************************************************************/
    public static boolean same_signature(TYPE_FUNCTION f, TYPE_FUNCTION g) {
        
        // Compare return types
        if (!f.returnType.equals(g.returnType)) {
            return false; 
        }
        // Compare parameter types
        TYPE_LIST f_params = f.params;
        TYPE_LIST g_params = g.params;
        while (f_params != null && g_params != null) {
            TYPE t1 = f_params.head.getType();
            TYPE t2 = g_params.head.getType();
            if (!(t1.equals(t2))) {
                return false; 
            }
            f_params = f_params.tail;
            g_params = g_params.tail;
        }
        // not the same length
        if (f_params != null || g_params != null) {
            return false; 
        }
        return true; 
    }

}