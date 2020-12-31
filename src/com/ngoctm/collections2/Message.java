package com.ngoctm.collections2;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Message implements Comparable<Message>{
    String phoneNumber;
    String time;
    String content;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        return time + "|" + phoneNumber + "|" + content;
    }

    @Override
    public int compareTo(Message message) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            return format.parse(this.getTime()).compareTo(format.parse(message.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
