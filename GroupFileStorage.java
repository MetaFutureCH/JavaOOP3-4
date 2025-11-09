package sample;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class GroupFileStorage {

    public void saveGroupToCSV(Group gr) {
        if (gr == null) throw new IllegalArgumentException("Group is null");
        String fileName = sanitize(gr.getGroupName()) + ".csv";
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), StandardCharsets.UTF_8))) {

            // шапка (необов’язкова)
            pw.println("name,lastName,gender,id,groupName");

            for (Student s : gr.getStudents()) {
                if (s == null) continue;
                String gender = (s.getGender() == null) ? "" : s.getGender().name();
                String gName = (s.getGroupName() == null) ? gr.getGroupName() : s.getGroupName();
                pw.printf("%s,%s,%s,%d,%s%n",
                        nvl(s.getName()), nvl(s.getLastName()), gender, s.getId(), nvl(gName));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Group loadGroupFromCSV(File file) {
        if (file == null || !file.isFile()) throw new IllegalArgumentException("Bad file");
        String groupName = stripExt(file.getName());
        Group group = new Group(groupName);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { // пропустити шапку
                    first = false;
                    if (line.toLowerCase().startsWith("name,last")) continue;
                }
                if (line.isBlank()) continue;
                String[] c = line.split(",", -1); // просте розбиття
                if (c.length < 5) continue;

                String name = c[0];
                String last = c[1];
                String genderStr = c[2];
                int id = Integer.parseInt(c[3].trim());
                String gName = c[4].isBlank() ? groupName : c[4];

                Gender gender = genderStr.isBlank() ? null : Gender.valueOf(genderStr);
                Student st = new Student(name, last, gender, id, gName);
                group.addStudent(st);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GroupOverflowException e) {
            throw new RuntimeException("Group is full while loading CSV", e);
        }
        return group;
    }

    public File findFileByGroupName(String groupName, File workFolder) {
        if (groupName == null || workFolder == null || !workFolder.isDirectory()) return null;
        File f = new File(workFolder, sanitize(groupName) + ".csv");
        return f.isFile() ? f : null;
    }

    // helpers
    private static String nvl(String s) { return s == null ? "" : s; }
    private static String sanitize(String s) {
        if (s == null || s.isBlank()) return "group";
        return s.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
    }
    private static String stripExt(String name) {
        int i = name.lastIndexOf('.');
        return (i > 0) ? name.substring(0, i) : name;
    }
}
