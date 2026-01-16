import javax.swing.*;
import java.util.ArrayList;

public class Series {
    public static class SeriesItem {
        public String id;
        public String name;
        public String episode;
        public int ageRestriction;

        public SeriesItem(String id, String name, String episode, int ageRestriction) {
            this.id = id;
            this.name = name;
            this.episode = episode;
            this.ageRestriction = ageRestriction;
        }

        @Override
        public String toString() {
            return "Series ID: " + id +
                   "\nSeries Name: " + name +
                   "\nAge Restriction: " + ageRestriction +
                   "\nNumber of Episodes: " + episode;
        }
    }

    private ArrayList<SeriesItem> seriesList = new ArrayList<>();

    public ArrayList<SeriesItem> getSeriesList() {
        return seriesList;
    }

    public void CaptureSeries() {
        String id;
        while (true) {
            id = JOptionPane.showInputDialog("Enter series ID:");
            if (id == null) return;

            boolean exists = false;
            for (SeriesItem s : seriesList) {
                if (s.id.equalsIgnoreCase(id)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                JOptionPane.showMessageDialog(null, "Series ID already exists. Try another.");
            } else {
                break;
            }
        }

        String name = JOptionPane.showInputDialog("Enter series name:");
        if (name == null) return;

        String episode = JOptionPane.showInputDialog("Enter number of episodes:");
        if (episode == null) return;

        int age;
        while (true) {
            String ageStr = JOptionPane.showInputDialog("Enter age restriction (2–18):");
            if (ageStr == null) return;
            try {
                age = Integer.parseInt(ageStr);
                if (age >= 2 && age <= 18) break;
                else JOptionPane.showMessageDialog(null, "Age must be between 2 and 18.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number. Try again.");
            }
        }

        seriesList.add(new SeriesItem(id, name, episode, age));
        JOptionPane.showMessageDialog(null, "Series captured successfully.");
    }

    public void SearchSeries() {
        String id = JOptionPane.showInputDialog("Enter series ID to search:");
        if (id == null) return;

        for (SeriesItem item : seriesList) {
            if (item.id.equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(null, item.toString(), "Series Found", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Series not found.");
    }

    public void UpdateSeries() {
        String id = JOptionPane.showInputDialog("Enter series ID to update:");
        if (id == null) return;

        for (SeriesItem item : seriesList) {
            if (item.id.equalsIgnoreCase(id)) {
                int newAge;
                while (true) {
                    String ageStr = JOptionPane.showInputDialog("Enter new age restriction (2–18):");
                    if (ageStr == null) return;
                    try {
                        newAge = Integer.parseInt(ageStr);
                        if (newAge >= 2 && newAge <= 18) break;
                        else JOptionPane.showMessageDialog(null, "Age must be between 2 and 18.");
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid number. Try again.");
                    }
                }
                item.ageRestriction = newAge;
                JOptionPane.showMessageDialog(null, "Age restriction updated.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Series not found.");}
   public void DeleteSeries() {
    String id = JOptionPane.showInputDialog("Enter series ID to delete:");
    if (id == null) return;

    for (int i = 0; i < seriesList.size(); i++) {
        if (seriesList.get(i).id.equalsIgnoreCase(id)) {
            seriesList.remove(i);
            JOptionPane.showMessageDialog(null, "Series deleted.");
            return;
        }
    }
    JOptionPane.showMessageDialog(null, "Series not found.");
}


    public void SeriesReport() {
        if (seriesList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No series captured yet.");
            return;
        }

        StringBuilder report = new StringBuilder("=== SERIES REPORT - 2025 ===\n");
        for (SeriesItem item : seriesList) {
            report.append(item.toString()).append("\n-------------------------\n");
        }
        JOptionPane.showMessageDialog(null, report.toString());
    }

    public void ExitSeriesApplication() {
        JOptionPane.showMessageDialog(null, "Exiting Series Application. Goodbye!");
    }
}
