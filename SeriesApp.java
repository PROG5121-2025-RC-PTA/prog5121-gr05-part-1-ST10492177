import javax.swing.*;

public class SeriesApp {
    public static void main(String[] args) {
        Series seriesManager = new Series();

        String input = JOptionPane.showInputDialog(
            "LATEST SERIES - 2025\n\nPress 1 to launch menu or any other key to exit:"
        );

        if (input == null || !input.equals("1")) {
            seriesManager.ExitSeriesApplication();
            return;
        }

        while (true) {
            StringBuilder menuText = new StringBuilder("Please select one of the following menu items:\n");
            menuText.append("1. Capture a new series\n");
            menuText.append("2. Search for a series\n");
            menuText.append("3. Update age restriction\n");
            menuText.append("4. Delete a series\n");
            menuText.append("5. Print series report\n");
            menuText.append("6. Exit");

            String choice = JOptionPane.showInputDialog(menuText.toString());
            if (choice == null) {
                seriesManager.ExitSeriesApplication();
                return;
            }

            switch (choice) {
                case "1": seriesManager.CaptureSeries(); break;
                case "2": seriesManager.SearchSeries(); break;
                case "3": seriesManager.UpdateSeries(); break;
                case "4": seriesManager.DeleteSeries(); break;
                case "5": seriesManager.SeriesReport(); break;
                case "6": seriesManager.ExitSeriesApplication(); return;
                default: JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }
}
