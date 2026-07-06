/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject4;

/**
 *
 * @author Sadia
 */
 import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CurrencyConverter extends JFrame {

    // ComboBoxes for currency selection
    private JComboBox<String> baseCurrencyBox;
    private JComboBox<String> targetCurrencyBox;
    
    // Text fields for input and output
    private JTextField amountInput;
    private JLabel resultDisplay;
    
    // HashMap to store exchange rates relative to a base currency (e.g., USD)
    private HashMap<String, Double> exchangeRates;

    public CurrencyConverter() {
        // Initialize exchange rates (Predefined rates relative to 1 USD)
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.92);
        exchangeRates.put("GBP", 0.79);
        exchangeRates.put("PKR", 278.50);
        exchangeRates.put("INR", 83.50);
        exchangeRates.put("JPY", 161.00);

        // Setup Frame Window
        setTitle("Currency Converter");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(76, 175, 80)); // Green theme inspired by the image
        JLabel headerLabel = new JLabel("CURRENCY CONVERTER");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Main Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // List of currencies for selection
        String[] currencies = {"USD", "EUR", "GBP", "PKR", "INR", "JPY"};

        // Base Currency Row
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("From (Base Currency):"), gbc);
        gbc.gridx = 1;
        baseCurrencyBox = new JComboBox<>(currencies);
        formPanel.add(baseCurrencyBox, gbc);

        // Target Currency Row
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("To (Target Currency):"), gbc);
        gbc.gridx = 1;
        targetCurrencyBox = new JComboBox<>(currencies);
        targetCurrencyBox.setSelectedItem("EUR"); // Default selection
        formPanel.add(targetCurrencyBox, gbc);

        // Amount Input Row
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Enter Amount:"), gbc);
        gbc.gridx = 1;
        amountInput = new JTextField();
        formPanel.add(amountInput, gbc);

        // Result Display Row
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        resultDisplay = new JLabel("Converted Amount: --", SwingConstants.CENTER);
        resultDisplay.setFont(new Font("Arial", Font.BOLD, 16));
        resultDisplay.setForeground(new Color(33, 33, 33));
        formPanel.add(resultDisplay, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Action Button Panel
        JPanel actionPanel = new JPanel();
        JButton convertButton = new JButton("Convert");
        convertButton.setFont(new Font("Arial", Font.BOLD, 14));
        convertButton.setBackground(new Color(76, 175, 80));
        convertButton.setForeground(Color.WHITE);
        convertButton.setFocusPainted(false);
        
        actionPanel.add(convertButton);
        add(actionPanel, BorderLayout.SOUTH);

        // Add Button Functionality (Event Listener)
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
    }

    private void performConversion() {
        String base = (String) baseCurrencyBox.getSelectedItem();
        String target = (String) targetCurrencyBox.getSelectedItem();
        String inputText = amountInput.getText().trim();

        // Input Validation
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an amount to convert.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(inputText);
            if (amount < 0) {
                JOptionPane.showMessageDialog(this, "Amount cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Conversion Logic using the predefined exchange rates
            // Formula: Amount in USD = amount / base_rate -> Amount in Target = USD_amount * target_rate
            double amountInUSD = amount / exchangeRates.get(base);
            double convertedAmount = amountInUSD * exchangeRates.get(target);

            // Display result formatted to 2 decimal places
            resultDisplay.setText(String.format("Converted Amount: %.2f %s", convertedAmount, target));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please enter numerical values only.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Run the GUI inside the Event Dispatch Thread (EDT) for thread-safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CurrencyConverter().setVisible(true);
            }
        });
    }
}