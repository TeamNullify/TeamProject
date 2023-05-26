import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UITest {
    private UI ui;

    @Before
    public void setUp() {
        ui = new UI();
    }

    @After
    public void tearDown() {
        ui = null;
    }

    @Test
    public void testIsValidEmail() {
        assertTrue(ui.isValidEmail("user@example.com"));
        assertFalse(ui.isValidEmail("user@example"));
        assertFalse(ui.isValidEmail("userexample.com"));
        assertFalse(ui.isValidEmail("user@"));
        assertFalse(ui.isValidEmail("@example.com"));
    }

    @Test
    public void testIsValidUsername() {
        assertTrue(ui.isValidUsername("user123"));
        assertFalse(ui.isValidUsername(""));
        assertFalse(ui.isValidUsername("a"));
        assertFalse(ui.isValidUsername("user@example.com"));
        assertFalse(ui.isValidUsername("user_123!"));
    }
}

