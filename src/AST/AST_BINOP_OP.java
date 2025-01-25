package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;


public class AST_BINOP_OP extends AST_BINOP
{
    public int opType;

    public AST_BINOP_OP(int opType, int line){
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.opType = opType;

        /***************************************/
        /* PRINT operation in constructor */
        /***************************************/
        String binop = "====================== BINOP -> ";
        switch (opType) {
            case 0:
                System.out.println(binop + "PLUS\n");
                break;
            case 1:
                System.out.println(binop + "MINUS\n");
                break;
            case 2:
                System.out.println(binop + "TIMES\n");
                break;
            case 3:
                System.out.println(binop + "DIVIDE\n");
                break;
            case 4:
                System.out.println(binop + "LT\n");
                break;
            case 5:
                System.out.println(binop + "GT\n");
                break;
            case 6:
                System.out.println(binop + "EQ\n");
                break;
            default:
                System.out.println(binop + "UNKNOWN\n");
                break;
        }
    }

    /***************************************************/
    /* The printing message for a binary operation AST node */
    /***************************************************/
    public void PrintMe() {
        /*********************************/
        /* AST NODE TYPE = BINARY OPERATION */
        /*********************************/
        System.out.print("AST NODE BINOP\n");

        /******************************************/
        /* PRINT operation */
        /******************************************/
        System.out.print("BINOP: ");
        String sOP="";
        switch (opType) {
            case 0:
                sOP = "PLUS";
                break;
            case 1:
                sOP = "MINUS";
                break;
            case 2:
                sOP = "TIMES";
                break;
            case 3:
                sOP = "DIVIDE";
                break;
            case 4:
                sOP = "LT";
                break;
            case 5:
                sOP = "GT";
                break;
            case 6:
                sOP = "EQ";
                break;
            default:
                sOP = "UNKNOWN";
                break;
        }

        System.out.println(sOP);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "BINOP(" + sOP + ")");
    }

    public TYPE SemantMe() {
        return null;
    }

    public TEMP IRme(){
        return null;
    }
}
