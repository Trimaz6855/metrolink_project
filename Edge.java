public class Edge {
    
    private int vertex_start;
    private int vertex_end;
    private double weight;
    private String line;

    public Edge(int start, int end, double weight, String line) {
        this.vertex_start = start;
        this.vertex_end = end;
        this.weight = weight;
        this.line = line;
    }

    public int get_start_vertex() {
        return vertex_start;
    }

    public int get_end_vertex() {
        return vertex_end;
    }

    public double get_weight() {
        return weight;
    }

    public String get_line() {
        return line;
    }

    public void set_weight(int weight) {
        this.weight = weight;
    }

    public void set_line(String line) {
        this.line = line;
    }

}
