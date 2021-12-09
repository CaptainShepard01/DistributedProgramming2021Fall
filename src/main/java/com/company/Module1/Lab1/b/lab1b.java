package com.company.Module1.Lab1.b;

import javax.swing.*;
import java.awt.*;

public class lab1b {
    static Thread Thread1, Thread2;
    static JButton start1Btn, stop1Btn, start2Btn, stop2Btn;
    static JSlider slider;
    static JTextField info;
    static JPanel panel, linePanelTop, linePanelBottom, linePanelCentral;

    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(500, 400);

        JPanel panel = getjPanel();

        win.setContentPane(panel);
        win.setVisible(true);
    }

    private static void changePriority(Thread th, boolean isIncrement) {
        if (isIncrement) {
            if (th.getPriority() < 10)
                th.setPriority(th.getPriority() + 1);
        } else {
            if (th.getPriority() > 1)
                th.setPriority(th.getPriority() - 1);
        }
    }

    private static void startThread(SharedValue data, int threadNum) {
        if(data.getSemaphore().get() != 0){
            info.setText("Зайнято потоком!");
            return;
        }

        switch(threadNum){
            case 1:{
                Thread1 = new Thread(new CustomThread(data, 10));

                Thread1.setPriority(1);
                Thread1.setDaemon(true);

                Thread1.start();

                start1Btn.setEnabled(false);
                stop1Btn.setEnabled(true);
                stop2Btn.setEnabled(false);

                break;
            }
            case 2:{
                Thread2 = new Thread(new CustomThread(data, 90));

                Thread2.setPriority(10);
                Thread2.setDaemon(true);

                Thread2.start();

                start2Btn.setEnabled(false);
                stop2Btn.setEnabled(true);
                stop1Btn.setEnabled(false);

                break;
            }
        }
    }

    private static void stopThread(int threadNum){
        switch(threadNum){
            case 1:{
                Thread1.interrupt();
                start1Btn.setEnabled(true);
                stop1Btn.setEnabled(false);

                break;
            }
            case 2:{
                Thread2.interrupt();
                start2Btn.setEnabled(true);
                stop2Btn.setEnabled(false);

                break;
            }
        }

        info.setText("");
    }

    private static JPanel getjPanel() {
        panel = new JPanel();

        linePanelTop = new JPanel();
        linePanelCentral = new JPanel();

        start1Btn = new JButton("Пуск1");
        stop1Btn = new JButton("Стоп1");
        stop1Btn.setEnabled(false);

        start2Btn = new JButton("Пуск2");
        stop2Btn = new JButton("Стоп2");
        stop2Btn.setEnabled(false);

        slider = new JSlider();
        slider.setEnabled(false);
        slider.setValue(50);

        info = new JTextField(12);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setText("");
        info.setDisabledTextColor(Color.red);
        info.setEnabled(false);

        SharedValue sliderData = new SharedValue(slider, 0);
        start1Btn.addActionListener(e -> {
            startThread(sliderData, 1);
        });
        start2Btn.addActionListener(e -> {
            startThread(sliderData, 2);
        });

        stop1Btn.addActionListener(e -> {
            stopThread(1);
        });
        stop2Btn.addActionListener(e -> {
            stopThread(2);
        });


        linePanelTop.add(start1Btn);
        linePanelTop.add(stop1Btn);
        linePanelTop.add(start2Btn);
        linePanelTop.add(stop2Btn);

        linePanelCentral.add(slider);
        linePanelCentral.add(info);

        panel.add(linePanelTop);
        panel.add(linePanelCentral);

        return panel;
    }
}

