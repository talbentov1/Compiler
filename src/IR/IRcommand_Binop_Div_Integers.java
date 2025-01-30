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

public class IRcommand_Binop_Div_Integers extends IRcommand_Binop
{
	public IRcommand_Binop_Div_Integers(TEMP dst,TEMP t1,TEMP t2)
	{
		super(dst,t1,t2);
	}

	public void printCommand() {
		System.out.println("t" + dst.getSerialNumber() + " = " + "t" + t1.getSerialNumber() + " / t" + t2.getSerialNumber());
	}
}
