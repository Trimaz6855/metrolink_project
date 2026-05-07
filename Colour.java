/**
  * A basic colour class containing the name and id to be used in Dijkstra's.
  **/
public class Colour {
    
    private String colour_name;
    private int colour_id;

    /**
      * Constructor for a new colour.
      * @param name String - the name of the colour line.
      * @param id int - the id of the colour, unique.
      **/
    public Colour(String name, int id) {
        this.colour_name = name;
        this.colour_id = id;
    }

    /**
      * Getter method for the colours name.
      * @return String - the name of the colour.
      **/
    public String get_colour_name() {
        return colour_name;
    }

    /**
      * Getter method for the colours unique id.
      * @return int - the id of the colour.
      **/
    public int get_colour_id() {
        return colour_id;
    }

}
