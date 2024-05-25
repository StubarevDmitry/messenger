package visual;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class UsersList extends JFrame {
    //public Image;
    //private StringBuilder str = new StringBuilder();
    private final Vector<String[]> userList;

    int numberOfColumns = 0;
    public UsersList(Vector<String[]> userList) {
        image = new ImageIcon("C:\\Users\\stuba\\IdeaProjects\\messenger\\src\\visual\\image\\Filed.jpg").getImage();
        this.userList = userList;
        //UsersList f = new UsersList();
        //f.setSize(300, 300);
        //f.setVisible(true);
    }
    public Image image;
    final Font f = new Font("SansSerif", Font.BOLD, 15);

    public void paint(Graphics g) {
        Dimension d = this.getSize();

        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.black);
        g.setFont(f);
        g.drawImage(image, 6, 29, this);
        StringBuilder str = new StringBuilder("Имя   |   статус");
        drawCenteredString(str.toString(), g);
        for (String[] strings : userList) {
            str.delete(0,str.length());
            str.append(strings[0]).append("   |   ").append(strings[1]).append("\n");
            drawCenteredString(str.toString(), g);
        }
    }

    public void drawCenteredString(String s, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = 22;
        int y = 60 + 15 * numberOfColumns;
        numberOfColumns += 1;
        g.drawString(s, x, y);
    }
}