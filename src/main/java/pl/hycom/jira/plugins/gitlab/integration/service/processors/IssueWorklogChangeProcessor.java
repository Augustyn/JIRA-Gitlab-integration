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
package pl.hycom.jira.plugins.gitlab.integration.service.processors;

import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.exceptions.ProcessException;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class IssueWorklogChangeProcessor implements ProcessorInterface {

    public static final Pattern TIME_PATTERN = Pattern.compile("([0-9]+y)?([0-9]+w)?([0-9]+d)?([0-9]+h)?([0-9]+m)?([0-9]+s)?");

    @Override
    public void execute(@NotNull Commit commitInfo) throws ProcessException {
        List<Time> timeFromMessage = getExtractedMsg(commitInfo);
        ApplicationUser userFromCommit = getJiraUser(commitInfo);
//        AuthenticationContext context = new
    }

    public ApplicationUser getJiraUser(Commit commitInfo) {
        String userEmail = commitInfo.getAuthorEmail();
        ApplicationUser user = UserUtils.getUserByEmail(userEmail);
        return user;
    }


    public List<Time> getExtractedMsg(@NotNull Commit commitInfo) {
        List<Time> timesList = new ArrayList<>();
        String messageWithoutSpaces = commitInfo.getMessage().replaceAll("\\s+", "");
        Matcher matcher = TIME_PATTERN.matcher(messageWithoutSpaces);
        String extractedMessage = "";
        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String group = matcher.group(i);
                if (StringUtils.isBlank(group)) {
                    continue;
                }
                String onlyChars = group.replaceAll("\\d+", "");
                group = matcher.group(i);
                String onlyDigits = group.replaceAll("(?i)[a-z]", "");
                Time time = Time.getTime(onlyChars, Integer.parseInt(onlyDigits));
                timesList.add(time);
                extractedMessage += onlyDigits + onlyChars;
            }
            log.info("Found worklog: " + extractedMessage);

        }
        return timesList;
    }

    public int getTimeConvertedToSeconds(List<Time> timeFromMessageList) {
        long summedTime = 0;
        for (Time time : timeFromMessageList) {
            switch (time) {
                case YEAR: {
                    summedTime += TimeUnit.DAYS.toSeconds(time.getFieldValue() * 365L);
                    break;
                }
                case WEEK: {
                    summedTime += TimeUnit.DAYS.toSeconds(time.getFieldValue() * 7L);
                    break;
                }
                case DAY: {
                    summedTime += TimeUnit.DAYS.toSeconds(time.getFieldValue());
                    break;
                }
                case HOUR: {
                    summedTime += TimeUnit.HOURS.toSeconds(time.getFieldValue());
                    break;
                }
                case MINUTE: {
                    summedTime += TimeUnit.MINUTES.toSeconds(time.getFieldValue());
                    break;
                }
                case SECOND: {
                    summedTime += TimeUnit.SECONDS.toSeconds(time.getFieldValue());
                    break;
                }
            }
        }
        return Math.toIntExact(summedTime);
    }

    public enum Time {
        YEAR("y"),
        WEEK("w"),
        DAY("d"),
        HOUR("h"),
        MINUTE("m"),
        SECOND("s");

        private String fieldName;
        private int fieldValue;

        public int getFieldValue() {
            return fieldValue;
        }

        public String getFieldName() {
            return fieldName;
        }

        Time(String fieldName2) {
            fieldName = fieldName2;
        }

        public static Time getTime(String fieldName, int fieldValue) {
            Time time = SECOND;
            switch (fieldName) {
                case "y": {
                    time = YEAR;
                    time.fieldValue = fieldValue;
                    break;
                }
                case "w": {
                    time = WEEK;
                    time.fieldValue = fieldValue;
                    break;
                }
                case "d": {
                    time = DAY;
                    time.fieldValue = fieldValue;
                    break;
                }
                case "h": {
                    time = HOUR;
                    time.fieldValue = fieldValue;
                    break;
                }
                case "m": {
                    time = MINUTE;
                    time.fieldValue = fieldValue;
                    break;
                }
                case "s": {
                    time = SECOND;
                    time.fieldValue = fieldValue;
                    break;
                }
            }
            return time;
        }
    }
}
