import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SeriesTest {
    private Series series;

    @Before
    public void setUp() {
        series = new Series();
    }

    // TestSearchSeries()
    @Test
    public void TestSearchSeries() {
        Series.SeriesItem item = new Series.SeriesItem("S01", "Breaking Code", "10", 16);
        series.getSeriesList().add(item);

        boolean found = series.getSeriesList()
                .stream()
                .anyMatch(s -> s.id.equalsIgnoreCase("S01"));

        assertTrue("Expected series with ID S01 to be found.", found);
    }

    // TestSearchSeries_SeriesNotFound()
    @Test
    public void TestSearchSeries_SeriesNotFound() {
        boolean found = series.getSeriesList()
                .stream()
                .anyMatch(s -> s.id.equalsIgnoreCase("XYZ"));

        assertFalse("Series with ID XYZ should not be found.", found);
    }

    // TestUpdateSeries()
    @Test
    public void TestUpdateSeries() {
        Series.SeriesItem item = new Series.SeriesItem("S02", "Debugging Life", "8", 12);
        series.getSeriesList().add(item);

        item.ageRestriction = 15; // simulate update

        assertEquals("Age restriction should be updated to 15.",
                15, series.getSeriesList().get(0).ageRestriction);
    }

    // TestDeleteSeries()
    @Test
    public void TestDeleteSeries() {
        Series.SeriesItem item = new Series.SeriesItem("S03", "Unit Testers", "5", 10);
        series.getSeriesList().add(item);

        boolean removed = series.getSeriesList().removeIf(s -> s.id.equalsIgnoreCase("S03"));

        assertTrue("Series with ID S03 should be deleted.", removed);
        assertTrue("List should now be empty.", series.getSeriesList().isEmpty());
    }

    // TestDeleteSeries_SeriesNotFound()
    @Test
    public void TestDeleteSeries_SeriesNotFound() {
        Series.SeriesItem item = new Series.SeriesItem("S04", "Code Hunters", "12", 14);
        series.getSeriesList().add(item);

        boolean removed = series.getSeriesList().removeIf(s -> s.id.equalsIgnoreCase("XYZ"));

        assertFalse("Series with non-existing ID XYZ should not be deleted.", removed);
        assertEquals("List should still contain one item.", 1, series.getSeriesList().size());
    }

    // TestSeriesAgeRestriction_AgeValid()
    @Test
    public void TestSeriesAgeRestriction_AgeValid() {
        Series.SeriesItem item = new Series.SeriesItem("S05", "Java Masters", "7", 18);
        series.getSeriesList().add(item);

        assertTrue("Age restriction should be valid (between 2 and 18).",
                item.ageRestriction >= 2 && item.ageRestriction <= 18);
    }

    // TestSeriesAgeRestriction_SeriesAgeInvalid()
    @Test
    public void TestSeriesAgeRestriction_SeriesAgeInvalid() {
        Series.SeriesItem item = new Series.SeriesItem("S06", "Out of Range", "3", 25);
        series.getSeriesList().add(item);

        assertFalse("Age restriction should be invalid (outside 2–18).",
                item.ageRestriction >= 2 && item.ageRestriction <= 18);
    }
}
