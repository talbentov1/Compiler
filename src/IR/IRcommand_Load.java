/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class IRcommand_Load extends IRcommand
{
	TEMP dst;
	String var_name;
	
	public IRcommand_Load(TEMP dst,String var_name)
	{
		this.dst      = dst;
		this.var_name = var_name;
	}

	public void printCommand() {
		System.out.println("t" + dst.getSerialNumber() + " = " + var_name);
	}
}
