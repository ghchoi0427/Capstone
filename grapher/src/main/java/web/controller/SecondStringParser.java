package web.controller;

import org.jfree.data.time.Second;

public class SecondStringParser {

    public static Second StringToSecond(String string) {
        String time = string.split(" ")[3];
        int second = Integer.parseInt(time.split(":")[2]);
        int minute = Integer.parseInt(time.split(":")[1]);
        int hour = Integer.parseInt(time.split(":")[0]);
        int day = Integer.parseInt(string.split(" ")[2]);
        int month = 0;
        switch (string.split(" ")[1]) {
            case "Jan":
                month = 1;
                break;
            case "Feb":
                month = 2;
                break;
            case "Mar":
                month = 3;
                break;
            case "Apr":
                month = 4;
                break;
            case "May":
                month = 5;
                break;
            case "June":
                month = 6;
                break;
            case "July":
                month = 7;
                break;
            case "Aug":
                month = 8;
                break;
            case "Sept":
                month = 9;
                break;
            case "Oct":
                month = 10;
                break;
            case "Nov":
                month = 11;
                break;
            case "Dec":
                month = 12;
                break;
        }
        int year = Integer.parseInt(string.split(" ")[5]);


        return new Second(second, minute, hour, day, month, year);
    }
}
