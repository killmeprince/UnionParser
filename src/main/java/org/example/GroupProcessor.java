package org.example;

import java.util.*;

//business logic class , reads valid lines from file via FRService, than keeps only unique lines, initializes UnionFind for all unique lines.
//builds a map fo value for every column -> first index and merges lines the same value in the same column;
//collect final groups.
//filters groups with more than one element;
//calls OutputWriter to write results to file.
public class GroupProcessor {

    public void process(String inputFilePath) throws Exception {
        FileReaderService fileReader = new FileReaderService();
        List<String> lines = fileReader.readValidLines(inputFilePath);

        Set<String> uniqueLinesSet = new LinkedHashSet<>(lines);
        List<String> uniqueLines = new ArrayList<>(uniqueLinesSet);
        lines = uniqueLines;

        int n = lines.size();
        if (n == 0) {
            System.out.println("No valid lines found.");
            return;
        }
        UnionFind uf = new UnionFind(n);

        int maxColumns = 0;
        for (String line : lines) {
            int cols = line.split(";", -1).length;
            if (cols > maxColumns) maxColumns = cols;
        }

        for (int col = 0; col < maxColumns; col++) {
            Map<String, Integer> tokenToFirstIdx = new HashMap<>();
            for (int i = 0; i < n; i++) {
                String[] tokens = lines.get(i).split(";", -1);
                if (col >= tokens.length) continue;
                String token = tokens[col].trim();
                if (token.startsWith("\"") && token.endsWith("\"") && token.length() >= 2) {
                    token = token.substring(1, token.length() - 1);
                }
                if (!token.isEmpty()) {
                    Integer prevIdx = tokenToFirstIdx.putIfAbsent(token, i);
                    if (prevIdx != null) {
                        uf.union(prevIdx, i);
                    }
                }
            }
            tokenToFirstIdx.clear();
        }

        Map<Integer, List<Integer>> groups = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            groups.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
        }

        List<List<String>> outputGroups = new ArrayList<>();
        for (List<Integer> groupIndexes : groups.values()) {
            if (groupIndexes.size() > 1) {
                List<String> groupLines = new ArrayList<>();
                for (int idx : groupIndexes) {
                    groupLines.add(lines.get(idx));
                }
                outputGroups.add(groupLines);
            }
        }
        outputGroups.sort((a, b) -> Integer.compare(b.size(), a.size()));
        System.out.println(outputGroups.size() + " groups with more than 1 element");
        OutputWriter.writeGroupsToFile("groups.txt", outputGroups);
    }
}
