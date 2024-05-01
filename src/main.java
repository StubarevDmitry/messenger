import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        Scanner in = new Scanner(System.in);
        if(Objects.equals(in.nextLine(), "S")){
            new Server();
        }
        else{
            //String name = in.nextLine();
            //String type = in.nextLine();
            //HorizontalVisibilityTest test = new HorizontalVisibilityTest();
            //test.main(args);
            //HorizontalVisibilityTest test = new HorizontalVisibilityTest();
            //test.main(args);
            new Controller("name", "type");
            //user.connect(name, type);
        }
        //new MainScreen();
        //MainScreen.main(args);
        //if(Objects.equals(in.nextLine(), "U"))
        //}
    }
}