import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen {
    private JFrame win;
    private JTextField tf3;
    private JLabel lbl1;
    private JButton btn1, btn2;
    private String message = "";
    public String getMessage(){
        return message;
    }
    MainScreen(String name, String status) {
        win = new JFrame("user");

        tf3 = new JTextField();

        lbl1 = new JLabel("Имя пользователя:" + name +" статус:" + status);
        lbl1.setBounds(90,0,325,20);
        win.add(lbl1);

        tf3.setBounds(10,215,300,25);
        win.add(tf3);

        btn1 = new JButton("отправить");
        btn1.setBounds(310,215,100,25);
        win.add(btn1);

        btn2 = new JButton("показать ползователей");
        btn2.setBounds(100,20,200,25);
        win.add(btn2);

        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //tf1.setText("");
                //tf2.setText("");
                message =  tf3.getText();
                tf3.setText("");
            }
        });

        win.setSize(430, 280);
        win.setLayout(null);
        win.setVisible(true);
    }
}