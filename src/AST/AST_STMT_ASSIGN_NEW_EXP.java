package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_ASSIGN_NEW_EXP extends AST_STMT
{
	/*******************/
	/*  var := new exp */
	/*******************/
	public AST_VAR var;
	public AST_NEW_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN_NEW_EXP(AST_VAR var,AST_NEW_EXP exp, int line)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN NEW exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
	}

	/*********************************************************/
	/* The printing message for an assign new exp AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGN NEW EXP */
		/********************************************/
		System.out.print("AST NODE ASSIGN NEW EXP\n");

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
			"ASSIGN NEW EXP");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}

	public TYPE SemantMe() {
		TYPE typeOfVar = var.SemantMe();
		TYPE typeOfNewExp = exp.SemantMe();

		boolean isTypeArray = false;

		TYPE typeOrTypeOfArray = null;
		if(typeOfVar instanceof TYPE_ARRAY) {
			isTypeArray = true;
            typeOrTypeOfArray = ((TYPE_ARRAY) typeOfVar).arrayType;
        }
        else {
            typeOrTypeOfArray = typeOfVar;
		}

		if (typeOfNewExp instanceof TYPE_FUNCTION){typeOfNewExp = ((TYPE_FUNCTION)typeOfNewExp).returnType;} // in case typeOfNewExp is a function, compare to its return type instead of its name

		if(!typeOrTypeOfArray.name.equals(typeOfNewExp.name)) {
			if(!(typeOrTypeOfArray instanceof TYPE_CLASS) || (typeOrTypeOfArray instanceof TYPE_CLASS && !((TYPE_CLASS)typeOrTypeOfArray).isSuperClassOf(typeOfNewExp))){
				System.out.format(">> ERROR [%d] Type mismatch in assignment: %s cannot be assigned to %s\n", line, typeOfNewExp.name, typeOrTypeOfArray.name);
				print_error_and_exit();
			}
		}

		if(!isTypeArray && !(exp instanceof AST_NEW_EXP_TYPE)) {
            System.out.format(">> ERROR [%d] class should assign with class type, not array.\n", line);
            print_error_and_exit();
        }

		if(isTypeArray && !(exp instanceof AST_NEW_EXP_TYPE_EXP)) {
            System.out.format(">> ERROR [%d] array should assign with array type.\n", line);
            print_error_and_exit();
        }

	return null;
	}
}
