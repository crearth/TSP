package tsp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;


public class DataTest {
    private Data berlin;
    private String berlinFileName;

    @Before
    public void setUp() {
        berlin = new Data("berlin52");
    }

    @Test
    public void testGetFile() {
        assertEquals(berlin.getFile(), new File("../data/berlin52.tsp"));
    }
}
