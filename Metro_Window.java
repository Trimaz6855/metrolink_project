import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

public class Metro_Window {

    private JFrame frame_main;
    private JPanel panel_main;
    private JTextArea text_start = new JTextArea();
    private JTextArea text_end = new JTextArea();
    private JCheckBox check_least_changes = new JCheckBox("Least Changes Route");
    private JCheckBox check_shortest = new JCheckBox("Shortest Route");


    int height = 800;
    int width = 600;

    public Metro_Window() {
        setup_window();
    }

    public void setup_window() {
        frame_main = new JFrame("Metrolink Route Planner");
        frame_main.setSize(width, height);
        frame_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_main.setLocationRelativeTo(null);
        frame_main.setResizable(false);
        frame_main.setMaximumSize(new Dimension(width, height));
        panel_main = new JPanel();

        component_setup();

        frame_main.add(panel_main);
        frame_main.setVisible(true);
    }

    public void component_setup() {
        
        JLabel label_title = new JLabel("Metrolink Route Planner");
        label_title.setFont(new Font("Serif", 0, 30));

        JPanel panel_input = new JPanel();
        BoxLayout layout_input = new BoxLayout(panel_input, BoxLayout.Y_AXIS);
        panel_input.setLayout(layout_input);

        Font font_main = new Font("Serif", 0, 20);

        JLabel label_start = new JLabel("Enter Start Station");
        label_start.setFont(font_main);
        panel_input.add(label_start);
        text_start.setFont(font_main);
        text_start.setToolTipText("Enter Start Station");
        // text_start.setMaximumSize(new Dimension(width, height));
        panel_input.add(text_start);

        JLabel label_end = new JLabel("Enter End Station");
        label_end.setFont(font_main);
        panel_input.add(label_end);
        text_end.setFont(font_main);
        text_end.setToolTipText("Enter End Station");
        // text_end.setMaximumSize(new Dimension(width, height));
        panel_input.add(text_end);

        JPanel panel_constraints = new JPanel();
        GridLayout layout_constraints = new GridLayout(2, 2);
        panel_constraints.setLayout(layout_constraints);

        check_shortest.setToolTipText("Shortest Route");
        panel_constraints.add(check_shortest);
        check_least_changes.setToolTipText("Least Changes Route");
        panel_constraints.add(check_least_changes);
        panel_input.add(panel_constraints);

        JButton button_find_route = new JButton("Find Route");
        button_find_route.setToolTipText("Find Route");
        button_find_route.setFont(font_main);
        panel_input.add(button_find_route);

        BoxLayout layout_main = new BoxLayout(panel_main, BoxLayout.Y_AXIS);
        panel_main.setLayout(layout_main);
        panel_main.add(label_title);
        panel_main.add(panel_input);

    }

    public String[] get_station_inputs() {
        String station_start =  text_start.getText();
        String station_end = text_end.getText();
        String[] inputs = new String[2];
        inputs[0] = station_start;
        inputs[1] = station_end;
        return inputs;
    }

    public static void main(String[] args) {
        Metro_Window window = new Metro_Window();
    }

}
