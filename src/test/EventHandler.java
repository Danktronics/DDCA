import org.danktronics.jdca.events.Event;
import org.danktronics.jdca.entities.EventListener;
import org.danktronics.jdca.events.ReadyEvent;

public class EventHandler implements EventListener {
    @Override
    public void onEvent(Event event) {
        if (event instanceof ReadyEvent) {
            System.out.println("Connected");
        }
    }
}
