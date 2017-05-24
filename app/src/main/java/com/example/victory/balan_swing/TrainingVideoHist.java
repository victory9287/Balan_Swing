package com.example.victory.balan_swing;

/**
 * Created by victory on 2017-05-24.
 */

public class TrainingVideoHist {
    private String trainingSEQ;
    private String filePath;
    private String fileName;

    public TrainingVideoHist(String trainingSEQ, String filePath, String fileName) {
        this.trainingSEQ = trainingSEQ;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public String getTrainingSEQ() {
        return trainingSEQ;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setTrainingSEQ(String trainingSEQ) {
        this.trainingSEQ = trainingSEQ;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
