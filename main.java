package com.payroll; // ONLY one package declaration at the very top

import java.io.*;
import java.util.*;

// --- 1. Employee Class ---
class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private double basicSalary;

    public Employee(int id, String name, double basicSalary) {
        this.id = id;
        this.name = name;
        this.basicSalary = basicSalary;
    }

    public void display() {
        System.out.println("ID: " + id + " | Name: " + name + " | Net: " + (basicSalary * 1.3));
    }

    public int getId() { return id; }
}

// --- 2. Main Class ---
public class main {
    private static final String FILE_NAME = "employees.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Employee> employees = readFromFile();

        while (true) {
            try {
                System.out.println("\nEmployee Payroll System");
                System.out.println("1. Add Employee\n2. Display All\n3. Search\n4. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); 

                if (choice == 1) {
                    addEmployee(sc, employees);
                } else if (choice == 2) {
                    employees.forEach(Employee::display);
                } else if (choice == 3) {
                    searchEmployee(sc, employees);
                } else if (choice == 4) {
                    writeToFile(employees);
                    System.out.println("Exiting... Data saved!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input.");
                sc.nextLine();
            }
        }
        sc.close();
    }

    private static void addEmployee(Scanner sc, ArrayList<Employee> employees) {
        System.out.print("ID: "); int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Salary: "); double salary = sc.nextDouble();
        employees.add(new Employee(id, name, salary));
    }

    private static void searchEmployee(Scanner sc, ArrayList<Employee> employees) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        employees.stream()
            .filter(e -> e.getId() == id)
            .findFirst()
            .ifPresentOrElse(Employee::display, () -> System.out.println("Not found."));
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Employee> readFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Employee>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static void writeToFile(ArrayList<Employee> employees) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
} // Final closing brace for the Main class