/*
 * <p>Copyright (c) 2016, Authors</p>
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p>
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
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
