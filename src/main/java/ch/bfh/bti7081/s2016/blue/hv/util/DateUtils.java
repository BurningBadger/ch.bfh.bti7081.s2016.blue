package ch.bfh.bti7081.s2016.blue.hv.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * DateUtils. Utils class to handle date and time functions.
 * 
 * @author nicolasschmid
 */
public class DateUtils {

    /**
     * Current time plus one hour.
     */
    public static Date nowPlusOneHour() {
	LocalDateTime ldt = LocalDateTime.now().plusHours(1);
	Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
	return Date.from(instant);
    }

    /**
     * Current date and time plus five years.
     */
    public static Date nowPlusFiveYears() {
	LocalDateTime ldt = LocalDateTime.now().plusYears(5);
	Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
	return Date.from(instant);
    }

    /**
     * Get the current time and date.
     */
    public static Date now() {
	LocalDateTime ldt = LocalDateTime.now();
	Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
	return Date.from(instant);
    }

    public static String formatTime(Date date) {
	DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	return timeFormat.format(date);
    }

    public static String formatDate(Date date) {
	DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
	return timeFormat.format(date);
    }

}
