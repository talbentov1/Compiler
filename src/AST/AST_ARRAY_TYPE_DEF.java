package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_ARRAY_TYPE_DEF extends AST_ARRAYDEC_TYPE
{
    public String name;
    public AST_TYPE type;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_ARRAY_TYPE_DEF(String name, AST_TYPE type, int line)
    {
        super(line);
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== arrayTypedef -> ARRAY ID( %s ) EQ type LBRACK RBRACK SEMICOLON\n", name);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
        this.type = type;
    }

    /*************************************************/
	/* The printing message for array Typedef declaration AST node */
	/*************************************************/
	
	public void PrintMe()
	{
		
		/*************************************/
		/* AST NODE TYPE = AST ARRAY TYPE DEFINOTION */
		/*************************************/
		System.out.print("AST NODE ARRAY TYPE DEFINOTION\n");

		/**************************************/
		/* PRINT ARRAY TYPEDEF DEFINOTION */
		/**************************************/
		System.out.format("ARRAY NAME( %s )\n", name);
        if (type != null) type.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("Array Type Definition\n%s", name));

		/****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
		
	}

    public TYPE SemantMe() {
        String[] reservedKeywords = {
            "int", "string", "void", "if", "else", "while",
            "new", "nil", "class", "array", "return", "extends", "PrintInt", "PrintString"
        };
        for (String keyword : reservedKeywords) {
            if (name.equals(keyword)) {
                System.out.format(">> ERROR [%d] array name (%s) is a reserved kew word\n",line,name);
                print_error_and_exit();		
            }
        }

        TYPE t = type.SemantMe();
        SYMBOL_TABLE st = SYMBOL_TABLE.getInstance();

        // check that the array definition is in the uppermost scope
        if (st.getScope() > 0){
            System.out.format(">> ERROR: array definition not in global scope!\n");
			print_error_and_exit();
        }

        // check that the type is valid: not void, null or nil
        if (t instanceof TYPE_VOID || t instanceof TYPE_NIL || t == null){
            System.out.format(">> ERROR: array definition invalid type!\n");
			print_error_and_exit();
        }

        // check that the array is not already declared
        if (st.find(name) != null){
            System.out.format(">> ERROR: array definition already exists!\n");
			print_error_and_exit();
        }

        // insert the new type into the symbol table
        TYPE_ARRAY arr = new TYPE_ARRAY(t, name);
        int offset = SYMBOL_TABLE.getInstance().findNextAvailableOffset();
        st.enter(name, arr, false, offset);
        return arr;
    }
}