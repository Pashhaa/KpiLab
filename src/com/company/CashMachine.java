package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CashMachine {
    private Buyer buyer = new Buyer();
    private int total;
    private String ware;
    private List<String> wares = new ArrayList<>();
    private List<Integer> prices = new ArrayList<>();


    public void start() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("1.Розпочати роботу");
        int scan = sc.nextInt();
        boolean trigger = true;
        while (trigger) {
            System.out.println("1.Додати новий товар");
            System.out.println("2.Перейти до оплати");

            scan = sc.nextInt();
            switch (scan) 
            {
                case 1:

                    addware();
                    break;

                case 2:
                    if (this.wares.isEmpty()) 
                    {
                        System.out.println("В кошику выдсутні товари");
                    } else {
                        pay();
                        trigger = false;

                    }
                    break;
            }


        }
    }


    private void pay() 
    {
        System.out.println(wares);
        total = 0;
        for (int price : prices) {
            total += price;
        }
        System.out.println("До сплати: " + total + " грн");
        System.out.println("Оберіть спосіб оплати");
        System.out.println("1.Готівка");
        System.out.println("2.Банківська картка");
        System.out.println("3.Якщо вам не вистачає коштів - видаліть товар");
        Scanner scanner = new Scanner(System.in);
        int scan = scanner.nextInt();
        switch(scan){
            case 1:

                    cashPay();

                break;
            case 2:
                if(checkBalance()){
                    cardPay();
                }
                else{
                    removeChosenWaresbyCard();
                    cardPay();
                }

                break;
            case 3:
                removeChosenWaresbyCash();
                cashPay();
                break;

        }
    }
        private void addware() {
            System.out.println("Введіть назву товару");
            Scanner sc = new Scanner(System.in);
            this.ware = sc.nextLine();
            int price = 1+(int) (Math.random() * 100);
            System.out.println("Ціна товару: " + price + "грн");
            this.wares.add(this.ware);
            this.prices.add(price);

        }
    private void removeWare(int num){
         wares.remove(num);
         total-=prices.remove(num);

    }
    private void cashPay(){
        int cashSum=0;
        int varTotal=total;
        Scanner scanner= new Scanner(System.in);
        while(total>cashSum){
            System.out.println("Внесіть кошти");
            int cash=scanner.nextInt();
            varTotal-=cash;
            cashSum+=cash;

        }
        System.out.println("Ваша решта: " + (cashSum-total));
    }
    private void removeChosenWaresbyCash(){
        System.out.println("Оберіть номер товару, який бажаєте видалити");
        while(total>buyer.getCashbalance()) {
            for (int i = 0; i < wares.size(); i++) {
                System.out.println("№" + i + ":" + wares.get(i) + "-" + prices.get(i));
            }
            Scanner scanner = new Scanner(System.in);
            int numberOfWare = scanner.nextInt();
            removeWare(numberOfWare);
        }

    }
    private void removeChosenWaresbyCard(){
        System.out.println("На ващому рахнуку недостатньо коштів");
        System.out.println("Оберіть номер товару, який бажаєте видалити");
        while(total>buyer.getBankacc()) {
            for (int i = 0; i < wares.size(); i++) {
                System.out.println("№" + i + ":" + wares.get(i) + "-" + prices.get(i));
            }
            Scanner scanner = new Scanner(System.in);
            int numberOfWare = scanner.nextInt();
            removeWare(numberOfWare);
        }

    }
    private void cardPay(){
        buyer.setBankacc(buyer.getBankacc()-total);
        System.out.println(total+" списано з карти");
        System.out.println("Чи бажаєте ви надрукувати чек?");
        System.out.println("1.Так");
        System.out.println("2.Hi");
        Scanner scanner= new Scanner(System.in);
        int receipt=scanner.nextInt();
        switch (receipt){
            case 1:
                printReceipt();
                break;
            case 2:
                System.out.println("Вдалого дня, приходьте ще!");
        }
    }
    private boolean checkBalance(){
        if(buyer.getBankacc()>total){
            return true;
        }
        return false;
    }

    private void printReceipt(){
        System.out.println("\u001B[34m"+"СІЛЬПО"+"\u001B[0m");
        System.out.println("М. Київ");
        System.out.println("Касир: Білолипецький П.А.");
        System.out.println("Касир: Демидченко О.Р.");
        System.out.println("-----------------");
        for (int i = 0; i < wares.size(); i++) {
            System.out.println("№" + i + ":" + wares.get(i) + "             " + prices.get(i));
        }
        List<String> foreseight = new ArrayList<>();
        foreseight.add("Оптиміст у всьому бачить можливість");
        foreseight.add("Хваліть щиро");
        foreseight.add("Великі думки народжуються у серці");
        foreseight.add("Живіть у згоді з природою");
        System.out.println("-----------------");
        System.out.println("ПЕРЕДБАЧЕННЯ ДЛЯ ВАС:" + foreseight.get((int)(Math.random() * foreseight.size())));
        System.out.println("-----------------");
        System.out.println("Сума         "+ total);
        System.out.println("-----------------");
        System.out.println("ПДВ А 20%");
        System.out.println("-----------------");
        System.out.println(new Date().toString());
    }
}


