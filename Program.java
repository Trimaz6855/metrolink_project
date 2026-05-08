import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.regex.*;

/**
  * The main program file which handles the data input and then performs the Dijkstra's algorithm on the graph.
  **/
public class Program {
    
    private Graph graph = new Graph();
    private String desired_start, desired_end, constraint;

    /**
      * The constructor for the main program which calls the data handler, gets the user input and then calls Dijkstra's. 
      **/
    public Program() {
    
        data_handler(new File("Metrolink_times_linecolour.csv"));
        get_user_input();
        graph.dijkstras(desired_start, desired_end, constraint);

    }

    /**
      * The data handler function, parses the data from the input file and adds the edges and vertices to the graph.
      * @param times_stations File - the input file containing the tram lines between stations.
      **/
    public void data_handler(File times_stations) {

        // Attempts to open the station and times file passed into the data handler.
        try(Scanner new_scanner = new Scanner(times_stations)) {
            // Defines the regex condition to match when splitting the lines
            String regex = ",";
            String line_colour = "Blank";
            // Loops through each line in the file.
            while(new_scanner.hasNextLine()) {
                String line = new_scanner.nextLine();
                // Checks that the line is not the line with the format of the headers.
                Pattern pattern = Pattern.compile("^From ");
                Matcher matcher = pattern.matcher(line);
                boolean match_found = matcher.find();
                // Splits up the line according to the regex if its not the header line.
                if(match_found == false) {
                    String[] split_line = line.split(regex);
                    switch (split_line.length) {
                        // This case handles the lines in the input file that contain the colours.
                        case 1:
                            line_colour = split_line[0];
                            break;
                        // This case handles the lines in the input file containing the station a tram line starts at, the station it ends at and the time taken for the line.
                        case 3:
                            String station_start = split_line[0];
                            String station_end = split_line[1];
                            // Converts the time taken for the tram line from a string to a double.
                            double line_time = Double.parseDouble(split_line[2].strip());
                            graph.add_edge(station_start, station_end, line_colour, line_time);
                            break;
                        default:
                            System.out.println("Shouldn't print!");
                            break;
                    }
                }
            }
        }
        // Prevents the program trying to open a non-existent file.
        catch(FileNotFoundException e) {
            System.err.println("File not found.");
        }
    }

    /**
      * Gets the input from the user and validates the stations against the stations in the graph.
      * Requires the user to enter valid start and end stations. 
      */
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
                // System.out.println("Valid start and end station");
                input_gathered = true;
            }
        }

        input_gathered = false;
        while(input_gathered == false) { 
            System.out.println("Please Enter a Journey Constraint:");
            System.out.println("(a) 'Shortest' for shortest path");
            System.out.println("(b) 'Least' for the route with the least changes");
            constraint = input_scanner.nextLine();
            if(constraint.equals("Shortest") || constraint.equals("Least")) {
                input_gathered = true;
            }
        }

        input_scanner.close();
    }
    

    public static void main(String[] args) throws Exception {
        // System.setOut(new PrintStream(new FileOutputStream("results.txt")));
        Program new_program = new Program();
    }

}