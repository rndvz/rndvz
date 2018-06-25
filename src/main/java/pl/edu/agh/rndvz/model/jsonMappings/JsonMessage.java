package pl.edu.agh.rndvz.model.jsonMappings;

public class JsonMessage {
    private  String message;

    public JsonMessage() {
    }

    public JsonMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
