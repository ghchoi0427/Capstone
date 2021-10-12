package web.controller;

import org.jfree.data.time.Second;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private boolean isDataSent = false;

    @GetMapping
    public ResponseEntity<String> getData(@RequestParam(value = "temp") int temp, @RequestParam(value = "humidity") int humidity) throws IOException {
        int returnTemp = -1;
        int returnHumid = -1;

        if (validateParam(temp, humidity)) {
            CSVFactory.addData(new String[]{new Second().toString(), String.valueOf(temp), String.valueOf(humidity)});
            List<Integer> returnList = getDataAtTime(returnTemp, returnHumid);
            returnTemp = returnList.get(0);
            returnHumid = returnList.get(1);
        }
        System.out.println("[" + new Date() + "]: temperature: " + temp + ", " + "humidity: " + humidity);
        return new ResponseEntity<>(returnTemp + "/" + returnHumid, HttpStatus.OK);
    }

    private List<Integer> getDataAtTime(int returnTemp, int returnHumid) {
        LocalTime now = LocalTime.now();
        if (now.getHour() == 6 || now.getHour() == 11 || now.getHour() == 16) {
            if (!isDataSent) {

                String requestHour = String.format("%02d", (now.getHour() - 1));
                String json = APIHandler.sendRequest(getDate(), requestHour + "00");
                try {
                    returnTemp = (int) Float.parseFloat(JSONHandler.getTemperature(json));
                    returnHumid = Integer.parseInt(JSONHandler.getHumidity(json));
                } catch (org.json.simple.parser.ParseException e) {
                    System.out.println(e.getMessage());
                }

                isDataSent = true;
            }
        } else {
            isDataSent = false;
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

    private boolean validateParam(int temp, int humid) {

        if (temp == -1 && humid == -1) {
            return false;
        }

        if (humid < 0 || humid > 100) {
            return false;
        }

        return true;
    }

}
