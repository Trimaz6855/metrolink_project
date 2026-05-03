public class Graph {
    
    private int next_vertex_id = 0;
    private Edge[][][] edges;
    private Vertex[] vertices;
    private String[] lines;
    private String[] line_previous;

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
        
        // IMPLEMENT FUNCTIONALITY WHERE IT ASSUMES THE TIME TO TRAVEL IN THE REVERSE DIRECTION IS THE SAME!

        if(lines != null && lines.length != 0) {
            boolean line_found = false;
            String[] lines_new = new String[lines.length + 1];
            for(int i = 0; i < lines.length; i ++ ) {
                if(lines[i] == line_colour) {
                    line_found = true;
                    break;
                }
                else {
                    lines_new[i] = lines[i];
                }
            }
            if(line_found == false) {
                lines_new[lines_new.length-1] = line_colour;
                lines = lines_new;
            }
        }
        else {
            String[] lines_new = new String[1];
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
                for(int j = 0; j < edges.length; j ++ ) {
                    for(int k = 0; k < lines.length; k ++ ) {
                        edges_new[i][j][k] = edges[i][j][k];
                    }
                }
            }
        }

        int id_start_vertex = get_vertex_id_from_name(station_start);
        int id_end_vertex = get_vertex_id_from_name(station_end);

        if(id_start_vertex != -1 && id_end_vertex != -1) {
            for(int i = 0; i < lines.length; i ++ ) {
                if(edges[id_start_vertex][id_end_vertex][i] == null) {
                    edges[id_start_vertex][id_end_vertex][i] = new Edge(id_start_vertex, id_end_vertex, line_time, line_colour);
                }
                if(edges[id_end_vertex][id_start_vertex][i] == null) {
                    edges[id_end_vertex][id_start_vertex][i] = new Edge(id_end_vertex, id_start_vertex, line_time, line_colour);
                }
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
                        if(edges[i][j] != null) {
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

    public void dijkstras(String start, String end) {
        // Store the start and end vertex ids for ease of use.
        int id_start = get_vertex_id_from_name(start);
        int id_end = get_vertex_id_from_name(end);

        String line_current = "";

        // Set up the visited array to store which vertices have been visited, ensures all values start at false.
        boolean[] vertices_visited = new boolean[vertices.length];
        for(int i = 0; i < vertices.length; i ++ ) {
            vertices_visited[i] = false;
        }

        // Set up the shortest distance array, ensuring all values start at infinity.
        double[] shortest_distance_to_start = new double[vertices.length];
        for(int i = 0; i < vertices.length; i ++ ) {
            shortest_distance_to_start[i] = Double.POSITIVE_INFINITY;
        }
        shortest_distance_to_start[id_start] = 0;

        // Set up the previous vertex array to store the index of the previous vertex visited.
        // Starts out as being -1 as a placeholder for a null value.
        int[] vertices_previous = new int[vertices.length];
        for(int i = 0; i < vertices.length; i ++ ) {
            vertices_previous[i] = -1;
        }
        vertices_previous[id_start] = id_start;

        // Stores a flag that is updated each iteration to say whether all vertices have been visited.
        boolean visited_all = false;
        int id_current = id_start;
        double weight_next_shortest = Double.POSITIVE_INFINITY;
        int id_next = -1;

        while (visited_all == false) {
            // Start at start vertex.
            // Check all edges coming from start vertex.
            for(int i = 0; i < vertices.length; i ++ ) {
                // System.out.println(edges[id_current][i]);
                if(edges[id_current][i] != null) {
                    
                    Edge[] edge_current = edges[id_current][i];
                    
                    for(int j = 0; j < edge_current.length; j ++ ) {
                        boolean line_changed = false;
                        
                        if(line_previous[i] != edge_current[j].get_line()) {
                            line_changed = true;
                        }
                        
                        // Assign shortest distances from start vertex to each vertex in the array.
                        // Assign previous vertex in array.
                        if(shortest_distance_to_start[i] == Double.POSITIVE_INFINITY) {
                            double weight_new = edge_current[j].get_weight() + shortest_distance_to_start[id_current]; 
                            if(line_changed == true) {
                                shortest_distance_to_start[i] = weight_new + 2;
                                line_previous[i] = edge_current[j].get_line();
                            }
                            else {
                                shortest_distance_to_start[i] = weight_new;
                                line_previous[i] = edge_current[j].get_line();
                            }
                            vertices_previous[i] = id_current;
                        }
                        else {
                            double weight_new = shortest_distance_to_start[i] + edge_current[j].get_weight();
                            if(line_changed == true) {
                                weight_new += 2;
                            }

                            if(weight_new < shortest_distance_to_start[i]) {
                                shortest_distance_to_start[i] = weight_new;
                                vertices_previous[i] = id_current;
                                line_previous[i] = edge_current[j].get_line();
                            }
                        }
                    }
                }
            }

            // System.out.println("Current Vertex: " + get_vertex_name_from_id(id_current));
            // for(int j = 0; j < shortest_distance_to_start.length; j ++ ) {
            //     System.out.print(shortest_distance_to_start[j] + ", ");
            // }
            // System.out.println();
            
            // Find vertex that is shortest distance from start vertex.
            for(int j = 0; j < shortest_distance_to_start.length; j ++ ) {
                // System.out.println(shortest_distance_to_start[j] + " < " + weight_next_shortest);
                if(shortest_distance_to_start[j] < weight_next_shortest && vertices_visited[j] == false) {
                    if(shortest_distance_to_start[j] > 0) {
                        weight_next_shortest = shortest_distance_to_start[j];
                        id_next = j;
                        // System.out.println("Changed next closest vertex to " + get_vertex_name_from_id(j) + " to " + shortest_distance_to_start[j]);
                    }
                }
                else {
                    weight_next_shortest = Double.POSITIVE_INFINITY;
                }
            }
            
            vertices_visited[id_current] = true;
            
            // Check if all vertices have been visted.
            for(int j = 0; j < vertices_visited.length; j ++ ) {
                visited_all = true;
                if (vertices_visited[j] == false) {
                    // If false then dont adjust flag.
                    visited_all = false;
                    break;

                }
            }

            // Change current vertex to be this vertex.
            id_current = id_next;

        }

        // Loop through previous vertex array, backtracking from end vertex to start.

        int[] route = new int[1];
        route[0] = id_end;

        while(id_end != id_start) {
            
            // Add each vertex to a new array with variable length (reassign length before adding a new node each time).
            int[] route_new = new int[route.length + 1];
            for(int i = route.length; i >= 1; i -- ) {
                route_new[i] = route[i - 1];
            }

            if(id_end == -1 || vertices_previous[id_end] == -1 ) {
                System.out.println("Previous Vertex id: " + vertices_previous[id_end]);
                break;
            }
            else {
                id_end = vertices_previous[id_end];
                route_new[0] = id_end;
            }

            // Make sure most recent node is at start of array, with start vertex being at index 0 once whole route has been added to array.
            
            route = route_new;

        }

        line_current = line_previous[route[1]];

        // Loop through array, printing array and line colour taken.
        for(int i = 0; i < route.length; i ++ ) {
            System.out.println(get_vertex_name_from_id(route[i]) + " on " + line_current + " line");
            if(i + 1 < route.length && line_previous[route[i + 1]] != line_current) {
                line_current = line_previous[route[i + 1]];
                System.out.println("** Change line to " + line_current + " line **");
                System.out.println(get_vertex_name_from_id(route[i]) + " on " + line_current + " line"); 
                // shortest_distance_to_start[route[route.length - 1]] += 2;   
            }
        }
        System.out.println("Total journey time (mins) = " + shortest_distance_to_start[route[route.length - 1]]);

        // Print out any line changes by storing current line then checking if it changes at next station.
        // Print out total line changes, store in a counter variable.
        // Print out total journey time.

    }

}   
