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

public class IR
{
	private IRcommand head=null;
	private IRcommandList tail=null;

	/******************/
	/* Add IR command */
	/******************/
	public void Add_IRcommand(IRcommand cmd)
	{
		if ((head == null) && (tail == null))
		{
			this.head = cmd;
		}
		else if ((head != null) && (tail == null))
		{
			this.tail = new IRcommandList(cmd,null);
		}
		else
		{
			IRcommandList it = tail;
			while ((it != null) && (it.tail != null))
			{
				it = it.tail;
			}
			it.tail = new IRcommandList(cmd,null);
		}
	}

	public void printIR(){

	}
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static IR instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected IR() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static IR getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new IR();
		}
		return instance;
	}
	
	public static IRcommand get_head()
	{
		return getInstance().head;
	}
	
	public static IRcommandList get_tail()
	{
		return getInstance().tail;
	}

	public void printIRCommands()
	{
		System.out.println("----------------------------IR---------------------");
		if (head != null)
		{
			// Print the first command
			head.printCommand();

			// Print the rest of the commands in the list
			IRcommandList current = tail;
			while (current != null)
			{
				current.head.printCommand();
				current = current.tail;
			}
		}
		else
		{
			System.out.println("No IR commands in the list.");
		}
	}
}
