package com.ngoctm.string;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringExcercise2 {
    String rootPath;
    List<SDRFile> sdrFiles = new ArrayList<>();
    Map<String, List<String>> phoneNumbersOfInput = new HashMap<>();
    Map<String, List<String>> phoneNumbersOfOutput = new HashMap<>();

    public static void main(String[] args) {

        StringExcercise2 stringExcercise2 = new StringExcercise2( "/home/ngoctm/SDR");
        try {
            stringExcercise2.runTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StringExcercise2(String rootPath){
        this.rootPath = rootPath;
    }

    public void runTest() throws IOException {
        loadListFile(rootPath);
        filterPhoneNumberByDate();
        comparePhoneNumbers(FileType.INPUT);
        comparePhoneNumbers(FileType.OUTPUT);
    }

    public void loadListFile(String path){
        File[] files = new File(path).listFiles();
        for(File file : files){
            if(file.isFile()){
                handingPathSDRFile(file.getPath());
            } else if(file.isDirectory()){
                loadListFile(file.getPath());
            }
        }
    }

    private void handingPathSDRFile(String path) {
        String[] parts = path.split("/");
        String indexFolderName = parts[parts.length - 3];
        SDRFile sdrFile = new SDRFile();
        boolean isValid = false;
        if(indexFolderName.equals("input")) {
            sdrFile.setFileType(FileType.INPUT);
            sdrFile.setPath(path);
            isValid = validPathFile(sdrFile);
        } else if(indexFolderName.equals("output")){
            sdrFile.setFileType(FileType.OUTPUT);
            sdrFile.setPath(path);
            isValid = validPathFile(sdrFile);
        }
        if(isValid) sdrFiles.add(sdrFile);

    }

    public boolean validPathFile(SDRFile sdrFile){
        String regexInput = ".*cdr_([0-9]{8})_8x56_[0-9].txt";
        String regexOutput = ".*/([0-9]{8})_8x56_[0-9].txt";
        String regex;
        if(sdrFile.getFileType() == FileType.INPUT){
            regex = regexInput;
        } else {
            regex = regexOutput;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sdrFile.getPath());
        boolean isMatcher = matcher.matches();
        if(isMatcher){
            sdrFile.setDate(matcher.group(1));
            return true;
        }
        return false;
    }

    public void filterPhoneNumberByDate() throws FileNotFoundException {
        for(SDRFile sdrFile : sdrFiles){
           if(sdrFile.getFileType() == FileType.INPUT){
               addSDRFileToMapByDate(sdrFile, phoneNumbersOfInput);
           } else {
               addSDRFileToMapByDate(sdrFile, phoneNumbersOfOutput);
           }
        }
    }

    public void addSDRFileToMapByDate(SDRFile sdrFile, Map<String, List<String>> mapPhoneNumber)
            throws FileNotFoundException {
        if(mapPhoneNumber.containsKey(sdrFile.getDate())){
            List<String> phoneNumbers = mapPhoneNumber.get(sdrFile.getDate());
            phoneNumbers.addAll(readPhoneNumbers(sdrFile));
        } else {
            mapPhoneNumber.put(sdrFile.getDate(), readPhoneNumbers(sdrFile));
        }
    }


    public List<String> readPhoneNumbers(SDRFile sdrFile) throws FileNotFoundException {
        List<String> phoneNumbers = new ArrayList<>();
        Scanner scanner = new Scanner(new File(sdrFile.getPath()));
        while (scanner.hasNextLine()){
            String phoneNumber = scanner.nextLine();
            if(validPhoneNumber(phoneNumber)){
                phoneNumbers.add(phoneNumber);
            }
        }
        scanner.close();
        return phoneNumbers;
    }

    public boolean validPhoneNumber(String phoneNumber){
        String regex = "849[0-9]{7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public void comparePhoneNumbers(FileType fileTypeNeedFilter) throws IOException {
        if(fileTypeNeedFilter == FileType.INPUT){  // input available but output not
            Map<String, List<String>> resultMap = compareMapPhoneNumbers(phoneNumbersOfInput, phoneNumbersOfOutput);
            writeToFile(resultMap, "input_co.txt");
        } else {
            Map<String, List<String>> resultMap = compareMapPhoneNumbers(phoneNumbersOfOutput, phoneNumbersOfInput);
            writeToFile(resultMap, "output_co.txt");
        }
    }

    public Map<String, List<String>> compareMapPhoneNumbers(Map<String, List<String>> mapAvailablePhoneNumber,
                                       Map<String, List<String>> mapNotAvailablePhoneNumber){
        Map<String, List<String>> resultMap = new HashMap<>();
        for (String key : mapNotAvailablePhoneNumber.keySet()){
            if(mapAvailablePhoneNumber.containsKey(key)){
                List<String> filterPhoneNumbers = filterDifferencePhoneNumbers(mapNotAvailablePhoneNumber.get(key),
                        mapAvailablePhoneNumber.get(key));
                resultMap.put(key, filterPhoneNumbers);
            }
        }
        return resultMap;
    }

    public List<String> filterDifferencePhoneNumbers(List<String> noAvailable, List<String> available){
        HashSet<String> results = new HashSet<>();
        for(String phoneNumber : available){
            if(!noAvailable.contains(phoneNumber)){
                results.add(phoneNumber);
            }
        }
        return new ArrayList<>(results);
    }

    private void writeToFile(Map<String, List<String>> content, String fileName) throws IOException {
        String pathFileOutput = rootPath + "/" + fileName;
        File fileOutput = new File(pathFileOutput);
        StringBuilder contentBuilder = new StringBuilder();
        for(String key : content.keySet()){
            List<String> lines = content.get(key);
            contentBuilder.append("Ngay " + key + "\n");
            for(String line : lines){
                contentBuilder.append(line + "\n");
            }
        }
        if (!fileOutput.exists()) {
            fileOutput.createNewFile();
        }
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(fileOutput);
        fileWriter.write(contentBuilder.toString());
        fileWriter.close();

    }


}
