/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.arsw.covidanalyzer;

import java.io.File;
import java.util.List;

/**
 *
 * @author Home
 */
public class Hilo extends Thread{

    private ResultAnalyzer resultAnalyzer;
    private TestReader testReader;
    List<File> resultFiles;
    private boolean pausa;
    private Object key;

    public Hilo(List<File> resultFiles) {
        resultAnalyzer = new ResultAnalyzer();
        testReader = new TestReader();
        this.resultFiles = resultFiles;
        pausa = false;
        key =1;

    }

    public void run() {
        for (File resultFile : resultFiles) {
            List<Result> results = testReader.readResultsFromFile(resultFile);
            for (Result result : results) {
                CovidAnalyzerTool.addResult(result);
            }
            CovidAnalyzerTool.incrementAndGet();

            if (pausa) {
                synchronized (key) {
                    try {
                        key.wait();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void pausa() {
        pausa = true;

    }

    public void reanudar() {
        pausa = false;
        synchronized (key) {
            key.notify();
        }
        ;
    }

}
