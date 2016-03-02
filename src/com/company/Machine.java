package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.Random;

public class Machine {

    private HashMap<String, String> rules;
    private char[] alphabet;
    private char[] command;
    private char[] bufer;
    private int position = 0;
    private Integer state = 1;
    private BufferedReader file;
    private int step = 0;

    public void readAlphabet(){
        try {
            String buf = file.readLine();
            alphabet = new char[buf.length()];
            for (int i = 0; i < buf.length() ; i++) {
                alphabet[i] = buf.charAt(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readRules(){
        rules = new HashMap<String, String>();

        try {
            String buf;
            while ((buf = file.readLine()) != null) {
                String rule = buf.substring(0, 1);
                rules.put(buf.substring(0, 2), buf.substring(6, 9));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void genStr(int size){
        char[] res = new char[size + 2];
        Random r = new Random();
        res[0] = '#';
        for (int i = 1; i <= size; i++) {
            res[i] = alphabet[r.nextInt(alphabet.length)];
        }
        res[size + 1] = '#';
        command = res;
        bufer = res.clone();
    }

    private void openFile(){
        System.out.println("Enter file name");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        try {
            file = new BufferedReader(new FileReader("D:\\Учёба\\4 курс\\TVPS\\TuringMachine\\" + scanner.nextLine() + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        readAlphabet();
        readRules();
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true){
            String answer = scanner.nextLine();
            if (answer.equals("open")) {
                openFile();
                System.out.println("file open");
            } else if (answer.equals("gen")) {
                position = 0;
                step = 0;
                state = 1;
                System.out.println("Enter size");
                genStr(scanner.nextInt());
                System.out.print("line is: ");
                System.out.println(command);
            } else if (answer.equals("read")) {
                position = 0;
                step = 0;
                state = 1;
                System.out.println("Enter string");
                String str = scanner.nextLine();
                str = "#" + str + "#";
                command = str.toCharArray();
                bufer = str.toCharArray();
                System.out.println("done");
            } else if (answer.equals("start")) {
                go();
            } else if (answer.equals("step")) {
                step();
            } else if (answer.equals("exit")) {
                break;
            }
        }
    }

    private void go(){
        do {
            step();
        } while (state != 0);
    }

    private void step(){
        String pointer = "";
        for (int i = 0; i < position; i++) {
            pointer += " ";
        }
        System.out.println("Step " + ++step + ":");
        char currentSymbol = command[position];
        String currentState =  state.toString() + currentSymbol;
        String nextState = rules.get(currentState);
        System.out.println("rule: q" + currentState + " -> q" + nextState);
        command[position] = nextState.charAt(1);
        char buf = nextState.charAt(0);
        state = ((int) buf) - 48;
        if (nextState.charAt(2) == 'r') position++;
        else position--;
        System.out.println(command);
        pointer += "|";
        System.out.println(pointer);
        System.out.println("nextState = " + nextState);
        System.out.println();

        if (state == 0){
            System.out.println("source:");
            System.out.println(bufer);
        }
    }
}