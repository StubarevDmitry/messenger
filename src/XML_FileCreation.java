import java.util.Vector;

public class XML_FileCreation {
    static String clientMessage(String message, String session){
        String word = "5\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<command name=\"message\" >\n" +
                "<message>"+ message +"</message>\n" +
                "<session>"+ session +"</session>\n" +
                "</command>\n";
        return word;
    }

    static String clientLoginMessage(String name, String type){
        String word = "5\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<command name=\"Login\" >\n" +
                "<name>"+ name +"</name>\n" +
                "<type>"+ type +"</type>\n" +
                "</command>\n";
        return word;
    }
    static String clientListMessage(){
        String word = "3\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<command name=\"List\" >\n" +
                //"<name>"+ name +"</name>\n" +
                "</command>\n";
        return word;
    }
    static String serverMessage(String message, String name){
        StringBuilder word = new StringBuilder("5\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<event name=\"message\" >\n" +
                "<message>" + message + "</message>\n" +
                "<name>" + name + "</name>\n" +
                "</event>\n");
        word.deleteCharAt(word.length() - 1);
        return word.toString();
    }
    static String serverToUserLoginMessage(String name){
        StringBuilder word = new StringBuilder("4\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<event name=\"userlogin\" >\n" +
                "<name>" + name + "</name>\n" +
                "</event>\n");
        word.deleteCharAt(word.length() - 1);
        return word.toString();
    }
    static String serverToUserListMessage(Vector<String[]> vector){
        int numberOfLines = vector.size() * 4 + 5;
        StringBuilder word = new StringBuilder(String.valueOf(numberOfLines) + "\n"+
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<success >\n" +
                "<listusers >\n");
        for (int i = 0; i < vector.size(); i++){
            word.append("<User>\n").append("<name>").append(vector.get(i)[0]).append("</name>\n").append("<type>").append(vector.get(i)[1]).append("</type>\n").append("</User>\n");
        }
        word.append("</listusers>\n" + "</success>\n");
        word.deleteCharAt(word.length() - 1);
        return word.toString();
    }
    static String success(String message, String name){
        String word = "3\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<success>\n" +
                "</success>\n";
        return word;
    }

}
