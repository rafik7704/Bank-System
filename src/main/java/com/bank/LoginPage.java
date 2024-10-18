package com.bank;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class LoginPage extends JFrame {

    public LoginPage() {
        setTitle("Maybank - Login");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(238, 238, 238));


        ImageIcon originalLogo = new ImageIcon("maybank-logo.png");
        Image scaledImage = originalLogo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedLogo = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(resizedLogo);
        logoLabel.setBounds(100, 20, 150, 150);
        add(logoLabel);


        JLabel titleLabel = new JLabel("Maybank");
        titleLabel.setBounds(80, 180, 200, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 230, 100, 30);
        add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 230, 150, 30);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 280, 100, 30);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 280, 150, 30);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 350, 150, 40);
        loginButton.setBackground(new Color(255, 223, 0));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User user = validateLogin(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(null, "Login successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
                    new DashboardPage(user);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials!", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private User validateLogin(String username, String password) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(new File("users.json"));
            JsonNode usersNode = rootNode.path("users");

            for (JsonNode userNode : usersNode) {
                String storedUsername = userNode.path("username").asText();
                String storedPassword = userNode.path("password").asText();
                String fullName = userNode.path("fullName").asText();
                String accountNumber = userNode.path("accountNumber").asText();
                double balance = userNode.path("balance").asDouble();

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    return new User(fullName, balance,accountNumber);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
