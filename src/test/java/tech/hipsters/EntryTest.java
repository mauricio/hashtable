package tech.hipsters;

import static org.junit.Assert.*;

import org.junit.Test;

public class EntryTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNewEntryNullKey() {
        new Entry<String,Object>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewEntryNullKeyAndValue() {
        new Entry<String,Object>(null, "some value");
    }

    @Test
    public void testNewEntry() {
        var entry = new Entry<>("one", 1);
        assertEquals("one", entry.getKey());
        assertEquals(Integer.valueOf(1), entry.getValue());

        entry.setValue(2);
        assertEquals(Integer.valueOf(2), entry.getValue());
    }

    @Test
    public void testEquals() {
        var entry = new Entry<>("one", 1);
        assertEquals(new Entry<>("one", 1), entry);
        assertEquals(new Entry<>("one", null), new Entry<>("one", null));

        assertNotEquals(new Entry<>("one", 2), entry);
        assertNotEquals(new Entry<>("two", 1), entry);
    }

}
