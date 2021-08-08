/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import javax.swing.UIManager;
import mdlaf.MaterialLookAndFeel;
import mvc.controllers.ClientsController;

/**
 *
 * @author andre
 */
public class MVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Mencoba menghubungkan ke database
        try {
            // Aktifkan tema material design
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
            ClientsController clientsController = new ClientsController();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
