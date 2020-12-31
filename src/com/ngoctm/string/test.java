package com.ngoctm.string;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) throws FileNotFoundException, ParseException {

    }


    private static void deleteSpace(StringBuilder contentFile) {
        for(int positionOfChar = 0; positionOfChar < contentFile.length() - 1; positionOfChar++){
            System.out.println((int)contentFile.charAt(positionOfChar));
            boolean isSpaceExcess = (contentFile.charAt(positionOfChar) == ' ') && (contentFile.charAt(positionOfChar + 1) == ' ');
            if(isSpaceExcess){
                contentFile.deleteCharAt(positionOfChar + 1);
                positionOfChar--;
            }
        }

    }
    public void hihi(){
        List<String> s1 = new ArrayList<>();
        s1.add("1");
        s1.add("2");
        s1.add("3");
        List<String> s3 = new ArrayList<>();
        s3.add("1");
        s3.add("2");
        s3.add("4");
        s3.add("1");
        s3.add("5");

        Set<String> set = new HashSet<>(s1);
        for(String a : s3){
            set.add(a);
            System.out.println(set.size());
        }
    }
}
