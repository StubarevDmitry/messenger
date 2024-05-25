package visual;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Vector;

public class MainScreen {
    private JFrame win;
    private JTextField tf3;
    private JLabel lbl1;
    private JButton sendMsgButton, openListButton, exitButton;
    private final JTextArea messagesField;
    private JScrollPane sp;
    private String message = "";
    private boolean isOpenList = false;
    private boolean isExit = false;
    public boolean isOpenList(){
        return isOpenList;
    }

    public  void turnIsOpenList(){
        isOpenList = !isOpenList;
    }
    public String getMessage(){
        return message;
    }

    public boolean isExit(){return isExit;}
    public MainScreen(String name, String status) {
        win = new JFrame("Zov");

        messagesField = new JTextArea(10, 15);
        messagesField.isFocusable();
        sp = new JScrollPane(messagesField);
        sp.setBounds(10,45,395,170);
        win.add(sp);

        tf3 = new JTextField();

        lbl1 = new JLabel("Имя пользователя:" + name +" статус:" + status);
        lbl1.setBounds(110,0,325,20);
        win.add(lbl1);

        tf3.setBounds(10,215,300,25);
        win.add(tf3);

        sendMsgButton = new JButton("отправить");
        sendMsgButton.setBounds(310,215,100,25);
        win.add(sendMsgButton);

        exitButton = new JButton("выход");
        exitButton.setBounds(60,20,100,25);
        win.add(exitButton);

        openListButton = new JButton("показать ползователей");
        openListButton.setBounds(160,20,200,25);
        win.add(openListButton);

        sendMsgButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                message =  tf3.getText();
                tf3.setText("");
            }
        });

        openListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isOpenList = true;
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isExit = true;
                //win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                win.dispose();
            }
        });

        win.setSize(430, 280);
        win.setLayout(null);
        win.setVisible(true);
    }
    public void printMsg(String message) throws FileNotFoundException {
        messagesField.append(message + "\n");
    }
    public void printUserList(Vector<String[]> userList) throws FileNotFoundException {
        UsersList f = new UsersList(userList);
        f.setSize(212, 385);
        f.setVisible(true);
    }
}