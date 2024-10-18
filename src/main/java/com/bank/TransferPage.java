package com.bank;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class TransferPage extends JFrame {

    private User loggedInUser;

    public TransferPage(User user) {
        this.loggedInUser = user;

        setTitle("Transfer");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 255));

        JLabel accountNumberLabel = new JLabel("Recipient Account Number:");
        accountNumberLabel.setBounds(100, 100, 200, 30);
        add(accountNumberLabel);

        JTextField accountNumberField = new JTextField();
        accountNumberField.setBounds(300, 100, 150, 30);
        add(accountNumberField);

        JLabel amountLabel = new JLabel("Amount to Transfer:");
        amountLabel.setBounds(100, 150, 200, 30);
        add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(300, 150, 150, 30);
        add(amountField);

        JButton transferButton = new JButton("Confirm");
        transferButton.setBounds(175, 250, 150, 40);
        transferButton.setBackground(new Color(255, 223, 0));
        transferButton.setForeground(Color.BLACK);
        transferButton.setFont(new Font("Arial", Font.BOLD, 15));
        add(transferButton);

        transferButton.addActionListener(e -> {
            String recipientAccount = accountNumberField.getText();
            String amountText = amountField.getText();

            try {
                double amount = Double.parseDouble(amountText);
                User recipient = getRecipient(recipientAccount);

                if (recipient != null && performTransfer(recipient, amount)) {
                    new ReceiptPage(loggedInUser, recipient.getFullName(), amount, generateReferenceID(), new Date());
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Transfer failed! Invalid account or insufficient balance.", "Transfer Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    public String generateReferenceID() {
        return String.valueOf((int) (Math.random() * 100000000));
    }

    private User getRecipient(String recipientAccount) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("users.json");
            JsonNode rootNode = mapper.readTree(file);
            JsonNode usersNode = rootNode.path("users");

            for (JsonNode userNode : usersNode) {
                String accountNumber = userNode.path("accountNumber").asText();
                if (accountNumber.equals(recipientAccount)) {
                    double recipientBalance = userNode.path("balance").asDouble();
                    return new User(
                            userNode.path("fullName").asText(),
                            recipientBalance,
                            accountNumber
                    );
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean performTransfer(User recipient, double amount) {
        if (amount > loggedInUser.getBalance()) {
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("users.json");
            JsonNode rootNode = mapper.readTree(file);
            JsonNode usersNode = rootNode.path("users");

            loggedInUser.setBalance(loggedInUser.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);

            for (JsonNode userNode : usersNode) {
                String accountNumber = userNode.path("accountNumber").asText();
                if (accountNumber.equals(loggedInUser.getAccountNumber())) {
                    ((ObjectNode) userNode).put("balance", loggedInUser.getBalance());
                } else if (accountNumber.equals(recipient.getAccountNumber())) {
                    ((ObjectNode) userNode).put("balance", recipient.getBalance());
                }
            }

            try (FileWriter fileWriter = new FileWriter(file)) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, rootNode);
            }

            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
