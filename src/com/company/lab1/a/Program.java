package com.company.lab1.a;

import javax.swing.*;

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

    public synchronized void increment(int value) {
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

class MyTh implements Runnable {
    private final SharedValue sharedValue;
    private final int value;

    public MyTh(SharedValue sharedValue, int value) {
        this.sharedValue = sharedValue;
        this.value = value;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start " + value);

        boolean isInterrupted = false;
        while (!isInterrupted) {
            sharedValue.increment(value);
            try {
                Thread.sleep(0, 1);
            } catch (InterruptedException e) {
                isInterrupted = true;
            }
        }

        System.out.println(Thread.currentThread().getName() + " end ");
    }
}

public class Program {
    static Thread th1, th2;
    static JButton btn, stopBtn, th1Plus, th2Plus, th1Minus, th2Minus;
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
        textField.setText(" " + th1.getPriority() + " : " + th2.getPriority() + " ");
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

        btn = new JButton("Run");
        stopBtn = new JButton("Stop");
        stopBtn.setEnabled(false);
        slider = new JSlider();
        slider.setValue(50);
        slider.setMinimum(10);
        slider.setMaximum(90);

        th1Plus = new JButton("First +");
        th1Minus = new JButton("First -");
        th2Plus = new JButton("Second +");
        th2Minus = new JButton("Second -");
        priorities = new JTextField("       :       ");
        priorities.setEnabled(false);
        priorities.setHorizontalAlignment(SwingConstants.CENTER);

        th1Plus.setEnabled(false);
        th2Plus.setEnabled(false);
        th1Minus.setEnabled(false);
        th2Minus.setEnabled(false);

        SharedValue sliderData = new SharedValue(slider);
        btn.addActionListener(e -> {
            th1 = new Thread(new MyTh(sliderData, 1));
            th2 = new Thread(new MyTh(sliderData, -1));

            th1.setPriority(5);
            th2.setPriority(5);

            th1.start();
            th2.start();

            btn.setEnabled(false);
            stopBtn.setEnabled(true);

            th1Plus.setEnabled(true);
            th2Plus.setEnabled(true);
            th1Minus.setEnabled(true);
            th2Minus.setEnabled(true);

            printPriorities(priorities);
        });


        stopBtn.addActionListener(e -> {
            th1.interrupt();
            th2.interrupt();

            slider.setValue(50);

            btn.setEnabled(true);
            stopBtn.setEnabled(false);

            th1Plus.setEnabled(false);
            th2Plus.setEnabled(false);
            th1Minus.setEnabled(false);
            th2Minus.setEnabled(false);

            priorities.setText("      :      ");
        });

        th1Plus.addActionListener(e -> {
            changePriority(th1, true);
        });
        th1Minus.addActionListener(e -> {
            changePriority(th1, false);
        });

        th2Plus.addActionListener(e -> {
            changePriority(th2, true);
        });
        th2Minus.addActionListener(e -> {
            changePriority(th2, false);
        });

        linePanelTop.add(btn);
        linePanelTop.add(stopBtn);
        linePanelTop.add(slider);

        linePanelBottom.add(th1Plus);
        linePanelBottom.add(th1Minus);
        linePanelBottom.add(priorities);
        linePanelBottom.add(th2Plus);
        linePanelBottom.add(th2Minus);

        panel.add(linePanelTop);
        panel.add(linePanelBottom);

        return panel;
    }
}

