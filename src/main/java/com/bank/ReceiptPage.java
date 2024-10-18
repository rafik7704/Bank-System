package com.bank;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptPage extends JFrame {

    private User loggedInUser;

    public ReceiptPage(User user, String recipientName, double amount, String referenceID, Date date) {
        this.loggedInUser = user;

        setTitle("Receipt");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 255));

        ImageIcon success = new ImageIcon("success.png");
        Image successImage = success.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel successLabel = new JLabel(new ImageIcon(successImage));
        successLabel.setBounds(160, 30, 80, 80);
        add(successLabel);

        JLabel statusLabel = new JLabel("Payment successful");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBounds(0, 120, 400, 30);
        add(statusLabel);

        JLabel amountLabel = new JLabel("RM " + String.format("%.2f", amount));
        amountLabel.setFont(new Font("Arial", Font.BOLD, 24));
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        amountLabel.setBounds(0, 160, 400, 30);
        add(amountLabel);

        JLabel recipientLabel = new JLabel(recipientName);
        recipientLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        recipientLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recipientLabel.setBounds(0, 200, 400, 30);
        add(recipientLabel);

        JLabel referenceIDLabel = new JLabel("Reference ID: " + referenceID);
        referenceIDLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        referenceIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        referenceIDLabel.setBounds(0, 240, 400, 30);
        add(referenceIDLabel);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        JLabel dateLabel = new JLabel("Date & time: " + dateFormat.format(date));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateLabel.setBounds(0, 280, 400, 30);
        add(dateLabel);

        JLabel transactionTypeLabel = new JLabel("Transaction type: Transfer");
        transactionTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        transactionTypeLabel.setBounds(0, 320, 400, 30);
        add(transactionTypeLabel);

        JButton doneButton = new JButton("Done");
        doneButton.setBounds(125, 400, 150, 40);
        doneButton.setBackground(new Color(255, 223, 0));
        doneButton.setForeground(Color.BLACK);
        doneButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(doneButton);

        doneButton.addActionListener(e -> {
            dispose();
            new DashboardPage(loggedInUser);
        });

        setVisible(true);
    }
}
