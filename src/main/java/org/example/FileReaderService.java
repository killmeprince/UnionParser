package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
//reads line by line and check with isValid method
public class FileReaderService {

    public List<String> readValidLines(String path) throws Exception {
        List<String> validLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (isValid(line)) {
                    validLines.add(line);
                }
            }
        }
        return validLines;
    }

    private boolean isValid(String line) {
        if (line == null || line.isBlank()) return false;
        String[] tokens = line.split(";", -1);
        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) continue;
            int firstQuote = token.indexOf('"');
            if (firstQuote >= 0) {

                if (!(token.startsWith("\"") && token.endsWith("\"") && token.length() >= 2)) {
                    return false;
                }
                if (token.substring(1, token.length()-1).contains("\"")) {
                    return false;
                }
            }
        }
        return true;
    }
}
