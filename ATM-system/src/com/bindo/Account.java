package com.bindo;

public class Account {
    private String cardID;
    private String userNAME;
    private char sex;
    private String passWord;
    private double money;
    private double limit;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getUserNAME() {
        return userNAME + (sex == '男' ? "先生" : "女士");
    }

    public void setUserNAME(String userNAME) {
        this.userNAME = userNAME;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
