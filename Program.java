import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.regex.*;

public class Program {
    
    private Graph graph = new Graph();

    public Program() {
        
        data_handler(new File("Metrolink_times_linecolour.csv"));

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
                            System.out.println("Line colour: " + line_colour);
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
        }
        catch(FileNotFoundException e) {
            System.err.println("File not found.");
        }

        graph.print_vertices();
        // graph.print_edges();

    }

    public static void main(String[] args) {
        Program new_program = new Program();
    }

}