package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_TYPE_STRING extends AST_TYPE
{
    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_TYPE_STRING(int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== type -> TYPE_STRING");

    }

    /***************************************************/
    /* The printing message for a TYPE_STRING AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = TYPE_STRING */
        /*********************************/
        System.out.println("AST NODE TYPE_STRING");

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "TYPE_STRING");

    }

    public TYPE SemantMe() {
        return TYPE_STRING.getInstance();
    }
}