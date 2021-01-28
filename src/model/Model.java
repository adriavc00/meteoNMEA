package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import net.sf.marineapi.nmea.event.AbstractSentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.MDASentence;

/**
 *
 * @author jsoler
 */
public class Model {

    //===================================================================
    //implementa el patron singleton
    // esto asegura que solamente se va a crear una instancia de la clase model
    // y se podra acceder a ella desde cualquier clase del proyecto
    private static Model model;

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    //===================================================================
    // CUIDADO, el objeto de la clase SentenceReader se ejecuta en un hilo
    // no se pueden modificar las propiedades de los objetos graficos desde
    // un metodo ejecutado en este hilo
    private SentenceReader reader;

    //===================================================================
    // propiedades que almacenan el ultimo valor de TWD, TWS, TEMP y presion
    //True Wind Dir -- direccion del viento respecto al norte
    private final DoubleProperty TWD = new SimpleDoubleProperty();

    // True Wind Speed -- intensidad de viento
    private final DoubleProperty TWS = new SimpleDoubleProperty();

    // TEMP -- temperatura
    private final DoubleProperty TEMP = new SimpleDoubleProperty();

    //Barometric Pressure -- presion barometrica
    private final DoubleProperty barometricPressure = new SimpleDoubleProperty();

    //Barometric Pressure Unit -- unidades de presion barometrica
    private final StringProperty barometricUnit = new SimpleStringProperty();

    // ESTRUCTURAS GRÁFICA TEMPERATURA
    private final XYChart.Series<String, Number> tempSerie = new XYChart.Series<>();

    private final ObservableList<XYChart.Data<String, Number>> tempList = tempSerie.getData();

    private final IntegerProperty sizeDataTempChart = new SimpleIntegerProperty();
    // ESTRUCTURAS GRÁFICA PRESIÓN
    private final XYChart.Series<String, Number> pressureSerie = new XYChart.Series<>();
    private final ObservableList<XYChart.Data<String, Number>> pressureList = pressureSerie.getData();
    private final IntegerProperty sizeDataPressureChart = new SimpleIntegerProperty();
    //==================================================================
    // estructuras para las graficas de viento
    // TWDSerie -- serie de datos de la direccion del viento TWD
    private final XYChart.Series<String, Number> TWDSerie = new XYChart.Series<>();
    private final ObservableList<XYChart.Data<String, Number>> TWDList = TWDSerie.getData();
    // TWSSerie -- serie de datos de la intensidad del viento TWS
    private final XYChart.Series<String, Number> TWSSerie = new XYChart.Series<>();
    private final ObservableList<XYChart.Data<String, Number>> TWSList = TWSSerie.getData();
    // tamanyo de la lista de valores a mostrar en las graficas del viento
    private final IntegerProperty sizeDataWindChart = new SimpleIntegerProperty();

    private Model() {
    }

    public DoubleProperty TWDProperty() {
        return TWD;
    }

    public DoubleProperty TWSProperty() {
        return TWS;
    }

    public DoubleProperty TEMPProperty() {
        return TEMP;
    }

    public DoubleProperty barometricPressureProperty() {
        return barometricPressure;
    }

    public String getBarometricUnit() {
        return barometricUnit.get();
    }

    public StringProperty barometricUnitProperty() {
        return barometricUnit;
    }

    public XYChart.Series<String, Number> getTempSerie() {
        return tempSerie;
    }

    public int getSizeDateTempChart() {
        return sizeDataTempChart.get();
    }

    public void setSizeTempChart(int value) {
        sizeDataTempChart.set(value);
    }

    public IntegerProperty sizeDataTempChartProperty() {
        return sizeDataTempChart;
    }

    public XYChart.Series<String, Number> getPressureSerie() {
        return pressureSerie;
    }

    public int getSizeDatePressureChart() {
        return sizeDataPressureChart.get();
    }

    public void setSizePressureChart(int value) {
        sizeDataPressureChart.set(value);
    }

    public IntegerProperty sizeDataPressureChartProperty() {
        return sizeDataPressureChart;
    }

    public XYChart.Series<String, Number> getTWDSerie() {
        return TWDSerie;
    }

    public XYChart.Series<String, Number> getTWSSerie() {
        return TWSSerie;
    }

    public int getSizeDateWindChart() {
        return sizeDataWindChart.get();
    }

    public void setSizeWindChart(int value) {
        sizeDataWindChart.set(value);
    }

    public IntegerProperty sizeDataWindChartProperty() {
        return sizeDataWindChart;
    }

    //===================================================================
    // METODO a invocar para RECIBIR DATOS desde el fichero de log
    public void addSentenceReader(File file) throws FileNotFoundException {

        InputStream stream = new FileInputStream(file);
        if (reader != null) {  // esto ocurre si ya estamos leyendo un fichero
            reader.stop();
        }
        reader = new SentenceReader(stream);

        //===============================================================
        //creamos los SentenceListener para cada tipo de trama y los registramos
        MDASentenceListener mda = new MDASentenceListener();
        reader.addSentenceListener(mda);

        //===============================================================
        //anadimos un exceptionListener para que capture las tramas que no tienen parser
        reader.setExceptionListener(e -> {
            System.out.println(e.getMessage());
        });

        // arrancamos el SentenceReader para que empieze a escuchar
        reader.start();
    }

    //===================================================================
    // clase listener que se encarga de tratar sentencias del tipo MDA
    class MDASentenceListener
            extends AbstractSentenceListener<MDASentence> {

        @Override
        public void sentenceRead(MDASentence sentence) {
            // anadimos el codigo necesario para guardar la información de la sentence
            TWD.set(sentence.getTrueWindDirection());
            TWS.set(sentence.getWindSpeedKnots());
            TEMP.set(sentence.getAirTemperature());
            barometricPressure.set(sentence.getSecondaryBarometricPressure());
            barometricUnit.set(sentence.getSecondaryBarometricPressureUnit() + "");

            //=================================================================
            // anyadimos el dato del viento a su respectiva lista y gestionamos el tamaño
            TWDList.add(new XYChart.Data<>(LocalDateTime.now().format(
                    DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)), TWD.get()));
            TWSList.add(new XYChart.Data<>(LocalDateTime.now().format(
                    DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)), TWS.get()));
            while (TWDList.size() > sizeDataWindChart.intValue()) {
                TWDList.remove(0);
                TWSList.remove(0);
            }
            //================================================================

            //Gestionar datos temperatura gráfica
            tempList.add(new XYChart.Data<>(LocalDateTime.now().format(
                    DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)), TEMP.get()));
            while (tempList.size() > sizeDataTempChart.intValue()) {
                tempList.remove(0);
            }

            //Gestionar datos presión gráfica
            pressureList.add(new XYChart.Data<>(LocalDateTime.now().format(
                    DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)), barometricPressure.get()));
            while (pressureList.size() > sizeDataPressureChart.intValue()) {
                pressureList.remove(0);
            }
        }
    }
}
