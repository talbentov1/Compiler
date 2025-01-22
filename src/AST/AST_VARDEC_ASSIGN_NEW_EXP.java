package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VARDEC_ASSIGN_NEW_EXP extends AST_VARDEC_TYPE
{
    public AST_TYPE type;
    public String name;
    public AST_NEW_EXP ex;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_VARDEC_ASSIGN_NEW_EXP(AST_TYPE type, String name, AST_NEW_EXP ex, int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== varDec -> type ID ASSIGN newExp SEMICOLON");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.type = type;
        this.name = name;
        this.ex = ex;
    }

    /***************************************************/
    /* The printing message for a variable declaration with type, ID, and new expression AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = VARDEC TYPE AND ID NEW EXP */
        /*********************************/
        System.out.println("AST NODE VARDEC TYPE ASSIGN ID NEW EXP");

        /******************************************/
        /* RECURSIVELY PRINT type, name, ex  */
        /******************************************/
        if (type != null) type.PrintMe();
        System.out.format("name: %s\n" , name);
        if (ex != null) ex.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "VARDEC\nTYPE\nASSIGN\nID(" + name + ")\nNEW\nEXP\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (ex != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, ex.SerialNumber);
    }

    public TYPE SemantMe()
	{
		TYPE typeOfVar = type.SemantMe();
        TYPE typeOfNewExp = ex.SemantMe();

        if(typeOfVar instanceof TYPE_VOID) {
            System.out.format(">> ERROR [%d] variable %s can't be of type void\n",line ,name);
            print_error_and_exit();
        }

        String[] reservedKeywords = {
            "int", "string", "void", "if", "else", "while",
            "new", "nil", "class", "array", "return", "extends"
        };
        for (String keyword : reservedKeywords) {
            if (name.equals(keyword)) {
                System.out.format(">> ERROR [%d] var name (%s) is a reserved kew word\n",line,name);
                print_error_and_exit();		
            }
        }

        if (SYMBOL_TABLE.getInstance().findInLastScope(name) != null) {
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",2,2,name);
            print_error_and_exit();				
		}

        boolean isTypeArray = false;

		TYPE typeOrTypeOfArray = null;
		if(typeOfVar instanceof TYPE_ARRAY) {
			isTypeArray = true;
            typeOrTypeOfArray = ((TYPE_ARRAY) typeOfVar).arrayType;
        }
        else {
            typeOrTypeOfArray = typeOfVar;
		}

        TYPE tmp = typeOfNewExp;
        if (typeOfNewExp instanceof TYPE_FUNCTION){tmp = ((TYPE_FUNCTION)typeOfNewExp).returnType;} // in case typeOfNewExp is a function, compare to its return type instead of its name

        if(!typeOrTypeOfArray.name.equals(tmp.name)) {
			if(!(typeOrTypeOfArray instanceof TYPE_CLASS) || (typeOrTypeOfArray instanceof TYPE_CLASS && !((TYPE_CLASS)typeOrTypeOfArray).isSuperClassOf(tmp))) {
				System.out.format(">> ERROR [%d] Type mismatch in assignment: %s cannot be assigned to %s\n", line, typeOfNewExp.name, typeOrTypeOfArray.name);
				print_error_and_exit();
			}
		}

        TYPE_CLASS currClass = SYMBOL_TABLE.getInstance().getCurrentClass();
        if (currClass != null && SYMBOL_TABLE.getInstance().getScope() == 1) {
            System.out.format(">> ERROR [%d] can be initialized only with a constant value.\n", line);
            print_error_and_exit();
        }

        if(!isTypeArray && !(ex instanceof AST_NEW_EXP_TYPE)) {
            System.out.format(">> ERROR [%d] class should assign with class type, not array.\n", line);
            print_error_and_exit();
        }

		if(isTypeArray && !(ex instanceof AST_NEW_EXP_TYPE_EXP)) {
            System.out.format(">> ERROR [%d] array should assign with array type.\n", line);
            print_error_and_exit();
        }

        if(typeOfNewExp instanceof TYPE_CLASS && !(SYMBOL_TABLE.getInstance().isTypeAClassDec(typeOfNewExp))){
            System.out.format(">> ERROR [%d] %s is variable\n", line, typeOfNewExp.name);
            print_error_and_exit();
        }

        SYMBOL_TABLE.getInstance().enter(name, typeOfVar, false);

        return new TYPE_CLASS_VAR_DEC(typeOfVar, name);
    }
}