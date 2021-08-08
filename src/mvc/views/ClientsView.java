package mvc.views;

import javax.swing.*;
import mvc.db.DBConnection;
import mdlaf.animation.*;
import mdlaf.utils.MaterialColors;
import net.miginfocom.swing.MigLayout;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.models.Cliente;

public class ClientsView extends JFrame {

    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private Callable getTableData = null;

    public ClientsView(Callable func) {
        super("Actividad 2 - MVC con JDBC");
        this.con = DBConnection.getConnection();
        setMinimumSize(new Dimension(600, 400));
        this.getTableData = func;
    }

    public void onSelectedTableRow() {
        int idx = this.table.getSelectedRow();
        if (idx == -1) {
            return;
        }
        idUser = String.valueOf(tableModel.getValueAt(idx, 0));
        typeField.setText(String.valueOf(tableModel.getValueAt(idx, 1)));
        nameField.setText((String) tableModel.getValueAt(idx, 2));
        lastNameField.setText((String) tableModel.getValueAt(idx, 3));
        userIdField.setText((String) tableModel.getValueAt(idx, 4));
        passwordField.setText((String) tableModel.getValueAt(idx, 5));
        phoneNumberField.setText((String) tableModel.getValueAt(idx, 6));
        emailField.setText((String) tableModel.getValueAt(idx, 7));
        statusField.setText(String.valueOf(tableModel.getValueAt(idx, 8)));
        statusClientField.setText(String.valueOf(tableModel.getValueAt(idx, 9)));
    }

    public void initGUI() {

        formPanel.add(titleLabel, "center, wrap, span");
        String commonLabel = "gapy 20";
        String common = "wrap, push, span";
        formPanel.add(name, commonLabel);
        formPanel.add(nameField, common);
        formPanel.add(type, commonLabel);
        formPanel.add(typeField, common);
        formPanel.add(lastName, commonLabel);
        formPanel.add(lastNameField, common);
        formPanel.add(userId, commonLabel);
        formPanel.add(userIdField, common);
        formPanel.add(password, commonLabel);
        formPanel.add(passwordField, common);
        formPanel.add(phoneNumber, commonLabel);
        formPanel.add(phoneNumberField, common);
        formPanel.add(email, commonLabel);
        formPanel.add(emailField, common);
        formPanel.add(status, commonLabel);
        formPanel.add(statusField, common);
        formPanel.add(statusClient, commonLabel);
        formPanel.add(statusClientField, common);

        tableModel.addColumn("Id");
        tableModel.addColumn("Type");
        tableModel.addColumn("Name");
        tableModel.addColumn("LastName");
        tableModel.addColumn("UserId");
        tableModel.addColumn("Password");
        tableModel.addColumn("PhoneNumber");
        tableModel.addColumn("Email");
        tableModel.addColumn("Status");
        tableModel.addColumn("StatusClient");

        formPanel.add(new JScrollPane(table), "wrap, width 100%, growx, push, span, gapy 20");

        JPanel buttonPanel = new JPanel();

        addButton.setMaximumSize(new Dimension(200, 200));
        addButton.setBackground(MaterialColors.LIGHT_BLUE_400);
        addButton.setForeground(MaterialColors.WHITE);
        addButton.setOpaque(true);
        addButton.addMouseListener(MaterialUIMovement.getMovement(addButton, MaterialColors.LIGHT_BLUE_600));

        editButton.setMaximumSize(new Dimension(200, 200));
        editButton.setBackground(MaterialColors.AMBER_400);
        editButton.setForeground(MaterialColors.WHITE);
        editButton.setOpaque(true);
        editButton.addMouseListener(MaterialUIMovement.getMovement(editButton, MaterialColors.AMBER_600));

        deleteButton.setMaximumSize(new Dimension(200, 200));
        deleteButton.setBackground(MaterialColors.RED_400);
        deleteButton.setForeground(MaterialColors.WHITE);
        deleteButton.setOpaque(true);
        deleteButton.addMouseListener(MaterialUIMovement.getMovement(deleteButton, MaterialColors.RED_600));

        refreshButton.setMaximumSize(new Dimension(200, 200));

        //tambahkan button pada panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        formPanel.add(buttonPanel, "gapy 10, center, span");

        add(formPanel);
        try {
            this.getTableData.call();
        } catch (Exception ex) {
            Logger.getLogger(ClientsView.class.getName()).log(Level.SEVERE, null, ex);
        }

        pack();
        setVisible(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                onSelectedTableRow();
            }
        });

        addButton.addActionListener((ActionEvent e) -> {
            if ("Add".equals(addButton.getText())) {
                addButton.setText("Save");
                editButton.setText("Cancel");
                deleteButton.setEnabled(false);
                refreshButton.setEnabled(false);
                reset();
            } else {
                try {
                    Cliente.create(
                            typeField.getText(),
                            nameField.getText(),
                            lastNameField.getText(),
                            userIdField.getText(),
                            passwordField.getText(),
                            phoneNumberField.getText(),
                            emailField.getText(),
                            statusField.getText(),
                            statusClientField.getText()
                    );

                    JOptionPane.showMessageDialog(null, "Creado con exito");
                } catch (HeadlessException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error " + ex.getMessage());
                }
                addButton.setText("Add");
                editButton.setText("Edit");
                deleteButton.setEnabled(true);
                refreshButton.setEnabled(true);
                try {
                    this.getTableData.call();
                } catch (Exception ex) {
                    Logger.getLogger(ClientsView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        editButton.addActionListener((ActionEvent e) -> {
            if ("Edit".equals(editButton.getText())) {
                addButton.setText("Update");
                editButton.setText("Cancel");
                deleteButton.setEnabled(false);
                refreshButton.setEnabled(false);
            } else {
                addButton.setText("Add");
                editButton.setText("Edit");
                deleteButton.setEnabled(true);
                refreshButton.setEnabled(true);
            }
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            String sql = "DELETE FROM clients WHERE id = ?";
            try {
                try (PreparedStatement p2 = con.prepareStatement(sql)) {
                    p2.setString(1, idUser);
                    p2.executeUpdate();
                }
                this.getTableData.call();
                JOptionPane.showMessageDialog(null, "Datos eliminados con éxito");

            } catch (HeadlessException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Hay un error " + ex.getMessage());
            } catch (Exception ex) {
                Logger.getLogger(ClientsView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        refreshButton.addActionListener((ActionEvent e) -> {
            try {
                this.getTableData.call();
            } catch (Exception ex) {
                Logger.getLogger(ClientsView.class.getName()).log(Level.SEVERE, null, ex);
            }
            reset();
        });
    }

    private void reset() {
        idUser = "";
        type.setText("");
        name.setText("");
        lastName.setText("");
        userId.setText("");
        password.setText("");
        phoneNumber.setText("");
        email.setText("");
        status.setText("");
        statusClient.setText("");
    }

    private String idUser;
    private final JPanel formPanel = new JPanel(new MigLayout());
    private final JLabel titleLabel = new JLabel("MVC MYSQL");
    private final JLabel name = new JLabel("Name");
    private final JTextField nameField = new JTextField(50);
    private final JLabel type = new JLabel("Type");
    private final JTextField typeField = new JTextField(50);
    private final JLabel lastName = new JLabel("LastName");
    private final JTextField lastNameField = new JTextField(50);
    private final JLabel userId = new JLabel("UserId");
    private final JTextField userIdField = new JTextField(50);
    private final JLabel password = new JLabel("Password");
    private final JTextField passwordField = new JTextField(50);
    private final JLabel phoneNumber = new JLabel("PhoneNumber");
    private final JTextField phoneNumberField = new JTextField(50);
    private final JLabel email = new JLabel("Email");
    private final JTextField emailField = new JTextField(50);
    private final JLabel status = new JLabel("Status");
    private final JTextField statusField = new JTextField(50);
    private final JLabel statusClient = new JLabel("StatusClient");
    private final JTextField statusClientField = new JTextField(50);
    private final JButton addButton = new JButton("Add");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private final JButton refreshButton = new JButton("Refresh");
    public final DefaultTableModel tableModel = new DefaultTableModel();
    private final JTable table = new JTable(tableModel);
}
