import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private String studentId;
    private double[] grades;

    // Constructor
    public Student(String name, String studentId, int numberOfGrades) {
        this.name = name;
        this.studentId = studentId;
        this.grades = new double[numberOfGrades];
    }

    // Method to input grades
    public void inputGrades(Scanner scanner) {
        System.out.println("Enter grades for " + name + " (Student ID: " + studentId + "):");
        for (int i = 0; i < grades.length; i++) {
            while (true) {
                try {
                    System.out.print("Grade " + (i + 1) + ": ");
                    grades[i] = scanner.nextDouble();
                    
                    // Validate grade is between 0 and 100
                    if (grades[i] < 0 || grades[i] > 100) {
                        System.out.println("Invalid grade. Please enter a grade between 0 and 100.");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a numeric grade.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }
        }
    }

    // Method to calculate average grade
    public double calculateAverageGrade() {
        if (grades.length == 0) return 0;
        
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.length;
    }

    // Method to display individual grades
    public void displayGrades() {
        System.out.println("Grades for " + name + " (Student ID: " + studentId + "):");
        for (int i = 0; i < grades.length; i++) {
            System.out.println("Grade " + (i + 1) + ": " + grades[i]);
        }
    }

    // Method to determine if student passed
    public boolean hasPassed() {
        return calculateAverageGrade() >= 60;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Student Grade Calculator ---");
            System.out.println("1. Add New Student");
            System.out.println("2. Input Grades");
            System.out.println("3. Display Student Grades");
            System.out.println("4. Calculate Class Average");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewStudent(scanner, students);
                    break;
                case 2:
                    inputStudentGrades(scanner, students);
                    break;
                case 3:
                    displayAllStudentGrades(students);
                    break;
                case 4:
                    calculateClassAverage(students);
                    break;
                case 5:
                    System.out.println("Exiting the program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addNewStudent(Scanner scanner, ArrayList<Student> students) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        
        System.out.print("Enter number of grades: ");
        int numberOfGrades = scanner.nextInt();
        
        Student newStudent = new Student(name, studentId, numberOfGrades);
        students.add(newStudent);
        System.out.println("Student added successfully!");
    }

    private static void inputStudentGrades(Scanner scanner, ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students added. Please add students first.");
            return;
        }

        System.out.println("Select a student to input grades:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName() + 
                               " (ID: " + students.get(i).getStudentId() + ")");
        }
        
        System.out.print("Enter student number: ");
        int studentChoice = scanner.nextInt() - 1;
        
        if (studentChoice >= 0 && studentChoice < students.size()) {
            students.get(studentChoice).inputGrades(scanner);
        } else {
            System.out.println("Invalid student selection.");
        }
    }

    private static void displayAllStudentGrades(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students added.");
            return;
        }

        for (Student student : students) {
            student.displayGrades();
            System.out.println("Average Grade: " + String.format("%.2f", student.calculateAverageGrade()));
            System.out.println("Passed: " + (student.hasPassed() ? "Yes" : "No"));
            System.out.println("-------------------");
        }
    }

    private static void calculateClassAverage(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students added.");
            return;
        }

        double totalClassAverage = 0;
        int studentsWithGrades = 0;

        for (Student student : students) {
            double avgGrade = student.calculateAverageGrade();
            if (avgGrade > 0) {
                totalClassAverage += avgGrade;
                studentsWithGrades++;
            }
        }

        if (studentsWithGrades > 0) {
            totalClassAverage /= studentsWithGrades;
            System.out.println("Class Average: " + String.format("%.2f", totalClassAverage));
            System.out.println("Passed Students: " + 
                countPassedStudents(students) + "/" + students.size());
        } else {
            System.out.println("No grades entered yet.");
        }
    }

    private static int countPassedStudents(ArrayList<Student> students) {
        int passedCount = 0;
        for (Student student : students) {
            if (student.hasPassed()) {
                passedCount++;
            }
        }
        return passedCount;
    }
}
