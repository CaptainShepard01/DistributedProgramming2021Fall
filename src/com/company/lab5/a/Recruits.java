package com.company.lab5.a;

import java.util.Random;

public class Recruits {
    private int numberOfRecruits;
    //false = to the left, true = to the right
    private Boolean[] recruits;
    private Boolean[] previousState;

    public Recruits(int numberOfRecruits) {
        this.numberOfRecruits = numberOfRecruits;
        this.recruits = new Boolean[numberOfRecruits];
        initialStateOfRecruits();
    }

    public void setPreviousState(Boolean[] previousState) {
        this.previousState = recruits;
    }

    public void initialStateOfRecruits() {
        Random random = new Random();
        for (int i = 0; i < numberOfRecruits; ++i) {
            recruits[i] = random.nextBoolean();
        }
    }

    public void printRecruits() {
        System.out.print("Recruits current state:\n[ ");
        for (int i = 0; i < numberOfRecruits; ++i) {
            System.out.print(recruits[i] ? ">" : "<");
        }
        System.out.print(" ]");
    }

    //End index excluding
    public void partFixing(int begin, int end) {
        for (int i = begin; i < end - 1; ++i) {
            if (recruits[i] && !recruits[i + 1]) {
                recruits[i] = false;
                recruits[i + 1] = true;
            }
        }
        if (end != recruits.length) {
            if (recruits[end - 1] && !recruits[end]) {
                recruits[end - 1] = false;
                recruits[end] = true;
            }
        }
    }

    public synchronized boolean isStationary() {
        if (recruits == previousState) {
            return true;
        }
        return false;
    }
}
