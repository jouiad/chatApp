package com.login.login;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;


public class Request {
    private static String url="jdbc:mysql://127.0.0.1/chat";
    private static String user="root";
    private static String password="";
    private static Connection c;
    

    // public static void main(String[] args) throws Exception {
    //     Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1/chat", "root", "");
    //     Statement stm= c.createStatement();
    //     ResultSet r= stm.executeQuery("select * from personne");

    //     while(r.next()){
    //         String nom = r.getString(1);
    //         System.out.println(nom);
    //     }

    //     c.close();
    // }

    public static Connection connection() throws SQLException{
        

        // if(c.isClosed()){
        c = DriverManager.getConnection(url, user, password);
        // }

        return c;
    }


    public static Boolean register(String first,String last,String password) throws SQLException{
        Connection c = connection();

        System.out.println(first+" => "+last+" => "+password);
        Statement stm = c.createStatement();
        int r = stm.executeUpdate("insert into personne(first_name,last_name,passwd) values('"+first+"','"+last+"','"+password+"')");

        c.close();
        return r>0;
        // while(r.next()){
        //     String nom = r.getString(1);
        //     System.out.println(nom);
        // }
    }
    public static Boolean login(String first,String password) throws SQLException {
        try{
            Connection c = connection();
            PreparedStatement statement = c.prepareStatement("select * from personne where first_name=? and passwd=?");

            statement.setString(1, first);
            statement.setString(2, password);


            ResultSet resultSet = statement.executeQuery();
            Boolean res=resultSet.next();
            System.out.println(res+" lba9");
            statement.close();
            resultSet.close();
            return res;
        }catch(SQLException ex){
            return false;
        }finally{
            c.close();
        }
    }
}
