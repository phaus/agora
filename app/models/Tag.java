/**
 * Tag 13.04.2012
 *
 * @author Philipp Haussleiter
 *
 */
package models;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Entity;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.cache.Cache;
import play.db.jpa.Model;

@Entity
public class Tag extends Model implements Comparable<Tag> {

    public String name;

    public static Set<Tag> findOrCreateTagsForText(String text) {
        Set<Tag> tags = new TreeSet<Tag>();
        for (String tag : getTagList(text)) {
            if (tag.trim().length() > 1) {
                tags.add(Tag.findOrCreateTagByName(tag.trim()));
            }
        }
        return tags;
    }

    public static String tagsToIds(Set<Tag> tags) {
        Set<Long> ids = new HashSet<Long>();
        for (Tag tag : tags) {
            ids.add(tag.id);
        }
        return StringUtils.join(ids, ", ");
    }

    public static String tagsToNames(Set<Tag> tags) {
        Set<String> names = new HashSet<String>();
        for (Tag tag : tags) {
            names.add(tag.name);
        }
        return StringUtils.join(names, ", ");
    }

    public static Tag findOrCreateTagByName(String name) {
        String key = "tag_" + name;
        Tag tag = Cache.get(key, Tag.class);
        if (tag == null) {
            tag = Tag.find("name = ?", name).first();
        }
        if (tag == null) {
            tag = new Tag();
            tag.name = name;
            tag.save();
            Logger.debug("creating Tag: " + tag.name);
        }
        Cache.set(key, tag, "1d");
        Logger.debug("using Tag: " + tag);
        return tag;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static Set<String> getTagList(String text) {
        text = cleanText(text);
        Set<String> tags = new HashSet<String>();
        for (String tag : text.split(" ")) {
            tags.add(guessText(tag));
        }
        return tags;
    }

    private static String guessText(String text) {
        Date date = DateDim.getDateFromString(text);
        if (date != null) {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            Logger.debug("converting %s to %s", text, df.format(date));
            return df.format(date);
        }
        if (text.indexOf(".") > 0) {
            try {
                Double dbl = Double.parseDouble(text);
                Logger.debug("converting %s to " + dbl, text);
                DecimalFormat df = new DecimalFormat("##0.00");
                return df.format(dbl).replace(".", ",");
            } catch (NumberFormatException nfe) {
                Logger.warn("cannot convert %s to Double", text);
            }
        }
        return text;
    }

    private static String cleanText(String text) {
        text = text.toLowerCase().replace(" - ", "-");
        String[] findList = {"#", "%", ":", "=", "\n", "_", " -", "- ", "?", ". ", " .", " ,", ", ", "!", "/", "\\", "\"", "„", "“", "(", ")", "[", "]", "{", "}", "<", ">"};
        for (String find : findList) {
            text = text.replace(find, " ");
        }
        return text.trim();
    }

    public int compareTo(Tag o) {
        if (o == null) {
            return 1;
        }
        return o.name.compareTo(this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Tag) {
            Tag t = (Tag) o;
            return t.name.equals(name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}
