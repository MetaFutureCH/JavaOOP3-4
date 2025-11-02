package sample;

import java.util.Arrays;
import java.util.Comparator;

public class Group {

	private String groupName;
	private final Student[] students;

	public Group(String groupName) {
		super();
		this.groupName = groupName;
		this.students = new Student[10];
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Student[] getStudents() {
		return students;
	}

	public void addStudent(Student student) throws GroupOverflowException {

		for (int i = 0; i < students.length; i++) {
			if (students[i] == null) {
				students[i] = student;
				return;
			}

		}
		throw new GroupOverflowException("Невозможно добавить студента: группа " + groupName + " заполнена");

	}

	public Student searchStudentByLastName(String lastName) throws StudentNotFoundException {
		for (Student student : students) {
			if (student != null && student.getLastName().equals(lastName)) {
				return student;
			}
		}
		throw new StudentNotFoundException("Student with last name " + lastName + " not found.");
	}

	public boolean removeStudentByID(int id) {
		for (int i = 0; i < students.length; i++) {
			if (students[i] != null && students[i].getId() == id) {
				students[i] = null;
				return true;
			}
		}
		return false;
	}
	public void sortStudentsByLastName() {
        Arrays.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                if (s1 == null && s2 == null) {
                    return 0;
                }
                if (s1 == null) {
                    return 1;
                }
                if (s2 == null) {
                    return -1;
                }
                return s1.getLastName().compareTo(s2.getLastName());
            }
        });
    }


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Group name: ").append(groupName).append("\n");
		sb.append("Students:\n");

		for (int i = 0; i < students.length; i++) {
			Student s = students[i];
			if (s != null) {
				sb.append((i + 1)).append(") ").append(s).append("\n");
			} else {
				sb.append((i + 1)).append(") [пусто]\n");
			}
		}
		return sb.toString();
	}

}
