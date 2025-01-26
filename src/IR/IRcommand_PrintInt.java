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

public class IRcommand_PrintInt extends IRcommand
{
	TEMP t;
	
	public IRcommand_PrintInt(TEMP t)
	{
		this.t = t;
	}

	public void printCommand() {
		System.out.println("printInt(t" + t.getSerialNumber() + ")");
	}
}
