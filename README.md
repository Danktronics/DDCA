# JDCA
In Development Danktronics Chat REST API wrapper for Java.

## Logging In
As with many other Java libraries the first step is using the JDCABuilder. Set the token by passing in a string to the constructor. Once everything is setup call the `build()` method to receive a JDCA object and login.

**Example**
```java
JDCA jdca = new JDCABuilder("token").build();
```

## Processing events from the gateway
The main point of this wrapper is to process events and respond accordingly. This wrapper is event driven and classes which implement the EventListener interface can be added as event handlers.

**Example**
```java
JDCA jdca = new JDCABuilder("token")
    .addListener(new EventHandler())
    .build();
```

**EventListener Example**
```java
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
```

## Download
Coming soon