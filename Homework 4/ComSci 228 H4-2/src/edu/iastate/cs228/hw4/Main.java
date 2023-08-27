package edu.iastate.cs228.hw4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Subham Bhattacharya
 * main class of the project for inputs
 */


public class Main {
    /**
     *
     * @param args
     * @throws IOException, FileNotFoundException
     */
    public static void main(String[] args) throws IOException {
        try {
            System.out.println("Please enter filename to decode:  ");
            Scanner sc = new Scanner(System.in);
            String file = sc.nextLine();

            // seperates thebinary code of 1010 which is the encoded message and pattern of ^^a...
            String contentOfFile = new String(Files.readAllBytes(Paths.get(file))).trim();
            int endOfStringPattern = contentOfFile.lastIndexOf('\n');
            String stringPattern = contentOfFile.substring(0, endOfStringPattern); // pattern for graph
            String binaryCode = contentOfFile.substring(endOfStringPattern).trim(); // binary code for encoded message

            String charsWithoutCarrot = "";
            for (int i = 0; i < stringPattern.length(); i++) {
                if (stringPattern.charAt(i) != '^') {
                    charsWithoutCarrot += stringPattern.charAt(i);
                }
            }
            MsgTree tree = new MsgTree(stringPattern);
            MsgTree.printCodes(tree, charsWithoutCarrot);
            tree.decode(tree, binaryCode);

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }
}


