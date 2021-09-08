package com.company.lab1.b;

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
        this.slider.setValue(value);
    }

    @Override
    public String toString() {
        return "SharedValue{" +
                "value=" + slider.getValue() +
                '}';
    }
}
