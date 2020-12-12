package ua.edu.ucu;

import org.junit.Before;
import org.junit.Test;
import ua.edu.ucu.stream.*;

import static org.junit.Assert.*;

public class AsIntStreamTest {

    private IntStream arrayStream;
    private IntStream emptyStream;
    private IntStream streamWithSingleElement;

    @Before
    public void init() {
        int[] array = {-1, 0, 1, 1, 2, 3};
        int[] emptyArray = {};
        int[] arrayWithSingleElement = {5};
        this.arrayStream = AsIntStream.of(array);
        this.emptyStream = AsIntStream.of(emptyArray);
        this.streamWithSingleElement =
                AsIntStream.of(arrayWithSingleElement);

    }

    @Test
    public void testAverageArray() {
        double expected = 1.0;
        double actual = arrayStream.average();
        assertEquals(expected, actual, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageEmpty() {
        emptyStream.average();
    }

    @Test
    public void testSumArray() {
        int expected = 6;
        int actual = arrayStream.sum();
        assertEquals(expected, actual);
    }

    @Test
    public void testSumArrayWithSingleElement() {
        int expected = 5;
        int actual = streamWithSingleElement.sum();
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSumEmpty() {
        emptyStream.sum();
    }

    @Test
    public void testMaxArray() {
        int expected = 3;
        int actual = arrayStream.max();
        assertEquals(expected, actual);
    }

    @Test
    public void testMinArrayWithSingleElement() {
        int expected = 5;
        int actual = streamWithSingleElement.min();
        assertEquals(expected, actual);
    }

    @Test
    public void testCountArray() {
        int expected = 6;
        int actual = (int) arrayStream.count();
        assertEquals(expected, actual);
    }

    @Test
    public void testCountEmpty() {
        int expected = 0;
        int actual = (int) emptyStream.count();
        assertEquals(expected, actual);
    }

    @Test
    public void testFilterArray() {
        int [] expected = {2, 3};
        int [] actual = arrayStream.filter(x -> x > 1).
                filter(x -> x <= 3).toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testFilterArrayWithSingleElement() {
        int [] expected = {5};
        int [] actual = streamWithSingleElement.
                filter(x -> x >= 2).toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testForEachArray() {
        StringBuilder line = new StringBuilder();
        arrayStream.forEach(x -> line.append(x));
        assertEquals("-101123", line.toString());
    }

    @Test
    public void testMapArray() {
        int [] expected = {0, 1, 2, 2, 3, 4};
        int [] actual = arrayStream.map(x -> x + 1).toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testMapEmpty() {
        int [] expected = {};
        int [] actual = emptyStream.map(x -> x + 2).toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testFlatMapArray() {
        int [] expected = {0, 2, 4, 1, 3, 5};
        int [] actual = arrayStream.filter(x -> x > 1).
                flatMap(x -> AsIntStream.of(x - 2,
                        x, x + 2)).toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testFlatMapEmpty() {
        int [] expected = {};
        int [] actual = emptyStream.filter(x -> x > 1).
                flatMap(x -> AsIntStream.of(x - 2, x, x + 2)).toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testReduceArray() {
        int expected = 35;
        int actual = arrayStream.reduce(5,
                (x, y) -> (x + 2) + (y + 2));
        assertEquals(expected, actual);
    }

    @Test
    public void testReduceEmpty() {
        int expected = 5;
        int actual = emptyStream.reduce(5,
                (x, y) -> (x + 2) + (y + 2));
        assertEquals(expected, actual);
    }
}
