package tech.hipsters;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class HashtableTest {

    @Test
    public void testPut() {
        var h = new Hashtable<String, Integer>();
        assertFalse(h.containsKey("me"));
        h.put("me", 1);
        assertTrue(h.containsKey("me"));
        assertEquals(Integer.valueOf(1), h.get("me"));

        h.put("me", 2);
        assertEquals(Integer.valueOf(2), h.get("me"));
    }

    @Test
    public void testAddMultiple() {
        var h = new Hashtable<String, Integer>();

        var keys = new ArrayList<Integer>();
        for (int x = 0; x < 50; x++) {
            keys.add(x);
        }

        for (var key : keys) {
            h.put(key.toString(), key * 1000);
        }

        for (var key : keys) {
            assertEquals(Integer.valueOf(key * 1000), h.get(key.toString()));
        }

        for (int x = 50; x < 100; x++) {
            assertFalse(h.containsKey(Integer.toString(x)));
        }
    }

    @Test
    public void testRemove() {
        var h = new Hashtable<String, Integer>();
        h.put("one", 1);
        h.put("two", 2);
        h.put("three", 3);

        assertEquals(Integer.valueOf(2), h.remove("two"));
        assertEquals(Integer.valueOf(1), h.get("one"));
        assertEquals(Integer.valueOf(3), h.get("three"));

        assertNull(h.get("two"));
        assertFalse(h.containsKey("two"));
    }

}
