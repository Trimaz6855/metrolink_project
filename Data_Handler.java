import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.regex.*;

public class Data_Handler {

    private String regex = ",";

    public Data_Handler(File station_times) {
        try(Scanner new_scanner = new Scanner(station_times)) {

            
            while(new_scanner.hasNextLine()) {
                String line = new_scanner.nextLine();
                Pattern pattern = Pattern.compile("^From ");
                Matcher matcher = pattern.matcher(line);
                boolean match_found = matcher.find();
                // System.out.println(matches);
                if(match_found == false) {
                    String[] split_line = line.split(regex);
                    
                    switch (split_line.length) {
                        case 1:
                            System.out.println("Line colour: " + split_line[0]);
                            break;
                        case 3:    
                            System.out.println("From " + split_line[0] + " to " + split_line[1] + " in " + split_line[2] + " minutes." );
                            break;
                        default:
                            System.out.println("Shouldn't print!");
                            break;
                    }
                }
            }
        }
        catch(FileNotFoundException e) {
            System.err.println("File not found.");
        }
    }

    public Data_Handler(File station_times, File walking_times) {
        ;
    }

}