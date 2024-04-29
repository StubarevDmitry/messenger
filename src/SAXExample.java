import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
//import java.io.File;
import java.io.IOException;
import java.io.StringBufferInputStream;
//import java.util.ArrayList;

public class SAXExample {
    private String  message, session, name, type;
    private boolean isCommandLogin,isCommandList, isCommandMessage;

    public SAXExample(String arg) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        AdvancedXMLHandler handler = new AdvancedXMLHandler();

        parser.parse(new StringBufferInputStream(arg), handler);
        if(isCommandMessage) {
            System.out.printf("Собщение %s, его сессия: %s%n", message, session);
        }
        if(isCommandLogin) {
            System.out.printf("CommandLogin Имя %s, его тип: %s%n", name, type);
        }
        if(isCommandList) {
            System.out.printf("CommandList сессия: %s%n", session);
        }
    }

    public String getMessage() { return message; }

    public boolean isCommandList() {
        return isCommandList;
    }

    public boolean isCommandLogin() {
        return isCommandLogin;
    }

    public boolean isCommandMessage() {
        return isCommandMessage;
    }

    public String getName() {
        return name;
    }

    public String getSession() {
        return session;
    }

    public String getType() {
        return type;
    }
    private class AdvancedXMLHandler extends DefaultHandler {
        private String lastElementName;
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            lastElementName = qName;
            if (lastElementName.equals("command")){
                String name = attributes.getValue("name");
                if (name.equals("List")){
                    isCommandList = true;
                }
                if (name.equals("message")){
                    isCommandMessage = true;
                }
                if (name.equals("Login")){
                    isCommandLogin = true;
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String information = new String(ch, start, length);
            information = information.replace("\n", "").trim();
            if(isCommandMessage){
                if (!information.isEmpty()) {
                    if (lastElementName.equals("message")) message = information;
                    if (lastElementName.equals("session")) session = information;
                }
            }
            if(isCommandList){
                if (!information.isEmpty()) {
                    if (lastElementName.equals("session")) session = information;
                }
            }
            if(isCommandLogin){
                if (!information.isEmpty()) {
                    if (lastElementName.equals("name")) name = information;
                    if (lastElementName.equals("type")) type = information;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            lastElementName = qName;
        }
    }
}