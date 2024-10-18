package com.bank;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardPage extends JFrame {

    private User loggedInUser;

    public DashboardPage(User user) {
        this.loggedInUser = user;

        setTitle("Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 255));

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFullName() + "!");
        welcomeLabel.setBounds(100, 50, 300, 30);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel);

        JLabel dateLabel = new JLabel("Date & Time: " + getCurrentDateTime());
        dateLabel.setBounds(100, 85, 300, 30);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(dateLabel);

        JLabel accountNumberLabel = new JLabel("Account Number: " + user.getAccountNumber());
        accountNumberLabel.setBounds(100, 130, 300, 30);
        accountNumberLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(accountNumberLabel);

        JLabel balanceLabel = new JLabel("Your Balance: RM " + user.getBalance());
        balanceLabel.setBounds(100, 170, 200, 30);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(balanceLabel);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(175, 250, 150, 40);
        transferButton.setBackground(new Color(255, 223, 0));
        transferButton.setForeground(Color.BLACK);
        transferButton.setFont(new Font("Arial", Font.BOLD, 15));
        add(transferButton);

        transferButton.addActionListener(e -> {
            new TransferPage(loggedInUser);
            dispose();
        });

        JButton exitButton = new JButton("Log Out");
        exitButton.setBounds(175, 300, 150, 40);
        exitButton.setBackground(new Color(255, 223, 0));
        exitButton.setForeground(Color.BLACK);
        exitButton.setFont(new Font("Arial", Font.BOLD, 15));
        add(exitButton);

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        setVisible(true);
    }

    private String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        return formatter.format(new Date());
    }
}
