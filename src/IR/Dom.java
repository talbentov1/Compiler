package IR;
import java.util.Objects;

class Dom {
    private String varName;
    private Integer label;

    public Dom(String varName, Integer label) {
        this.varName = varName;
        this.label = label;
    }

    public String getVarName() {
        return varName;
    }

    public Integer getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dom pair = (Dom) o;
        return Objects.equals(varName, pair.varName) && Objects.equals(label, pair.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(varName, label);
    }

    @Override
    public String toString() {
        return "(" + varName + ", " + label + ")";
    }
}