package AST;

import TYPES.TYPE;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_CFIELD_FUNC_DEC extends AST_CFIELD
{
    public AST_FUNCDEC_TYPE f;
        
    public AST_CFIELD_FUNC_DEC(AST_FUNCDEC_TYPE f, int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== cField -> funcDec\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.f = f;
    }

    /***************************************************/
    /* The printing message for a class field function declaration AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASS FIELD FUNCTION DECLARATION */
        /*********************************/
        System.out.println("AST NODE CLASS FIELD FUNCTION DECLARATION");

        /******************************************/
        /* RECURSIVELY PRINT f ... */
        /******************************************/
        if (f != null) f.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "CLASS FIELD\nFUNCTION DECLARATION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (f != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, f.SerialNumber);
    }

    public TYPE SemantMe() {
        if (f != null){
            return f.SemantMe();
        }
        return null;
    }
}