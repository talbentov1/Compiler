package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;
import TYPES.TYPE_LIST;

public class AST_CLASS_DEC_SIMPLE extends AST_CLASSDEC_TYPE
{
	public String name;
	public AST_CFIELD_LIST body;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CLASS_DEC_SIMPLE(String name, AST_CFIELD_LIST body, int line)
	{
        super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== classDec -> CLASS ID LBRACE cField (cField)* RBRACE");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.body = body;
	}

	/***************************************************/
    /* The printing message for a simple class declaration AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASSDEC SIMPLE */
        /*********************************/
        System.out.println("AST NODE CLASSDEC SIMPLE");

        /******************************************/
        /* PRINT class name and body ... */
        /******************************************/
        System.out.format("CLASS NAME( %s )\n", name);
        if (body != null) body.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            String.format("CLASSDEC\nSIMPLE\n%s", name));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
    }

    public TYPE SemantMe() {
        String[] reservedKeywords = {
            "int", "string", "void", "if", "else", "while",
            "new", "nil", "class", "array", "return", "extends", "PrintInt", "PrintString"
        };
        for (String keyword : reservedKeywords) {
            if (name.equals(keyword)) {
                System.out.format(">> ERROR [%d] class name (%s) is a reserved kew word\n",line,name);
                print_error_and_exit();		
            }
        }
        SYMBOL_TABLE st = SYMBOL_TABLE.getInstance();
        // make sure class name is not taken
        if (st.find(name) != null){
            System.out.format(">> ERROR: class declaration: class already exists!\n");
			print_error_and_exit();
        }

        // make sure class is declared in global scope
        if (st.getScope() > 0){
            System.out.format(">> ERROR: class declaration: must be in global scope!\n");
			print_error_and_exit();
        }

        /*************************/
        /* Begin Class Scope */
        /*************************/
        SYMBOL_TABLE.getInstance().beginScope();
        SYMBOL_TABLE.getInstance().setCurrentClass(new TYPE_CLASS(null, name, new TYPE_LIST(null, null)));

        /*************************/
        /* Semant Body */
        /*************************/

        SYMBOL_TABLE.getInstance().enter(name, new TYPE_CLASS(null, name, null), true);
        TYPE_CLASS class1 = new TYPE_CLASS(null, name, body.SemantMe());

        
        /*****************/
        /* End Scope */
        /*****************/
        SYMBOL_TABLE.getInstance().setCurrentClass(null);
        SYMBOL_TABLE.getInstance().endScope();

        /*****************/
        /* enter class to symbol table */
        /*****************/

        SYMBOL_TABLE.getInstance().enter(name, class1, true);

        return null;
    }
}