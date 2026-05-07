/**
  * A class to represent a vertex on the graph. 
  **/
public class Vertex {
    
    private String vertex_name;
    private int vertex_id;

    /**
      * Constructor method for a new vertex.
      * @param name String - the name of the vertex.
      * @param id int - the unique id of the vertex.
      **/
    public Vertex(String name, int id) {
        this.vertex_name = name;
        this.vertex_id = id;
    }

    /**
      * Getter method for the unique id of the vertex.
      * @return int - the unique id of the vertex.
      **/
    public int get_vertex_id() {
        return vertex_id;
    }

    /**
      * Getter method for the name of the vertex.
      * @return String - the name of the vertex.
      **/
    public String get_vertex_name() {
        return vertex_name;
    }

}
