import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import java.util.regex.*;

public class Program {
    
    private Graph graph = new Graph();
    private String desired_start, desired_end;

    public Program() {
        
        data_handler(new File("Metrolink_times_linecolour.csv"));
        // data_handler(new File("test_times.csv"));
        get_user_input();
        graph.print_edges();
        // graph.dijkstras(desired_start, desired_end);

    }

    public void data_handler(File times_stations) {

        try(Scanner new_scanner = new Scanner(times_stations)) {
            String regex = ",";
            String line_colour = "Blank";
            while(new_scanner.hasNextLine()) {
                String line = new_scanner.nextLine();
                Pattern pattern = Pattern.compile("^From ");
                Matcher matcher = pattern.matcher(line);
                boolean match_found = matcher.find();
                if(match_found == false) {
                    String[] split_line = line.split(regex);
                    switch (split_line.length) {
                        case 1:
                            line_colour = split_line[0];
                            // System.out.println("Line colour: " + line_colour);
                            break;
                        case 3:
                            String station_start = split_line[0];
                            String station_end = split_line[1];
                            double line_time = Double.parseDouble(split_line[2].strip());
                            // System.out.println("From " + station_start + " to " + station_end + " in " + line_time + " minutes." );

                            graph.add_edge(station_start, station_end, line_colour, line_time);
                            
                            break;
                        default:
                            System.out.println("Shouldn't print!");
                            break;
                    }
                }
            }
            // graph.print_vertices();
            // graph.print_edges();
        }
        catch(FileNotFoundException e) {
            System.err.println("File not found.");
        }
    }

    public void get_user_input() {
        Boolean input_gathered = false;
        Scanner input_scanner = new Scanner(System.in);
        while(input_gathered == false) {
            System.out.println("Enter Desired Start Station: ");
            desired_start = input_scanner.nextLine();
            if(graph.find_vertex(desired_start) == true) {
               input_gathered = true;
            }
        }

        input_gathered = false;
        
        while(input_gathered == false) {
            System.out.println("Enter Desired End Station: ");
            desired_end = input_scanner.nextLine();
            if(graph.find_vertex(desired_end) == true) {
                System.out.println("Valid start and end station");
                input_gathered = true;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // System.setOut(new PrintStream(new FileOutputStream("results.txt")));
        Program new_program = new Program();
    }

}