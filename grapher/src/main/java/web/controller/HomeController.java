package web.controller;

import org.jfree.data.time.Second;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> getData(@RequestParam(value = "temp") int temp, @RequestParam(value = "humidity") int humidity) throws IOException, ParseException {
        int returnTemp = -1;
        int returnHumid = -1;

        CSVFactory.addData(new String[]{new Second().toString(), String.valueOf(temp), String.valueOf(humidity)});
        List<Integer> returnList = getDataAtTime(returnTemp, returnHumid);
        returnTemp = returnList.get(0);
        returnHumid = returnList.get(1);
        return new ResponseEntity<>(returnTemp + " / " + returnHumid, HttpStatus.OK);
    }

    private List<Integer> getDataAtTime(int returnTemp, int returnHumid) throws ParseException {
        LocalTime now = LocalTime.now();
        System.out.println(now.getHour());
        if (now.getHour() == 6 || now.getHour() == 11 || now.getHour() == 16) {
            String requestHour = String.format("%02d", (now.getHour() - 1));
            String json = APIHandler.sendRequest(getDate(), requestHour+"00");
            returnTemp = (int) Float.parseFloat(JSONHandler.getTemperature(json));
            returnHumid = Integer.parseInt(JSONHandler.getHumidity(json));
            System.out.println("it's time"+returnTemp+","+returnHumid);
        }
        List<Integer> result = new ArrayList<>();
        result.add(returnTemp);
        result.add(returnHumid);

        return result;
    }

    private String getDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return now.format(formatter);
    }

}
