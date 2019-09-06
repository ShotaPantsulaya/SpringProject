package pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component("pack.CacheFileLogger")
public class CacheFileLogger extends FileEventLogger {

    private int cacheSize;
    private List<Event> eventCache = new ArrayList<>();

    @Autowired
    public CacheFileLogger(@Value("file.txt") String fileName,@Value("5") int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public List<Event> getEventCache() {
        return eventCache;
    }

    public void setEventCache(List<Event> eventCache) {
        this.eventCache = eventCache;
    }

    @Override
    public void logEvent(Event event) {
        eventCache.add(event);
        if (eventCache.size() >= cacheSize) {
            eventCache.forEach(super::logEvent);
            eventCache.clear();
        }
    }

    @PreDestroy
    public void destroy() {
        if (!eventCache.isEmpty()) {
            eventCache.forEach(super::logEvent);
        }
    }
}
