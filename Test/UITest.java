package Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import View.UI;
import model.ValidChecker;

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
        assertTrue(ValidChecker.isValidEmail("user@example.com"));
        assertFalse(ValidChecker.isValidEmail("user@example"));
        assertFalse(ValidChecker.isValidEmail("userexample.com"));
        assertFalse(ValidChecker.isValidEmail("user@"));
        assertFalse(ValidChecker.isValidEmail("@example.com"));
    }

    @Test
    public void testIsValidUsername() {
        assertTrue(ValidChecker.isValidUsername("user123"));
        assertFalse(ValidChecker.isValidUsername(""));
        assertFalse(ValidChecker.isValidUsername("a"));
        assertFalse(ValidChecker.isValidUsername("user@example.com"));
        assertFalse(ValidChecker.isValidUsername("user_123!"));
    }
}

