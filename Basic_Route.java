public class Basic_Route {

    private Edge[] to_visit;
    private Edge[] traversed;
    private int head = 0;
    private int tail = 0;
    private Vertex[] visited;

    public Basic_Route(Graph input, String start, String end) {
        
        Vertex current_vertex = input.get_vertex(start);
        Edge[] new_to_visit;
        Vertex[] new_visited;

        for(int i = 0; i < input.get_station_count(); i ++ ) {
            if(input.find_edge(start, input.get_vertex_name_from_id(i)) == true) {
                if(to_visit == null) {
                    new_to_visit = new Edge[1];
                }
                else {
                    new_to_visit = new Edge[to_visit.length + 1];
                    for(int j = 0; j < new_to_visit.length - 1; j ++ ) {
                        new_to_visit[i] = to_visit[i];
                    }
                }
            }
            to_visit = new_to_visit;
        }
        
        if(visited == null) {
            new_visited = new Vertex[1];
        }
        else {
            new_visited = new Vertex[visited.length + 1];
            for(int i = 0; i < new_visited.length - 1; i ++ ) {
                new_visited[i] = visited[i];
            }
        }
        new_visited[visited.length - 1] = current_vertex;
        visited = new_visited;

        for(int i = 0; i < to_visit.length - 1; i ++ ) {
            System.out.println(to_visit[i].get_start_vertex() + " to " + to_visit[i].get_end_vertex() + " in " + to_visit[i].get_weight());
        }

    }

}
