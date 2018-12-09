import org.danktronics.jdca.JDCA;
import org.danktronics.jdca.JDCABuilder;
import org.danktronics.jdca.entities.exceptions.LoginException;

public class Main {
    public static void main(String[] args) {
        try {
            JDCA jdca = new JDCABuilder("token")
                    .addListener(new EventHandler())
                    .build();
        } catch(LoginException error) {
            System.out.println("Oops failed to login");
        }
    }
}
