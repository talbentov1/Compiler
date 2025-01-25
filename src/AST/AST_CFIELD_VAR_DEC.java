package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;

public class AST_CFIELD_VAR_DEC extends AST_CFIELD
{
    public AST_VARDEC_TYPE v;
        
    public AST_CFIELD_VAR_DEC(AST_VARDEC_TYPE v, int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== cField -> varDec\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.v = v;
    }

    /***************************************************/
    /* The printing message for a class field variable declaration AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASS FIELD VARIABLE DECLARATION */
        /*********************************/
        System.out.println("AST NODE CLASS FIELD VARIABLE DECLARATION");

        /******************************************/
        /* RECURSIVELY PRINT v ... */
        /******************************************/
        if (v != null) v.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "CLASS FIELD\nVARIABLE DECLARATION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, v.SerialNumber);
    }

    public TYPE SemantMe() {
        if (v != null){
            TYPE cFieldType = v.SemantMe();
            if (!(cFieldType instanceof TYPE_CLASS_VAR_DEC)){
                System.out.format(">> ERROR: CFIELD var declaration should be TYPE_CLASS_VAR_DEC!\n");
                print_error_and_exit();
            }
            // TYPE_CLASS_VAR_DEC cFieldClassType = (TYPE_CLASS_VAR_DEC) cFieldType;
            // if ((!(cFieldClassType.t instanceof TYPE_INT)) && (!(cFieldClassType.t instanceof TYPE_STRING)) && (!(cFieldClassType.t instanceof TYPE_NIL))){
            //     System.out.format(">> ERROR: CFIELD var declaration should be int, string or nil!\n");
            //     print_error_and_exit();
            // }
            return cFieldType;
        }
        return null;
    }

    public TEMP IRme()
    {
		return null;
    }
}