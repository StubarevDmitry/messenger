package Controler;

import BusinessModel.User;
import visual.MainScreen;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class Controller {
    public Controller(String name, String type) throws IOException, InterruptedException {
        User user = new User(name, type);
        MainScreen screen = new MainScreen(name, type);
        String msg = "";
        while (true){

            String newMsg = screen.getMessage();
            if(!newMsg.equals(msg)){
                msg = newMsg;
                user.sendMsg(msg);
            }

            if(screen.isOpenList()){
                user.ListMsg();
                screen.turnIsOpenList();
            }

            if(user.isNewMassage()){
                screen.printMsg(user.getMassage());
                user.TurnInOldMassage();
            }

            if(user.isShowUserList()){
                screen.printUserList(user.getUserList());
                user.StopShowUserList();
            }

            if (screen.isExit()){
                user.exitMsg();
                break;
            }
            sleep(300);
        }
    }
}
