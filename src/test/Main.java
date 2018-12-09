import org.danktronics.jdca.JDCA;
import org.danktronics.jdca.JDCABuilder;

public class Main {
    public static void main(String[] args) {
        JDCA jdca = new JDCABuilder()
                .addListener(new EventHandler())
                .build();
    }
}
