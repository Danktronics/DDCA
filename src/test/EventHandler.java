import org.danktronics.jdca.entities.User;
import org.danktronics.jdca.events.ErrorEvent;
import org.danktronics.jdca.events.Event;
import org.danktronics.jdca.entities.EventListener;
import org.danktronics.jdca.events.PresenceUpdateEvent;
import org.danktronics.jdca.events.ReadyEvent;

public class EventHandler implements EventListener {
    @Override
    public void onEvent(Event event) {
        if (event instanceof ReadyEvent) {
            System.out.println("Connected");
        } else if (event instanceof PresenceUpdateEvent) {
            PresenceUpdateEvent presenceUpdateEvent = (PresenceUpdateEvent) event;
            User user = presenceUpdateEvent.getUser();
            System.out.printf("User presence updated: id: %s, username: %s, new presence: %s", user.getId(), user.getUsername(), "idk");
        } else if (event instanceof ErrorEvent) {
            ((ErrorEvent) event).getException().printStackTrace();
        }
    }
}
