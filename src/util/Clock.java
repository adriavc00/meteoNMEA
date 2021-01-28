package util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

/**
 * Class that implements an observable clock that updates its values in a thread.
 *
 * @author Adria V.
 * @author Felipe Z.
 */
public class Clock extends Task<Void> {

    // Clock of the system.
    private LocalDateTime dateTime;

    // Properties of the clock
    private final SimpleStringProperty time;
    private final SimpleStringProperty date;
    private final SimpleStringProperty day;

    /**
     * Returns a clock that has to be initialized to be working.
     */
    public Clock() {
        // Initialize dateTime as now to set day and date
        this.dateTime = LocalDateTime.now();
        // Set day and date to the moment and don't update it unless it's necessary
        this.day = new SimpleStringProperty(translate(dateTime.getDayOfWeek()));
        this.date = new SimpleStringProperty(dateTime.toLocalDate().format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        // Create time property.
        this.time = new SimpleStringProperty();

        // Listener to time property to change date and day
        time.addListener((observable, oldV, newV) -> {
            if ("00:00:00".equals(newV)) {
                date.set(dateTime.toLocalDate().format(DateTimeFormatter.ofLocalizedDate(
                        FormatStyle.SHORT)));
                day.set(dateTime.getDayOfWeek().name());
            }
        });
    }

    /**
     * Starts the task of the Clock.
     */
    public void initClock() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Property of the time of the clock
     *
     * @return String property that represents the time in format HH:MM:SS.
     */
    public SimpleStringProperty timeProperty() {
        return time;
    }

    /**
     * Property of the date of the clock
     *
     * @return String property that represents the date in format DD-MM-YYYY.
     */
    public SimpleStringProperty dateProperty() {
        return date;
    }

    /**
     * Property of the day of the clock
     *
     * @return String property that represents the day of the week in spanish.
     */
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

    private String translate(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "lunes";
            case TUESDAY:
                return "martes";
            case WEDNESDAY:
                return "miércoles";
            case THURSDAY:
                return "jueves";
            case FRIDAY:
                return "viernes";
            case SATURDAY:
                return "sábado";
            case SUNDAY:
                return "domingo";
            default:
                throw new AssertionError(dayOfWeek.name());

        }
    }
}
