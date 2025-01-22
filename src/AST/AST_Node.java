package AST;

import java.io.PrintWriter;

public abstract class AST_Node
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public int SerialNumber;
	public static PrintWriter fileWriter;
	public int line;

	public AST_Node(int line){
		this.line = line;
	}

	public void print_error_and_exit(){
		fileWriter.write("ERROR("+line+")\n");
		fileWriter.close();
		System.exit(0);
	}
	
	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}
}
