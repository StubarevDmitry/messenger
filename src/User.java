import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;

public class User {
    boolean isExist = true;
    private final Socket clientSocket;
    private final BufferedReader reader;
    private final BufferedReader in;
    private final BufferedWriter out;

    private String massage = "";
    private boolean isNewMassage = false;
    private final String name;
    private final String type;
    //public Object addActionListener;
    boolean isConnect = false;

    public String getMassage(){
        return massage;
    }
    public boolean isNewMassage(){
        return isNewMassage;
    }
    public void TurnIsNewMassage(){
        isNewMassage = !isNewMassage;
    }
    User(String name, String type) throws IOException {
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
        userWord = XML_FileCreation.clientMessage(userWord, "123");
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

            StringBuilder str;
            try {
                do {
                    str = new StringBuilder(in.readLine());
                    int N = Integer.parseInt(str.toString());
                    str = new StringBuilder();
                    for (int i = 0; i < N; i++) {
                        str.append(in.readLine()).append("\n");
                    }
                    //System.out.print(str);
                    SAXparser parser = new SAXparser(str.toString());
                    //if(parser.isCommandLogin())
                    if(parser.isCommandMessage()){
                        System.out.println(parser.getName() + ": " + parser.getMessage());
                        massage = parser.getName() + ": " + parser.getMessage();
                        isNewMassage = true;

                    }
                    if(parser.isCommandLogin()){
                        System.out.println(parser.getName() + " зашел в чат");
                        massage = "                                       " + parser.getName() + " зашел в чат";
                        isNewMassage = true;
                    }
                } while (!str.toString().equals("stop"));
            } catch (IOException ignored) {

            } catch (ParserConfigurationException | SAXException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void loginMsg() throws IOException {
        String str = XML_FileCreation.clientLoginMessage(name, type);
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
                        String str = XML_FileCreation.clientLoginMessage(name, type);
                        out.write(str);
                        isConnect = true;
                    }
                    else {

                        userWord = reader.readLine();
                        userWord = XML_FileCreation.clientMessage(userWord, "123");
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
