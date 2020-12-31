package com.ngoctm.string;

import java.util.ArrayList;
import java.util.List;

public class SDRFile {
    private String path;
    private FileType fileType;
    private String date;
    private List<String> phoneNumbers;

    public SDRFile(){

    }

    public SDRFile(String path, FileType fileType){
        this.path = path;
        this.fileType = fileType;
    }

    public List<String> getPhoneNumbers(){
        return this.phoneNumbers;
    }

    public String getPath(){
        return this.path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addPhoneNumber(String phoneNumber){
        if(phoneNumber == null){
            this.phoneNumbers = new ArrayList<String>();
        }
        this.phoneNumbers.add(phoneNumber);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
