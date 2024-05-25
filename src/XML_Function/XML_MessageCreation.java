package XML_Function;

import java.util.Vector;

public class XML_MessageCreation {
    public static String clientMessage(String message, String session){
        String word = "5\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<command name=\"message\" >\n" +
                "<message>"+ message +"</message>\n" +
                "<session>"+ session +"</session>\n" +
                "</command>\n";
        return word;
    }

    public static String clientLoginMessage(String name, String type){
        String word = "5\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<command name=\"Login\" >\n" +
                "<name>"+ name +"</name>\n" +
                "<type>"+ type +"</type>\n" +
                "</command>\n";
        return word;
    }

    public static String clientListMessage(){
        String word = "3\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<command name=\"List\" >\n" +
                "</command>\n";
        return word;
    }

    public static String serverMessage(String message, String name){
        StringBuilder word = new StringBuilder("5\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<event name=\"message\" >\n" +
                "<message>" + message + "</message>\n" +
                "<name>" + name + "</name>\n" +
                "</event>\n");
        word.deleteCharAt(word.length() - 1);
        return word.toString();
    }

    public static String serverToUserLoginMessage(String name){
        StringBuilder word = new StringBuilder("4\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<event name=\"userlogin\" >\n" +
                "<name>" + name + "</name>\n" +
                "</event>\n");
        word.deleteCharAt(word.length() - 1);
        return word.toString();
    }

    public static String serverMsgAboutUserList(Vector<String[]> vector){
        int numberOfLines = vector.size() * 4 + 5;
        StringBuilder word = new StringBuilder(String.valueOf(numberOfLines) + "\n"+
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<success >\n" +
                "<listusers >\n");
        for (int i = 0; i < vector.size(); i++){
            word.append("<BusinessModel.User>\n").append("<name>").append(vector.get(i)[0]).append("</name>\n").append("<type>").append(vector.get(i)[1]).append("</type>\n").append("</BusinessModel.User>\n");
        }
        word.append("</listusers>\n" + "</success>\n");
        word.deleteCharAt(word.length() - 1);
        return word.toString();
    }

    static String success(){
        String word = "3\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<success>\n" +
                "</success>\n";
        return word;
    }
    public static String userMsgAboutExitToServer(){
        String word = "3\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<exit>\n" +
                "</exit>\n";
        return word;
    }

}
