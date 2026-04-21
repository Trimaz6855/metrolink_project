public class Vertex {
    
    private String vertex_name;
    private int vertex_id;

    public Vertex(String name, int id) {
        this.vertex_name = name;
        this.vertex_id = id;
    }

    public int get_vertex_id() {
        return vertex_id;
    }

    public String get_vertex_name() {
        return vertex_name;
    }

}
