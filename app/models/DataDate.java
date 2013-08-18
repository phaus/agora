/**
 * DateDim 10.12.2012
 *
 * @author Philipp Haussleiter
 *
 */
package models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import play.Logger;
import play.db.jpa.Model;

@Entity
public class DateDim extends Model {

    public Date fullDate;
    public String dateString;
    public int year;
    public int month;
    public int day;
    public int day_of_year;
    public int hour;
    public int minute;
    @OneToMany(mappedBy = "created")
    public List<Entry> createdEntries;
    @OneToMany(mappedBy = "updated")
    public List<Entry> updatedEntries;

    public DateDim(final Date date) {
        dateString = date.toString();
        createdEntries = new ArrayList<Entry>();
        updatedEntries = new ArrayList<Entry>();
        fullDate = date;
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault());
        c.setTime(fullDate);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        day_of_year = c.get(Calendar.DAY_OF_YEAR);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
    }

    public static DateDim getDataDateFromString(final String dateString) {
        Date date = getDateFromString(dateString);
        return getDataDate(date);
    }

    public static DateDim getDataDate(final Date date) {
        DateDim dd = DateDim.find(" fullDate = ?", date).first();
        if (date != null && dd == null) {
            dd = new DateDim(date);
            return dd.save();
        }
        return dd;
    }

    public static Date getDateFromString(final String dateString) {
        try {
            DateFormat df = new SimpleDateFormat(getDateFormat(dateString));
            return df.parse(dateString);
        } catch (ParseException ex) {
            Logger.error(dateString, ex.getLocalizedMessage());
        }
        return null;
    }

    public static List<DateDim> getDatesFor(int dateType, int dateValue) {
        switch (dateType) {
            case Calendar.YEAR:
                return DateDim.find(" year = ? ", dateValue).fetch();
            case Calendar.MONTH:
                return DateDim.find(" month = ? ", dateValue).fetch();
            case Calendar.DAY_OF_MONTH:
                return DateDim.find(" day = ? ", dateValue).fetch();
            case Calendar.DAY_OF_YEAR:
                return DateDim.find(" day_of_year = ? ", dateValue).fetch();
            case Calendar.HOUR_OF_DAY:
                return DateDim.find(" hour = ? ", dateValue).fetch();
            case Calendar.MINUTE:
            default:
                return DateDim.find(" minute = ? ", dateValue).fetch();
        }
    }

    public static int getLastYear() {
        DateDim dd = DateDim.find("order by fullDate ASC").first();
        if (dd != null) {
            return dd.year;
        } else {
            return Calendar.getInstance().get(Calendar.YEAR);
        }
    }

    @Override
    public String toString() {
        return String.format("%02d.%02d.%04d", day, (month + 1), year);
    }

    private static String getDateFormat(final String dateString) {
        int o = countOccurrencesOf(dateString, '-');
        if (o == 2) {
            Logger.debug("found - in " + dateString + " " + o + " times!");
            return "yyyy-MM-dd";
        }
        o = countOccurrencesOf(dateString, '.');
        Logger.debug("found . in " + dateString + " " + o + " times!");
        return "dd.MM.yyyy";
    }

    private static int countOccurrencesOf(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }
}
