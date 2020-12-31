package com.ngoctm.string;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StringExcercise1 {
    String pathFile;
    StringBuilder contentFile;

    public StringExcercise1(String pathFile){
        this.pathFile = pathFile;
    }

    public void runExercise() {
            readFile();
            checkUpperCaseAndLowerCase();
            deleteSpace();
            insertString();
            replaceString();
            writeToFile();
    }

    public void readFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(this.pathFile));
            this.contentFile = new StringBuilder();
            while (scanner.hasNextLine()){
                String tempString = scanner.nextLine();
                contentFile.append(tempString + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private void checkUpperCaseAndLowerCase(){
        List<Character> listUpperCase = new ArrayList<>();
        int numberOfchar = 0;
        int numberOfLowerCase = 0;
        for(int positionOfChar = 0; positionOfChar < contentFile.length(); positionOfChar++){
            char charTemp = contentFile.charAt(positionOfChar);
            boolean isUpperCase = charTemp >= 'A' && charTemp <= 'Z';
            boolean isLowerCase = charTemp >= 'a' && charTemp <= 'z';
            if(isUpperCase) listUpperCase.add(charTemp);
            if(isLowerCase) numberOfLowerCase++;
            numberOfchar++;
        }
        printUpperCase(listUpperCase);
        printLowerCase(numberOfLowerCase, numberOfchar);

    }

    private void printLowerCase(int numberOfLowerCase, int numberOfchar) {
        System.out.println("So luong ky tu: " + numberOfchar);
        System.out.println("So ky tu thuong: " + numberOfLowerCase);
    }

    private void printUpperCase(List<Character> listUpperCase) {
        System.out.println("Cac ky tu in hoa: ");
        for(char temp : listUpperCase){
            System.out.print(temp + " ");
        }
        System.out.println();
    }

    private void deleteSpace() {
        System.out.println("Xoa khoang trang thua");
        char tab = 9; // ascII of tab
        char enter = 10; // ascII of enter
        for(int positionOfChar = 0; positionOfChar < contentFile.length() - 1; positionOfChar++){
            boolean isSpaceExcess = (contentFile.charAt(positionOfChar) == ' ')
                    && (contentFile.charAt(positionOfChar + 1) == ' ');
            boolean isTabExcess = (contentFile.charAt(positionOfChar) == tab) && positionOfChar > 0
                    && (contentFile.charAt(positionOfChar - 1) != enter);
            if(isSpaceExcess || isTabExcess){
                contentFile.deleteCharAt(positionOfChar);
                positionOfChar--;
            }
        }
    }

    private void insertString(){
        System.out.println("chen 'o con ga cua toi' vao sau '$'");
        String oldString = "$";
        String addingString = "o con ga cua toi";
        String temp = contentFile.toString();
        temp.replace(oldString, addingString);
        contentFile = new StringBuilder(temp);

    }

    private void replaceString(){
        System.out.println("Doi 'Toi yeu ha noi pho' thanh in hoa");
        String oldString = "Toi yeu ha noi pho";
        String newString = oldString.toUpperCase();
        String temp = contentFile.toString();
        temp = temp.replace(oldString, newString);
        contentFile = new StringBuilder(temp);
    }


    private void writeToFile(){
        String pathFileOutput = pathFile.replace("inPut", "outPut");
        File fileOutput = new File(pathFileOutput);
        if(!fileOutput.exists()) {
            try {
                fileOutput.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileOutput);
            fileWriter.write(contentFile.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void print(String content){
            System.out.println(content);
    }

    public static void main(String[] args) {
        StringExcercise1 stringExcercise1 = new StringExcercise1("/home/ngoctm/String/inPut1.txt");
        stringExcercise1.runExercise();
    }
}
