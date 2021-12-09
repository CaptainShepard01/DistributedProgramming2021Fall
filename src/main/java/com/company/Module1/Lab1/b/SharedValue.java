package com.company.Module1.Lab1.b;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedValue {
    private JSlider slider;
    private volatile AtomicInteger semaphore;

    public SharedValue(JSlider slider, int value) {
        this.slider = slider;
        this.semaphore = new AtomicInteger(value);
    }

    public synchronized JSlider getValue() {
        return slider;
    }

    public synchronized AtomicInteger getSemaphore(){
        return semaphore;
    }

    public synchronized void setValue(JSlider slider) {
        this.slider = slider;
    }

    public synchronized void incrementSemaphore(){
        semaphore.getAndIncrement();
    }

    public synchronized void decrementSemaphore(){
        semaphore.getAndDecrement();
    }

    public synchronized void modify(int value) {
        this.slider.setValue(value);
    }

    @Override
    public String toString() {
        return "SharedValue{" +
                "value=" + slider.getValue() +
                '}';
    }
}
