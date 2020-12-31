package com.ngoctm.collections;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Message implements Comparable<Message>{
    String phoneNumber;
    String prefixPhoneNumber;
    String time;
    String content;

    public Message(){

    }

    public Message(String phoneNumber, String prefixPhoneNumber, String time, String content) {
        this.phoneNumber = phoneNumber;
        this.prefixPhoneNumber = prefixPhoneNumber;
        this.time = time;
        this.content = content;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPrefixPhoneNumber() {
        return prefixPhoneNumber;
    }

    public void setPrefixPhoneNumber(String prefixPhoneNumber) {
        this.prefixPhoneNumber = prefixPhoneNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        return phoneNumber + "(" + content + "|" + time + "|" + prefixPhoneNumber + ")";
    }

    @Override
    public int compareTo(Message message) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            return format.parse(this.getTime()).compareTo(format.parse(message.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
