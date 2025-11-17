import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEventDemo extends JFrame {
    private JLabel displayLabel;

    public MouseEventDemo() {
        setTitle("Mouse Event");// set the title to Mouse Event
        setSize(500, 400); // set the size to 500x400
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayLabel = new JLabel("An image will appear here when you move the mouse within the label.", SwingConstants.CENTER);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        displayLabel.setOpaque(true);
        displayLabel.setBackground(Color.lightGray);// make the background light gray
        displayLabel.setPreferredSize(new Dimension(300, 200));

        displayLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {// listetenr for when mouse enters
                displayLabel.setText("");
                displayLabel.setIcon(new ImageIcon("smiley.jpg"));// this si my preffered image of a smile face
                displayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));// create a blackborder
            }

            @Override
            public void mouseExited(MouseEvent e) {//listen for mouse exit
                displayLabel.setIcon(null);
                displayLabel.setText("An image will appear here when you move the mouse within the label.");// reappear original text
                displayLabel.setBorder(null);// make border null - dissapear
            }
        });

        add(displayLabel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {// main method to run application
        SwingUtilities.invokeLater(() -> {
            MouseEventDemo demo = new MouseEventDemo();
            demo.setVisible(true);
        });
    }
}
