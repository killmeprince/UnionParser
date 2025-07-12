package org.example;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
//writing groups to an output file
public class OutputWriter {
    public static void writeGroupsToFile(String outputPath, List<List<String>> groups) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(groups.size() + " groups with more than 1 element");

            int groupNumber = 1;
            for (List<String> group : groups) {
                writer.write("group " + groupNumber + "\n");
                for (String line : group) {
                    writer.write(line + "\n");
                }
                writer.write("\n");
                groupNumber++;
            }
        }
    }
}
