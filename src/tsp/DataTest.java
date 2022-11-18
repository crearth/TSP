package tsp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;


public class DataTest {
    private Data berlin;
    private String berlinFileName;

    @Before
    public void setUp() throws FileNotFoundException {
        berlin = new Data("berlin52");
    }

    @Test
    public void testGetFile() {
        assertEquals(berlin.getFile(), new File("../data/berlin52.tsp"));
    }

    @Test
    public void testReadFile() {
        assertEquals(berlin.name, "berlin52");
        assertEquals(berlin.type, "TSP");
        assertEquals(berlin.dimension, 52);
        assertEquals(berlin.edgeWeightType, "EUC_2D");
    }
}
