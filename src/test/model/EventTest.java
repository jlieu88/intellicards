package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e;
	private Calendar d;
    private Date dTime;
    private LocalDateTime d0;
    private LocalDateTime d1;
    private LocalDateTime d2;
    private ZoneId z = ZoneId.of("America/Los_Angeles");

	@BeforeEach
	public void runBefore() {
		e = new Event("Flashcard added to deck");
        d = Calendar.getInstance();
        dTime = d.getTime();
		d0 = LocalDateTime.ofInstant(d.toInstant(), z);
        d1 = d0.now(z).minusMinutes(1);
        d2 = d0.now(z).plusMinutes(1);
	}
	
	@Test
	public void testEvent() {
		assertEquals("Flashcard added to deck", e.getDescription());
        assertTrue(!(d0.isBefore(d1)) && (d0.isBefore(d2)));
	}

	@Test
	public void testToString() {
		assertEquals(dTime.toString() + "\n" + "Flashcard added to deck", e.toString());
	}

    @Test
    public void testDate() {
        assertTrue(e.getDate().equals(dTime));
        assertFalse(e.hashCode() == dTime.hashCode());
    }

    @Test
    public void testNullEquals() {
        assertFalse(e.equals(null));
    }

    @Test
    public void testOtherObject() {
        assertFalse(e.equals(d));
    }


}
