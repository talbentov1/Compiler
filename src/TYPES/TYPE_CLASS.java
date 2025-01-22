package TYPES;

public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_LIST data_members;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name,TYPE_LIST data_members)
	{
		this.name = name;
		this.father = father;
		this.data_members = data_members;
	}

	/*************************************************************************************/
	/* Search method in class's methods. returns the method if found and null otherwise  */
	/*************************************************************************************/     
	public TYPE_FUNCTION get_method_from_class(String methodName) {
        TYPE_LIST currentMethod = this.data_members;
        while (currentMethod != null) {
            if (((TYPE_CLASS_VAR_DEC)currentMethod.head).name.equals(methodName) && ((TYPE_CLASS_VAR_DEC)currentMethod.head).t instanceof TYPE_FUNCTION) {
                return (TYPE_FUNCTION)((TYPE_CLASS_VAR_DEC)currentMethod.head).t;
            }
            currentMethod = currentMethod.tail;
        }
		return null;
	}

	public TYPE_FUNCTION get_method_from_ancestors (String methodName) {
		TYPE_CLASS currAncestor = this.father;
		while (currAncestor != null) {
			TYPE_LIST currentMethod = currAncestor.data_members;
			while (currentMethod != null) {
				if (((TYPE_CLASS_VAR_DEC)currentMethod.head).name.equals(methodName) && ((TYPE_CLASS_VAR_DEC)currentMethod.head).t instanceof TYPE_FUNCTION) {
					return (TYPE_FUNCTION)((TYPE_CLASS_VAR_DEC)currentMethod.head).t;
				}
				currentMethod = currentMethod.tail;
        	}
			currAncestor = currAncestor.father;
		}
		return null;
	}

	public TYPE_FUNCTION get_method(String methodName){
		TYPE_FUNCTION func = get_method_from_class(methodName);
		if (func != null){
			return func;
		}

		return get_method_from_ancestors(methodName);
	}



	public boolean isSuperClassOf(TYPE typeClass) {
    if (typeClass == null || !(typeClass instanceof TYPE_CLASS)) {
        return false;
    }

    if (this.name.equals(typeClass.name)) {
        return true;
    }

    TYPE_CLASS currentClass = (TYPE_CLASS) typeClass;
    while (currentClass.father != null) {
        currentClass = currentClass.father;
        if (currentClass.name.equals(this.name)) {
            return true;
        }
    }

    return false;
}

public TYPE findDataMember(String propertyName) {
        TYPE_LIST currDataMember = this.data_members;
        while (currDataMember != null && currDataMember.head != null) {
            if (((TYPE_CLASS_VAR_DEC)currDataMember.head).name.equals(propertyName)) {
                return ((TYPE_CLASS_VAR_DEC)currDataMember.head).t;
            }
            currDataMember = currDataMember.tail;
        }

        TYPE_CLASS currentAncestor = this.father;
        while (currentAncestor != null) {
            currDataMember = currentAncestor.data_members;
            while (currDataMember != null) {
                if (((TYPE_CLASS_VAR_DEC)currDataMember.head).name.equals(propertyName)) {
                    return ((TYPE_CLASS_VAR_DEC)currDataMember.head).t;

                }
                currDataMember = currDataMember.tail;
            }
            currentAncestor = currentAncestor.father;
        }
        return null;
    }
}
