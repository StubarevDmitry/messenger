import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //System.out.println("Hello world!");
        Scanner in = new Scanner(System.in);
        if(Objects.equals(in.nextLine(), "S")){
            new Server();
        }
        if(Objects.equals(in.nextLine(), "U")){
            new User();
        }
    }
}