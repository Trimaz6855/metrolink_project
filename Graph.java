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
                    if(i == j) {
                        edges_new[i][j] = new Edge(-1, -1, 0, "Blank");
                    }
                }
            }
            edges_new[edges.length][edges.length] = new Edge(-1, -1, 0, "Blank");
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

    public void print_vertices() {

        if(vertices != null) {
            for(int i = 0; i < vertices.length; i ++ ) {
                System.out.println(vertices[i].get_vertex_name());
            }
        }    

    }

    public void print_edges() {

        if(edges != null) {
            for(int i = 0; i < edges.length; i ++ ) {
                for(int j = 0; j < edges.length; j ++ ) {
                    if(edges[i][j] != null) {
                        System.out.print(edges[i][j].get_weight() + " ");
                    }
                    else {
                        System.out.print("- ");
                    }
                }
                System.out.println(); 
            }
        }

    }

    public int get_vertex_id_from_name(String name) {
        for(int i = 0; i < vertices.length; i ++ ) {
            if(vertices[i].get_vertex_name().equals(name)) {
                return vertices[i].get_vertex_id();
            }
        }
        return -1;
    }

}
