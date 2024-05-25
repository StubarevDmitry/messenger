package Server;

//import XML_Function.SAX_parser;
//import XML_Function.SAXparser;
import XML_Function.XML_parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Vector;

import static XML_Function.XML_MessageCreation.*;

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
            //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            serverSocket.bind(new InetSocketAddress("localhost", 5454));
//            serverSocket.configureBlocking(false);
//            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            ByteBuffer buffer = ByteBuffer.allocate(256);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            start();
        }
        @Override
        public void run() {
            StringBuilder msg;
            try {
                while (true) {
                    msg = new StringBuilder(in.readLine());// ждёт команды

                    //оборобатывает команду
                    int N = Integer.parseInt(msg.toString());
                    msg = new StringBuilder();
                    for (int i = 0; i < N; i++) {
                        msg.append(in.readLine()).append("\n");
                    }
                    XML_parser parser = new XML_parser(msg.toString());

                    //выход
                    if (parser.isExit()){
                        break;
                    }

                    //передёт User List
                    if(parser.isCommandList()){
                        Vector<String[]> userList = new Vector<>();
                        for (InteractionWithClients vr : allConnections) {
                            String[] str = new String[2];
                            str[0] = vr.getUserName();
                            str[1] = vr.getType();
                            userList.add(str);
                        }
                        msg = new StringBuilder(serverMsgAboutUserList(userList));
                        send(msg.toString());
                    }

                    //если пользователь залогинился:
                    if (parser.isCommandLogin()) {
                        System.out.println("пользователь с ником " + parser.getName() + " и типом " + parser.getType());
                        userName = parser.getName();
                        type = parser.getType();
                        msg = new StringBuilder(serverToUserLoginMessage(userName));
                        try {
                            File file = new File("C:\\Users\\stuba\\IdeaProjects\\messenger\\src\\Server\\messagesFromUser.txt");
                            FileReader fr = new FileReader(file);
                            BufferedReader br = new BufferedReader(fr);
                            String line;
                            while((line = br.readLine()) != null){
                                if(!line.equals("///") && !line.equals("===")){
                                    String[] parts = line.split("----");
                                    String oldMsg = serverMessage(parts[1], parts[0]);
                                    send(oldMsg);
                                    sleep(420);
                                } else if(line.equals("===")) {
                                    line = br.readLine();
                                    String oldMsg = serverToUserLoginMessage(line);
                                    send(oldMsg);
                                    sleep(450);
                                }
                            }
                            br.close();
                            fr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        try (FileWriter writer = new FileWriter("C:\\Users\\stuba\\IdeaProjects\\messenger\\src\\Server\\messagesFromUser.txt", true)) {
                            writer.write("\n" + "===" +"\n"+ userName);
                            writer.close();

                        }
                        for (InteractionWithClients vr : allConnections) {
                            vr.send(msg.toString()); // отослать сообщение о входе клиента всем остальным
                        }
                    }

                    if (parser.isCommandMessage()) {
                        try (FileWriter writer = new FileWriter("C:\\Users\\stuba\\IdeaProjects\\messenger\\src\\Server\\messagesFromUser.txt", true)) {
                            writer.write("\n" + userName + "----" + parser.getMessage());
                            writer.close();

                        }
                        msg = new StringBuilder(serverMessage(parser.getMessage(), userName));
                        for (InteractionWithClients vr : allConnections) {
                            vr.send(msg.toString()); // отослать принятое сообщение с привязанного клиента всем остальным включая его
                        }
                    }

                }
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

    public Server() throws IOException {
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
