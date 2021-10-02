package com.assessment.mercedesservice.handler;

import com.assessment.mercedesservice.model.User;
import com.assessment.mercedesservice.util.Constants;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CSVHandler {
    private FileWriter fileWriter;
    private CSVWriter csvWriter;
    private Reader reader;

    public boolean writeUser(String filePath, User user) {
        try {
            initializeWrite(filePath, true);
            String[] userDetails = getUsers(user);
            csvWriter.writeNext(userDetails);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (null != csvWriter)
                    csvWriter.close();
                if (null != filePath)
                    fileWriter.close();
            } catch (IOException e) {
                return false;
            }
        }
    }

    private String[] getUsers(User user) {
        return (user.getUuid() + "," +
                user.getName() + "," + user.getDob() + "," + user.getSalary() + "," +
                user.getAge()).split(",");
    }

    public boolean updateUser(String filePath, String key, User updateDetails) {
        try {
            CsvToBean<User> users = getCsvToBean(filePath);
            List<User> collect = users.stream().collect(Collectors.toList());
            if (0 == collect.size()) {
                return false;
            }
            List<User> updatedList = updateDetails(collect, updateDetails, key);//
            initializeWrite(filePath, false);
            if (0 < updatedList.size()) {
                for (User user : updatedList) {
                    String[] userDetails = getUsers(user);
                    csvWriter.writeNext(userDetails);
                }
            }
            if (0 < updatedList.stream().filter(user -> user.getUuid().equals(key))
                    .collect(Collectors.toList()).size())
                return true;
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (null != reader)
                    reader.close();
                if (null != csvWriter) {
                    csvWriter.flush();
                    csvWriter.close();
                }
                if (null != fileWriter)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<User> updateDetails(List<User> users, User updateDetails, String key) {
        Function<User, User> modifier = (user) -> {
            if (key.equals(user.getUuid())) {
                updateDetails.setUuid(key);
                return updateDetails;
            }
            return user;
        };
        return users.stream().map(modifier).collect(Collectors.toList());
    }

    public List<User> readUser(String filePath, String input) {
        try {
            CsvToBean<User> users = getCsvToBean(filePath);
            List<User> collect = users.stream().collect(Collectors.toList());
            return getFilterList(collect, input);
        } catch (IOException e) {
            return new ArrayList<>();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    return new ArrayList<>();
                }
            }
        }
    }

    private List<User> getFilterList(List<User> list, String request) {
        if ("All".equalsIgnoreCase(request))
            return (List<User>) list.stream().collect(Collectors.toList());
        return (List<User>) list.stream()
                .filter(x -> ((User) x).getUuid().equalsIgnoreCase(request))
                .collect(Collectors.toList());
    }

    private CsvToBean<User> getCsvToBean(String filePath) throws IOException {
        final File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        reader = Files.newBufferedReader(Paths.get(filePath));
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(User.class);
        String[] memberFieldsToBindTo = {Constants.UUID, Constants.NAME, Constants.DOB,
                Constants.SALARY, Constants.AGE};
        strategy.setColumnMapping(memberFieldsToBindTo);
        return new CsvToBeanBuilder<User>(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
    }

    private void initializeWrite(String filePath, boolean isWrite) throws IOException {
        File file = new File(filePath);
        if (!file.exists())
            file.createNewFile();
        fileWriter = new FileWriter(file, isWrite);
        csvWriter = new CSVWriter(fileWriter,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        if (file.length() == 0) {
            String[] headerRecord = {"Id", "Name", "DOB", "Salary", "Age"};
            csvWriter.writeNext(headerRecord);
        }
    }
}
