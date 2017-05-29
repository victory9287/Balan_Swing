package com.example.victory.balan_swing;

/**
 * Created by victory on 2017-05-24.
 */

public class TrainingHeader {
    private String trainingSEQ; // T+년월일시분초 ex)T20170320112125
    private int trainingFLG; // 0: 일반연습, 1:반복연습
    private String trainingYMD; // 트레이닝 일자 ex)20170320
    private String trainingTime; // 트레이닝 시간 ex)180105
    private int clubNumber; // 클럽 종류

    public TrainingHeader(){}
    public TrainingHeader(String trainingSeq, int trainingFlg, String trainingYMD, String trainingTime, int clubNumber){
        this.trainingSEQ = trainingSeq;
        this.trainingFLG = trainingFlg;
        this.trainingYMD = trainingYMD;
        this.trainingTime = trainingTime;
        this.clubNumber = clubNumber;
    }

    public String getTrainingSEQ() {
        return trainingSEQ;
    }

    public int getTrainingFLG() {
        return trainingFLG;
    }

    public String getTrainingYMD() {
        return trainingYMD;
    }

    public String getTrainingTime() {
        return trainingTime;
    }

    public int getClubNumber() {
        return clubNumber;
    }

    public void setTrainingSEQ(String trainingSeq) {
        this.trainingSEQ = trainingSeq;
    }

    public void setTrainingFLG(int trainingFlg) {
        this.trainingFLG = trainingFlg;
    }

    public void setTrainingYMD(String trainingYMD) {
        this.trainingYMD = trainingYMD;
    }

    public void setTrainingTime(String trainingTime) {
        this.trainingTime = trainingTime;
    }

    public void setClubNumber(int clubNumber) {
        this.clubNumber = clubNumber;
    }
}
