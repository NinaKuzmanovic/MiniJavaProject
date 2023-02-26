package com.example.demo2;

import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.Locale;

public class ServerThreadPalindrom extends Thread
{
private final Socket socket;

private final BufferedReader ulaz;
private final PrintWriter izlaz;

public ServerThreadPalindrom(Socket socket)
{

    this.socket=socket;
    try {
        ulaz=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        izlaz=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
start();
}

public void run()
{
    try {
        String zahtev=ulaz.readLine();
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
               ServerPalindrom .elementiListe.add("Klijent je uneo:"+zahtev);
            }
        });
        String [] izmeni=zahtev.split(" ");
       String spoj="";
       for(int i=0;i<izmeni.length;i++)
       {spoj=spoj+izmeni[i];}
       spoj=spoj.toLowerCase();
        char c; String novi="";
        for(int i=0;i<spoj.length();i++)
        {   c=spoj.charAt(i);
            novi=c+novi;
        } novi=novi.toLowerCase();
System.out.println(novi);
System.out.println(spoj);
if(novi.equals(spoj))
{
    izlaz.println("SERVER:Jeste palindrom!\n\r");
    Platform.runLater(new Runnable() {

        @Override
        public void run() {
            ServerPalindrom .elementiListe.add("SERVER:Jeste palindrom!");
        }
    });
}
else
{
    izlaz.println("SERVER:Nije palindrom!\n\r");
    izlaz.flush();
    Platform.runLater(new Runnable() {

        @Override
        public void run() {
            ServerPalindrom .elementiListe.add("SERVER:Nije palindrom!");
        }
    });
}
    } catch (IOException e) {
        throw new RuntimeException(e);
    }


}



}
