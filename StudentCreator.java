package sample;

import java.util.Scanner;

public class StudentCreator {
	
	public Student createStudent() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter student's first name: ");
		String name = scanner.nextLine();
		
		System.out.println("Enter student's last name: ");
		String lastName = scanner.nextLine();
		
		System.out.println("Enter student's gender (MALE/FEMALE): ");
		Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());
		
		System.out.print("Enter student's ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter student's group name: ");
        String groupName = scanner.nextLine();

        return new Student(name, lastName, gender, id, groupName);
    }

    public void addStudentToGroup(Group group) {
        try {
            Student student = createStudent();
            group.addStudent(student);
            System.out.println("Student successfully added to the group!");
        } catch (GroupOverflowException e) {
            System.out.println("Failed to add student to the group: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
}
