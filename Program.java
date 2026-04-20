import java.io.File;

public class Program {

    public Program() {
        
        Data_Handler handler = new Data_Handler(new File("Metrolink_times_linecolour.csv"));

    }

    public static void main(String[] args) {
        Program new_program = new Program();
    }

}