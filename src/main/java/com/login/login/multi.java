package com.login.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.print.Collation;

public class multi extends Thread {

    private Integer nb=0;
    private List<Communication> com=new ArrayList<Communication>();
    public static void main(String[] args) {
        new multi().start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            ServerSocket ss=new ServerSocket(1234);
            System.out.println("i'm lesetning for all clients");

            while(true){
                Socket s= ss.accept();
                System.out.println("client "+s.getRemoteSocketAddress().toString()+" has connected !!");
                Communication c = new Communication(s,++nb);
                com.add(c);
                c.start();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }

    private class Communication extends Thread{
        public Socket s;
        private Integer nb;
        public Communication(Socket s, Integer nb){
            this.s=s;
            this.nb=nb;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                BufferedReader b= new BufferedReader(new InputStreamReader(s.getInputStream()));
                // System.out.println("yeah bro");
                PrintWriter pw=new PrintWriter(s.getOutputStream(), true);
                pw.print("you are client number "+nb);
                
                while(true){
                    String str = b.readLine();
                    if(str!=null){
                        // System.out.println(str);
                        for(Communication cl : com){
                            PrintWriter pww=new PrintWriter(cl.s.getOutputStream(),true);

                            pww.println(str);
                        }
                        
                    }
                }
                
                
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            
        }
    }
    
}
