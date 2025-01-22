package AST;

import TYPES.*;

public class AST_EXP_BINOP extends AST_EXP
{
	public AST_BINOP OP;
	public AST_EXP left;
	public AST_EXP right;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,AST_BINOP OP, int line)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		this.OP = OP;
	}

	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	
	public void PrintMe()
	{
	
		/*************************************/
		/* AST NODE TYPE = AST BINOP EXP */
		/*************************************/
		System.out.print("AST NODE BINOP EXP\n");

		/**************************************/
		/* RECURSIVELY PRINT left OP right */
		/**************************************/
		if (left != null) left.PrintMe();
		if (OP != null) OP.PrintMe();
		if (right != null) right.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP EXP"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,left.SerialNumber);
		if (OP  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,OP.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,right.SerialNumber);
	}
	
	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;
		
		if (left  != null) t1 = left.SemantMe();
		if (right != null) t2 = right.SemantMe();

		if (t1 instanceof TYPE_FUNCTION){t1 = ((TYPE_FUNCTION)t1).returnType;} // in case t1 is a function, compare to its return type instead of its name
		if (t2 instanceof TYPE_FUNCTION){t2 = ((TYPE_FUNCTION)t2).returnType;} // in case t2 is a function, compare to its return type instead of its name

		// no division in zero
		if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()) && // both ints
			(OP instanceof AST_BINOP_OP) && (((AST_BINOP_OP)OP).opType == 3) && // division
			(right instanceof AST_EXP_INT && ((AST_EXP_INT)right).value == 0)){ // division in const zero
			System.out.format(">> ERROR (%d) division in zero\n", line);
			print_error_and_exit();
		}

		// strings allowed in PLUS
		if ((t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance()) && (OP instanceof AST_BINOP_OP) && (((AST_BINOP_OP)OP).opType == 0)){
			return TYPE_STRING.getInstance();
		}

		// Equality Testing
		if ((OP instanceof AST_BINOP_OP) && (((AST_BINOP_OP)OP).opType == 6)) // OP = EQ
		{
			if (!t1.are_types_allowed_for_equal_binop(t2) && !t2.are_types_allowed_for_equal_binop(t1)) {
				System.out.format(">> ERROR(%d): types are not allowed to be checked for equality\n", line);
				print_error_and_exit();
			}
			return TYPE_INT.getInstance();
		}
		
		// two ints are allowed in any binary operation
		if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()))
		{
			return TYPE_INT.getInstance();
		}

		System.out.format(">> ERROR(%d): binary operation is not allowed\n", line);
        print_error_and_exit();
        return null;
	
	}

	public boolean isExpConst() {
		return false;
	}
}
