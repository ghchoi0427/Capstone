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
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private boolean isDataSent = false;
    private boolean isCalibrated = false;
    private List<Integer> tempList = new ArrayList<>();
    private int targetTemp = 23;
    int returnTemp = -1;
    int returnHumid = -1;

    int currentTemp = -1;
    int currentHumid = -1;

    @GetMapping
    public ResponseEntity<String> getData(@RequestParam(value = "temp") int temp, @RequestParam(value = "humidity") int humidity) throws IOException {


        if (validateParam(temp, humidity)) {
            CSVFactory.addData(new String[]{new Second().toString(), String.valueOf(temp), String.valueOf(humidity)});
            List<Integer> returnList = getDataAtTime(returnTemp, returnHumid);
            returnTemp = returnList.get(0);
            returnHumid = returnList.get(1);
            setTempList(temp);

            currentTemp = temp;
            currentHumid = humidity;

            if (Modulator.isDesignatedTime(LocalTime.now().getHour())) {
                calibrateTargetTemp(getLowestTemp(tempList), targetTemp);
            }
        }
        System.out.println("[" + new Date() + "]: temperature: " + temp + ", " + "humidity: " + humidity);
        return new ResponseEntity<>(returnTemp + "/" + targetTemp, HttpStatus.OK);
    }

    @GetMapping("/sensor")
    public ResponseEntity<String> retrieveData() {
        return new ResponseEntity<>(currentTemp + "/" + currentHumid, HttpStatus.OK);
    }

    private List<Integer> getDataAtTime(int returnTemp, int returnHumid) {
        LocalTime now = LocalTime.now();
        if (Modulator.isDesignatedTime(now.getHour())) {
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

    private void calibrateTargetTemp(int lowestTemp, int targetTemp) {
        if (Modulator.isDesignatedTime(LocalTime.now().getHour())) {
            if (!isCalibrated) {
                Modulator.modulateTargetTemp(lowestTemp, targetTemp);
                isCalibrated = true;
            }
        } else {
            isCalibrated = false;
        }
    }

    private void setTempList(int temp) {
        tempList.add(temp);
        if (!tempList.isEmpty() && Modulator.isDesignatedTime(LocalTime.now().getHour() - 1)) {
            tempList.clear();
        }
    }

    private int getLowestTemp(List<Integer> tempList) {
        return tempList.get(tempList.indexOf(Collections.min(tempList)));
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

        if (humid > 100) {
            return false;
        }

        return true;
    }

}
