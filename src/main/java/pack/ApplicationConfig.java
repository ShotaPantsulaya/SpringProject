package pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

@Configuration
@ComponentScan("")
public class ApplicationConfig {

    public ApplicationConfig() {
    }

    @Autowired
    @Qualifier("ConsoleEventLogger")
    private ConsoleEventLogger consoleEventLogger;
    @Autowired
    private CombinedEventLogger combinedEventLogger;
    @Autowired
    private FileEventLogger fileEventLogger;

    public ConsoleEventLogger getConsoleEventLogger() {
        return consoleEventLogger;
    }

    public void setConsoleEventLogger(ConsoleEventLogger consoleEventLogger) {
        this.consoleEventLogger = consoleEventLogger;
    }

    public CombinedEventLogger getCombinedEventLogger() {
        return combinedEventLogger;
    }

    public void setCombinedEventLogger(CombinedEventLogger combinedEventLogger) {
        this.combinedEventLogger = combinedEventLogger;
    }

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        Properties properties = new Properties();
        File file = new File("src/client.properties");
        FileReader reader = new FileReader(file);
        properties.load(reader);
        propertyPlaceholderConfigurer.setProperties(properties);
        propertyPlaceholderConfigurer.setSystemPropertiesMode(1);
        propertyPlaceholderConfigurer.setIgnoreResourceNotFound(true);
        return propertyPlaceholderConfigurer;
    }

    @Bean
    public Map<EventType, EventLogger> eventTypeEventLoggerMap() {
        Map<EventType, EventLogger> map = new LinkedHashMap<>();
        map.put(EventType.INFO, consoleEventLogger);
        map.put(EventType.ERROR, combinedEventLogger);
        return map;
    }

    @Bean
    public List<EventLogger> eventLoggerList() {
        List<EventLogger> list = new ArrayList<>();
        list.add(consoleEventLogger);
        list.add(combinedEventLogger);
        return list;
    }

    @Bean
    public DateFormat dateFormat() {
        return DateFormat.getDateInstance();
    }

    @Bean
    public Date date() {
        return new Date();
    }


}
