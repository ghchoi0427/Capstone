package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.view.MainScreen;

@SpringBootApplication
public class BootWebApplication {

    public static void main(String[] args) {
        if (args.length == 0) {
            new MainScreen();
        } else {
            SpringApplication.run(BootWebApplication.class, args);
        }
    }
}
