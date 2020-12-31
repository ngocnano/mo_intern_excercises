package com.ngoctm.collections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectionExcercise1 {
    static List<Struct> structs = new ArrayList<>();
    static List<Message> messages = new ArrayList<>();

    public static void main(String[] args){
        try {
            CollectionExcercise1.run("/home/ngoctm/Collections");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void run(String rootPath) throws IOException {
        loadListFile(rootPath);
        sortMessage();
        validMessage();
        printStruct();
        writeToFile(rootPath);
        print();
    }

    public static void loadListFile(String path) throws FileNotFoundException {
        File[] files = new File(path).listFiles();
        for(File file : files){
            if(file.isFile()){
                handingPathFile(file.getPath());
            } else if(file.isDirectory()){
                loadListFile(file.getPath());
            }
        }
    }

    private static void handingPathFile(String path) throws FileNotFoundException {
        String structFileName = "struct.txt";
        String messageFileName = "message.txt";
        if (path.contains(structFileName)){
            filterContentStructFile(readFile(path));
        } else if(path.contains(messageFileName)){
            filterContentMessageFile(readFile(path));
        }
    }

    private static List<String> readFile(String path) throws FileNotFoundException {
        List<String> content = new ArrayList<>();
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            content.add(line);
        }
        scanner.close();
        return content;
    }

    private static void filterContentStructFile(List<String> contents) {
        String regex = ".*\\+([0-9]+):.*\\((.*)\\).";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        for(String line : contents){
            matcher = pattern.matcher(line);
            if(matcher.matches()){
                String number = matcher.group(1);
                List<String> syntaxs = Arrays.asList(matcher.group(2).split(", "));
                structs.add(new Struct(number, syntaxs));
            }
        }
    }

    public static void printStruct(){
        System.out.println("Cu phap theo dau so");
        for(Struct struct : structs){
            System.out.println("Dau so " + struct.getNumber() + " cu phap " + struct.getSyntax());
        }
    }

    private static void filterContentMessageFile(List<String> contents) {
        String regex = ".*(\\+[0-9]+)\\((.*)\\|(.*)\\|([0-9]+)\\)";
        Pattern pattern = Pattern.compile(regex);
        for(String line : contents){
            Matcher matcher = pattern.matcher(line);
            if(matcher.matches()){
                Message message = new Message();
                message.setPhoneNumber(matcher.group(1));
                message.setContent(matcher.group(2));
                message.setTime(matcher.group(3));
                message.setPrefixPhoneNumber(matcher.group(4));
                messages.add(message);
            }
        }
    }

    private static void sortMessage() {
        Collections.sort(messages);
    }

    private static void validMessage() {
        for(int index= 0; index < messages.size(); index++) {
            Message message = messages.get(index);
            Struct struct = findStruct(message.prefixPhoneNumber);
            boolean isValidMessage = isValidTime(message) && isValidContent(message, struct)
                    && isIntervalsOfTime(message, index);
            if(!isValidMessage){
                messages.remove(index);
                index--;
            }
        }
    }

    private static Struct findStruct(String prefixPhoneNumber) {
        for(Struct struct : structs){
            if(struct.getNumber().equals(prefixPhoneNumber)) {
                return struct;
            }
        }
        return null;
    }

    private static boolean isValidTime(Message message) {
        Date date = null;
        try {
            SimpleDateFormat formatter =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            formatter.setLenient(false);
            date = formatter.parse(message.getTime());
        } catch (ParseException e) {
            return false;
        }
        if(date.before(new Date())) {
            return true;
        }
        return false;
    }

    private static boolean isValidContent(Message message, Struct struct) {
        String content = message.getContent();
        if(struct == null){
            return false;
        }
        for(String syntax : struct.getSyntax()){
            if(syntax.equalsIgnoreCase(content)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isIntervalsOfTime(Message message, int index)  {
        for(index = index - 1; index > 0; index--){
            Message messagePrevious = messages.get(index);
            boolean isSamePrefixPhoneNumberAndPhoneNumber = (message.phoneNumber.equals(messagePrevious.phoneNumber)) &&
                    (message.prefixPhoneNumber.equals(messagePrevious.prefixPhoneNumber));
            if(isSamePrefixPhoneNumberAndPhoneNumber){
                int numberDayOfMonth = 30;
                long numberOfDifferenceDay = calculateDifferenceDay(message, messagePrevious);
                if(numberOfDifferenceDay < numberDayOfMonth) {
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    private static long calculateDifferenceDay(Message message, Message messagePrevious) {
        SimpleDateFormat formatter =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date dateOfMessage = null;
        Date dateOfMessagePrevious = null;
        try {
            dateOfMessage = formatter.parse(message.getTime());
            dateOfMessagePrevious = formatter.parse(messagePrevious.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        long timeDifferenceMilliseconds = dateOfMessage.getTime() - dateOfMessagePrevious.getTime();
        return Math.abs(TimeUnit.DAYS.convert(timeDifferenceMilliseconds, TimeUnit.MILLISECONDS));
    }

    public static void writeToFile(String rootPath) throws IOException {
        Map<String, List<String>> mapMessage = filterMessageByPrefixPhoneNumber();
        for(String key : mapMessage.keySet()){
            List<String> messageList = mapMessage.get(key);
            String pathFile = rootPath + "/output/" + key + ".txt";
            File fileOutPut = new File(pathFile);
            if(!fileOutPut.exists()){
                fileOutPut.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(fileOutPut);
            for(String message : messageList){
                fileWriter.write(message);
                fileWriter.write("\n");
            }
            fileWriter.close();
        }
    }

    private static Map<String, List<String>> filterMessageByPrefixPhoneNumber() {
        Map<String, List<String>> mapMessage = new HashMap<>();
        for(Message message : messages){
            if(mapMessage.containsKey(message.prefixPhoneNumber)){
                List<String> messageList = mapMessage.get(message.prefixPhoneNumber);
                messageList.add(message.toString());
            } else {
                List<String> messageList = new ArrayList<>();
                messageList.add(message.toString());
                mapMessage.put(message.prefixPhoneNumber, messageList);
            }
        }
        return mapMessage;
    }

    private static void print() {
        System.out.println("Cac tin nhan hop le");
        for(Message message : messages){
            System.out.println(message);
        }

    }

}
