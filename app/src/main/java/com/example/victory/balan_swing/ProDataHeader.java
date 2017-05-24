package com.example.victory.balan_swing;

/**
 * Created by victory on 2017-05-24.
 */

public class ProDataHeader {
    private String proID;
    private String proName;
    private int clubNumber;

    public ProDataHeader(String proID, String proName, int clubNumber){
        this.proID = proID;
        this.proName = proName;
        this.clubNumber = clubNumber;
    }

    public String getProID() {
        return proID;
    }

    public String getProName() {
        return proName;
    }

    public int getClubNumber() {
        return clubNumber;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public void setClubNumber(int clubNumber) {
        this.clubNumber = clubNumber;
    }
}
