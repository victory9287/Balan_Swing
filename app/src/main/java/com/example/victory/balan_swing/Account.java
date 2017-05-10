package com.example.victory.balan_swing;

/**
 * Created by victory on 2017-05-08.
 */

public class Account {
    private String m_id, m_name;
    private int m_gender; // 0 : 여, 1 : 남

    public Account(){}
    public Account(String id, String name, int gender){
        m_id = id;
        m_name = name;
        m_gender = gender;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public void setM_gender(int m_gender) {
        this.m_gender = m_gender;
    }

    public String getM_Id() {
        return m_id;
    }

    public String getM_name() {
        return m_name;
    }

    public int getM_gender(){
        return m_gender;
    }

}
