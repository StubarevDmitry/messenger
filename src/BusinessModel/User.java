package BusinessModel;

//import XML_Function.SAXparser;
import XML_Function.XML_parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;
import java.util.Vector;

import static XML_Function.XML_MessageCreation.*;

public class User {

    boolean isExit = false;
    private final Socket clientSocket;
    private final BufferedReader reader;
    private final BufferedReader in;
    private final BufferedWriter out;

    private String massage = "";
    private boolean isNewMassage = false;
    private boolean isShowUserList = false;
    private final String name;
    private final String type;
    private Vector<String[]> userList;
    boolean isConnect = false;

    public String getMassage(){
        return massage;
    }
    public boolean isNewMassage(){
        return isNewMassage;
    }
    public void TurnInOldMassage(){
        isNewMassage = false;
    }
    public Vector<String[]> getUserList(){
        return userList;
    }
    public boolean isShowUserList(){
        return isShowUserList;
    }
    public void StopShowUserList(){
        isShowUserList = false;
    }
    public User(String name, String type) throws IOException {
        this.name = name;
        this.type = type;
        System.out.println("пользователь запущен!");
        clientSocket = new Socket("localhost", 4004);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        reader = new BufferedReader(new InputStreamReader(System.in));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        ReadMsg read = new ReadMsg();
        read.start();
        loginMsg();
        //System.out.println("+");
        //WriteMsg write = new WriteMsg();
        //write.start();
    }
    public void sendMsg(String msg) throws IOException {
        String userWord = msg;
        //userWord = reader.readLine();
        userWord = clientMessage(userWord, "123");
        if (userWord.equals("stop")) {
            out.write("stop" + "\n");
        } else {
            out.write(userWord);
        }
        out.flush();
    }
    private class ReadMsg extends Thread {
        @Override
        public void run() {

            try {
                while (true) {

                    //выключение
                    if (isExit){
                        break;
                    }
                    StringBuilder msg = new StringBuilder(readLineTimeout(in,500));

                    if(!msg.toString().equals(" ")) {
                        //обработка сообщений
                        //msg = new StringBuilder(in.readLine());
                        int N = Integer.parseInt(msg.toString());
                        msg = new StringBuilder();
                        for (int i = 0; i < N; i++) {
                            msg.append(in.readLine()).append("\n");
                        }

                        XML_parser parser = new XML_parser(msg.toString());

                        if (parser.isCommandList()) {
                            userList = parser.vector;
                            isShowUserList = true;
                            //System.out.println(userList.get(0)[0] + ": " + userList.get(0)[1]);
                        }

                        if (parser.isCommandMessage()) {
                            System.out.println(parser.getName() + ": " + parser.getMessage());
                            massage = parser.getName() + ": " + parser.getMessage();
                            isNewMassage = true;
                        }

                        if (parser.isCommandLogin()) {
                            System.out.println(parser.getName() + " зашел в чат");
                            massage = "                                       " + parser.getName() + " зашел в чат";
                            isNewMassage = true;
                        }
                    }
                }
            } catch (IOException ignored) {

            } catch (ParserConfigurationException | SAXException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String readLineTimeout(BufferedReader reader, long timeout) throws IOException{
        long start = System.currentTimeMillis();

        while (!reader.ready()) {
            if (System.currentTimeMillis() - start >= timeout)
                return " ";
            try { Thread.sleep(50); } catch (Exception ignore) {}
        }
        String str = reader.readLine();
        return str;
    }

    public void loginMsg() throws IOException {
        String str = clientLoginMessage(name, type);
        out.write(str);
        out.flush(); // чистим
    }

    public void exitMsg() throws IOException {
        String str = userMsgAboutExitToServer();
        isExit = true;
        out.write(str);
        out.flush(); // чистим
    }

    public void ListMsg() throws IOException {
        String str = clientListMessage();
        out.write(str);
        out.flush(); // чистим
    }

    public class WriteMsg extends Thread {
        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    if(!isConnect){
                        //sleep(3000);
                        String str = clientLoginMessage(name, type);
                        out.write(str);
                        isConnect = true;
                    }
                    else {

                        userWord = reader.readLine();
                        userWord = clientMessage(userWord, "123");
                        if (userWord.equals("stop")) {
                            out.write("stop" + "\n");
                            break;
                        } else {
                            out.write(userWord);
                        }
                    }
                    out.flush(); // чистим
                } catch (IOException e) {}
            }
        }
    }
}
