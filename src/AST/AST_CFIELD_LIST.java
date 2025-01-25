package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;

public class AST_CFIELD_LIST extends AST_Node {

    public AST_CFIELD cField;
    public AST_CFIELD_LIST cFieldList;

    public AST_CFIELD_LIST(AST_CFIELD cfield, AST_CFIELD_LIST cfield_list, int line){
        super(line);
        SerialNumber = AST_Node_Serial_Number.getFresh();
        
        if (cfield_list == null){
            System.out.println("====================== cFieldList -> cField");
        }
        else{
            System.out.println("====================== cFieldList -> cField cFieldList");
        }
        this.cFieldList = cfield_list;
        this.cField = cfield;
    }

    public void PrintMe() {
        /*********************************/
        /* AST NODE TYPE = CLASS FIELD LIST */
        /*********************************/
        System.out.println("AST NODE CLASS FIELD LIST");

        /******************************************/
        /* RECURSIVELY PRINT cField and cFieldList */
        /******************************************/
        if (cField != null) cField.PrintMe();
        if (cFieldList != null) cFieldList.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "CLASS FIELD LIST");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (cField != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cField.SerialNumber);
        if (cFieldList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cFieldList.SerialNumber);
    }

    public TYPE_LIST SemantMe() {
        TYPE t = cField.SemantMe();
        if (cFieldList == null){
            return new TYPE_LIST(t, null);
        }
        return new TYPE_LIST(t, cFieldList.SemantMe());
    }

    public TEMP IRme()
    {
		return null;
    }

}