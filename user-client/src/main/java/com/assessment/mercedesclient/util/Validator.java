package com.assessment.mercedesclient.util;

import com.assessment.mercedesclient.exception.InputValidationException;
import com.assessment.mercedesclient.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static void validateName(String name){
        if(null==name && name.equals(""))
            throw new InputValidationException("Invalid name.");
    }

    public static void validateSalary(String salary){
        final String regex = "^[0-9]\\d*(\\.\\d+)?$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(salary);
        if(!m.find() && !m.group().equals(salary))
            throw new InputValidationException("Invalid salary.");
    }

    public static void validateDob(String dob){
        try{
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate inputDate = LocalDate.parse(dob, format);
            LocalDate date = LocalDate.of(1899, 12, 31);
            if( !(inputDate.isBefore(LocalDate.now().plusDays(1)) && inputDate.isAfter(date)) )
                throw new InputValidationException("Invalid Date.");
        }catch (Exception e){
            throw new InputValidationException("Invalid Date.");
        }

    }

    public static void validateAge(int age){
        if(! (0<age && 100>age) )
            throw new InputValidationException("Invalid age.");
    }

    public static void validateInput(User user,String fileType) {
        validateName(user.getName());
        validateDob(user.getDob());
        validateAge(user.getAge());
        validateSalary(String.valueOf(user.getSalary()));
        validateFileType(fileType);
    }

    private static void validateFileType(String fileType) {
        if( !("XML".equalsIgnoreCase(fileType) || "CSV".equalsIgnoreCase(fileType)) )
            throw new InputValidationException("Invalid file type. XML/CSV expected");
    }
}
