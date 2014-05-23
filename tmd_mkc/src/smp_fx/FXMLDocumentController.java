/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smp_fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Tanay Agrawal
 */
public class FXMLDocumentController implements Initializable {
    
    //@FXML
    //private Label label;
    String shadow;
    @FXML
    private TextField login_user_text;
    @FXML
    private PasswordField login_pass;
    @FXML
    TextField login_fname, login_username, login_regno, login_email, login_password, login_repassword;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
      //  label.setText("Hello World!");
    }
    static Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void login() throws Exception {
        String s = login_user_text.getText();
        String pass = login_pass.getText();
        if(s.equals(null) || s.equals("") || pass.equals(null) || pass.equals("")) {
            return;
        }
        user u = GuestUser.login(s, pass);
        if(u != null) {
            System.out.println("successful login");
            ProfiletabsController.u = u;
            System.out.println("ProfileTabsController u is set: " + u.full_name);
            Main.ob.start2();
        }
    }

    @FXML
    private void signup() throws Exception {
        String fname = login_fname.getText();
        String uname = login_username.getText();
        String regno = login_regno.getText();
        String email = login_email.getText();
        String pass = login_password.getText();
        String repass = login_repassword.getText();
        String shadow = this.shadow;
        if(fname.equals(null) || fname.equals("") || uname.equals(null) || uname.equals("") || regno.equals(null) || regno.equals("") || email.equals(null) || email.equals("") || pass.equals(null) || pass.equals("") || repass.equals(null) || repass.equals("") || !pass.equals(repass) || shadow.equals(null) || shadow.equals("")) {
            return;
        }

        user u = GuestUser.register(fname, email, shadow, uname, pass);
        if(u != null) {
            ProfiletabsController.u = u;
            Main.ob.start2();
        }
    }

    @FXML
    public void handle() {
        FileChooser fC = new FileChooser();
        fC.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fC.showOpenDialog(stage);
        if (file != null)
            shadow = file.getAbsolutePath();
        /*
        Path p = Paths.get("C:\\Users\\ACER\\IdeaProjects\\tmd_mkc\\src\\smp_fx\\");
        Path p2 = Paths.get(shadow);
        String s = replace(p2.toString());
        File file2 = new File(s);
        shadow = file2.toURI().toString();
        */
    }

    String replace(String s) {
        String s1 = "";
        String x = "";
        for(int i1 = 0 ; i1 < s.length() ; i1++) {
            if(i1 != s.length()-1)
                x = s.substring(i1,i1+1);
            else
                x = s.substring(i1);
            if(x.equals("\\"))
                s1 += "/";
            else
                s1 += x;
        }
        return s1;
    }
}
