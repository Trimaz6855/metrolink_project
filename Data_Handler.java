import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Data_Handler {

    public Data_Handler(File station_times) {
        try(Scanner new_scanner = new Scanner(station_times)) {
            while(new_scanner.hasNextLine()) {
                String line = new_scanner.nextLine();
                System.out.println(line);
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