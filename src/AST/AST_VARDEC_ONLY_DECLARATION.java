package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VARDEC_ONLY_DECLARATION extends AST_VARDEC_TYPE
{
    public AST_TYPE type;
    public String name;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_VARDEC_ONLY_DECLARATION(AST_TYPE type, String name, int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== varDec -> type ID SEMICOLON");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.type = type;
        this.name = name;
    }

    /***************************************************/
    /* The printing message for a variable declaration with type and ID AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = VARDEC TYPE AND ID EXP */
        /*********************************/
        System.out.println("AST NODE VARDEC TYPE ID");

        /******************************************/
        /* RECURSIVELY PRINT type, name, ex  */
        /******************************************/
        if (type != null) type.PrintMe();
        System.out.format("name: %s\n" , name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "VARDEC\nTYPE\nID(" + name + ")");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
    }

    public TYPE SemantMe() {
        TYPE t = type.SemantMe();

        if(t instanceof TYPE_VOID){
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

        if (SYMBOL_TABLE.getInstance().findInLastScope(name) != null)
		{
			System.out.format(">> ERROR [%d] variable %s already exists in scope\n",line,name);
            print_error_and_exit();				
		}

        int offset;

        TYPE_CLASS currClass = SYMBOL_TABLE.getInstance().getCurrentClass();
        if ( currClass != null && SYMBOL_TABLE.getInstance().getScope() == 1){
            if(currClass.findDataMember(name) != null){
                System.out.format(">> ERROR [%d] shadowing %s is illegal.\n", line, name);
                print_error_and_exit();
            }
            offset = SYMBOL_TABLE.getInstance().findNextAvailableOffsetInClass(currClass);
        } else {
            offset = SYMBOL_TABLE.getInstance().findNextAvailableOffset();
        }

        SYMBOL_TABLE.getInstance().enter(name, t, false, offset);

        return new TYPE_CLASS_VAR_DEC(t, name);
    }
}