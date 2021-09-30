package web.controller;

import org.jfree.data.time.Second;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> getData(@RequestParam(value = "temp") int temp, @RequestParam(value = "humidity") int humidity) throws IOException {
        CSVFactory.addData(new String[]{new Second().toString(), String.valueOf(temp), String.valueOf(humidity)});
        return new ResponseEntity<>(temp + " / " + humidity, HttpStatus.OK);
    }
}
