package aps.view;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import aps.view.innerview.ParentRepController;

public class LoggerM {
	ConnectDB database = new ConnectDB();
	 private Connection con;
	    private ResultSet rs,rs4,rs2,rs3;
	    private Statement st,st4,st2,st3;
	    
	 
	   
	  String pattern = "EEEEE dd/MM/yyyy HH:mm:ss";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	    
	
	    public void writter(String action){
	    	con=database.dbconnect();
		 String date = format.format(new Date());
		 
			String str="INSERT INTO log(user,action,time) values('"+LoginController.getMyName()+"','"+action+"','"+date+"')";
			try {
				st=con.createStatement();
				st.executeUpdate(str);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
	
	
}
