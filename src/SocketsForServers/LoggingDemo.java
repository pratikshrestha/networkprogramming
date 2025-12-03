import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingDemo {
    private final static Logger errorLogger = Logger.getLogger("errors");

    public static void main(String[] args) {
        try {
            int result = 10/ 0; // Attempting to divide by zero
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            errorLogger.log(Level.WARNING, "Error", e);
            errorLogger.info(new Date() + " : " + e);
        }
    }
}