package com.login.login;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class ChatWithServer extends Thread {
    private int ClientNbre;
    private List<Communication> lstCon = new ArrayList<Communication>();

    public static void main(String[] args) {
        new ChatWithServer().start();
    }

    @Override
    public void run()
    {
        try {
            ServerSocket ss =new ServerSocket(1234);
            System.out.println("Waiting for Someone to Connect...");
            while(true)
            {
                Socket s = ss.accept();
                ++ClientNbre;
                Communication newCom = new Communication(s, ClientNbre,"user"+ClientNbre);
                lstCon.add(newCom);
                newCom.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class Communication extends Thread{
        private Socket s;
        private int ClientNumber;
        private String username;

        Communication(Socket s, int ClientNumber,String username){
            this.s = s;
            this.ClientNumber=ClientNumber;
            this.username=username;
        }
        @Override
        public void run()
        {
            try {
                /*InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream();*/
                InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream();
                InputStreamReader isr=new InputStreamReader(is); // caractere
                BufferedReader br = new BufferedReader(isr); // chaine de caracteres OutputStream os = s.getOutputStream();
                String Ip = s.getRemoteSocketAddress().toString();
                System.out.println("The Client "+ ClientNumber+" Connected!     |   IP " +Ip);
                PrintWriter pw = new PrintWriter (os,  true);
                
                pw.println(this.username);
                // sendActiveUsers();

                pw.println("Connected to The Server as Client N "+ ClientNumber + ", Say Something! :");

                for (Communication client : lstCon){
                    PrintWriter pw2 = new PrintWriter(client.s.getOutputStream(), true);
                    pw2.println("#"+users());
                }

                while (true) {
                    String UserRequest = br.readLine();
                    System.out.println("I GOT : "+UserRequest);
                    if (UserRequest != null)
                    {
                        String[] usermessage = UserRequest.split(";");
                        Send(usermessage[0], s, usermessage[1]);
                        // if (UserRequest.contains(";")) {
                        //     String[] usermessage = UserRequest.split("=>");
                        //     if(usermessage.length==2){
                        //         String msg = usermessage[1];
                        //         int numeroClient = Integer.parseInt(usermessage[0]);
                        //         Send(msg, s, numeroClient);
                        //     }
                        // }
                        // else
                        // {
                        //     Send(UserRequest, s, -1);
                        // }
                    }
                }
        } catch (IOException e) {
                e.printStackTrace();
            }
        }
        void Send(String UserRequest , Socket socket, String us) throws IOException {
            // System.out.println("SEND TO EVERY");
            for (Communication client : lstCon){
                if (client.s != socket)
                {
                    if (client.username.equals(us) || us.equals("all"))
                    {
                        PrintWriter pw = new PrintWriter(client.s.getOutputStream(), true);
                        pw.println(UserRequest+"="+users());
                    }
                }
            }

            for (Communication client : lstCon){
                PrintWriter pw = new PrintWriter(client.s.getOutputStream(), true);
                pw.println("#"+users());
            }
        }

    }

    private String users(){
        String usr="";

        for(Integer i=0;i<lstCon.size();i++){
            usr+=lstCon.get(i).username;
            if(i<lstCon.size()-1){
                usr+=";";
            }
        }
        return usr;
    }

    // private void sendActiveUsers() throws IOException{
    //     String usr=users();

    //     for (Communication client : lstCon){
    //         PrintWriter pw = new PrintWriter(client.s.getOutputStream(), true);
    //         pw.println("#"+usr);
    //     }
    // }
}
