import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private final Vector<InteractionWithClients> allConnections;

    private class InteractionWithClients extends Thread{
        private String userName = "Dima";
        private String type = "Dima";
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
            StringBuilder word;
            try {
                do {
                    word = new StringBuilder(in.readLine());// ждёт команды

                    //оборобатывает команду
                    int N = Integer.parseInt(word.toString());
                    word = new StringBuilder();
                    for (int i = 0; i < N; i++) {
                        word.append(in.readLine()).append("\n");
                    }
                    SAXparser parser = new SAXparser(word.toString());
                    //

                    if (parser.isCommandLogin()) {
                        System.out.println("пользователь с ником " + parser.getName() + " и типом " + parser.getType());
                        userName = parser.getName();
                        type = parser.getType();
                        word = new StringBuilder(XML_FileCreation.serverUserLoginMessage(userName));
                        for (InteractionWithClients vr : allConnections) {
                            vr.send(word.toString()); // отослать сообщение о входе клиента всем остальным
                        }
                    }

                    if (parser.isCommandMessage()) {
                        word = new StringBuilder(XML_FileCreation.serverMessage(parser.getMessage(), userName));
                        for (InteractionWithClients vr : allConnections) {
                            vr.send(word.toString()); // отослать принятое сообщение с привязанного клиента всем остальным включая его
                        }
                    }

                } while (!word.toString().equals("stop"));
                in.close();
                out.close();
            } catch (IOException ignored) {
            } catch (ParserConfigurationException | SAXException e) {
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
