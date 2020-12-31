package com.ngoctm.collections2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CollectionExcercise2 {
    static List<Message> messageList = new LinkedList<>();
    static Map<String, List<Message>> dayToMessagesMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            CollectionExcercise2.run("/home/ngoctm/Collections2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void run(String rootPath) throws IOException {
        loadListFileInput(rootPath);
        sortMessageByDay();
        filterMessageByDay();
        exportResults(rootPath);
        print();
    }

    public static void loadListFileInput(String path) throws FileNotFoundException {
        File[] files = new File(path).listFiles();
        for(File file : files){
            if(file.isFile()){
                readFile(file.getPath());
            } else if(file.isDirectory()){
                loadListFileInput(file.getPath());
            }
        }
    }

    private static void readFile(String path) throws FileNotFoundException {
        String[] split = path.split("/");
        String fileName = split[split.length - 1];
        if(path.contains("input")){
            String phoneNumber = fileName.substring(0, fileName.length() - 4);
            readFileToMessage(path, phoneNumber);
        }
    }

    private static void readFileToMessage(String path, String phoneNumber) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            Message message = convertStringToMessage(line, phoneNumber);
            messageList.add(message);
        }
        scanner.close();
    }

    private static Message convertStringToMessage(String messageString, String phoneNumber) {
        Message message = new Message();
        String[] splitMessage = messageString.split("\\|");
        message.setContent(splitMessage[0]);
        message.setPhoneNumber(phoneNumber);
        message.setTime(splitMessage[1]);
        return message;
    }

    private static void filterMessageByDay() {
        for(Message message : messageList){
            addMessageToMap(message);
        }
    }

    private static void addMessageToMap(Message message) {
        String dayOfMessage = message.getTime().split(" ")[0];
        String key = dayOfMessage;
        if(dayToMessagesMap.containsKey(key)){
            List<Message> messages = dayToMessagesMap.get(key);
            messages.add(message);
        } else {
            List<Message> messages = new ArrayList<>();
            messages.add(message);
            dayToMessagesMap.put(key, messages);
        }
    }

    private static void sortMessageByDay() {
        Collections.sort(messageList);
    }

    private static void exportResults(String rootPath) throws IOException {
        String pathOutputFolder = rootPath + "/output/";
        File theDirectoryOutput = new File(pathOutputFolder);
        if(!theDirectoryOutput.exists()){
            theDirectoryOutput.mkdir();
        }
        for(String key : dayToMessagesMap.keySet()){
            String fileName = key.replace("/", "");
            String pathFile = pathOutputFolder + fileName + ".txt";
            List<Message> messages = dayToMessagesMap.get(key);
            writeToFile(pathFile, messages);
        }
    }

    private static void writeToFile(String pathFile, List<Message> messages) throws IOException {
        File fileOutput = new File(pathFile);
        if(!fileOutput.exists()){
            fileOutput.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(fileOutput);
        for(Message message : messages){
            fileWriter.write(message.toString());
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    private static void print() {
        for(String key : dayToMessagesMap.keySet()){
            System.out.println("Thoi gian " + key);
            List<Message> messages = dayToMessagesMap.get(key);
            for(Message message : messages){
                System.out.println(message);
            }
        }
    }
}
