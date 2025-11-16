package dev.aksifar.workshop.tools.datetime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class DateTimeTools {

    @Tool(description = "Get the current date and time in the user's timezone.")
    public String getCurrentDateTime() {
        log.info("Calculating current date and time for timezone");

        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId())
                .toString();
    }
}
