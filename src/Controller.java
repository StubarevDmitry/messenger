import java.io.IOException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Controller {
    Controller(String name, String type) throws IOException, InterruptedException {
        User user = new User(name, type);
        //user.loginMsg();
        MainScreen screen = new MainScreen(name, type);
        String msg = "";
        while (true){
            String newMsg = screen.getMessage();
            if(!newMsg.equals(msg)){
                msg = newMsg;
                user.sendMsg(msg);
            }
            if(user.isNewMassage()){
                //screen.printMsg(user.getMassage());
                user.TurnIsNewMassage();
                user.ListMsg();
            }
            sleep(500);
        }
    }
}
