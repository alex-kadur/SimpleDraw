import javax.swing.JFrame;

public class Frame extends JFrame{

    // Constructor
    Frame () {

        Panel panel = new Panel();
        this.add(panel);
        this.setTitle("SimpleDraw");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        // .pack is a method that sizes the frame so that all its contents are at or above their preferred sizes
        this.pack();
        this.setVisible(true);
        // .setLocationRelativeTo(null) is a method that sets the location of the window to the center of the screen
        this.setLocationRelativeTo(null);            
    }
}
