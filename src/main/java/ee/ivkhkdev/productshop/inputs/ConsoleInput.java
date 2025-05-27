package ee.ivkhkdev.productshop.inputs;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class ConsoleInput {

    private final Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }

    public String nextLine() {
        return scanner.nextLine();
    }
}
