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

    static String serverMessage(String message, String name){
        String word = "5\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<event name=\"message\" >\n" +
                "<message>"+ message +"</message>\n" +
                "<name>"+ name +"</name>\n" +
                "</event>\n";
        return word;
    }
}
