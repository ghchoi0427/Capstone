package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.view.MainScreen;

@SpringBootApplication
public class BootWebApplication {

    public static void main(String[] args) {
        new Thread(() -> new MainScreen()).run();
        SpringApplication.run(BootWebApplication.class, args);
    }
}
