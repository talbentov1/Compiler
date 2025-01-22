package TYPES;

public class TYPE_ARRAY extends TYPE{

    public TYPE arrayType;

	public TYPE_ARRAY(TYPE type, String name)
	{
        this.arrayType = type;
		this.name = name;
	}
}