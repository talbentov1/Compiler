package TYPES;

public abstract class TYPE
{
	/******************************/
	/*  Every type has a name ... */
	/******************************/
	public String name;

	/*************/
	/* isClass() */
	/*************/
	public boolean isClass(){ return false;}

	/*************/
	/* isArray() */
	/*************/
	public boolean isArray(){ return false;}

	public TYPE getType(){
		if (this instanceof TYPE_CLASS_VAR_DEC){
			return ((TYPE_CLASS_VAR_DEC)this).t;
		}
		return this;
	}

    public boolean are_types_allowed_for_equal_binop(TYPE t2) {

		boolean same_type = this.name.equals(t2.name);
		boolean derived_class = (this instanceof TYPE_CLASS && t2 instanceof TYPE_CLASS &&
		((TYPE_CLASS) t2).isSuperClassOf((TYPE_CLASS) this));
		boolean comparible_to_nil = TYPE_NIL.canAssignNilTo(this) && t2 instanceof TYPE_NIL;

		return same_type || derived_class || comparible_to_nil ;
				
    }
}
