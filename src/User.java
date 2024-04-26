import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class User {
    private Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    User() throws IOException {
        clientSocket = new Socket("localhost", 4004);
        ReadMsg read = new ReadMsg();
        read.run();
        WriteMsg write = new WriteMsg();
        write.run();
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
                    if (userWord.equals("stop")) {
                        out.write("stop" + "\n");
                        break;
                    } else {
                        out.write(userWord + "\n");
                    }
                    out.flush(); // чистим
                } catch (IOException e) {

                }

            }
        }
    }
}
