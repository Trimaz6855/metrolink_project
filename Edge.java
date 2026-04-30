public class Edge {
    
    private int vertex_start;
    private int vertex_end;
    private double[] weights = new double[1];
    private String[] lines = new String[1];

    public Edge(int start, int end, double weight, String line) {
        this.vertex_start = start;
        this.vertex_end = end;
        this.weights[0] = weight;
        this.lines[0] = line;
    }

    public int get_start_vertex() {
        return vertex_start;
    }

    public int get_end_vertex() {
        return vertex_end;
    }

    public double[] get_weights() {
        return weights;
    }

    public String[] get_lines() {
        return lines;
    }

    public void add_line(String line, double weight) {
        
        boolean line_exists = false;
        for(int i = 0; i < lines.length; i ++ ) {
            if(lines[i] == line) {
                if(weight < weights[i]) {
                    weights[i] = weight;
                }
                line_exists = true;
            }
        }

        if(line_exists == false) {
            double[] new_weights = new double[weights.length + 1];
            
            for(int i = 0; i < weights.length; i ++ ) {
                new_weights[i] = weights[i];
            }
            new_weights[new_weights.length - 1] = weight; 
            
            String[] new_lines = new String[lines.length + 1];
    
            for(int i = 0; i < lines.length; i ++ ) {
                new_lines[i] = lines[i];
            }
            new_lines[new_lines.length - 1] = line; 
    
            weights = new_weights;
            lines = new_lines;
        }

    }

}
