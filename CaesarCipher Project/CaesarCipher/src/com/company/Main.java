package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            File f = new File("caeserCipher.txt");
            Scanner sF = new Scanner(f);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you wish to encrypt or decrypt a message?");
            String userinput = scanner.nextLine();

            if(userinput.equals("encrypt")) {
                System.out.println("Enter your message: ");
                String message = scanner.nextLine();
                System.out.println("Enter the key number (1-52)");
                int key = scanner.nextInt();
                System.out.println("Your translated text is: ");
               // String text = "";
                FileWriter writer = new FileWriter("caeserCipher.txt");

                char[] chars = message.toCharArray();
                for(char c : chars) {
                    c += key;
                    writer.append(c);
                    System.out.print(c);
                }
                writer.close();
            }
            else if(userinput.equals("decrypt")) {

                String message = sF.next();
                char[] chars = message.toCharArray();

                System.out.println("Enter your message: ");
                System.out.println(message);
                System.out.println("Enter the key number (1-52)");
                int key = scanner.nextInt();
                System.out.println("Your translated text is: ");

                for(char c : chars) {
                    c -= key;
                    System.out.print(c);
                }
            }
            sF.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
