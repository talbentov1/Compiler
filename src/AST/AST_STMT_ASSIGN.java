package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import IR.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp, int line)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}

	public TYPE SemantMe() {
		TYPE typeOfVar = var.SemantMe();
		TYPE typeOfExp = exp.SemantMe();

		if (typeOfExp instanceof TYPE_FUNCTION){typeOfExp = ((TYPE_FUNCTION)typeOfExp).returnType;} // in case typeOfExp is a function, compare to its return type instead of its name

		if(!typeOfVar.name.equals(typeOfExp.name) && !(typeOfExp instanceof TYPE_NIL && TYPE_NIL.canAssignNilTo(typeOfVar))) {
			if(!(typeOfVar instanceof TYPE_CLASS) || (typeOfVar instanceof TYPE_CLASS && !((TYPE_CLASS)typeOfVar).isSuperClassOf(typeOfExp))) {
				System.out.format(">> ERROR [%d] Type mismatch in assignment: %s cannot be assigned to %s\n", line, typeOfExp.name, typeOfVar.name);
				print_error_and_exit();
			}
		}

		return null;
	}

	public TEMP IRme()
	{
		//IR.getInstance().Add_IRcommand(new IRcommand_Assign(((AST_VAR_SIMPLE) var).name));
		TEMP src = exp.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_Store(((AST_VAR_SIMPLE) var).name, src));

		return null;
	}
}
