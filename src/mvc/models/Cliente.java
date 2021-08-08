package mvc.models;

// CReate class Cliente
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mvc.db.DBConnection;

public class Cliente {

    private int id;
    private int type;
    private String name;
    private String lastName;
    private String userId;
    private String password;
    private String phoneNumber;
    private String email;
    private int status;
    private int statusClient;

    public static ArrayList<Cliente> findAll() throws SQLException {
        Connection con = DBConnection.getConnection();
        ResultSet rs = null;

        String sql = "SELECT * FROM clients";
        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        ArrayList<Cliente> array = new ArrayList();
        while (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("id"));
            cliente.setType(rs.getInt("type"));
            cliente.setName(rs.getString("name"));
            cliente.setLastName(rs.getString("lastName"));
            cliente.setUserId(rs.getString("userId"));
            cliente.setPassword(rs.getString("password"));
            cliente.setPhoneNumber(rs.getString("phoneNumber"));
            cliente.setEmail(rs.getString("email"));
            cliente.setStatus(rs.getInt("status"));
            cliente.setStatusClient(rs.getInt("statusClient"));
            array.add(cliente);
        }
        return array;
    }

    public static void create(String type, String name, String lastName, String userId, String password, String phoneNumber, String email, String status, String statusClient) throws SQLException {
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO clients (type, name, lastName, userId, password, phoneNumber, email, status, statusClient) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement p2 = con.prepareStatement(sql);
        p2.setString(1, type);
        p2.setString(2, name);
        p2.setString(3, lastName);
        p2.setString(4, userId);
        p2.setString(5, password);
        p2.setString(6, phoneNumber);
        p2.setString(7, email);
        p2.setString(8, status);
        p2.setString(9, statusClient);
        p2.executeUpdate();
        p2.close();
    }

    public static void edit(int id, String type, String name, String lastName, String userId, String password, String phoneNumber, String email, String status, String statusClient) throws SQLException {
        Connection con = DBConnection.getConnection();
        String sql = "UPDATE clients SET type = ?, name = ?, lastName = ?, userId = ?, password = ?, phoneNumber = ?, email = ?, status = ?, statusClient = ? WHERE id = ?";
        PreparedStatement p2 = con.prepareStatement(sql);
        p2.setString(1, type);
        p2.setString(2, name);
        p2.setString(3, lastName);
        p2.setString(4, userId);
        p2.setString(5, password);
        p2.setString(6, phoneNumber);
        p2.setString(7, email);
        p2.setString(8, status);
        p2.setString(9, statusClient);
        p2.setInt(10, id);
        p2.executeUpdate();
        p2.close();
    }

    public Cliente() {
    }

    public Cliente(int id, String name, String lastName, String userId, String password, String phoneNumber, String email, int status, int statusClient) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
        this.statusClient = statusClient;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatusClient() {
        return statusClient;
    }

    public void setStatusClient(int statusClient) {
        this.statusClient = statusClient;
    }
}
