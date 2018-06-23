package pl.edu.agh.rndvz.model.jsonMappings;

public class JsonLogin {
    private  String login;

    public JsonLogin() {
    }

    public JsonLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
