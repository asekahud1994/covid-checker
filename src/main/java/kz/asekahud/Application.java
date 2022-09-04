package kz.asekahud;

import kz.asekahud.service.PrintService;
import java.util.Scanner;

/**
 * This application displays COVID-19 information across different countries
 * @author asekahud
 */
public class Application {

    public static void main(String[] args) {

        // Instantiation of object that will print information
        var printService = new PrintService();

        // prints welcome message
        printService.printWelcomeMessage();

        // app will wait for user input to analyze
        var scanner = new Scanner(System.in);
        System.out.println("enter country name:");
        String input = scanner.next();

        // until user types exit command, the app will be running and
        // waiting for user input
        while (!input.equals("exit")) {
            printService.printCountryInfo(input);
            System.out.println();
            System.out.println("enter country name:");
            input = scanner.next();
        }

        // prints goodbye message
        printService.printGoodbyeMessage();
    }
}
