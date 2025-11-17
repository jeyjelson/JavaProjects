import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HouseholdCooker extends JFrame {
    private JLabel cookerLabel; // jlabel for cooker brand
    private JLabel[] cookerZones; //array of jlabel for the 5 cookers
    private JSlider[] sliders; // array of sliders for the cooker zones
    private JComboBox<String> dropdownBox; // dropdown box to change font size

    public HouseholdCooker() {
        setTitle("Household Cooker");
        setSize(600, 400);// window size to 600x400
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
//call method to initlaize label,zones sliders and dropdown box
        initCookerLabel();
        initCookerZones();
        initDropdownBox();
    }
    private void initCookerLabel() {
        cookerLabel = new JLabel("Cooker Brand: Belling Cookcenter", SwingConstants.CENTER);
        cookerLabel.setFont(new Font("Arial", Font.BOLD, 18));//set font and size for jlabel
        add(cookerLabel, BorderLayout.NORTH);
    }

    private void initCookerZones() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 5, 10, 10));// create  1 row with 5 columns with horizontal gap of 10 and vertical gap of 10

        cookerZones = new JLabel[5];
        sliders = new JSlider[5];

        for (int i = 0; i < 5; i++) {// loop to create 5 cooker zones
            JPanel zonePanel = new JPanel(new BorderLayout());
            cookerZones[i] = new JLabel();
            cookerZones[i].setBorder(new LineBorder(Color.BLACK, 2)); 
            cookerZones[i].setOpaque(true);
            cookerZones[i].setBackground(Color.WHITE);// set background colour to white

            sliders[i] = new JSlider(0, 10);
            sliders[i].setMajorTickSpacing(1);// create big ticks spacing for slider
            sliders[i].setPaintTicks(true);// create small ticks along slider 
            sliders[i].addChangeListener(new ZoneSliderListener(i));

            zonePanel.add(cookerZones[i], BorderLayout.CENTER);
            zonePanel.add(sliders[i], BorderLayout.SOUTH);
            centerPanel.add(zonePanel);
        }
        add(centerPanel, BorderLayout.CENTER);
    }
    private void initDropdownBox() {
        JPanel bottomPanel = new JPanel();
        dropdownBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});// create jcombobox with font size option
        dropdownBox.addActionListener(new FontChangeListener()); 

        bottomPanel.add(new JLabel("Font Size:"));
        bottomPanel.add(dropdownBox);
        add(bottomPanel, BorderLayout.SOUTH);// add it to bottom of frame
    }

    private class ZoneSliderListener implements ChangeListener {// inner class to handle slider changes
        private final int zoneIndex;

        public ZoneSliderListener(int index) {
            this.zoneIndex = index;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            int value = sliders[zoneIndex].getValue();
            int redIntensity = (int) (value * 25.5);// calculate red intenisty based on slider
            cookerZones[zoneIndex].setBackground(new Color(redIntensity, 0, 0));// change red intensity 
        }
    }

    private class FontChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = (String) dropdownBox.getSelectedItem();
            int fontSize;
            switch (selected) {
                case "Small": fontSize = 14; break;//for small
                case "Medium": fontSize = 18; break;//for medium
                case "Large": fontSize = 24; break;//for large
                default: fontSize = 18; // default size
            }
            cookerLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        }
    }
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HouseholdCooker cooker = new HouseholdCooker();
            cooker.setVisible(true);
        });
    }
}
