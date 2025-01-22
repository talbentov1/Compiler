package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VAR_SIMPLE extends AST_VAR
{
	/************************/
	/* simple variable name */
	/************************/
	public String name;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(String name, int line)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID( %s )\n",name);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE VAR( %s )\n",name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("VAR\n(%s)",name));
	}

	public TYPE SemantMe() {
		TYPE_CLASS classType = SYMBOL_TABLE.getInstance().getCurrentClass();
		TYPE type;
		if (classType != null) { // inside a class
			type = SYMBOL_TABLE.getInstance().findInScopeDownTo(name, 1); // check if it's a local variable (inside a class := scope >= 1)
			if (type == null) {
				type = classType.findDataMember(name); // check if it's a class field
			}
			if (type == null) {
				type = SYMBOL_TABLE.getInstance().find(name); // check if it's a global variable
			}
		} 
		else { // not inside a class
			type = SYMBOL_TABLE.getInstance().find(name);
		}

		if (type == null) {
            System.out.format(">> ERROR [%d] variable %s is not declared\n", line, name);
            print_error_and_exit();
        }

		if (SYMBOL_TABLE.getInstance().findClassDec(name) != null && SYMBOL_TABLE.getInstance().isTypeAClassDec(type)) {
            System.out.format(">> ERROR [%d] class %s can't be a variable\n", line, name);
            print_error_and_exit();
        }

		return type;
	}
}
