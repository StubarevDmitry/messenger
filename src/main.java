import Controler.Controller;
import Server.Server;
import org.xml.sax.SAXException;
import visual.UsersList;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public class main {
    private static Vector<String[]> userList;

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        Scanner in = new Scanner(System.in);
        String typeOfInteraction = in.nextLine();
        while(true) {
            if (Objects.equals(typeOfInteraction, "S")) {
                new Server();
                break;
            } else if (Objects.equals(typeOfInteraction, "U")) {
                System.out.print("имя пользователя :");
                String name = in.nextLine();
                System.out.print("тип пользователя :");
                String type = in.nextLine();
                new Controller(name, type);
                break;
            }
        }
    }
}