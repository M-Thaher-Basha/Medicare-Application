package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class main 
{
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username="root";
	private static final String password="9014029812";
	
	public static void main(String args[])
	{
		Scanner scanner=new Scanner(System.in);
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection=DriverManager.getConnection(url,username,password);
			
			patient patient=new patient(connection,scanner);
			doctor doctor=new doctor(connection);
			
			while(true)
			{
				System.out.println("Hospital Management System");
				System.out.println("1.Add Patient");
				System.out.println("2.View Patient");
				System.out.println("3.View Doctors");
				System.out.println("4.Bool Appointment");
				System.out.println("5.Exit");
				System.out.println("Enter Your Choice: ");
				int choice=scanner.nextInt();
				scanner.nextLine();
				
				switch(choice)
				{
					case 1:
						patient.addpatient();
						System.out.println();
						break;
						
					case 2:
						patient.viewpatient();
						System.out.println();

						break;
						
						
					case 3:
						doctor.viewdoctor();
						System.out.println();

						break;

						
					case 4:
						bookappointment(patient,doctor,connection,scanner);
						System.out.println();

						break;

						
					case 5:
						System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM...!!");
						return;

					
					
				}
				
			}
			
			
			
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	public static void bookappointment(patient patient,doctor doctor,Connection connection, Scanner scanner)
	{
		
		System.out.println("Enter Patient ID: ");
		int patientid=scanner.nextInt();
		
		System.out.println("Enter Doctor ID: ");
		int doctorid=scanner.nextInt();
		scanner.nextLine();
		
		
		System.out.println("Enter the Appointment Date (YYYY-MM-DD) : ");
		String appointmentdate=scanner.nextLine();
		
		if(patient.getpatientByid(patientid) && doctor.getdoctorByid(doctorid))
		{
			if(checkDoctorAvailability(doctorid,appointmentdate,connection))
			{
				String appointmentQuery=" INSERT into appointments(patient_id , doctor_id, appointment_date) VALUES (?,?,?)";
				try
				{
					
					PreparedStatement preparestatement=connection.prepareStatement(appointmentQuery);
					preparestatement.setInt(1, patientid);
					preparestatement.setInt(2, doctorid);
					preparestatement.setString(3, appointmentdate);
					int rowsaffected=preparestatement.executeUpdate();
					if(rowsaffected>0)
					{
						System.out.println("Appointment Booked!!");
					}
					else
					{
						System.out.println(" ^^Failed to Book Appointment^^ ");
					}
					
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println(" ^^Doctor Not Available On this date ^^");
			}
		}
		else
		{
			System.out.println("Either Doctor or Patient Does Not Exist....");
			
		}
		
			
	}
	
	
	
	public static boolean checkDoctorAvailability(int doctorid, String appointmentdate,Connection connection)
	{
		String checkcount= "SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
		
		try
		{
			PreparedStatement preparestatement=connection.prepareStatement(checkcount);
			preparestatement.setInt(1, doctorid);
			preparestatement.setString(2, appointmentdate);
			ResultSet res=preparestatement.executeQuery();
			if(res.next())
			{
				int count=res.getInt(1);
				if(count==0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
			
		
		return false;
	}
	

}
