public class Graph {
    
    private int next_vertex_id = 0;
    private Edge[][] edges;
    private Vertex[] vertices;

    public Graph() {
        ;
    }

    public void add_vertex(String name) {

        Vertex[] vertices_new = new Vertex[next_vertex_id];
        vertices_new[next_vertex_id] = new Vertex(name, next_vertex_id);

        Edge[][] edges_new = new Edge[next_vertex_id][next_vertex_id];


        this.vertices = vertices_new;
        this.edges = edges_new;
    }

}
