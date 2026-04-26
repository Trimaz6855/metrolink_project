public class Graph {
    
    private int next_vertex_id = 0;
    private Edge[][] edges;
    private Vertex[] vertices;

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
        
        if(find_vertex(station_start) == false) {

            add_vertex(station_start);
            
        }

        if(find_vertex(station_end) == false) {
            
            add_vertex(station_end);
        }
        
        Edge[][] edges_new = new Edge[vertices.length][vertices.length];
        if(edges != null && edges.length != 0) {
            for(int i = 0; i < edges.length; i ++ ) {
                for(int j = 0; j < edges.length; j ++ ) {
                    edges_new[i][j] = edges[i][j];
                    // if(i == j) {
                    //     edges_new[i][j] = new Edge(-1, -1, 0, "Blank");
                    // }
                }
            }
        }

        int id_start_vertex = get_vertex_id_from_name(station_start);
        int id_end_vertex = get_vertex_id_from_name(station_end);

        switch (id_start_vertex) {
            case -1:
                break;
            default:
                switch (id_end_vertex) {
                    case -1:
                        break;
                    default:
                        if(edges_new[id_start_vertex][id_end_vertex] == null || edges_new[id_start_vertex][id_end_vertex].get_weight() > line_time) {
                            edges_new[id_start_vertex][id_end_vertex] = new Edge(id_start_vertex, id_end_vertex, line_time, line_colour);
                        }
                        break;
                }
                break;
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

        if(edges[id_start][id_end] != null && edges[id_start][id_end].get_weight() > 0.0) {
            return true;
        }
        else {
            return false;
        }

    }

    public Edge get_edge_from_stations(String start, String end) {
        
        int id_start = get_vertex_id_from_name(start);
        int id_end = get_vertex_id_from_name(end);

        return edges[id_start][id_end];
        
    }

    public String get_line_from_stations(int start, int end) {

        return edges[start][end].get_line();

    }

    public int get_station_count() {
        return vertices.length;
    }
    
    public void dijkstras(String start, String end) {

        Boolean[] vertices_visited = new Boolean[vertices.length];
        for(int i = 0; i < vertices.length; i ++ ) {
            vertices_visited[i] = false;
        }

        double[] shortest_distance_to_start = new double[vertices.length];
        for(int i = 0; i < vertices.length; i ++ ) {
            shortest_distance_to_start[i] = Double.POSITIVE_INFINITY;
        }

        int[] vertices_previous = new int[vertices.length];
        for(int i = 0; i < vertices.length; i ++ ) {
            vertices_previous[i] = -1;
        }

        Boolean visited_all = false;

        int id_start = get_vertex_id_from_name(start);
        int id_end = get_vertex_id_from_name(end);
        int id_current = id_start;

        vertices_visited[id_start] = true;
        shortest_distance_to_start[id_start] = 0; 
        
        while(visited_all == false) {

            for(int i = 0; i < vertices.length; i ++ ) {
                if(edges[id_current][i] != null) {
                    Edge edge_current = edges[id_current][i];

                    if(vertices_visited[i] == false && shortest_distance_to_start[i] == Double.POSITIVE_INFINITY) {
                        vertices_previous[i] = id_current;
                        shortest_distance_to_start[i] = edge_current.get_weight();
                    }
                    else if(vertices_visited[i] == false && vertices_previous[i] != -1){
                        int id_previous = vertices_previous[i];
                        double weight_new = shortest_distance_to_start[id_previous] + edge_current.get_weight();
                        if(weight_new < shortest_distance_to_start[i]) {
                            shortest_distance_to_start[i] = weight_new;
                            vertices_previous[i] = id_previous;
                        }
                    }
                    
                }

            }
            vertices_visited[id_current] = true;
            
            for(int i = 0; i < vertices_visited.length; i ++ ) {
                if(vertices_visited[i] == true) {
                    visited_all = true;
                }
                else if(vertices_visited[i] == false) {
                    visited_all = false;
                }
            }

            if(id_current == vertices.length - 1) {
                id_current = 0;
            }
            else {
                id_current ++;
            }

        }

        int route[] = new int[1];
        route[0] = id_end;

        for(int i = 0; i < vertices_previous.length; i ++ ) {
            System.out.println("Previous vertex for vertex " + get_vertex_name_from_id(i) + " is " + vertices_previous[i] + ", ");
        }
        System.out.println();

        while(id_end != id_start){

            int[] new_route = new int[route.length + 1];

            for(int i = route.length; i >= 1; i -- ) {
                new_route[i] = route[i-1];
            }

            if(id_end == -1) {
                break;
            }
            else {
                System.out.println(vertices_previous[id_end]);
                id_end = vertices_previous[id_end];
            }

            new_route[0] = id_end;

            route = new_route;
        }
        
        int counter_stations = 2;

        for(int i = 0; i < route.length; i ++ ) {
            System.out.print(route[i] + ", ");
        }
        System.out.println();

        while(counter_stations < route.length ) {

            System.out.println("Getting Line at position edge[" + counter_stations + "-1][" + counter_stations + "]" );
            System.out.println("Edge between stations: " +  vertices[route[counter_stations - 1]].get_vertex_name() + " and " + vertices[route[counter_stations]].get_vertex_name());

            System.out.println(get_vertex_name_from_id(route[counter_stations]) + " via " + get_line_from_stations(route[counter_stations-1], route[counter_stations]) + " line ");

            counter_stations ++;
        }

        System.out.println("Total Number of Stations: " + counter_stations);
        System.out.println("Total Journey Time: " + shortest_distance_to_start[route[route.length-1]]);

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
                    if(edges[i][j] != null) {
                        System.out.print(edges[i][j].get_weight() + " ");
                    }
                    else {
                        System.out.print("- ");
                    }
                }
                System.out.println("]"); 
            }
        }
        System.out.println(edges.length + "x" + edges.length + " matrix");

    }

}   
