package com.company.lab1.a;

import javax.swing.*;
import java.awt.*;

class SharedValue {
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
        this.slider.setValue(this.slider.getValue() + value);
        //System.out.println(Thread.currentThread().getName() + " : " + this.slider.getValue());
    }

    @Override
    public String toString() {
        return "SharedValue{" +
                "value=" + slider.getValue() +
                '}';
    }
}

class CustomThread implements Runnable {
    private final SharedValue sharedValue;
    private final int value;

    public CustomThread(SharedValue sharedValue, int value) {
        this.sharedValue = sharedValue;
        this.value = value;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start " + value);

        boolean isInterrupted = false;
        while (!isInterrupted) {
            sharedValue.modify(value);
            try {
                Thread.sleep(0, 2);
            } catch (InterruptedException e) {
                isInterrupted = true;
            }
        }

        System.out.println(Thread.currentThread().getName() + " end ");
    }
}

public class Program {
    static Thread Thread1, Thread2;
    static JButton startBtn, stopBtn, Thread1Plus, Thread2Plus, Thread1Minus, Thread2Minus;
    static JSlider slider;
    static JTextField priorities;
    static JPanel panel, linePanelTop, linePanelBottom;

    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(500, 400);

        JPanel panel = getjPanel();

        win.setContentPane(panel);
        win.setVisible(true);
    }

    private static void printPriorities(JTextField textField) {
        textField.setText(" " + Thread1.getPriority() + " : " + Thread2.getPriority() + " ");
    }

    private static void changePriority(Thread th, boolean isIncrement) {
        if (isIncrement) {
            if (th.getPriority() < 10)
                th.setPriority(th.getPriority() + 1);
        } else {
            if (th.getPriority() > 1)
                th.setPriority(th.getPriority() - 1);
        }
        printPriorities(priorities);
    }

    private static JPanel getjPanel() {
        panel = new JPanel();

        linePanelTop = new JPanel();
        linePanelBottom = new JPanel();

        startBtn = new JButton("Start");
        stopBtn = new JButton("Stop");
        stopBtn.setEnabled(false);
        slider = new JSlider();
        slider.setEnabled(false);
        slider.setValue(50);
        slider.setMinimum(10);
        slider.setMaximum(90);

        Thread1Plus = new JButton("First +");
        Thread1Minus = new JButton("First -");
        Thread2Plus = new JButton("Second +");
        Thread2Minus = new JButton("Second -");
        priorities = new JTextField("         :         ");
        priorities.setEnabled(false);
        priorities.setDisabledTextColor(Color.black);
        priorities.setHorizontalAlignment(SwingConstants.CENTER);

        Thread1Plus.setEnabled(false);
        Thread2Plus.setEnabled(false);
        Thread1Minus.setEnabled(false);
        Thread2Minus.setEnabled(false);

        SharedValue sliderData = new SharedValue(slider);
        startBtn.addActionListener(e -> {
            Thread1 = new Thread(new CustomThread(sliderData, -1));
            Thread2 = new Thread(new CustomThread(sliderData, 1));

            Thread1.setPriority(5);
            Thread2.setPriority(5);

            Thread1.start();
            Thread2.start();

            startBtn.setEnabled(false);
            stopBtn.setEnabled(true);

            Thread1Plus.setEnabled(true);
            Thread2Plus.setEnabled(true);
            Thread1Minus.setEnabled(true);
            Thread2Minus.setEnabled(true);

            printPriorities(priorities);
        });


        stopBtn.addActionListener(e -> {
            Thread1.interrupt();
            Thread2.interrupt();

            slider.setValue(50);

            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);

            Thread1Plus.setEnabled(false);
            Thread2Plus.setEnabled(false);
            Thread1Minus.setEnabled(false);
            Thread2Minus.setEnabled(false);

            priorities = new JTextField("         :         ");
        });

        Thread1Plus.addActionListener(e -> {
            changePriority(Thread1, true);
        });
        Thread1Minus.addActionListener(e -> {
            changePriority(Thread1, false);
        });

        Thread2Plus.addActionListener(e -> {
            changePriority(Thread2, true);
        });
        Thread2Minus.addActionListener(e -> {
            changePriority(Thread2, false);
        });

        linePanelTop.add(startBtn);
        linePanelTop.add(stopBtn);
        linePanelTop.add(slider);

        linePanelBottom.add(Thread1Plus);
        linePanelBottom.add(Thread1Minus);
        linePanelBottom.add(priorities);
        linePanelBottom.add(Thread2Plus);
        linePanelBottom.add(Thread2Minus);

        panel.add(linePanelTop);
        panel.add(linePanelBottom);

        return panel;
    }
}

