package AST;
import TYPES.TYPE;
import TEMP.TEMP;

public abstract class AST_ARRAYDEC_TYPE extends AST_Node
{
    public AST_ARRAYDEC_TYPE(int line){
        super(line);
    }
    public TYPE SemantMe() {
        return null;
    }
    public TEMP IRme(){
        return null;
    }
}