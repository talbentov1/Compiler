package TYPES;

public class TYPE_NIL extends TYPE
{
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static TYPE_NIL instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected TYPE_NIL() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static TYPE_NIL getInstance()
	{
		if (instance == null)
		{
			instance = new TYPE_NIL();
			instance.name = "nil";
		}
		return instance;
	}

	public static boolean canAssignNilTo(TYPE type){
    	return type instanceof TYPE_CLASS || type instanceof TYPE_ARRAY || type instanceof TYPE_NIL;
    }
}
