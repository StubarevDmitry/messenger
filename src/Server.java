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
        public String getUserName(){
            return userName;
        }
        public String getType(){
            return type;
        }
        InteractionWithClients(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            start();
        }
        @Override
        public void run() {
            StringBuilder msg;
            try {
                do {
                    msg = new StringBuilder(in.readLine());// ждёт команды

                    //оборобатывает команду
                    int N = Integer.parseInt(msg.toString());
                    msg = new StringBuilder();
                    for (int i = 0; i < N; i++) {
                        msg.append(in.readLine()).append("\n");
                    }
                    SAXparser parser = new SAXparser(msg.toString());
                    //

                    if(parser.isCommandList()){
                        Vector<String[]> vector = new Vector<>();
                        for (InteractionWithClients vr : allConnections) {
                            String[] str = new String[2];
                            str[0] = vr.getUserName();
                            str[1] = vr.getType();
                            vector.add(str);
                        }
                        msg = new StringBuilder(XML_FileCreation.serverToUserListMessage(vector));
                        send(msg.toString());
                    }

                    if (parser.isCommandLogin()) {
                        System.out.println("пользователь с ником " + parser.getName() + " и типом " + parser.getType());
                        userName = parser.getName();
                        type = parser.getType();
                        msg = new StringBuilder(XML_FileCreation.serverToUserLoginMessage(userName));
                        for (InteractionWithClients vr : allConnections) {
                            vr.send(msg.toString()); // отослать сообщение о входе клиента всем остальным
                        }
                    }

                    if (parser.isCommandMessage()) {
                        msg = new StringBuilder(XML_FileCreation.serverMessage(parser.getMessage(), userName));
                        for (InteractionWithClients vr : allConnections) {
                            vr.send(msg.toString()); // отослать принятое сообщение с привязанного клиента всем остальным включая его
                        }
                    }

                } while (!msg.toString().equals("stop"));
                in.close();
                out.close();
            } catch (IOException ignored) {
            } catch (ParserConfigurationException | SAXException e) {
                try {
                    in.close();
                    out.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
