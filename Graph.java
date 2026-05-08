/**
  * A class to represent a graph data structure.
  **/
public class Graph {
    
    private int next_vertex_id = 0;
    private int next_colour_id = 0;
    private Edge[][][] edges;
    private Vertex[] vertices;
    private Colour[] lines;

    /**
      * Constructor method for the graph data structure.
      **/
    public Graph() {
        ;
    }

    /**
      * Adds a new vertex to the graph.
      * @param name String - the name of the vertex to be added to the graph.
      **/
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
    
    /**
      * Adds a new forwards and backwards edge to the graph.
      * @param station_start int - the unique id of the vertex the edge starts at.
      * @param station_end int - the unique id of the vertex the edge ends at.
      * @param line_colour String - the name of the tram line colour the edge is on.
      * @param line_time double - the weight of the edge.
      **/
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
        int id_line_colour = get_colour(line_colour);

        if(id_start_vertex != -1 && id_end_vertex != -1) {
            if(edges_new[id_start_vertex][id_end_vertex][id_line_colour] == null) {
                edges_new[id_start_vertex][id_end_vertex][id_line_colour] = new Edge(id_start_vertex, id_end_vertex, line_time, line_colour);
            }
            if(edges_new[id_end_vertex][id_start_vertex][id_line_colour] == null) {
                edges_new[id_end_vertex][id_start_vertex][id_line_colour] = new Edge(id_end_vertex, id_start_vertex, line_time, line_colour);
            }
        }

        this.edges = edges_new;

    }

    /**
      * Checks to see if a vertex of the input name exists in the graph.
      * @param name String - the name of the vertex.
      * @return boolean - returns true if the vertex exists in the graph.
      **/
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

    /**
      * Getter method for the unique id of a vertex using the name provided.
      * @param name String - the name of the vertex.
      * @return int - the unique id of the vertex with the name provided, -1 is returned if the vertex doesn't exist in the graph.
      **/
    public int get_vertex_id_from_name(String name) {
        for(int i = 0; i < vertices.length; i ++ ) {
            if(vertices[i].get_vertex_name().equals(name)) {
                return vertices[i].get_vertex_id();
            }
        }
        return -1;
    }

    /**
      * Getter method for the name of a vertex using the unique id provided.
      * @param id int - the unique id of the vertex.
      * @return String - the name of the vertex.
      **/
    public String get_vertex_name_from_id(int id) {
        try {
            return vertices[id].get_vertex_name();
        } catch (Exception e) {
            System.err.println("Unable to retrieve a vertex at id: " + id);
            return e.getMessage();
        }
    }

    /**
      * Getter method for the actual vertex object.
      * @param name String - the name of the vertex.
      * @return Vertex - the vertex object with the provided name.
      **/
    public Vertex get_vertex(String name) {
        return vertices[get_vertex_id_from_name(name)];
    }
    
    /**
      * Alternative getter method for the vertex object.
      * @param id int - the unique id of the vertex.
      * @return Vertex - the vertex object corresponding to the unique id.
      **/
    public Vertex get_vertex(int id) {
        return vertices[id];
    }

    /**
      * Getter method for the array of edges starting at the start vertex and ending at the end vertex.
      * @param start String - the name of the start vertex.
      * @param end String - the name of the end vertex.
      * @return Edge[] - 1D array of edges between the two vertices.
      **/
    public Edge[] get_edge_from_stations(String start, String end) {
        
        int id_start = get_vertex_id_from_name(start);
        int id_end = get_vertex_id_from_name(end);

        return edges[id_start][id_end];
        
    }

    /**
      * Getter method for the total number of vertices/stations in the graph.
      * @return int - the number of vertices/graphs.
      **/
    public int get_station_count() {
        return vertices.length;
    }

    /**
      * Prints out the vertices in order. 
      **/
    public void print_vertices() {

        if(vertices != null) {
            for(int i = 0; i < vertices.length; i ++ ) {
                System.out.println(vertices[i].get_vertex_name());
            }
        }
        
        System.out.println(vertices.length + " stations");

    }

    /**
      * Prints out the edges in order.
      **/
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

    /**
      * Prints all the edges between two specific vertices. 
      * @param start String - the vertex the edges start at.
      * @param end String - the vertex the edges end at.
      **/
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

    /**
      * Getter method for the name of a colour from its unique id.
      * @param id int - the unique id of the colour.
      * @return String - the name of the colour.
      **/
    public String get_colour(int id) {
        for(int i = 0; i < lines.length; i ++ ) {
            if(lines[i].get_colour_id() == id) {
                return lines[i].get_colour_name();
            }
        }
        return null;
    }

    /**
      * Getter method for the unique id of a colour from its name.
      * @param name String - the name of the colour.
      * @return int - the unique id of the colour.
      **/
    public int get_colour(String name) {
        for(int i = 0; i < lines.length; i ++ ) {
            if(lines[i].get_colour_name().equals(name)) {
                return lines[i].get_colour_id();
            }
        }
        return -1;
    }

    /**
      * Checks if the colour with that name exists.
      * @param name String - the name of the colour.
      * @return boolean - returns true if the colour exists, false if not.
      **/
    public boolean find_colour(String name) {
        for(int i = 0; i < lines.length; i ++ ) {
            if(lines[i].get_colour_name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
      * Performs a slightly modified version of Dijkstra's algorithm on the multigraph. 
      * @param start String - the name of the start vertex.
      * @param end String - the name of the end vertex.
      **/
    public void dijkstras(String start, String end, String constraint) {

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
        int[][][] vertex_previous = new int[vertices.length][lines.length][2];
        // Initialises all the shortest distances to infinity, the previous vertex ids to be -1 and the vertex visited flag to be false (becasuse they havent been visited yet).
        for(int i = 0; i < vertices.length; i ++ ) { 
            for(int j = 0; j < lines.length; j ++ ) {
                if(i == id_start) {
                    shortest_distance_to_start[i][j] = 0;
                }
                else {
                    shortest_distance_to_start[i][j] = Double.POSITIVE_INFINITY;
                    vertices_visted[i][j] = false;
                }
                for(int k = 0; k < vertex_previous[i][j].length; k ++ ) {
                    vertex_previous[i][j][k] = -1;
                }
            }
        }

        // Stores the current start vertex for edges.
        int id_current = id_start;
        // Stores the id of the line colour used to travel to the current start vertex.
        int id_colour_current = -1;
        // Stores the id of the vertex with the next shortest distance to it.
        int id_next_vertex = -1;
        
        // If the least changes constraint is selected then the array storing the number of changes for each route is initialised as infinity.
        // Would probably be better as an int, however logically using Double.POSITIVE_INFINITY works better.
        double[][] least_changes_to_start = new double[vertices.length][lines.length];
        if(constraint.equals("Least")) {
            for(int i = 0; i < vertices.length; i++ ) {
                for(int j = 0; j < lines.length; j ++ ) {
                    if(i == id_start) {
                        least_changes_to_start[i][j] = 0;
                    }
                    else{
                        least_changes_to_start[i][j] = Double.POSITIVE_INFINITY;
                    }
                }
            }
        }

        // Dijkstras pathfinding section.
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
                            
                            if(constraint.equals("Shortest")) {
                                if(line_changed == true) {
                                    weight_new += 2;
                                }
                                if(shortest_distance_to_start[i][id_colour] == Double.POSITIVE_INFINITY && weight_new < Double.POSITIVE_INFINITY) {
                                    shortest_distance_to_start[i][id_colour] = weight_new;
                                    vertex_previous[i][id_colour][0] = id_current;
                                    vertex_previous[i][id_colour][1] = id_colour_current;
                                }
                                else {
                                    if(weight_new <= shortest_distance_to_start[i][id_colour]) {
                                        shortest_distance_to_start[i][id_colour] = weight_new;
                                        vertex_previous[i][id_colour][0] = id_current;
                                        vertex_previous[i][id_colour][1] = id_colour_current;
                                    }
                                }
                            }
                            else if(constraint.equals("Least")) {
                                double weight_relative = least_changes_to_start[id_current][id_colour_current];
                                if(line_changed == true) {
                                    weight_relative += 1; 
                                    weight_new += 2;
                                }
                                if(least_changes_to_start[i][id_colour] == Double.POSITIVE_INFINITY && shortest_distance_to_start[i][id_colour] == Double.POSITIVE_INFINITY) {
                                    if(weight_relative < Double.POSITIVE_INFINITY && weight_new < Double.POSITIVE_INFINITY) {
                                        least_changes_to_start[i][id_colour] = weight_relative;
                                        shortest_distance_to_start[i][id_colour] = weight_new;
                                        vertex_previous[i][id_colour][0] = id_current;
                                        vertex_previous[i][id_colour][1] = id_colour_current;
                                    }
                                }
                                else { 
                                    if(weight_relative <= least_changes_to_start[i][id_colour] && weight_new <= shortest_distance_to_start[i][id_colour]) {
                                        least_changes_to_start[i][id_colour] = weight_relative;
                                        shortest_distance_to_start[i][id_colour] = weight_new;
                                        vertex_previous[i][id_colour][0] = id_current;
                                        vertex_previous[i][id_colour][1] = id_colour_current;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            vertices_visted[id_current][id_colour_current] = true;

            if(constraint.equals("Shortest")) {
                // Finds and stores the next vertex to visit, based off the next shortest unvisited vertex.
                double current_shortest_distance = Double.POSITIVE_INFINITY;
                int id_colour_next = -1;
                for(int i = 0; i < shortest_distance_to_start.length; i ++ ) {
                    for(int j = 0; j < shortest_distance_to_start[i].length; j ++ ) {
                        if(shortest_distance_to_start[i][j] < current_shortest_distance && vertices_visted[i][j] == false) {
                            if(shortest_distance_to_start[i][j] > 0) {
                                current_shortest_distance = shortest_distance_to_start[i][j];
                                id_next_vertex = i;
                                id_colour_next = j;
                            }
                        }
                    }
                }
                // Exits the loop if there are no more possible vertices to visit
                if(current_shortest_distance == Double.POSITIVE_INFINITY) {
                    break;
                }
                else {
                    id_current = id_next_vertex;
                    id_colour_current = id_colour_next;
                }
            }
            else if(constraint.equals("Least")) {
                // Finds and stores the next vertex to visit, based off the fewest number of changes up to that vertex.
                double current_shortest_changes = Double.POSITIVE_INFINITY;
                int id_colour_next = -1;
                for(int i = 0; i < least_changes_to_start.length; i ++ ) {
                    for(int j = 0; j < least_changes_to_start[i].length; j ++ ) {
                        if(least_changes_to_start[i][j] < current_shortest_changes && vertices_visted[i][j] == false) {
                            current_shortest_changes = least_changes_to_start[i][j];
                            id_next_vertex = i;
                            id_colour_next = j;
                        }
                    }
                }
                // Exits the current loop if there are no more possible vertices to visit.
                if(current_shortest_changes == Double.POSITIVE_INFINITY) {
                    break;
                }
                else {
                    id_current = id_next_vertex;
                    id_colour_current = id_colour_next;
                }
            }

        }

        int id_shortest_line = -1;
        double distance_final = Double.POSITIVE_INFINITY;
        double changes_final = Double.POSITIVE_INFINITY;
        if(constraint.equals("Shortest")) {
            // Finds and stores the shortest distance to the end vertex, along with the line used to reach it.
            for(int i = 0; i < shortest_distance_to_start[id_end].length; i ++ ) {
                if(shortest_distance_to_start[id_end][i] < distance_final && shortest_distance_to_start[id_end][i] > 0) {
                    distance_final = shortest_distance_to_start[id_end][i];
                    id_shortest_line = i;
                }
            }
        }
        else if(constraint.equals("Least")) {
            // Finds and stores the route with the least number of changes along with the line used to reach it.
            for(int i = 0; i < least_changes_to_start[id_end].length; i ++ ) {
                System.out.println("Comparing " + least_changes_to_start[id_end][i] + " < " + changes_final);
                if(least_changes_to_start[id_end][i] < changes_final) {
                    changes_final = least_changes_to_start[id_end][i];
                    distance_final = shortest_distance_to_start[id_end][i];
                    id_shortest_line = i;
                }
            }
        }
    
        int[][] route = new int[1][2];
        route[0][0] = id_end;
        System.out.println("id_end: " + id_end);
        System.out.println("id_shortest_line: " + id_shortest_line);
        route[0][1] = vertex_previous[id_end][id_shortest_line][1];
        int id_line_next = id_shortest_line;

        // Backtracks through the previous vertex array to form the shortest route from the end vertex to the start vertex.
        while(id_end != id_start) {
            
            int[][] route_new = new int[route.length + 1][2];
            for(int i = route.length; i > 0; i -- ) {
                route_new[i][0] = route[i - 1][0];
                route_new[i][1] = route[i - 1][1];
            }

            if(id_end != -1 && vertex_previous[id_end][id_line_next][0] != -1 && vertex_previous[id_end][id_line_next][1] != -1) {
                id_next_vertex = vertex_previous[id_end][id_line_next][0];
                id_line_next = vertex_previous[id_end][id_line_next][1];
                id_end = id_next_vertex;
                route_new[0][0] = id_end;
                route_new[0][1] = id_line_next;
            }

            route = route_new;

        }

        if(constraint.equals("Shortest")) {
            System.out.println("** Shortest Route **");
        }
        else if(constraint.equals("Least")) {
            System.out.println("** Route with Fewest Changes **");
        }

        // Prints out the route and any changes, counting how many line changes happen.
        int line_current = route[0][1];
        int changes = 0;
        for(int i = 0; i < route.length; i ++ ) {
            if(route[i][0] == id_start) {
                line_current = route[i + 1][1];
            }
            System.out.println(get_vertex_name_from_id(route[i][0]) + " on " + get_colour(line_current) + " line");
            if(i + 1 < route.length - 1 && route[i + 1][1] != -1 && route[i + 1][1] != line_current) {
                changes ++;
                line_current = route[i + 1][1];
                System.out.println("** Change line to " + get_colour(line_current) + " line **");
                System.out.println(get_vertex_name_from_id(route[i][0]) + " on " + get_colour(line_current) + " line");
            }
        }

        System.out.println("Overall Journey Time (mins): " + distance_final);
        System.out.println("Number of Changes: " + changes);

    }
}   