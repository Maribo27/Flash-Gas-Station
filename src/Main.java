import controller.GasStation;
import javax.swing.*;
import java.io.IOException;

/**
 * Created by Maria on 08.09.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        try {
            String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(systemLookAndFeelClassName);
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Данное оформление не поддерживается на данной платформе");
        } catch (Exception e) {
            System.err.println("Невозможно применить данную тему оформления");
        }

        GasStation gasStation = new GasStation();
    }
}