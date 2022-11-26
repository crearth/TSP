package tsp.ts;

import org.junit.Before;
import org.junit.Test;
import tsp.Graph;
import tsp.Tour;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class TourTest {
    private Graph gr666;
    private Graph att532;
    private Tour grTour;
    private Tour attTour;

    @Before
    public void setUp() throws FileNotFoundException {
        gr666 = new Graph("gr666");
        att532 = new Graph("att532");

        grTour = new Tour(gr666);
        attTour = new Tour(att532);
    }

    @Test
    public void testCanonicalTours() {
        //assertEquals(grTour.getCanonicalTourLength(), 423710);
        assertEquals(attTour.getCanonicalTourLength(), 309636);
    }
}
