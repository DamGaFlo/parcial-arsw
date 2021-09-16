package eci.arsw.covidanalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Camel Application
 */
public class CovidAnalyzerTool {

    private static List<Hilo> hilos;
    private static boolean pausa = false;
    private int threadNumber = 5;
    private static ResultAnalyzer resultAnalyzer;
    private TestReader testReader;
    private int amountOfFilesTotal;
    private static AtomicInteger amountOfFilesProcessed;

    public CovidAnalyzerTool() {
        resultAnalyzer = new ResultAnalyzer();
        testReader = new TestReader();
        amountOfFilesProcessed = new AtomicInteger();
    }

    public void processResultData() {
        amountOfFilesProcessed.set(0);
        List<File> resultFiles = getResultFileList();
        amountOfFilesTotal = resultFiles.size();
        List<List<File>> particion = new ArrayList<List<File>>();
        hilos = new ArrayList<Hilo>();
        for (int i = 0; i < threadNumber; i++) {
            particion.add(new ArrayList<File>());

        }
        int part = 0;

        for (File resultFile : resultFiles) {
            particion.get(part).add(resultFile);
            part = (part + 1) % 5;
            System.out.println(part);

            /*List<Result> results = testReader.readResultsFromFile(resultFile);
            for (Result result : results) {
                resultAnalyzer.addResult(result);
            }
            amountOfFilesProcessed.incrementAndGet();*/
        }
        for (int i = 0; i < threadNumber; i++) {
            hilos.add(new Hilo(particion.get(i)));
            hilos.get(i).start();
        }

    }

    public static void addResult(Result result) {
        resultAnalyzer.addResult(result);
    }

    public static int incrementAndGet() {
        return amountOfFilesProcessed.incrementAndGet();
    }

    private List<File> getResultFileList() {
        List<File> csvFiles = new ArrayList<>();
        try ( Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }

    public Set<Result> getPositivePeople() {
        return resultAnalyzer.listOfPositivePeople();
    }

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        CovidAnalyzerTool covidAnalyzerTool = new CovidAnalyzerTool();
        Thread processingThread = new Thread(() -> covidAnalyzerTool.processResultData());
        processingThread.start();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.contains("exit")) {
                break;
            }

            if (pausa) {
                pausa = false;
                for (Hilo h : hilos) {
                    h.reanudar();
                    
                }
                System.out.println("reanudado!!!!!!!");
            } else {
                pausa = true;
                for (Hilo h : hilos) {
                    h.pausa();
                }
                System.out.println("pausado!!!!!!!");
                String message = "Processed %d out of %d files.\nFound %d positive people:\n%s";
                Set<Result> positivePeople = covidAnalyzerTool.getPositivePeople();
                String affectedPeople = positivePeople.stream().map(Result::toString).reduce("", (s1, s2) -> s1 + "\n" + s2);
                message = String.format(message, covidAnalyzerTool.amountOfFilesProcessed.get(), covidAnalyzerTool.amountOfFilesTotal, positivePeople.size(), affectedPeople);
                System.out.println(message);
            }

        }
    }

}
