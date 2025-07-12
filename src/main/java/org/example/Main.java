package org.example;
//app entry) command line ags check like c))
public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");
        if (args.length != 1) {
            System.out.println("No file");
            return;

        }
        String inputFilePath = args[0];
        long start = System.nanoTime();

        try {
            GroupProcessor processor = new GroupProcessor();
            processor.process(inputFilePath);
        } catch (Exception e) {
//            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }

        long end = System.nanoTime();
        System.out.printf("Execution time: %.2f seconds%n", (end - start) / 1e9);
    }

}