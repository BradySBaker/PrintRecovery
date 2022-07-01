import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class main {
    private static Scanner x;

    public static void main(String args[]) throws IOException {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter height reached in mm: ");
        double objectHeight = scnr.nextDouble();
        System.out.println("Enter layer height: ");
        double layerHeight = scnr.nextDouble();

        int printAt = layerCalc(objectHeight, layerHeight);

        System.out.println("Print at Layer: " + printAt);

        String pathString = "C:/Users/kingb/OneDrive/Desktop/CE3_Cali-Dragon_v1.gcode";

        Path path = Paths.get(pathString);


        String file = Files.readString(path);

        String fixed = fixString(file, printAt, objectHeight);

        try (PrintWriter out = new PrintWriter("C:/Users/kingb/OneDrive/Desktop/recovered.gcode")) {
            out.println(fixed);
        }

    }

    public static String fixString(String file, int layer, double height) {
        Scanner scnr = new Scanner(file);
        String input = scnr.nextLine();
        boolean start = false;
        while (true) {
            input = scnr.nextLine();
            if(input.equals("G1 X10.1 Y20 Z0.28 F5000.0 ;Move to start position") || input.equals("G1 X10.1 Y200.0 Z0.28 F1500.0 E15 ;Draw the first line") || input.equals("G1 X10.4 Y200.0 Z0.28 F5000.0 ;Move to side a little") || input.equals("G1 X10.4 Y20 Z0.28 F1500.0 E30 ;Draw the second line")){
                file = file.replaceFirst(input, "");
            }
            if (input.equals(";LAYER:0")) {
                file = file.replaceFirst(input, "G1 Z" + height);
                start = true;
                break;
            }
            }
            if(start = true){
                int i = 1;
                while (true) {
                    input = scnr.nextLine();
                    if(input.equals(";LAYER:" + i)){
                        System.out.println("Progress: " + i + "/" + layer);
                        i++;
                    }
                    if(input.equals(";LAYER:" + layer)) {
                        break;
                    }
                    file = file.replaceFirst(input, "");
                }
            }

        return file;
        }

    public static int layerCalc(double objHeight, double layerHeight) {
        double printAt = (int) (objHeight / layerHeight) - 1 ;
        return (int)printAt;
    }

}


