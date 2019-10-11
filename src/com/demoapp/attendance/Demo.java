/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.demoapp.attendance;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author Dell
 */
public class Demo extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        ListView ls = new ListView();
        ls.setPrefSize(200, 400);
        ls.setMaxWidth(200);
        ls.setMinHeight(300);
        User [] users = {new User("Uche Maduka", "Branch Manager"), new User("Gozie Okeke", "Product Manager"), new User("Chukwu Godson", "Driver")};
        for(int i=0; i<users.length; i++){
            ls.getItems().add(users[i].getName());
        }
        ls.getSelectionModel().clearAndSelect(0);
        VBox center = new VBox();
        Label user = new Label("");
        Label role = new Label("");
        Label isChecked = new Label("");
        Image img = new Image("https://www.stickpng.com/assets/images/585e4bcdcb11b227491c3396.png", 150, 150, true, true, true);
        ImageView imgView = new ImageView(img);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(5,5,5,5));
        center.getChildren().addAll(imgView, user, role, isChecked);
        System.out.print(ls.getSelectionModel().getSelectedIndex());
        
        Button btn = new Button("Check in");
        btn.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                int index = ls.getSelectionModel().getSelectedIndex();
                User selectedUser = users[index];
                user.setText(selectedUser.getName());
                role.setText(selectedUser.getRole());
                if(selectedUser.isChecked() && selectedUser.isEnrolled()){ 
                    isChecked.setText(selectedUser.getName()+" is checked in ");
                } else if(!selectedUser.isChecked() && selectedUser.isEnrolled()){
                    isChecked.setText(selectedUser.getName()+" is not checked");
                }
                if(!selectedUser.isEnrolled()){
                    Button enroll = new Button("Enroll");
                    enroll.setOnAction(new EventHandler(){
                        @Override
                        public void handle(Event event) {
                            VerificationStage vStage = new VerificationStage();
                            vStage.setOnHidden(new EventHandler(){
                                @Override
                                public void handle(Event event) {
                                    System.out.print("Hidden");
                                }
                                
                            });
                            vStage.show();
                        }
                        
                    });
                    center.getChildren().add(enroll);
                }
                
                
            }
        });
        Label header = new Label("Staff in this region");
        BorderPane bPane = new BorderPane();
        bPane.setCenter(center);
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.getChildren().addAll(header,ls, btn);
        bPane.setLeft(root);
        Scene scene = new Scene(bPane, 600, 600);

        primaryStage.setTitle("List of users in this branch");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    class VerificationStage extends Stage{
        public VerificationStage(){
            Label lb = new Sensor();
            
            StackPane root = new StackPane();
            root.getChildren().add(lb);
            Scene scene = new Scene(root, 300, 300);
            setScene(scene);
            setResizable(false);
            setAlwaysOnTop(true);
        }
    }
    

}
