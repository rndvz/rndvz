package pl.edu.agh.rndvz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@RestController
public class RndvzApplication {
    @RequestMapping("/greeting")
    public Greeting greeting(){
        return new Greeting("hello vegas!");
    }
    public static void main(String[] args) {
        SpringApplication.run(RndvzApplication.class, args);
    }

}

class Greeting {
    private String id = UUID.randomUUID().toString();
    private String msg;

    @SuppressWarnings("unused")
    private Greeting() {
    }

    public Greeting(String msg){
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "msg='" + msg + '\'' +
                '}';
    }
}