package com.company.Module1.lab1.a;

import javax.swing.*;
import java.awt.*;

public class lab1a {
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
        textField.setText(Thread1.getPriority() + " : " + Thread2.getPriority());
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

        startBtn = new JButton("Пуск");
        stopBtn = new JButton("Стоп");
        stopBtn.setEnabled(false);
        slider = new JSlider();
        slider.setEnabled(false);
        slider.setValue(50);

        Thread1Plus = new JButton("Перший +");
        Thread1Minus = new JButton("Перший -");
        Thread2Plus = new JButton("Другий +");
        Thread2Minus = new JButton("Другий -");
        priorities = new JTextField(5);
        priorities.setText(":");
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

            Thread1.setDaemon(true);
            Thread2.setDaemon(true);

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

            priorities.setText(":");
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

