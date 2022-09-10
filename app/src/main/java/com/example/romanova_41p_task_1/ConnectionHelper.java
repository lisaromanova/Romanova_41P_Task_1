package com.example.romanova_41p_task_1;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
@SuppressLint("NewApi")
public class ConnectionHelper
{
    String userName, userPassword, ip, port, dataBase;

    public Connection connectionClass()
    {
        ip = "ngknn.ru";
        dataBase = "41P_Romanova_Mobile";
        userPassword = "12357";
        userName="31П";
        port ="1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String connectionURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databasename=" + dataBase + ";user=" + userName + ";password=" + userPassword + ";";
            connection = DriverManager.getConnection(connectionURL);
        }
        catch(Exception ex){
            Log.e("Error", ex.getMessage());
        }
        return connection;
    }
}
