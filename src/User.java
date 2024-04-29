import java.io.*;
import java.net.Socket;

public class User {
    private Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    User() throws IOException {
        System.out.println("пользователь запущен!");
        clientSocket = new Socket("localhost", 4004);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        reader = new BufferedReader(new InputStreamReader(System.in));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        ReadMsg read = new ReadMsg();
        read.start();
        System.out.println("+");
        WriteMsg write = new WriteMsg();
        write.start();
    }
    private class ReadMsg extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = in.readLine();
                    if (str.equals("stop")) {
                        break;
                    }
                }
            } catch (IOException e) {

            }
        }
    }

    public class WriteMsg extends Thread {
        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    userWord = reader.readLine();
                    System.out.println("++");
                    userWord = XML_FileCreation.clientMessage(userWord, "123");
                    System.out.println("++");

                    if (userWord.equals("stop")) {
                        out.write("stop" + "\n");
                        break;
                    } else {
                        out.write(userWord);
                    }
                    out.flush(); // чистим
                } catch (IOException e) {

                }

            }
        }
    }
}
