import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        //System.out.println("Hello world!");
//        new SAXExample("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<company>\n" +
//                "    <name>IT-Heaven</name>\n" +
//                "    <offices>\n" +
//                "        <office floor=\"1\" room=\"1\">\n" +
//                "            <employees>\n" +
//                "                <employee>\n" +
//                "                    <name>Maksim</name>\n" +
//                "                    <job>Middle Software Developer</job>\n" +
//                "                </employee>\n" +
//                "                <employee>\n" +
//                "                    <name>Ivan</name>\n" +
//                "                    <job>Junior Software Developer</job>\n" +
//                "                </employee>\n" +
//                "                <employee>\n" +
//                "                    <name>Franklin</name>\n" +
//                "                    <job>Junior Software Developer</job>\n" +
//                "                </employee>\n" +
//                "            </employees>\n" +
//                "        </office>\n" +
//                "        <office floor=\"1\" room=\"2\">\n" +
//                "            <employees>\n" +
//                "                <employee>\n" +
//                "                    <name>Herald</name>\n" +
//                "                    <job>Middle Software Developer</job>\n" +
//                "                </employee>\n" +
//                "                <employee>\n" +
//                "                    <name>Adam</name>\n" +
//                "                    <job>Middle Software Developer</job>\n" +
//                "                </employee>\n" +
//                "                <employee>\n" +
//                "                    <name>Leroy</name>\n" +
//                "                    <job>Junior Software Developer</job>\n" +
//                "                </employee>\n" +
//                "            </employees>\n" +
//                "        </office>\n" +
//                "    </offices>\n" +
//                "</company>");
        new SAXExample("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<command name=\"message\" >\n" +
                "<message>mmmmmmmmmmmmmmmmmmm</message>\n" +
                "<session>UNIQUE_SESSION_ID</session>\n" +
                "</command>");
        //Scanner in = new Scanner(System.in);
//        if(Objects.equals(in.nextLine(), "S")){
//            new Server();
//        }
//        else{
//            new User();
//        }
        //if(Objects.equals(in.nextLine(), "U"))
        //}
    }
}