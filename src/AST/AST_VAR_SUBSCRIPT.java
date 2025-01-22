package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_VAR var;
	public AST_EXP subscript;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SUBSCRIPT(AST_VAR var,AST_EXP subscript, int line)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== var -> var [ exp ]\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.subscript = subscript;
	}

	/*****************************************************/
	/* The printing message for a subscript var AST node */
	/*****************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
		/****************************************/
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"SUBSCRIPT VAR");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var       != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,subscript.SerialNumber);
	}

	public TYPE SemantMe() {
        TYPE typeOfVar = var.SemantMe();

        if (!(typeOfVar instanceof TYPE_ARRAY)) {
            System.out.format(">> ERROR [%d] %s is not an array type\n", line, typeOfVar.name);
            print_error_and_exit();
        }

        TYPE typeOfSubscript = subscript.SemantMe();
        if (!(typeOfSubscript instanceof TYPE_INT)) {
            System.out.format(">> ERROR [%d] subscript must be of type int\n", line);
            print_error_and_exit();
        }

		if(subscript instanceof AST_EXP_INT && ((AST_EXP_INT)subscript).value < 0){
			System.out.format(">> ERROR [%d] array index must be greater than or equal to zero\n", line);
            print_error_and_exit();
		}

        return ((TYPE_ARRAY)typeOfVar).arrayType;
    }
}
