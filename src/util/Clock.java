package util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

/**
 *
 * @author ADRIA - LP
 */
public class Clock extends Task<Void> {

    private LocalDateTime dateTime = LocalDateTime.now();

    private final SimpleStringProperty time = new SimpleStringProperty();
    private final SimpleStringProperty date = new SimpleStringProperty(
            dateTime.toLocalDate().format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
    private final SimpleStringProperty day = new SimpleStringProperty(translate(
            dateTime.getDayOfWeek()));

    public Clock() {
        time.addListener((observable, oldV, newV) -> {
            if ("00:00:00".equals(newV)) {
                date.set(dateTime.toLocalDate().format(DateTimeFormatter.ofLocalizedDate(
                        FormatStyle.SHORT)));
                day.set(dateTime.getDayOfWeek().name());
            }
        });
    }

    public void initClock() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public SimpleStringProperty dayProperty() {
        return day;
    }

    @Override
    protected Void call() throws Exception {
        do {
            dateTime = LocalDateTime.now();
            time.set(dateTime.toLocalTime().format(DateTimeFormatter.ofLocalizedTime(
                    FormatStyle.MEDIUM)));
            Thread.sleep(500);
            if (isCancelled()) {
                break;
            }
        } while (true);
        return null;
    }

    private static String translate(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "lunes";
            case TUESDAY:
                return "martes";
            case WEDNESDAY:
                return "miercoles";
            case THURSDAY:
                return "jueves";
            case FRIDAY:
                return "viernes";
            case SATURDAY:
                return "sabado";
            case SUNDAY:
                return "domingo";
            default:
                return null;
        }
    }
}
