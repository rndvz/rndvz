package pl.edu.agh.rndvz.model.jsonMappings;

public class JsonBool {
    private boolean value;

    public JsonBool() {
    }

    public JsonBool(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
