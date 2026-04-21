public class Graph {
    
    private int next_vertex_id = 0;
    private Edge[][] edges;
    private Vertex[] vertices;

    public Graph() {
        ;
    }

    public void add_vertex(String name) {

        // System.out.println("Adding " + name + " to vertices array.");
        // print_vertices();
        
        next_vertex_id ++ ;
        Vertex[] vertices_new = new Vertex[next_vertex_id];
        if(vertices != null && vertices.length != 0) {
            for(int i = 0; i < vertices_new.length - 1; i ++ ) {
                vertices_new[i] = vertices[i];
            }
        }
        vertices_new[next_vertex_id - 1] = new Vertex(name, next_vertex_id - 1);
        // System.out.println(vertices_new[next_vertex_id - 1].get_vertex_name() + " id: " + vertices_new[next_vertex_id - 1].get_vertex_id());

        // print_vertices();
        this.vertices = vertices_new;
    }
    
    public void add_edge(String station_start, String station_end, String line_colour, double line_time ) {
        
        if(find_vertex(station_start) == false) {
            
            // System.out.println("Adding " + station_start + find_vertex(station_start));
            
            add_vertex(station_start);
            
            // System.out.println(find_vertex(station_start));

            // print_vertices();
        }

        if(find_vertex(station_end) == false) {
            // System.out.println("Adding " + station_end + find_vertex(station_end));
            
            add_vertex(station_end);
            
            // System.out.println(find_vertex(station_end));

            // print_vertices();
        }
        
        Edge[][] edges_new = new Edge[vertices.length][vertices.length];
        this.edges = edges_new;
        // print_vertices();
    }

    public boolean find_vertex(String name) {
        
        if(vertices != null) {
            // System.out.println(vertices.length);
            for(int i = 0; i < vertices.length; i ++ ) {
                if(vertices[i].get_vertex_name() == name) {
                    return true;
                }
            }
        }
        return false;
    }

    public void print_vertices() {

        // System.out.println("Length of vertices array: " + this.vertices.length);

        if(vertices != null) {
            for(int i = 0; i < vertices.length; i ++ ) {
                System.out.println(vertices[i].get_vertex_name());
            }
        }    
    }

}
