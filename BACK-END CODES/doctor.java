package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class doctor 
{
	private Connection connection;
	
	public doctor(Connection connection)
	{
		this.connection=connection;
	}
	
	
	
	
	
	
	
	
	public void viewdoctor()
	{
		
		String query="select * from doctors";
		
		try 
		{
			PreparedStatement prepareStatement=connection.prepareStatement(query);
			ResultSet res=prepareStatement.executeQuery();
			
            System.out.println("Doctors:");
            System.out.println("---------------------------------------------------");
            System.out.printf("%-5s %-20s %-20s%n", "ID", "Name", "Specialization");
            System.out.println("---------------------------------------------------");

            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String specialization = res.getString("specialization");

                System.out.printf("%-5d %-20s %-20s%n", id, name, specialization);
            }

            System.out.println("---------------------------------------------------");

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public boolean getdoctorByid(int id)
	{
		String query="SELECT * FROM doctors WHERE id=?";
		
		
		try {
			PreparedStatement prepareStatement=connection.prepareStatement(query);
			prepareStatement.setInt(1, id);
			ResultSet res=prepareStatement.executeQuery();
			
			
			if(res.next())
			{
				return true;
				
			}
			else
			{
				return false;
			}
			
			
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;

		
	}


}
