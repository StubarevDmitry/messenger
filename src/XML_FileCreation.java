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
    static String serverUserLoginMessage(String name){
        StringBuilder word = new StringBuilder("4\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<event name=\"userlogin\" >\n" +
                "<name>" + name + "</name>\n" +
                "</event>\n");
        word.deleteCharAt(word.length() - 1);
        return word.toString();
    }
    static String success(String message, String name){
        String word = "4\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<success>\n" +
                "</success>\n";
        return word;
    }
}
