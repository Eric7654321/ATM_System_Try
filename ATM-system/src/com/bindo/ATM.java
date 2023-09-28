package com.bindo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account> accounts = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    private Account loginAcc;

    //show start
    public void start(){
        while (true) {
            System.out.println("==歡迎進入ATM系統==");
            System.out.println("1.用戶登入");
            System.out.println("2.用戶開戶");
            System.out.println("請選擇:");

            int command = sc.nextInt();
            switch (command){
                case 1:
                    //用戶登入
                    login();
                    break;
                case 2:
                    //用戶開戶
                    createAccount();
                    break;
                default:
                    //亂打
                    System.out.println("沒有該操作，滾!");
            }
        }
    }
    private void login(){
        System.out.println("==系統登入==");

        if(accounts.size() == 0){
            System.out.println("你個白癡東西，沒有帳戶登入個屁");
            return;
        }

        while (true) {
            System.out.println("請輸入你的登入卡號");
            String cardID = sc.next();

            Account acc = getAccountByCardID(cardID);
            if(acc == null){
                System.out.println("你TM是來找碴的吧!");
            }
            else{
                while (true) {
                    System.out.println("請輸入密碼");
                    String passWord = sc.next();

                    if(acc.getPassWord().equals(passWord)){
                        loginAcc = acc;
                        System.out.println("恭喜你" + acc.getUserNAME() + "成功登入，你的卡號是" + acc.getCardID());
                        showUserCommand();
                        return;
                    }
                    else{
                        System.out.println("你這輩子還是不要來取錢了");
                    }
                }
            }
        }
    }

    private void showUserCommand(){
        while (true) {
            System.out.println(loginAcc.getUserNAME() + "你可以選擇以下功能進行帳戶處理");
            System.out.println("1.查詢帳戶");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.轉帳");
            System.out.println("5.密碼修改");
            System.out.println("6.退出");
            System.out.println("7.註銷當前帳戶");
            int command = sc.nextInt();

            switch(command){
                case 1:
                    showLoginAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    drawMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    updatePassWord();
                    return;
                case 6:
                    System.out.println(loginAcc.getUserNAME() + "恭喜你暫時逃離系統");
                    return;
                case 7:
                    if(deleteAccount()){
                        return;
                    }
                    break;
                default:
                    System.out.println("你看不懂指令是吧?那這樣子做應該可以給你一個深刻教訓");
                    loginAcc.setMoney(0);
                    System.out.println("已將您的帳戶金額扣除至零元~");
                    break;
            }
        }
    }

    private void updatePassWord() {
        System.out.println("==你準備要改密碼==");
        while (true) {
            System.out.println("請先輸入目前帳戶的密碼");
            String passWord = sc.next();

            if(loginAcc.getPassWord().equals(passWord)){
                while (true) {
                    System.out.println("請輸入新密碼");
                    String newPassWord = sc.next();
                    System.out.println("請再次輸入新密碼");
                    String okPassWord = sc.next();

                    if(okPassWord.equals(newPassWord)){
                        loginAcc.setPassWord(newPassWord);
                        System.out.println("你的密碼已修改成功~");
                        return;
                    }
                    else{
                        System.out.println("你輸入的兩次密碼不一致，是在哭");
                    }
                }
            }
            else{
                System.out.println("輸入錯誤~");
            }
        }
    }

    private boolean deleteAccount() {
        System.out.println("==正在銷戶作業==");
        System.out.println("你是認真要銷戶嗎?y/n");
        String command = sc.next();
        switch (command){
            case "y":

                if(loginAcc.getMoney() == 0){
                    accounts.remove(loginAcc);
                    System.out.println("你的帳戶掰了");
                    return true;
                }
                else{
                    System.out.println("你還有錢，人生看開一點，不要那麼悲觀");
                    return false;
                }

            default:
                System.out.println("你的帳戶已保留");
                return false;
        }
    }

    private void transferMoney() {
        System.out.println("用戶轉帳");

        if(accounts.size() < 2){
            System.out.println("可悲啊!只有你一個人開戶，你是不是沒朋友");
            return;
        }
        if(loginAcc.getMoney() == 0){
            System.out.println("你個窮鬼下個月都要吃土了還轉");
            return;
        }
        while (true) {
            System.out.println("你要轉給誰?");
            String cardID = sc.next();

            Account acc = getAccountByCardID(cardID);
            if(acc == null){
                System.out.println("沒這個人na~");
            }
            else{
                String name = "*" + acc.getUserNAME().substring(1);
                System.out.println("請輸入" + name + "的姓氏");
                String preName = sc.next();

                if(acc.getUserNAME().startsWith(preName)){
                    while (true) {
                        System.out.println("你要轉多少");
                        double money = sc.nextDouble();

                        if(loginAcc.getMoney() >= money){
                            loginAcc.setMoney(loginAcc.getMoney() - money);
                            acc.setMoney(acc.getMoney() + money);

                            return;

                        }
                        else{
                            System.out.println("你的餘額不足na，你只剩" + loginAcc.getMoney());
                        }
                    }
                }
                else{
                    System.out.println("錯了啦");
                }
            }
        }

    }

    private void drawMoney() {
        System.out.println("==你在取錢==");
        if(loginAcc.getMoney() < 100){
            System.out.println("你個窮鬼，帳戶連100都沒有");
            return;
        }
        while (true) {
            System.out.println("你要取多少???");
            double money = sc.nextDouble();

            if(money <= loginAcc.getMoney()){
                if(money > loginAcc.getLimit()){
                    System.out.println("你拿太多啦!你只能拿最多" + loginAcc.getLimit() + "元");
                }
                else{
                    loginAcc.setMoney(loginAcc.getMoney() - money);
                    System.out.println("你已取款" + money + "元，目前剩餘" + loginAcc.getMoney());
                    break;
                }
            }
            else{
                System.out.println("你不知道你有多少錢嗎?你只剩" + loginAcc.getMoney());
            }
        }
    }

    private void depositMoney() {
        System.out.println("==你在存錢==");
        System.out.println("你要存多少:");
        double money = sc.nextDouble();
        loginAcc.setMoney(loginAcc.getMoney() + money);
        System.out.println("你存了" + money + "元，目前餘額" + loginAcc.getMoney());
    }

    private void showLoginAccount(){
        System.out.println("==您的帳戶訊息如下==");
        System.out.println("卡號:" + loginAcc.getCardID());
        System.out.println("戶主:" + loginAcc.getUserNAME());
        System.out.println("性別:" + loginAcc.getSex());
        System.out.println("餘額:" + loginAcc.getMoney());
        System.out.println("每次取現額度:" + loginAcc.getLimit());

    }

    private void createAccount(){
        Account acc = new Account();

        System.out.println("請輸入你的帳戶名稱");
        String name = sc.next();
        acc.setUserNAME(name);

        while (true) {
            System.out.println("請輸入你的性別");
            char sex = sc.next().charAt(0);
            if(sex == '男' || sex == '女'){
                acc.setSex(sex);
                break;
            }
            else{
                System.out.println("你他媽是武裝直升機吧!");
            }
        }

        while (true) {
            System.out.println("請輸入你的密碼");
            String passWord = sc.next();
            System.out.println("請再次輸入你的密碼");
            String okPassWord = sc.next();
            if(okPassWord.equals(passWord)){
                acc.setPassWord(okPassWord);
                break;
            }
            else{
                System.out.println("你金魚腦啊!兩次密碼還能打不一樣");
            }
        }

        System.out.println("請輸入你的每次取現額度");
        double limit = sc.nextDouble();
        acc.setLimit(limit);

        String newCardID = createCardID();
        acc.setCardID(newCardID);

        accounts.add(acc);
        System.out.println("恭喜你" + acc.getUserNAME() + "開戶成功，你的卡號是:" + acc.getCardID());
    }
    private String createCardID(){
        while (true) {
            String cardID = "";
            Random r = new Random();

            for (int i = 0; i < 8; i++) {
                int data = r.nextInt(10);
                cardID += data;
            }
            Account acc = getAccountByCardID(cardID);
            if(acc == null){
                return cardID;
            }
        }
    }

    private Account getAccountByCardID(String cardID){
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);

            if(acc.getCardID().equals(cardID)){
                return acc;
            }
        }
        return null;//查無帳戶
    }
}
