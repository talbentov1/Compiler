package IR;
import java.util.Objects;

class Dom {
    private String varName;
    private Integer label;
    private Integer scope;

    public Dom(String varName, Integer label, Integer scope) {
        this.varName = varName;
        this.label = label;
        this.scope = scope;
    }

    public String getVarName() {
        return varName;
    }

    public Integer getLabel() {
        return label;
    }

    public Integer getScope() {
        return scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dom pair = (Dom) o;
        return Objects.equals(varName, pair.varName) && Objects.equals(label, pair.label) && Objects.equals(scope, pair.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(varName, label);
    }

    @Override
    public String toString() {
        return "(" + varName + ", " + label + ", " + scope + ")";
    }
}