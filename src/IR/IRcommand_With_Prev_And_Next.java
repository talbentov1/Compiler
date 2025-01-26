package IR;


public class IRcommand_With_Prev_And_Next {
    private IRcommand currentIRcommand; 
    private IRcommand_With_Prev_And_Next next; 
    private IRcommand_With_Prev_And_Next prev; 

    public IRcommand_With_Prev_And_Next(IRcommand currentIRcommand) {
        this.currentIRcommand = currentIRcommand;
        this.next = null;
        this.prev = null;
    }

    public IRcommand getIRcommand() {
        return currentIRcommand;
    }

    public IRcommand_With_Prev_And_Next getPrev() {
        return prev;
    }

    public IRcommand_With_Prev_And_Next getNext() {
        return next;
    }

    public void setPrev(IRcommand_With_Prev_And_Next prev) {
        this.prev = prev;
    }

    public void setNext(IRcommand_With_Prev_And_Next next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return currentIRcommand.toString(); 
    }
}
