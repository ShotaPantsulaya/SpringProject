package pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class App {

    private Client client;
    private EventLogger defaultLogger;
    private final Map<EventType, EventLogger> eventLoggers;

    public App(@Autowired Client client,@Autowired @Qualifier("pack.CacheFileLogger") EventLogger defaultLogger, @Autowired Map<EventType, EventLogger> eventLoggers) {
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.eventLoggers = eventLoggers;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        App app = ctx.getBean(App.class);
        Event event = ctx.getBean(Event.class);
        app.logEvent(event, EventType.ERROR);
    }

    public void logEvent(Event event, EventType type) {
        EventLogger logger = eventLoggers.get(type);
        if (logger == null) {
            logger = defaultLogger;
        }
        logger.logEvent(event);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public EventLogger getDefaultLogger() {
        return defaultLogger;
    }

    public void setDefaultLogger(EventLogger defaultLogger) {
        this.defaultLogger = defaultLogger;
    }

}