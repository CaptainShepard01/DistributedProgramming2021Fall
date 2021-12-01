package com.company.Module1.lab1.a;

import javax.swing.*;

public class SharedValue {
    private JSlider slider;

    public SharedValue(JSlider slider) {
        this.slider = slider;
    }

    public synchronized JSlider getValue() {
        return slider;
    }

    public synchronized void setValue(JSlider slider) {
        this.slider = slider;
    }

    public synchronized void modify(int value) {
        this.slider.setValue((value < 0) ? (Math.max(10, this.slider.getValue() + value)) : (Math.min(90, this.slider.getValue() + value)));
    }

    @Override
    public String toString() {
        return "SharedValue{" +
                "value=" + slider.getValue() +
                '}';
    }
}
