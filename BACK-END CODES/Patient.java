package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patient {
    private Connection connection;
    private Scanner scanner;

    public patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Add new patient
    public void addpatient() {
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Patient Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Patient Gender: ");
        String gender = scanner.nextLine();

        String query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);

            int affectedRow = ps.executeUpdate();

            if (affectedRow > 0) {
                System.out.println("Patient Added Successfully!!");
            } else {
                System.out.println("Failed to add Patient!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View all patients
    public void viewpatient() {
        String query = "SELECT * FROM patients";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet res = ps.executeQuery()) {

            System.out.println("\nPatients List:");
            System.out.println("------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-10s %-10s%n", "ID", "Name", "Age", "Gender");
            System.out.println("------------------------------------------------------------");

            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                int age = res.getInt("age");
                String gender = res.getString("gender");

                System.out.printf("%-5d %-20s %-10d %-10s%n", id, name, age, gender);
            }

            System.out.println("------------------------------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if patient exists by ID
    public boolean getpatientByid(int id) {
        String query = "SELECT * FROM patients WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet res = ps.executeQuery()) {
                return res.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
