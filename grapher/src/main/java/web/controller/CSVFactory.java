package web.controller;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVFactory {
    static final String PATH = "D:/sensor.csv";
    public static void createCsv() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(PATH));
        writer.close();
    }

    public static void addData(String[] nextLine) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(PATH));

        List<String[]> temp = reader.readAll();

        CSVWriter writer = new CSVWriter(new FileWriter(PATH));
        temp.forEach(writer::writeNext);
        writer.writeNext(nextLine);

        writer.close();
    }

    public static void readDataFromCsv() throws IOException {
        CSVReader reader = new CSVReader(new FileReader(PATH)); // 1

        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {   // 2
            for (int i = 0; i < nextLine.length; i++) {
                System.out.println(i + " " + nextLine[i]);
            }
            System.out.println();
        }
    }

    public static List<String[]> getCSV() throws IOException {
        CSVReader reader = new CSVReader(new FileReader(PATH));
        return reader.readAll();
    }
}
