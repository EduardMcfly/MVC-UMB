package mvc.controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import mvc.models.Cliente;
import mvc.views.ClientsView;

public final class ClientsController {

    private final Connection con = null;
    private final Statement stmt = null;
    private final ResultSet rs = null;
    private final ClientsView view;

    public ClientsController() {
        this.view = new ClientsView((Callable) () -> {
            getTableData();
            return null;
        });
        this.view.initGUI();
    }

    public void getTableData() {
        view.tableModel.getDataVector().removeAllElements();
        view.tableModel.fireTableDataChanged();
        try {
            ArrayList<Cliente> array = Cliente.findAll();
            for (Cliente cliente : array) {
                Object[] o = new Object[10];
                o[0] = cliente.getId();
                o[1] = cliente.getType();
                o[2] = cliente.getName();
                o[3] = cliente.getLastName();
                o[4] = cliente.getUserId();
                o[5] = cliente.getPassword();
                o[6] = cliente.getPhoneNumber();
                o[7] = cliente.getEmail();
                o[8] = cliente.getStatus();
                o[9] = cliente.getStatusClient();
                view.tableModel.addRow(o);
            }
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
    }

}
