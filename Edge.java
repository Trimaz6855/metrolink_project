/**
  * A class for representing one of the edges on the graph.
  **/
public class Edge {
    
    private int vertex_start;
    private int vertex_end;
    private double weight;
    private String line;

    /**
      * Constructor method for a new edge.
      * @param start int - the id of the vertex the edge starts at.
      * @param end int - the id of the vertex the edge ends at.
      * @param weight double - the weight of the edge.
      * @param line String - the colour tram line the edge runs on.
      **/
    public Edge(int start, int end, double weight, String line) {
        this.vertex_start = start;
        this.vertex_end = end;
        this.weight = weight;
        this.line = line;
    }

    /**
      * Getter method for the id of the vertex the edge starts at. 
      * @return int - the unique id of the vertex the edge starts at.
      */
    public int get_start_vertex() {
        return vertex_start;
    }

    /**
      * Getter method for the id of the vertex the edge ends at.
      * @return int - the unique id of the vertex the edge ends on.
      **/
    public int get_end_vertex() {
        return vertex_end;
    }

    /**
      * Getter method for the weight of the edge.
      * @return double - the weight of the edge.
      **/
    public double get_weight() {
        return weight;
    }

    /**
      * Gets the line colour of the edge. 
      * @return String - the line colour of the edge.
      **/
    public String get_line() {
        return line;
    }

    /**
      * Sets the weight of the edge to the new input weight.
      * @param weight int - the new weight for the edge.
      **/
    public void set_weight(int weight) {
        this.weight = weight;
    }

    /**
      * Sets the line colour of the edge to the new input colour.
      * @param line String - the new line colour for the edge.
      **/
    public void set_line(String line) {
        this.line = line;
    }

}
