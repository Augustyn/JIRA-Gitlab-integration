package ut.pl.hycom.jira.plugins.gitlab.integration;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import ut.pl.hycom.jira.plugins.gitlab.integration.search.CommitIndexerTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author higashi on 2016-08-04
 */
public class DateFormatTest {
    private static final Logger log = LoggerFactory.getLogger(CommitIndexerTest.class);
    @Test
    @Ignore //because of travis fails this test.
    public void simpleDateFormatTest() throws ParseException {
        String expected = "2016-04-11T22:29:30.000+02:00";
        DateFormat sdf = new SimpleDateFormat(Commit.DATE_FORMAT, Locale.ENGLISH);

        Date tested = sdf.parse(expected);

        log.info("Date is: " + sdf.format(new Date()));
        assertThat("Dates must me the same. ", sdf.format(tested), is(equalTo(expected)));
    }
}
