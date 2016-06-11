package pl.hycom.jira.plugins.gitlab.integration.service.processors;


import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.exceptions.ProcessException;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Karol Joachimiak on 07.06.2016.
 */
@Component
public class IssueWorklogChangeProcessor  implements ProcessorInterface{
    @Getter
    private StringBuilder extractedMessage = new StringBuilder();
    @Override
    public void execute(@NotNull Commit commitInfo) throws ProcessException {
        String messageWithoutSpaces = commitInfo.getMessage().replaceAll("\\s+","");
        Pattern timePattern = Pattern.compile("([0-9]+y)?([0-9]+w)?([0-9]+d)?([0-9]+h)?([0-9]+m)?([0-9]+s)?");
        Matcher matcher = timePattern.matcher(messageWithoutSpaces);
        while(matcher.find()){
            extractedMessage.append(matcher.group(0));
        }
    }
}
