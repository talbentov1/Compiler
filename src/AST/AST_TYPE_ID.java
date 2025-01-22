package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_TYPE_ID extends AST_TYPE
{
    public String type;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_TYPE_ID(String type, int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== type -> ID( %s )\n", type);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.type = type;
    }

    /***************************************************/
    /* The printing message for a TYPE_ID AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = TYPE_ID */
        /*********************************/
        System.out.println("AST NODE TYPE_ID");

        /******************************************/
        /* PRINT type name */
        /******************************************/
        System.out.format("( %s )\n", type);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            String.format("TYPE_ID\n(%s)", type));
    }

    public TYPE SemantMe() {
        TYPE t = SYMBOL_TABLE.getInstance().find(type);

        if (t == null) {
            System.out.format(">> ERROR [%d] %s is not defined\n", line, type);
            print_error_and_exit();
        }

        if (!(t instanceof TYPE_ARRAY) && !(t instanceof TYPE_CLASS)) {
            System.out.format(">> ERROR [%d] %s is not class or array\n", line, type);
            print_error_and_exit();
        }

        if(!type.equals(t.name)) {
            System.out.format(">> ERROR [%d] %s can't be variable\n", line, type);
            print_error_and_exit();
        }

        return t;
    }
    
}