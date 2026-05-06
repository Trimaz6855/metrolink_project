public class Graph {
    
    private int next_vertex_id = 0;
    private int next_colour_id = 0;
    private Edge[][][] edges;
    private Vertex[] vertices;
    private Colour[] lines;

    public Graph() {
        ;
    }

    public void add_vertex(String name) {

        next_vertex_id ++ ;
        Vertex[] vertices_new = new Vertex[next_vertex_id];
        if(vertices != null && vertices.length != 0) {
            for(int i = 0; i < vertices.length; i ++ ) {
                vertices_new[i] = vertices[i];
            }
        }
        vertices_new[next_vertex_id - 1] = new Vertex(name, next_vertex_id - 1);

        this.vertices = vertices_new;

    }
    
    public void add_edge(String station_start, String station_end, String line_colour, double line_time ) {
        
        if(lines != null && lines.length != 0) {
            boolean line_found = find_colour(line_colour);
            if(line_found == false) {
                Colour[] lines_new = new Colour[lines.length + 1];
                for(int i = 0; i < lines.length; i ++ ) {
                    lines_new[i] = lines[i];
                }
                lines_new[lines_new.length - 1] = new Colour(line_colour, next_colour_id);
                lines = lines_new;
                next_colour_id ++;
            }
        }
        else {
            Colour[] lines_new = new Colour[1];
            lines_new[0] = new Colour(line_colour, next_colour_id);
            lines = lines_new;
            next_colour_id ++;
        }

        if(find_vertex(station_start) == false) {

            add_vertex(station_start);
            
        }

        if(find_vertex(station_end) == false) {
            
            add_vertex(station_end);
        }
        
        Edge[][][] edges_new = new Edge[vertices.length][vertices.length][lines.length];
        if(edges != null && edges.length != 0) {
            for(int i = 0; i < edges.length; i ++ ) {
                for(int j = 0; j < edges[i].length; j ++ ) {
                    for(int k = 0; k < edges[i][j].length; k ++ ) {
                        edges_new[i][j][k] = edges[i][j][k];
                    }
                }
            }
        }

        int id_start_vertex = get_vertex_id_from_name(station_start);
        int id_end_vertex = get_vertex_id_from_name(station_end);
        // for(int i = 0; i < lines.length; i ++ ) {
        //     if(lines[i] == null) {
        //         System.out.println("Null value");
        //     }
        //     else {
        //         System.out.println(lines[i].get_colour_name());
        //     }
        // }
        int id_line_colour = get_colour(line_colour);

        // System.out.println("Length of array lines: " + lines.length);
        if(id_start_vertex != -1 && id_end_vertex != -1) {
            // System.out.println("Length of edges[" + id_start_vertex + "][" + id_end_vertex + "][" + i + "]: " + edges_new[id_start_vertex][id_end_vertex][i]);
            if(edges_new[id_start_vertex][id_end_vertex][id_line_colour] == null) {
                edges_new[id_start_vertex][id_end_vertex][id_line_colour] = new Edge(id_start_vertex, id_end_vertex, line_time, line_colour);
            }
            if(edges_new[id_end_vertex][id_start_vertex][id_line_colour] == null) {
                edges_new[id_end_vertex][id_start_vertex][id_line_colour] = new Edge(id_end_vertex, id_start_vertex, line_time, line_colour);
            }
        }

        this.edges = edges_new;

    }

    public boolean find_vertex(String name) {
        
        if(vertices != null) {

            for(int i = 0; i < vertices.length; i ++ ) {
                if(vertices[i].get_vertex_name().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int get_vertex_id_from_name(String name) {
        for(int i = 0; i < vertices.length; i ++ ) {
            if(vertices[i].get_vertex_name().equals(name)) {
                return vertices[i].get_vertex_id();
            }
        }
        return -1;
    }

    public String get_vertex_name_from_id(int id) {
        try {
            return vertices[id].get_vertex_name();
        } catch (Exception e) {
            System.err.println("Unable to retrieve a vertex at id: " + id);
            return e.getMessage();
        }
    }

    public Vertex get_vertex(String name) {
        return vertices[get_vertex_id_from_name(name)];
    }
    
    public Vertex get_vertex(int id) {
        return vertices[id];
    }

    public Boolean find_edge(String start, String end) {
        
        int id_start = get_vertex_id_from_name(start);
        int id_end = get_vertex_id_from_name(end);

        if(edges[id_start][id_end] != null && edges[id_start][id_end].length > 0) {
            return true;
        }
        else {
            return false;
        }

    }

    public Edge[] get_edge_from_stations(String start, String end) {
        
        int id_start = get_vertex_id_from_name(start);
        int id_end = get_vertex_id_from_name(end);

        return edges[id_start][id_end];
        
    }

    /*
    public String get_line_from_stations(int start, int end) {

        try {
            return edges[start][end].get_line();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            return e.getMessage();
        }
    }
    */

    public int get_station_count() {
        return vertices.length;
    }

    public void print_vertices() {

        if(vertices != null) {
            for(int i = 0; i < vertices.length; i ++ ) {
                System.out.println(vertices[i].get_vertex_name());
            }
        }
        
        System.out.println(vertices.length + " stations");

    }

    public void print_edges() {

        if(edges != null) {
            for(int i = 0; i < edges.length; i ++ ) {
                System.out.print("[");
                for(int j = 0; j < edges.length; j ++ ) {
                    System.out.print("[");
                    for(int k = 0; k < lines.length; k ++ ) {
                        if(edges[i][j][k] != null) {
                            System.out.print(edges[i][j][k].get_weight() + " ");
                        }
                        else {
                            System.out.print("- ");
                        }
                    }
                    System.out.print("]");
                }
                System.out.println("]"); 
            }
        }
        System.out.println(edges.length + "x" + edges.length + " matrix");

    }

    public void print_specific_edges(String start, String end) {
        int id_start = get_vertex_id_from_name(start);
        int id_end = get_vertex_id_from_name(end);

        System.out.print("[");
        for(int i = 0; i < edges[id_start][id_end].length; i ++ ) {
            if(edges[id_start][id_end][i] != null ) {
                System.out.print(edges[id_start][id_end][i].get_weight() + ",");
            }
        }
        System.out.println("]");
    }

    public String get_colour(int id) {
        for(int i = 0; i < lines.length; i ++ ) {
            if(lines[i].get_colour_id() == id) {
                return lines[i].get_colour_name();
            }
        }
        return null;
    }

    public int get_colour(String name) {
        for(int i = 0; i < lines.length; i ++ ) {
            if(lines[i].get_colour_name().equals(name)) {
                return lines[i].get_colour_id();
            }
        }
        return -1;
    }

    public boolean find_colour(String name) {
        for(int i = 0; i < lines.length; i ++ ) {
            if(lines[i].get_colour_name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void dijkstras(String start, String end) {

        // Store the start and end vertex ids for ease of use.
        int id_start = get_vertex_id_from_name(start);
        int id_end = get_vertex_id_from_name(end);

        // Sets up flag variable for if all vertices have been visited.
        boolean visited_all = false;

        // Set up shortest disatnce array.
        double[][] shortest_distance_to_start = new double[vertices.length][lines.length];
        // Set up the visited array.
        boolean[][] vertices_visted = new boolean[vertices.length][lines.length];
        // Set up previous vertex array.
        int[][] vertex_previous = new int[vertices.length][lines.length];
        for(int i = 0; i < vertices.length; i ++ ) { 
            for(int j = 0; j < lines.length; j ++ ) {
                if(i == id_start) {
                    shortest_distance_to_start[i][j] = 0;
                    vertex_previous[i][j] = -1;
                }
                else {
                    shortest_distance_to_start[i][j] = Double.POSITIVE_INFINITY;
                    vertices_visted[i][j] = false;
                    vertex_previous[i][j] = -1;
                }
            }
        }

        int id_current = id_start;
        int id_colour_current = -1;
        int id_next_vertex = -1;
        
        while(visited_all == false) {
            
            for(int i = 0; i < vertices.length; i ++ ) { 
                if(edges[id_current][i] != null) {
                    for(int j = 0; j < edges[id_current][i].length; j ++ ) {
                        if(edges[id_current][i][j] != null) {
                            Edge edge_current = edges[id_current][i][j];
                            int id_colour = get_colour(edge_current.get_line());
                            
                            boolean line_changed = false;

                            if(id_colour_current == -1) {
                                id_colour_current = id_colour;
                            }

                            double weight_new = shortest_distance_to_start[id_current][id_colour_current] + edge_current.get_weight();

                            if(id_current == id_start) {
                                line_changed = false;
                            }

                            else if(id_colour != id_colour_current) {
                                line_changed = true;
                            }
                            
                            
                            if(line_changed == true) {
                                weight_new += 2;
                            }
                            
                            if(shortest_distance_to_start[i][id_colour] == Double.POSITIVE_INFINITY && weight_new < Double.POSITIVE_INFINITY) {
                                shortest_distance_to_start[i][id_colour] = weight_new;
                                vertex_previous[i][id_colour] = id_current;
                            }
                            else {
                                if(weight_new < shortest_distance_to_start[i][id_colour]) {
                                    shortest_distance_to_start[i][id_colour] = weight_new;
                                    vertex_previous[i][id_colour] = id_current;
                                }
                            }
                        }
                    }
                }
            }

            // System.out.println("Current id: " + id_current);
            // System.out.println("Current colour: " + id_colour_current);
            vertices_visted[id_current][id_colour_current] = true;

            double current_shortest_distance = Double.POSITIVE_INFINITY;
            int id_colour_next = -1;
            for(int i = 0; i < shortest_distance_to_start.length; i ++ ) {
                for(int j = 0; j < shortest_distance_to_start[i].length; j ++ ) {
                    if(shortest_distance_to_start[i][j] < current_shortest_distance && vertices_visted[i][j] == false) {
                        if(shortest_distance_to_start[i][j] > 0) {
                            current_shortest_distance = shortest_distance_to_start[i][j];
                            id_next_vertex = i;
                            id_colour_next = j;
                            // System.out.println("i: " + i + " j: " + j);
                        }
                    }
                }
            }

            if(current_shortest_distance == Double.POSITIVE_INFINITY) {
                break;
            }
            else {
                id_current = id_next_vertex;
                id_colour_current = id_colour_next;
                // System.out.println("Next id: " + id_next_vertex);
            }

        }

        double distance_final = Double.POSITIVE_INFINITY;
        // int id_shortest_line = -1;
        for(int i = 0; i < shortest_distance_to_start[id_end].length; i ++ ) {
            if(shortest_distance_to_start[id_end][i] < distance_final && shortest_distance_to_start[id_end][i] > 0) {
                distance_final = shortest_distance_to_start[id_end][i];
                // id_shortest_line = i;
            }
        }

        // int[] route = new int[1];
        // route[0] = id_end;

        // while(id_end != id_start) {

        // }
        

        System.out.println(distance_final);
















        


        // // loops through until all vertices have been visited.
        // while(visited_all == false) {

        //     // Loops through each edge connected to the current vertex.
        //     for(int i = 0; i < vertices.length; i ++ ) {
        //         // Checks its not null, if null doesnt continue.
        //         if(edges[id_current][i] != null) {
        //             for(int j = 0; j < edges[id_current][i].length; j ++ ) { 
        //                 if(edges[id_current][i][j] != null) {
        //                     // If not null, stores the current edge.
        //                     Edge edge_current = edges[id_current][i][j];

        //                     // Checks if there has been a line change in between the this current vertex and the previous, if true a flag is set.
        //                     boolean line_changed = false;
        //                     if(id_current == id_start) { 
        //                         line_changed = false;
                                
        //                     }
        //                     else if(line_previous[id_current].equals(edge_current.get_line()) == false) {
        //                         line_changed = true;
        //                         System.out.println("1. " + get_vertex_name_from_id(id_current) + " to " + get_vertex_name_from_id(i) + " via " + line_previous[id_current] + " line vs " + edge_current.get_line() + " line");
        //                     }
        //                     else {
        //                         System.out.println("2. " + get_vertex_name_from_id(id_current) + " to " + get_vertex_name_from_id(i) + " via " + line_previous[id_current] + " line vs " + edge_current.get_line() + " line");
        //                     }

        //                     // Checks if the shortest distance to vertex is infinity.
        //                     if(shortest_distance_to_start[i] == Double.POSITIVE_INFINITY) {
        //                         // If shortest is infinity, sets shortest distance to be previous vertex distance + edge weight (+ 2 depending on flag).
        //                         if(line_changed == true) {
        //                             shortest_distance_to_start[i] = shortest_distance_to_start[id_current] + edge_current.get_weight() + 2;
        //                             System.out.println("3. Distance on " + edge_current.get_line() + " line: " + shortest_distance_to_start[i]);
        //                         }
        //                         else {
        //                             System.out.println("4. Distance on " + edge_current.get_line() + " line: " + shortest_distance_to_start[i]);
        //                             shortest_distance_to_start[i] = shortest_distance_to_start[id_current] + edge_current.get_weight();
        //                         }
        //                         // Update previous vertex array and line array.
        //                         vertices_previous[i] = id_current;
        //                         line_previous[i] = edge_current.get_line();
        //                     }
        //                     else {
        //                         // If not, calculates new weight = shortest distance + edge weight (+ 2 depending on flag).
        //                         double weight_new = shortest_distance_to_start[id_current] + edge_current.get_weight();
        //                         if(line_changed == true) {
        //                             weight_new += 2;
        //                             System.out.println("5. Distance on " + line_previous[i] + " line: " + shortest_distance_to_start[i]);
        //                             System.out.println("6. Distance on " + edge_current.get_line() + " line: " + weight_new);
        //                         }
        //                         else {
        //                             System.out.println("7. Distance on" + edge_current.get_line() + " line: " + weight_new);
        //                         }
                                
        //                         // Compare current and new weight, if new is less then current = new.
        //                         if(weight_new <= shortest_distance_to_start[i]) {
        //                             shortest_distance_to_start[i] = weight_new;
        //                             // Update previous vertex and line arrays.
        //                             vertices_previous[i] = id_current;
        //                             line_previous[i] = edge_current.get_line();
        //                         }

        //                     }
        //                 }
        //             }
        //         }
        //     }
            
        //     // Then loop through shortest distance array to find closest non-visited vertex and set next id = id. 
        //     double shortest_distance = Double.POSITIVE_INFINITY;
        //     for(int i = 0; i < shortest_distance_to_start.length; i ++ ) {
        //         if(shortest_distance_to_start[i] < shortest_distance && vertices_visited[i] == false) {
        //             if(shortest_distance_to_start[i] > 0) {
        //                 id_next = i;
        //                 shortest_distance = shortest_distance_to_start[i];
        //             }
        //         }
        //     }
            
        //     // Once loop is complete, update vertex visited array.
        //     vertices_visited[id_current] = true;
            
        //     // Check if all vertices have been visited.
        //     for(int i = 0; i < vertices_visited.length; i ++ ) {
        //         if(vertices_visited[i] == true) {
        //             visited_all = true;
        //         }
        //         else if(vertices_visited[i] == false){
        //             visited_all = false;
        //             break;
        //         }
        //     }
            
        //     // Update id current to be id next.
        //     id_current = id_next;

        // }

        // int route[] = new int[1];
        // route[0] = id_end;

        // // Backtrack through vertices previous array and store route from start to end.
        // while (id_end != id_start) {
        //     int[] route_new = new int[route.length + 1];
        //     for(int i = route.length; i > 0; i -- ) {
        //         route_new[i] = route[i - 1];
        //     }

        //     if(id_end != -1 && vertices_previous[id_end] != -1) {
        //         id_end = vertices_previous[id_end];
        //         route_new[0] = id_end;
        //     }

        //     route = route_new;
        // }

        // String line_current = line_previous[route[1]];
        // // Loop through, printing the stations and the time.
        // for(int i = 0; i < route.length; i ++ ) {
        //     System.out.println(get_vertex_name_from_id(route[i]) + " on " + line_current + " line");
        //     if(i + 1 < route.length && line_previous[route[i + 1]].equals(line_current) == false) {
        //         line_current = line_previous[route[i + 1]];
        //         System.out.println("** Change line to " + line_current + " line **");
        //         System.out.println(get_vertex_name_from_id(route[i]) + " on " + line_current + " line");
        //     }
        // }
        // System.out.println("Total Journey Time: " + shortest_distance_to_start[route[route.length - 1]]);

    }
}   