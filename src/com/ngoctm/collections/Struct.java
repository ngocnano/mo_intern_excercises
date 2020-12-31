package com.ngoctm.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

public class Struct {
    String number;
    List<String> syntax;

    public Struct(String number, List<String> syntax){
        this.number = number;
        this.syntax = syntax;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<String> getSyntax() {
        return syntax;
    }

    public void setSyntax(List<String> syntax) {
        this.syntax = syntax;
    }
}
