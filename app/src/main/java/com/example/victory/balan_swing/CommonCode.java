package com.example.victory.balan_swing;

/**
 * Created by victory on 2017-05-24.
 */

public class CommonCode {
    private int divCode; // 구분코드
    private int taskCode; // 업무코드 01: 클럽드라이버, 07:7번아이언

    public CommonCode(int divCode, int taskCode){
        this.divCode = divCode;
        this.taskCode = taskCode;
    }

    public int getDivCode() {
        return divCode;
    }

    public int getTaskCode() {
        return taskCode;
    }

    public void setDivCode(int divCode) {
        this.divCode = divCode;
    }

    public void setTaskCode(int taskCode) {
        this.taskCode = taskCode;
    }
}
