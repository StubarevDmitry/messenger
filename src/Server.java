import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private Vector<InteractionWithClients> allConnections;

    private class InteractionWithClients extends Thread{
        public BufferedReader in;
        public BufferedWriter out;
        public  Socket clientSocket;
        InteractionWithClients(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            start();
        }
        @Override
        public void run() {
            String word;
            try {
                while (true) {
                    word = in.readLine();
                    int N = Integer.valueOf(word);
                    word = "";
                    for (int i = 0; i < N; i++) {
                        word = word + in.readLine() + "\n";
                    }
                    //System.out.print(word);
                    //System.out.println(word);
                    SAXExample parser = new SAXExample(word);
//                    if(parser.isCommandMessage()){
//                        System.out.println(parser.getMessage());
//                        System.out.println(parser.getSession());
//                    }
                    if(word.equals("stop")) {
                        break;
                    }
                    for (InteractionWithClients vr : allConnections) {
                        vr.send(word); // отослать принятое сообщение с привязанного клиента всем остальным включая его
                    }
                }
                in.close();
                out.close();
            } catch (IOException ignored) {
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        }
        private void send(String msg) {
            try {
                out.write(msg + "\n");
                out.flush();
            } catch (IOException ignored) {}
        }
    }

    Server() throws IOException {
        try {
            ServerSocket server = new ServerSocket(4004);
            System.out.println("Сервер запущен!");
            allConnections = new Vector<>();
            while(true){
                Socket socket = server.accept();
                System.out.println("канект произведён");
                //assert allConnections != null;
                allConnections.add(new InteractionWithClients(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
