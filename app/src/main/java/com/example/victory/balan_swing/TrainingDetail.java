package com.example.victory.balan_swing;

/**
 * Created by victory on 2017-05-24.
 */

public class TrainingDetail {
    private String trainingSEQ; // key
    private int detailSEQ; // 상세 이력 순번 (1부터 순차적으로)
    private String REGTime; // 밀리세컨드까지등록, 시작시간 및 끝시간 체크
    private int[] rightWeight; // 우측 중량
    private int[] leftWeight; // 좌측 중량

    public TrainingDetail(String trainingSEQ, int detailSEQ, String REGTime, int[] rightWeight, int[] leftWeight){
        this.trainingSEQ = trainingSEQ;
        this.detailSEQ = detailSEQ;
        this.REGTime = REGTime;
        this.rightWeight = rightWeight;
        this.leftWeight = leftWeight;
    }

    public String getTrainingSEQ() {
        return trainingSEQ;
    }

    public int getDetailSEQ() {
        return detailSEQ;
    }

    public String getREGTime() {
        return REGTime;
    }

    public int[] getRightWeight() {
        return rightWeight;
    }

    public int[] getLeftWeight() {
        return leftWeight;
    }

    public void setTrainingSEQ(String trainingSEQ) {
        this.trainingSEQ = trainingSEQ;
    }

    public void setDetailSEQ(int detailSEQ) {
        this.detailSEQ = detailSEQ;
    }

    public void setREGTime(String REGTime) {
        this.REGTime = REGTime;
    }

    public void setRightWeight(int[] rightWeight) {
        this.rightWeight = rightWeight;
    }

    public void setLeftWeight(int[] leftWeight) {
        this.leftWeight = leftWeight;
    }
}
