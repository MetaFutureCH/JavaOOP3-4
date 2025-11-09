package sample;

import java.util.Scanner;
import java.io.File;

public class Main {
	public static void main(String[] args) {

		Group group = new Group("Java Developers");
		
		GroupFileStorage gfs = new GroupFileStorage();

		StudentCreator creator = new StudentCreator();

		try {
			group.addStudent(new Student("Student1", "Fam1", Gender.MALE, 1, "Group1"));
			group.addStudent(new Student("Student2", "Fam2", Gender.FEMALE, 2, "Group1"));
			group.addStudent(new Student("Student3", "Fam3", Gender.MALE, 3, "Group1"));
			group.addStudent(new Student("Student4", "Nam4", Gender.FEMALE, 4, "Group1"));
			group.addStudent(new Student("Student5", "Fam5", Gender.MALE, 5, "Group1"));
			group.addStudent(new Student("Student6", "Fam6", Gender.FEMALE, 6, "Group1"));
			group.addStudent(new Student("Student7", "Fam3", Gender.MALE, 7, "Group1"));
		} catch (GroupOverflowException e) {
			System.out.println("Error adding students: " + e.getMessage());
		}

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Add student");
			System.out.println("2. Search student by last name");
			System.out.println("3. Remove student by ID");
			System.out.println("4. Show group");
			System.out.println("5. Sort students by last name");
			System.out.println("6. Save group to CSV");
			System.out.println("7. Load group from CSV (by group name in current folder)");
			System.out.println("8. Exit");


			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				creator.addStudentToGroup(group);
				break;

			case 2:

				System.out.print("Enter student's last name to search: ");
				String searchLastName = scanner.nextLine();

				try {
					Student foundStudent = group.searchStudentByLastName(searchLastName);
					System.out.println("Student found: " + foundStudent);
				} catch (StudentNotFoundException e) {
					System.out.println("Error: " + e.getMessage());
				}
				break;

			case 3:

				System.out.print("Enter student's ID to remove: ");
				int removeId = scanner.nextInt();
				scanner.nextLine();

				if (group.removeStudentByID(removeId)) {
					System.out.println("Student removed successfully.");
				} else {
					System.out.println("Student with ID " + removeId + " not found.");
				}
				break;

			case 4:

				System.out.println("\nCurrent group: " + group);
				break;

			case 5:

				group.sortStudentsByLastName();
				System.out.println("Students sorted by last name.");
				break;

			case 6:
			    try {
			        gfs.saveGroupToCSV(group);
			        System.out.println("Saved to CSV as \"" + group.getGroupName() + ".csv\"");
			    } catch (RuntimeException ex) {
			        System.out.println("Save error: " + ex.getMessage());
			    }
			    break;

			case 7:
			    try {
			        File f = gfs.findFileByGroupName(group.getGroupName(), new File("."));
			        if (f == null) {
			            System.out.println("CSV file not found for group \"" + group.getGroupName() + "\" in current folder.");
			        } else {
			            Group loaded = gfs.loadGroupFromCSV(f);
			            System.out.println("Loaded group from CSV:");
			            System.out.println(loaded);
			            group = loaded; // якщо хочеш замінити поточну групу
			        }
			    } catch (RuntimeException ex) {
			        System.out.println("Load error: " + ex.getMessage());
			    }
			    break;


			case 8:
				System.out.println("Goodbye!");
				scanner.close();
				return;

			default:
				System.out.println("Invalid choice. Try again.");
			}
		}
	}
}
