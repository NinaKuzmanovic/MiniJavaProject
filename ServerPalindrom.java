package com.example.demo2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerPalindrom  extends Application{


    public final static ObservableList<String> elementiListe = FXCollections.observableArrayList("*** PALINDROM SERVER ***");

    public static void main(String[] args) {

        launch(args);}


        @Override
        public void start (Stage primaryStage) throws Exception {

            primaryStage.setTitle("SERVER PALINDROM");
            Group root = new Group();
            Scene scene = new Scene(root, 400, 400, Color.LIGHTYELLOW);
            ListView<String> listView = new ListView<String>(elementiListe);
            listView.setStyle("-fx-font-size:20px;");
            listView.setMinWidth(400);
            listView.setMinHeight(800);
            VBox vbox = new VBox(5);
            vbox.getChildren().addAll(listView);
            root.getChildren().add(vbox);

            primaryStage.setScene(scene);
            primaryStage.show();
            Thread socketServerThread = new Thread(new SocketServerThread());
            socketServerThread.setDaemon(true); //terminate the thread when program end
            socketServerThread.start();

        }
      class SocketServerThread extends Thread {

          @Override
            public void run() {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                     elementiListe.add("Server pokrenut");
                    }
                });
                try {
                    ServerSocket server=new ServerSocket(900);

                    while (true)
                    {

                        Socket klijentsoket=server.accept();


                        ServerThreadPalindrom serverNit=new ServerThreadPalindrom(klijentsoket);
                    }


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }

            }

        }

