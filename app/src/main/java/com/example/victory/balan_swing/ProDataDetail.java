package com.example.victory.balan_swing;

/**
 * Created by victory on 2017-05-24.
 */

public class ProDataDetail {
    private String proID;
    private String detailSEQ;
    private int rightWeight;
    private int leftWeight;

    public ProDataDetail(String proID, String detailSEQ, int rightWeight, int leftWeight){
        this.proID = proID;
        this.detailSEQ = detailSEQ;
        this.rightWeight = rightWeight;
        this.leftWeight = leftWeight;
    }

    public String getProID() {
        return proID;
    }

    public String getDetailSEQ() {
        return detailSEQ;
    }

    public int getRightWeight() {
        return rightWeight;
    }

    public int getLeftWeight() {
        return leftWeight;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public void setDetailSEQ(String detailSEQ) {
        this.detailSEQ = detailSEQ;
    }

    public void setRightWeight(int rightWeight) {
        this.rightWeight = rightWeight;
    }

    public void setLeftWeight(int leftWeight) {
        this.leftWeight = leftWeight;
    }
}
