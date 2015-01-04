package com.mapapp.mpi.core.db;

import com.mapapp.mpi.core.crypto.CryptoService;

import org.apache.commons.codec.binary.Base64;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Ganesh Ravendranathan
 */
public class Database {

    private static Statement statement;

    private static Base64 base = new Base64();

    static {

        try{
            Class.forName("org.gjt.mm.mysql.Driver");

            //TODO: Encryption - gotta get rid of the password mentioned here
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mapappt1", "root", "a7w282");


            statement = connection.createStatement();
            statement.setQueryTimeout(30);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[]args){
        createUser("zer43", "epic13373pw", "hello@asda.com");
    }

    /**
     * Adds a user into the database.
     * It is VERY important to use {@link Base64} as we are intertwining Strings and byte arrays.
     *
     * @param alias The alias of the user.
     * @param password The password of the user.
     */
    protected static User createUser(String alias, String password, String emailAddr){
        try{

            //Create a table containing an id, alias, salt, password hash
            exeCmd("create table if not exists userinfo (UID int NOT NULL AUTO_INCREMENT, Alias varchar(255), EmailAddr varchar(255), Salt varchar(255), TimeStamp varchar(255), PRIMARY KEY (UID))");

            ResultSet rs = statement.executeQuery("select * from userinfo");

            //Check every row of the table to see if the alias already exists
            while(rs.next()){
                if(rs.getString("Alias").equalsIgnoreCase(alias)){
                    throw new Error("Database Error: The alias, '" + alias + "', has been taken already. Please choose a different alias.");
                }
            }



            //TODO: Ensure that encryption goes in safely2
            CryptoService cs = new CryptoService();

            byte[] salt = cs.generateSalt();
            byte[] pass = cs.getEncryptedPassword(password, salt);

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

            exeCmd("insert into userinfo (Alias, EmailAddr, Salt, TimeStamp) values ('" + alias + "', '" + emailAddr + "', '" + base.encodeAsString(salt) + "', '" + sdf.format(cal.getTime()) + " EST')");
            System.out.println(alias + " has been successfully added to the user table.");

            rs = statement.executeQuery("select * from userinfo");

            int uid = -1;

            while(rs.next()){
                if(rs.getString("alias").equals(alias)){
                    uid = rs.getInt("UID");
                }
            }


            if(uid == -1){
                System.err.println("UID not found for alias: " + alias);
                return null;
            }


            exeCmd("create table if not exists userhash (UID int, UHash varchar(255))");
            exeCmd("insert into userhash (UID, UHash) values ('" + uid + "', '" + base.encodeAsString(pass) + "')");




        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private static int exeCmd(String cmd){
        try {
            return statement.executeUpdate(cmd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
