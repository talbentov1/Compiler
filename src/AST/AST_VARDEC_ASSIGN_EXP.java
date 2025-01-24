package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VARDEC_ASSIGN_EXP extends AST_VARDEC_TYPE
{
    public AST_TYPE type;
    public String name;
    public AST_EXP ex;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_VARDEC_ASSIGN_EXP(AST_TYPE type, String name, AST_EXP ex, int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== varDec -> type ID ASSIGN exp SEMICOLON");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.type = type;
        this.name = name;
        this.ex = ex;
    }

    /***************************************************/
    /* The printing message for a variable declaration with type, ID, and expression AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = VARDEC TYPE AND ID EXP */
        /*********************************/
        System.out.println("AST NODE VARDEC TYPE ASSIGN ID EXP");

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
            "VARDEC\nTYPE\nASSIGN\nID(" + name + ")\nEXP\n" );

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (ex != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, ex.SerialNumber);
    }

    public TYPE SemantMe()
	{
		TYPE typeOfVar = type.SemantMe();

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

        TYPE typeOfExp = ex.SemantMe();

        if (typeOfExp instanceof TYPE_FUNCTION){typeOfExp = ((TYPE_FUNCTION)typeOfExp).returnType;} // in case typeOfExp is a function, compare to its return type instead of its name

        if((!typeOfVar.name.equals(typeOfExp.name)) && !(typeOfExp instanceof TYPE_NIL && TYPE_NIL.canAssignNilTo(typeOfVar))) {
			if(!(typeOfVar instanceof TYPE_CLASS) || (typeOfVar instanceof TYPE_CLASS && !((TYPE_CLASS)typeOfVar).isSuperClassOf(typeOfExp))) {
				System.out.format(">> ERROR [%d] Type mismatch in assignment: %s cannot be assigned to %s\n", line, typeOfExp.name, typeOfVar.name);
				print_error_and_exit();
		    }
        }

        TYPE_CLASS currClass = SYMBOL_TABLE.getInstance().getCurrentClass();
        if (currClass != null && SYMBOL_TABLE.getInstance().getScope() == 1) {
            if(!ex.isExpConst()) {
                System.out.format(">> ERROR [%d] can be initialized only with a constant value, %s isn't constant.\n", line, typeOfExp.name);
                print_error_and_exit();
            }

            if(currClass.findDataMember(name) != null) {
                System.out.format(">> ERROR [%d] shadowing %s is illegal.\n", line, name);
                print_error_and_exit();
            }
        }
        
        offset = SYMBOL_TABLE.getInstance().findNextAvailableOffset();

        SYMBOL_TABLE.getInstance().enter(name,typeOfVar, false, offset);

        return new TYPE_CLASS_VAR_DEC(typeOfVar, name);
	}
}