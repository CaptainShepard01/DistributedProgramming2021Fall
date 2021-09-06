package com.company.lab1.a;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Program {
    static Thread th;

    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(500, 400);

        JPanel panel = getjPanel();

        win.setContentPane(panel);
        win.setVisible(true);
    }

    private static JPanel getjPanel() {
        JPanel panel = new JPanel();
        JTextField text = new JTextField("                    ");
        JButton btn = new JButton("Ok");
        JSlider slider = new JSlider();
        btn.addActionListener(e -> {
            th = new Thread(
                    () -> {
                        for (int i = 0; i < 100; i++) {
                            text.setText(th.getPriority() + " : " + i);
                            slider.setValue(100 - i);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    });
            th.setPriority(Thread.NORM_PRIORITY);
            th.start();
        });

        JButton bPlus = new JButton("+");
        bPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (th.getPriority() < 10)
                    th.setPriority(th.getPriority() + 1);
            }
        });
        JButton bMinus = new JButton("-");
        bMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (th.getPriority() > 1)
                    th.setPriority(th.getPriority() - 1);
            }
        });
        panel.add(bMinus);
        panel.add(bPlus);
        panel.add(btn);
        panel.add(text);
        panel.add(slider);
        return panel;
    }
}

