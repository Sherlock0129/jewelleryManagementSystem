package test;

import static org.junit.Assert.*;
import org.junit.*;
import tool.MySet;

public class TestMySet {
    MySet<String> t ;

    @Before
    public void setUp() throws Exception {
        t =new MySet<>();
        t.add("A");
        t.add("B");
        t.add("C");
        t.add("D");
    }

    @After
    public void tearDown() throws Exception {
        t=null;
    }

    @Test
    public void testAdd(){
        int size = t.size();
        t.add("A");
        assertEquals(t.size(),size);
        t.add("E");
        t.add("F");
        assertEquals(t.size(),size+2);
    }

    @Test
    public void testRemove() {
        int size = t.size();
        t.remove("A");
        assertEquals(t.size(),size-1);
    }

    @Test
    public void testContains() {
        assertTrue(t.contains("A"));
        assertTrue(t.contains("B"));
    }
}
