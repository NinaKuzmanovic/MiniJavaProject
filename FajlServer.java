package com.example.demo2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class FajlServer extends Application {

    private final static ObservableList<String> list= FXCollections.observableArrayList("*** FAJL SERVER ***");

  public static void main(String[] args) {
      launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SERVER");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 400, Color.LIGHTYELLOW);
        ListView<String> listView = new ListView<String>(list);
        listView.setStyle("-fx-font-size:20px;");
        listView.setMinWidth(400);
        listView.setMinHeight(800);
        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(listView);
        root.getChildren().add(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
        Task task = new Task<Void>() {
            @Override public Void call() {
                komunikacija();
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();


    }









    private static void komunikacija()
    {
 DataOutputStream dataOutputStream = null;
 DataInputStream dataInputStream = null;


    // Here we define Server Socket running on port 900
try (ServerSocket serverSocket = new ServerSocket(1000)) {
System.out.println("Server is Starting in Port 1000");
// Accept the Client request using accept method
while (true){
    Socket clientSocket = serverSocket.accept();
    Platform.runLater(new Runnable() {

        @Override
        public void run() {
            list.add("Server pokrenut");
        }
    });



int i=0;
   ArrayList<String> poruke=new ArrayList<>();
    ObjectInputStream citaj=new ObjectInputStream(clientSocket.getInputStream());
    poruke= (ArrayList<String>) citaj.readObject();






    dataInputStream = new DataInputStream(clientSocket.getInputStream());
    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
int bytes = 0;
FileOutputStream fileOutputStream = new FileOutputStream("src/main/java/com/example/demo2/DATA_SERVER/"+poruke.get(i));   i++;
long size = dataInputStream.readLong(); // read file size
byte[] buffer = new byte[4 * 1024];
while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
// Here we write the file using write method
fileOutputStream.write(buffer, 0, bytes);
size -= bytes; // read upto file size
}
// Here we received file

    ArrayList<String> finalPoruke = poruke;
    int finalI = i;
    Platform.runLater(new Runnable() {

        @Override
        public void run() {
            list.add("Klijent salje fajl:"+ finalPoruke.get(finalI -1));
            list.add("Fajl poslat na server!");
        }
    });
    PrintWriter pr = new PrintWriter(clientSocket.getOutputStream(), true);
    pr.println("Fajl poslat na server!");
fileOutputStream.close();
dataInputStream.close();
dataOutputStream.close();
clientSocket.close();}
}
catch (Exception e) {
e.printStackTrace();
}







    }

    }

