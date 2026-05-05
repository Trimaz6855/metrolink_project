public class Colour {
    
    private String colour_name;
    private int colour_id;

    public Colour(String name, int id) {
        this.colour_name = name;
        this.colour_id = id;
    }

    public String get_colour_name() {
        return colour_name;
    }

    public int get_colour_id() {
        return colour_id;
    }

}
