package com.example.victory.balan_swing;

/**
 * Created by victory on 2017-05-24.
 */

public class ProVideoHist {
    private String proID;
    private String filePath;
    private String fileName;

    public ProVideoHist(String proID, String filePath, String fileName){
        this.proID = proID;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public String getProID() {
        return proID;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
