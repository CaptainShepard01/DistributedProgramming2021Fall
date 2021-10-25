package com.company.lab5.a;

public class lab5a {
    public static void main(String[] args){
        Recruits recruits = new Recruits(160);
        recruits.printRecruits();

        ThreadManager manager = new ThreadManager(recruits, 50);
    }
}
