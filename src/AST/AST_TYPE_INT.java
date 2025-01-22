package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_TYPE_INT extends AST_TYPE
{
    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_TYPE_INT(int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== type -> TYPE_INT");

    }

    /***************************************************/
    /* The printing message for a TYPE_INT AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = TYPE_INT */
        /*********************************/
        System.out.println("AST NODE TYPE_INT");

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "TYPE_INT");

    }

    public TYPE SemantMe() {
        return TYPE_INT.getInstance();
    }
}