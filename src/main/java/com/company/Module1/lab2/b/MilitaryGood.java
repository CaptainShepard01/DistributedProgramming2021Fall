package com.company.Module1.lab2.b;

public class MilitaryGood {
    private int price;

    public MilitaryGood() {
        this.price = (int) (Math.random() * 100);
    }

    public int getPrice() {
        return price;
    }

    public void printGood(){
        System.out.println("Price: " + price);
    }
}
