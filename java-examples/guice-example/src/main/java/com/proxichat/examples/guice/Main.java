package com.proxichat.examples.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.proxichat.examples.guice.service.Registrar;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args) {

        // Create a RegistrationModule instance using the default values.
        RegistrationModule registrationModule = new RegistrationModule.Builder().build();

        Injector injector = Guice.createInjector(registrationModule);
        Registrar registrar = injector.getInstance(Registrar.class);
        registrar.registerStudent("Matthias Eder", 12);
        registrar.registerStudent("Ashley Thornton", 9);

        Scanner scanner = new Scanner(System.in);
        System.out.println( "Welcome to the Registration Database App.");

        String nextLine;
        while(true) {
            System.out.print("Lookup person by ID ('q' to exit): ");
            nextLine = scanner.nextLine();
            if (nextLine.equalsIgnoreCase("q")) {
                break;
            }

            try {
                Integer personId = new Integer(nextLine);
                System.out.printf( "You supplied Person ID %d searching...%n", personId );
                boolean status = registrar.checkStudentStatus( personId );
                if( status ) {
                    System.out.println( "This student is currently enrolled and meets " +
                            "the registrar's criteria" );
                } else {
                    System.out.println( "This student does not meet the registrar's criteria" );
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input: expected numeric person id.");
            }
        }

        scanner.close();
    }
}
