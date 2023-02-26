package com.example.demo2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main extends Application {
    BufferedReader in = null;
    Socket soket=null;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("*******Mini projekat*******");
      stage.setHeight(650.00);
        Parent koren = new VBox(10);
        dodajPalindromZadatak(koren);
        dodajFajlServerZadatak(koren);
        //dodajSequenceZadatak(koren);
   dodajStart(koren);
        Scene scene = new Scene(koren);
        stage.setScene(scene);
        stage.show();
    }
   public static void main(String[] args) {
       launch(args);
    }

    private void dodajPalindromZadatak(Parent koren)
    {
          VBox zad1=new VBox();
        Label zadatak=new Label("Zadatak A3 \n KLIJENT: unos i slanje stringa.\n SERVER : vraća podatak da li je reč o palindromu (razmaci se ne računaju).\n" +
                " KLIJENT: ispisuje odgovor servera u labeli.");
        zadatak.setWrapText(true);
        zadatak.setTextAlignment(TextAlignment.JUSTIFY);
        zadatak.setMaxWidth(730);

        zadatak.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        zad1.getChildren().add(zadatak);


        Label labelRecenica=new Label("Unesite recenicu:");
        zad1.getChildren().add(labelRecenica);

        TextField unosRecenice=new TextField();
        zad1.getChildren().add(unosRecenice);
        Button potvrda=new Button("Provera");
       potvrda.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        zad1.getChildren().add(potvrda);
      Label at=new Label();
      at.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        zad1.getChildren().add(at);
   zad1.setStyle("-fx-background-color:rgb(255, 230, 204);-fx-border-color:black");
        ((VBox) koren).getChildren().add(zad1);
        potvrda.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {

                String zaServer=unosRecenice.getText();
                try{
                    Socket sock = new Socket("localhost", 900);

                    PrintWriter out = new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            sock.getOutputStream())),true);
                    out.println(zaServer);
                    in= new BufferedReader(new InputStreamReader(sock.getInputStream()));
                  String odg = in.readLine();
                  at.setText(odg);


                    out.close();
                  in.close();
                    sock.close();
out.flush();
                }catch (Exception ex){
                    ex.printStackTrace();
                }


            }
        });





    }
private void dodajFajlServerZadatak(Parent koren)
{
    VBox zad1=new VBox();
    zad1.setStyle("-fx-background-color:rgb(255, 204, 153);-fx-border-color:black");
    Label zadatak=new Label("Zadatak B1\n" +
            "KLIJENT: odabranu datoteku iz klijentskog foldera DATA_KLIJENT šalje na serverski folder\n" +
            "DATA_SERVER.\n" +
            "SERVER : odgovara da li je datoteka uspešno preuzeta.\n" +
            "KLIJENT: ispisuje u labeli odgovor servera.");

    zadatak.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
    zad1.getChildren().add(zadatak);
    zadatak.setWrapText(true);
    zadatak.setTextAlignment(TextAlignment.JUSTIFY);
    zadatak.setMaxWidth(730);
    HBox dugmici=new HBox();
    dugmici.setPrefWidth(200.00);
    dugmici.setPrefHeight(50);
    dugmici.setAlignment(Pos.CENTER);
    FileChooser izaberifajl=new FileChooser();
    final File[] selectedFile = new File[1];
    Button izaberidatoteku=new Button("Izaberi fajl");

    izaberidatoteku.prefWidthProperty().bind(dugmici.maxWidthProperty());
    izaberidatoteku.prefHeightProperty().bind(dugmici.maxHeightProperty());
    izaberidatoteku.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
 dugmici.getChildren().add(izaberidatoteku);

    Button posaljifajl=new Button("Posalji fajl");
    posaljifajl.prefWidthProperty().bind(dugmici.maxWidthProperty());
    posaljifajl.prefHeightProperty().bind(dugmici.maxHeightProperty());
    posaljifajl.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
    dugmici.getChildren().add(posaljifajl);

    zad1.getChildren().add(dugmici);

    Label imefajla=new Label();
    imefajla.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
    zad1.getChildren().add(imefajla);
    ((VBox) koren).getChildren().addAll(zad1);

    izaberidatoteku.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {

            izaberifajl.setInitialDirectory(new File("src/main/java/com/example/demo2/DATA_KLIJENT"));
            selectedFile[0] = izaberifajl.showOpenDialog(null);
            if(selectedFile[0]!=null)
            {imefajla.setText("Fajl koji saljete je:"+ selectedFile[0].getName());}
            else
            {
                imefajla.setText("Morate izabrati fajl za slanje!");
            }

        }
    });
    posaljifajl.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;
            if (selectedFile[0] == null) {
                imefajla.setText("Morate izabrati fajl za slanje");
            } else {

                try (Socket socket = new Socket("localhost", 1000)) {
                    dataInputStream = new DataInputStream(
                            socket.getInputStream());
                    dataOutputStream = new DataOutputStream(
                            socket.getOutputStream());

                    System.out.println("Sending the File to the Server");

                    int bytes = 0;


                     ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
                 ArrayList<String> poruka= new ArrayList<>();
                 int i=0;
                    poruka.add(i,selectedFile[0].getName()); i++;
                    System.out.println(poruka.get(0));
                    outputStream.writeObject(poruka);
                    File file = new File(selectedFile[0].getAbsolutePath());
                    FileInputStream fileInputStream = new FileInputStream(file);


                    dataOutputStream.writeLong(file.length());

                    byte[] buffer = new byte[4 * 1024];
                    while ((bytes = fileInputStream.read(buffer)) != -1) {

                        dataOutputStream.write(buffer, 0, bytes);
                        dataOutputStream.flush();
                    }


                    Scanner in = new Scanner(socket.getInputStream());
                   imefajla.setText(in.nextLine());
                    fileInputStream.close();
                    dataInputStream.close();
                    dataInputStream.close();



                } catch (Exception e) {
                    e.printStackTrace();
                }





            }


        }


    });}


    private void dodajStart(Parent koren)
    {
        VBox zad1=new VBox();

        zad1.setStyle("-fx-background-color:rgb(255, 153, 51);-fx-border-color:black");
        Label zadatak=new Label("Zadatak C3\n Na klijentu kreirati igru \"Sequence 3x3\" koja ima talon 3x3 zeleno obojenih programskih dugmadi. Dugme START  pokreće novu igru i u " +
                "slučajnom redosledu boji " +
                "jedno po jedno programsko dugme u žuto svakih 0.25 sekundi. Kada su sva programska dugmad žuta" +
                " potrebnoje da igrač istim redosledom klikne na svu" +
                " programsku dugmad. Za ispravan redosled kvadrat postaje opet zelen. Cilj je ponoviti sekvencu nakon čega sledi poruka čestitke.");
        zadatak.setWrapText(true);
        zadatak.setTextAlignment(TextAlignment.JUSTIFY);
        zadatak.setMaxWidth(730);

        zadatak.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        zad1.getChildren().add(zadatak);
        zad1.setAlignment(Pos.CENTER);
        Button pokreninit=new Button();
       pokreninit.setText("POKRENI  \"Sequence 3x3\"");

        pokreninit.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        zad1.getChildren().add(pokreninit);
        ((VBox) koren).getChildren().add(zad1);

        pokreninit.setOnAction(actionEvent -> dodajSequenceZadatak());

    }

private void dodajSequenceZadatak()
{
  Stage stage=new Stage();
    stage.setTitle("Sequence3x3");
    stage.setHeight(500);
    stage.setWidth(500);
    Parent koren = new VBox(10);
((VBox)koren).setAlignment(Pos.CENTER);
 VBox taloni=new VBox();
//taloni.setAlignment(Pos.CENTER);
    HBox hBox=new HBox();
    hBox.setAlignment(Pos.CENTER);
    Button dugme1=new Button();
    dugme1.setPrefWidth(75.00);
    dugme1.setPrefHeight(75.00);
    dugme1.setId("1");
    Button dugme2=new Button();
    dugme2.setPrefWidth(75.00);
    dugme2.setPrefHeight(75.00);dugme2.setId("2");
    Button dugme3=new Button();
    dugme3.setPrefWidth(75.00);
    dugme3.setPrefHeight(75.00);dugme3.setId("3");
    hBox.getChildren().addAll(dugme1,dugme2,dugme3);



    HBox hBox2=new HBox();
    hBox2.setAlignment(Pos.CENTER);
    Button dugme4=new Button();
    dugme4.setPrefWidth(75.00);
    dugme4.setPrefHeight(75.00);
    dugme4.setId("4");
    Button dugme5=new Button();
    dugme5.setPrefWidth(75.00);
    dugme5.setPrefHeight(75.00);
    dugme5.setId("5");
    Button dugme6=new Button();
    dugme6.setPrefWidth(75.00);
    dugme6.setPrefHeight(75.00);dugme6.setId("6");
    hBox2.getChildren().addAll(dugme4,dugme5,dugme6);



    HBox hBox3=new HBox();
    hBox3.setAlignment(Pos.CENTER);
    Button dugme7=new Button();
    dugme7.setPrefWidth(75.00);
    dugme7.setPrefHeight(75.00);
    dugme7.setId("7");
    Button dugme8=new Button();
    dugme8.setPrefWidth(75);
    dugme8.setPrefHeight(75);dugme8.setId("8");
    Button dugme9=new Button();
    dugme9.setPrefWidth(75.00);
    dugme9.setPrefHeight(75.00);
    dugme9.setId("9");
    hBox3.getChildren().addAll(dugme7,dugme8,dugme9);

    taloni.getChildren().addAll(hBox,hBox2,hBox3);
    ((VBox) koren).getChildren().addAll(taloni);
    Button pokreninit=new Button();
    pokreninit.setText("START");
    pokreninit.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
    ((VBox) koren).getChildren().addAll(pokreninit);
    Label cestitka=new Label();
    ((VBox) koren).getChildren().addAll(cestitka);

    ArrayList<Button> dugmici=new ArrayList<>();
    dugmici.add(dugme1);
    dugmici.add(dugme2);
    dugmici.add(dugme3);
    dugmici.add(dugme4);
    dugmici.add(dugme5);
    dugmici.add(dugme6);
    dugmici.add(dugme7);
    dugmici.add(dugme8);
    dugmici.add(dugme9);
    for(int i=0;i<dugmici.size();i++) {
        dugmici.get(i).setStyle("-fx-background-color:green;-fx-border-color:black");
    }

    pokreninit.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Platform.runLater(() -> cestitka.setText(""));


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    for(int i=0;i<dugmici.size();i++) {
                        dugmici.get(i).setStyle("-fx-background-color:green;-fx-border-color:black");
                    }
                    String obojeni="";
                    final String[] kliknuti = {""};


                    Collections.shuffle(dugmici);
                    for(int i=0;i<dugmici.size();i++)
                    {
                        dugmici.get(i).setStyle("-fx-background-color:yellow;-fx-border-color:black");
                        try { Thread.sleep(250); }
                        catch (InterruptedException ex) {}
                        obojeni=obojeni+dugmici.get(i).getId();

                        System.out.println(obojeni);


                    }

                    for(int j=0;j<dugmici.size();j++) {

                        int finalI = j;
                        String finalObojeni = obojeni;
                        dugmici.get(j).setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                kliknuti[0] = kliknuti[0] +dugmici.get(finalI).getId();   System.out.println(kliknuti[0]);

                                if(kliknuti[0]!=(finalObojeni)&&(kliknuti[0].length()==finalObojeni.length()))
                                {  cestitka.setText("NISTE POGODILI SEKVENCU!");
                                  cestitka.setFont(Font.font("Lucida Sans Unicode", FontWeight.EXTRA_BOLD, 20));

                                  cestitka.setTextFill(Color.RED);

                                    for (int i = 0; i < dugmici.size(); i++) {
                                        dugmici.get(i).setStyle("-fx-background-color:red;-fx-border-color:black");
                                    }

                                }

                                if(kliknuti[0].equals(finalObojeni)&&(kliknuti[0].length()==finalObojeni.length())) {
                                    cestitka.setText("POGODILI STE SEKVENCU!");

                                    cestitka.setFont(Font.font("Lucida Sans Unicode", FontWeight.EXTRA_BOLD, 20));

                                    cestitka.setTextFill(Color.GREEN);

                                    for (int i = 0; i < dugmici.size(); i++) {
                                        dugmici.get(i).setStyle("-fx-background-color:green;-fx-border-color:black");
                                    }

                                }


                            }
                        });

                    }




                }
            });

            thread.setDaemon(true);
            thread.start();


        }


    });


    Scene scene = new Scene(koren);
    stage.setScene(scene);
    stage.show();



}










}


