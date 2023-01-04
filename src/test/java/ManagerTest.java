import org.example.Manager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ManagerTest {

    Manager manager = new Manager();

    @Test
    public void testSetGet() {
        assertEquals("NULL", manager.getResult("GET a"));
        assertNull(manager.getResult("SET a 5"));
        assertEquals("5", manager.getResult("GET a"));
        assertNull(manager.getResult("SET a cat"));
        assertEquals("cat", manager.getResult("GET a"));
    }

    @Test
    public void testCount() {
        assertNull(manager.getResult("SET a cat"));
        assertNull(manager.getResult("SET b cat"));

        assertEquals("0", manager.getResult("COUNT dog"));
        assertEquals("2", manager.getResult("COUNT cat"));

        assertNull(manager.getResult("DELETE a"));
        assertEquals("1", manager.getResult("COUNT cat"));
        assertNull(manager.getResult("DELETE b"));
        assertEquals("0", manager.getResult("COUNT cat"));
    }

    @Test
    public void testRollback() {
        assertEquals("TRANSACTION NOT FOUND", manager.getResult("ROLLBACK"));
    }

    @Test
    public void testBeginRollback() {
        assertNull(manager.getResult("BEGIN"));
        assertNull(manager.getResult("ROLLBACK"));
    }


    @Test
    public void exampleOne() {
        assertEquals("NULL",manager.getResult("GET a"));
        assertNull(manager.getResult("SET a foo"));
        assertNull(manager.getResult("SET b foo"));
        assertEquals("2", manager.getResult("COUNT foo"));
        assertEquals("0", manager.getResult("COUNT bar"));
        assertNull(manager.getResult("DELETE a"));
        assertEquals("1", manager.getResult("COUNT foo"));
        assertNull(manager.getResult("SET b baz"));
        assertEquals("0", manager.getResult("COUNT foo"));
        assertEquals("baz", manager.getResult("GET b"));
    }

    @Test
    public void exampleTwo() {
        assertNull(manager.getResult("SET a foo"));
        assertNull(manager.getResult("SET a foo"));
        assertEquals("1", manager.getResult("COUNT foo"));
        assertEquals("foo", manager.getResult("GET a"));
        assertNull(manager.getResult("DELETE a"));
        assertEquals("NULL", manager.getResult("GET a"));
        assertEquals("0", manager.getResult("COUNT foo"));
    }

    @Test
    public void exampleThree() {
        assertNull(manager.getResult("BEGIN"));
        assertNull(manager.getResult("SET a foo"));
        assertEquals("foo", manager.getResult("GET a"));
        assertNull(manager.getResult("BEGIN"));
        assertNull(manager.getResult("SET a bar"));
        assertEquals("bar", manager.getResult("GET a"));
        assertNull(manager.getResult("SET a baz"));
        assertNull(manager.getResult("ROLLBACK"));
        assertEquals("foo", manager.getResult("GET a"));
        assertNull(manager.getResult("ROLLBACK"));
        assertEquals("NULL", manager.getResult("GET a"));
    }

    @Test
    public void exampleFour() {
        assertNull(manager.getResult("SET a foo"));
        assertNull(manager.getResult("SET b baz"));
        assertNull(manager.getResult("BEGIN"));
        assertEquals("foo", manager.getResult("GET a"));
        assertNull(manager.getResult("SET a bar"));
        assertEquals("1", manager.getResult("COUNT bar"));
        assertNull(manager.getResult("BEGIN"));
        assertEquals("1", manager.getResult("COUNT bar"));
        assertNull(manager.getResult("DELETE a"));
        assertEquals("NULL",manager.getResult("GET a"));
        assertEquals("0", manager.getResult("COUNT bar"));
        assertNull(manager.getResult("ROLLBACK"));
        assertEquals("bar",manager.getResult("GET a"));
        assertEquals("1", manager.getResult("COUNT bar"));
        assertNull(manager.getResult("COMMIT"));
        assertEquals("bar",manager.getResult("GET a"));
        assertEquals("baz",manager.getResult("GET b"));
    }
}