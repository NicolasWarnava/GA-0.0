import javax.swing.JFrame;

public class Game {
/*    public static void main(String[] args) throws Exception {
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame  = new JFrame("GA00");
        //frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GA00 ga00 = new GA00();
        frame.add(ga00);
        frame.pack();
        ga00.requestFocus();  
        frame.setVisible(true);
    }*/
      public static void main(String[] args) {
        JFrame frame = new JFrame("GA00");
        GamePanel panel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
