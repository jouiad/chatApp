/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.login.login;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ramesh Godara
 */
public class MainPanelController {


    @FXML
    private Button send;

    @FXML
    private ListView<String> vlist;

    @FXML
    private TextField message;

    @FXML 
    private Text nbusers;

    Socket s;
    PrintWriter pw;
    BufferedReader br;
    String username;

    @FXML
    private ListView<String> usrlist;

    
    @FXML
    private void close() throws IOException {

        pw.close();
        br.close();
        s.close();

        Stage stage = (Stage) send.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("views/login-view.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Login");
        // stage.getIcons().add(new Image("/asset/icon.png"));
        stage.show();
    }

    @FXML
    private void send() throws IOException {
        

        String choix=usrlist.getSelectionModel().getSelectedItem();
        if(choix!=null){
            String msgToSend = message.getText();
            pw.println(msgToSend+";"+choix);
            message.clear();
        }

    }

    @FXML
    private void initialize() throws IOException {
        s = new Socket("localhost", 1234);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        pw = new PrintWriter(s.getOutputStream(), true);
        String[] u=new String[]{"user1","user2","user3"};
        // ,"user4","user5","user6"

        username=br.readLine();

        usrlist.getItems().add("all");

        // for(String x: u){
        //     if(!x.equals(username))
        //         usrlist.getItems().add(x);
        // }
        usrlist.getSelectionModel().select(0);

        // for(String sss:br.readLine().split(";")){
        //     if(!sss.equals(username))
        //         usrlist.getItems().add(sss);
        // }

        // System.out.println(br.readLine()+" ????????????????????????");

        vlist.getItems().add(br.readLine());

        new Thread(()-> {
            while (true)
            {
                try{
                    String response = br.readLine();
                    Platform.runLater(()->{
                        usrlist.getItems().clear();
                        usrlist.getItems().add("all");
                        usrlist.getSelectionModel().select(0);
                        if(response.startsWith("#")){
                            for(String s:response.split("#")[1].split(";")){
                                if(!s.equals(username))
                                    usrlist.getItems().add(s);
                            }
                            
                        }else{
                            String[] s=response.split("=");
                            vlist.getItems().add(s[0]);

                            for(String sr:s[1].split(";")){
                                if(!sr.equals(username))
                                    usrlist.getItems().add(sr);
                            }
                            
                        }
                        nbusers.setText(usrlist.getItems().size()+"");
                        

                    });
                    
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
        
        //br.close();

    }

    @FXML
    private void loadHomeView(ActionEvent e) throws IOException {

    }
}
