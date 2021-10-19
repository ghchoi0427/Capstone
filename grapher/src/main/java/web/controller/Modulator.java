package web.controller;

public class Modulator {

    public Modulator() {

    }

    public static boolean isDesignatedTime(int hour) {
        return hour % 6 == 0;
    }

    public static void modulateTargetTemp(int lowestTemp, int targetTemp) {

        if (lowestTemp <= 0) {
            targetTemp += 2;
        } else if (lowestTemp <= 5) {
            targetTemp += 3;
        } else if (lowestTemp <= 10) {
            targetTemp += 4;
        } else if (lowestTemp <= 15) {
            targetTemp += 5;
        } else if (lowestTemp <= 20) {
            targetTemp += 6;
        }
    }

}