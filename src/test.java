import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class HorizontalVisibilityTest {
    private static final boolean DEBUG = false;
    private static final String TEXT = "javascript:(function(){var l=location,"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa=0;}());";
    public JComponent makeUI() {
        Box box = Box.createVerticalBox();
        JScrollPane scroll = new JScrollPane(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setViewportView(new JTextField(TEXT));
        box.add(new JLabel("JScrollPane + VERTICAL_SCROLLBAR_NEVER"));
        box.add(scroll);

        box.add(Box.createVerticalStrut(15));
        final JTextField textField = new JTextField(TEXT);
        JScrollBar scroller = new JScrollBar(JScrollBar.HORIZONTAL);
        scroller.setModel(textField.getHorizontalVisibility());

//        if (DEBUG) { //I'm not sure if this is a bug...
//            EmptyThumbHandler handler = new EmptyThumbHandler(textField, scroller);
//            textField.addComponentListener(handler);
//            textField.getDocument().addDocumentListener(handler);
//        }

        box.add(new JLabel("BoundedRangeModel: textField.getHorizontalVisibility()"));
        box.add(textField, BorderLayout.SOUTH);
        box.add(Box.createVerticalStrut(5));
        box.add(scroller);

        JPanel p = new JPanel(new BorderLayout());
        p.add(box, BorderLayout.NORTH);
        p.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
        return p;
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                createAndShowGUI();
            }
        });
    }
    public static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(new HorizontalVisibilityTest().makeUI());
        f.setSize(320, 240);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}