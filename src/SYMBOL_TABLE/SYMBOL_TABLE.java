/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;

/****************/
/* SYMBOL TABLE */
/****************/
public class SYMBOL_TABLE
{
	private int hashArraySize = 13;
	
	/**********************************************/
	/* The actual symbol table data structure ... */
	/**********************************************/
	private SYMBOL_TABLE_ENTRY[] table = new SYMBOL_TABLE_ENTRY[hashArraySize];
	private SYMBOL_TABLE_ENTRY top;
	private int top_index = 0;
	private TYPE_CLASS currentClass = null;
	private int scope = 0; // 0 is the global scope
	private TYPE_FUNCTION currFunction = null;
	
	/**************************************************************/
	/* A very primitive hash function for exposition purposes ... */
	/**************************************************************/
	private int hash(String s)
	{
		if (s.charAt(0) == 'l') {return 1;}
		if (s.charAt(0) == 'm') {return 1;}
		if (s.charAt(0) == 'r') {return 3;}
		if (s.charAt(0) == 'i') {return 6;}
		if (s.charAt(0) == 'd') {return 6;}
		if (s.charAt(0) == 'k') {return 6;}
		if (s.charAt(0) == 'f') {return 6;}
		if (s.charAt(0) == 'S') {return 6;}
		return 12;
	}

	/****************************************************************************/
	/* Enter a variable, function, class type or array type to the symbol table */
	/****************************************************************************/
	public void enter(String name,TYPE t, boolean isClassDec, int offset)
	{
		/*************************************************/
		/* [1] Compute the hash value for this new entry */
		/*************************************************/
		int hashValue = hash(name);

		/******************************************************************************/
		/* [2] Extract what will eventually be the next entry in the hashed position  */
		/*     NOTE: this entry can very well be null, but the behaviour is identical */
		/******************************************************************************/
		SYMBOL_TABLE_ENTRY next = table[hashValue];
	
		/**************************************************************************/
		/* [3] Prepare a new symbol table entry with name, type, next and prevtop */
		/**************************************************************************/
		SYMBOL_TABLE_ENTRY e = new SYMBOL_TABLE_ENTRY(name,t,hashValue,next,top,top_index++,scope,isClassDec, offset);

		/**********************************************/
		/* [4] Update the top of the symbol table ... */
		/**********************************************/
		top = e;
		
		/****************************************/
		/* [5] Enter the new entry to the table */
		/****************************************/
		table[hashValue] = e;
		
		/**************************/
		/* [6] Print Symbol Table */
		/**************************/
		PrintMe();
	}

	/***********************************************/
	/* Find the inner-most scope element with name */
	/***********************************************/
	public TYPE find(String name)
	{
		SYMBOL_TABLE_ENTRY e;
				
		for (e = table[hash(name)]; e != null; e = e.next)
		{
			if (name.equals(e.name))
			{
				return e.type;
			}
		}
		
		return null;
	}

	/******************************************************/
	/* Find a symbol in the innermost (last) scope only   */
	/******************************************************/
	public TYPE findInLastScope(String name) {
    	SYMBOL_TABLE_ENTRY current = top;

   		while (current != null && !current.name.equals("SCOPE-BOUNDARY")) {
			if (current.name.equals(name)) {
            		return current.type;
        		}
        	current = current.prevtop;
    		}
    	return null;
	}

	public int findNextAvailableOffset() {
		// global variable
		if (this.scope == 0) {
			return 0;
		}
		
		SYMBOL_TABLE_ENTRY current = top;
		int offset = 0;
   		while (current != null && !current.name.equals("SCOPE-BOUNDARY")) {
			if(current.offset < 0) {
				offset = current.offset;
			}
		}
		return offset - 4;
	}

	public int findNextAvailableOffsetInClass () {
		SYMBOL_TABLE_ENTRY current = top;
		int offset = 0;
   		while (current != null && !current.name.equals("SCOPE-BOUNDARY")) {
			if(current.offset < 0) {
				offset = current.offset;
			}
		}
		return offset + 4;
	}


	public TYPE findInScopeDownTo(String name, int minimalScope) {
		SYMBOL_TABLE_ENTRY e;
		for (e = top; e != null; e = e.prevtop) {
			if (name.equals(e.name) && e.scope >= minimalScope) {return e.type;}
		}
		return null;
	}

	public boolean isTypeAClassDec(TYPE type) {
    	SYMBOL_TABLE_ENTRY e = top;

		while (e != null) {
        	if (e.type == type && !e.isClassDec && e.name.equals(type.name)) {
            	return false;
        	}
        	e = e.prevtop;
    	}

    	return true;
	}

	public TYPE findClassDec(String name) {
		SYMBOL_TABLE_ENTRY e;
	
		for (e = top; e != null; e = e.prevtop) {
			if (name.equals(e.name) && (e.type instanceof TYPE_CLASS) && e.isClassDec) {
				return e.type;
			}
		}

		return null;
	}

	public void setCurrentClass(TYPE_CLASS currentClass)
	{
		this.currentClass = currentClass;
	}

	public TYPE_CLASS getCurrentClass()
	{
		return this.currentClass;
	}

	public void setCurrentFunction(TYPE_FUNCTION func) 
	{
		currFunction = func;
	}

	public TYPE_FUNCTION getCurrentFunction() 
	{
		return currFunction;
	}

	/***************************************************************************/
	/* begine scope = Enter the <SCOPE-BOUNDARY> element to the data structure */
	/***************************************************************************/
	public void beginScope()
	{
		/************************************************************************/
		/* Though <SCOPE-BOUNDARY> entries are present inside the symbol table, */
		/* they are not really types. In order to be ablt to debug print them,  */
		/* a special TYPE_FOR_SCOPE_BOUNDARIES was developed for them. This     */
		/* class only contain their type name which is the bottom sign: _|_     */
		/************************************************************************/
		enter(
			"SCOPE-BOUNDARY",
			new TYPE_FOR_SCOPE_BOUNDARIES("NONE"),
			false,
			0);
		scope++;
		
		/*********************************************/
		/* Print the symbol table after every change */
		/*********************************************/
		PrintMe();
	}

	/********************************************************************************/
	/* end scope = Keep popping elements out of the data structure,                 */
	/* from most recent element entered, until a <NEW-SCOPE> element is encountered */
	/********************************************************************************/
	public void endScope()
	{
		/**************************************************************************/
		/* Pop elements from the symbol table stack until a SCOPE-BOUNDARY is hit */		
		/**************************************************************************/
		while (top.name != "SCOPE-BOUNDARY")
		{
			table[top.index] = top.next;
			top_index = top_index-1;
			top = top.prevtop;
		}
		/**************************************/
		/* Pop the SCOPE-BOUNDARY sign itself */		
		/**************************************/
		table[top.index] = top.next;
		top_index = top_index-1;
		top = top.prevtop;

		scope--;

		/*********************************************/
		/* Print the symbol table after every change */		
		/*********************************************/
		PrintMe();
	}
	
	public int getScope(){
		return scope;
	}

	public static int n=0;
	
	public void PrintMe()
	{
		int i=0;
		int j=0;
		String dirname="./output/";
		String filename=String.format("SYMBOL_TABLE_%d_IN_GRAPHVIZ_DOT_FORMAT.txt",n++);

		try
		{
			/*******************************************/
			/* [1] Open Graphviz text file for writing */
			/*******************************************/
			PrintWriter fileWriter = new PrintWriter(dirname+filename);

			/*********************************/
			/* [2] Write Graphviz dot prolog */
			/*********************************/
			fileWriter.print("digraph structs {\n");
			fileWriter.print("rankdir = LR\n");
			fileWriter.print("node [shape=record];\n");

			/*******************************/
			/* [3] Write Hash Table Itself */
			/*******************************/
			fileWriter.print("hashTable [label=\"");
			for (i=0;i<hashArraySize-1;i++) { fileWriter.format("<f%d>\n%d\n|",i,i); }
			fileWriter.format("<f%d>\n%d\n\"];\n",hashArraySize-1,hashArraySize-1);
		
			/****************************************************************************/
			/* [4] Loop over hash table array and print all linked lists per array cell */
			/****************************************************************************/
			for (i=0;i<hashArraySize;i++)
			{
				if (table[i] != null)
				{
					/*****************************************************/
					/* [4a] Print hash table array[i] -> entry(i,0) edge */
					/*****************************************************/
					fileWriter.format("hashTable:f%d -> node_%d_0:f0;\n",i,i);
				}
				j=0;
				for (SYMBOL_TABLE_ENTRY it=table[i];it!=null;it=it.next)
				{
					/*******************************/
					/* [4b] Print entry(i,it) node */
					/*******************************/
					fileWriter.format("node_%d_%d ",i,j);
					fileWriter.format("[label=\"<f0>%s|<f1>%s|<f2>prevtop=%d|<f3>offset=%d|<f4>next\"];\n",
						it.name,
						it.type.name,
						it.offset,
						it.prevtop_index);

					if (it.next != null)
					{
						/***************************************************/
						/* [4c] Print entry(i,it) -> entry(i,it.next) edge */
						/***************************************************/
						fileWriter.format(
							"node_%d_%d -> node_%d_%d [style=invis,weight=10];\n",
							i,j,i,j+1);
						fileWriter.format(
							"node_%d_%d:f4 -> node_%d_%d:f0;\n",
							i,j,i,j+1);
					}
					j++;
				}
			}
			fileWriter.print("}\n");
			fileWriter.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static SYMBOL_TABLE instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected SYMBOL_TABLE() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static SYMBOL_TABLE getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new SYMBOL_TABLE();

			/*****************************************/
			/* [1] Enter primitive types int, string */
			/*****************************************/
			instance.enter("int",   TYPE_INT.getInstance(), false, 0);
			instance.enter("string",TYPE_STRING.getInstance(), false, 0);

			/*************************************/
			/* [2] How should we handle void ??? */
			/*************************************/
			
			//nothing 

			/***************************************/
			/* [3] Enter library function PrintInt */
			/***************************************/
			instance.enter(
				"PrintInt",
				new TYPE_FUNCTION(
					TYPE_VOID.getInstance(),
					"PrintInt",
					new TYPE_LIST(
						TYPE_INT.getInstance(),
						null)),
						false,
						0);

			/***************************************/
			/* [3] Enter library function PrintString */
			/***************************************/
			instance.enter(
				"PrintString",
				new TYPE_FUNCTION(
					TYPE_VOID.getInstance(),
					"PrintString",
					new TYPE_LIST(
						TYPE_STRING.getInstance(),
						null)),
						false,
						0);
			
		}
		return instance;
	}
}
