package TYPES;

public class TYPE_LIST
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public TYPE head;
	public TYPE_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public static boolean checkCompatibleLists(TYPE_LIST l1, TYPE_LIST l2){
		if (l1 == null && l2 == null){
			return true;
		}
		if (l1 == null || l2 == null){
			return false;
		}

		TYPE head1 = l1.head;
		TYPE head2 = l2.head;
		TYPE_LIST tail1 = l1.tail;
		TYPE_LIST tail2 = l2.tail;

		boolean headsCompatible = areCompatibleTypes(head1, head2);

		if (!headsCompatible){
			return false;
		}

		return checkCompatibleLists(tail1, tail2);
	}

	public static boolean areCompatibleTypes(TYPE t1, TYPE t2){
		if (t1 == null && t2 == null){
			return true;
		}

		if (t1 == null || t2 == null){
			return false;
		}

		if (t1.name == t2.name){
			return true;
		}

		if (t1 instanceof TYPE_CLASS && t2 instanceof TYPE_CLASS){
			if (((TYPE_CLASS)t2).isSuperClassOf(((TYPE_CLASS)t1))){
				return true;
			}
			else{
				return false;
			}
		}

		if (TYPE_NIL.canAssignNilTo(t2) && (t1 instanceof TYPE_NIL)){
			return true;
		}

		return false;
	}
}
