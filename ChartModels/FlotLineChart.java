package br.com.arvus.sws.chart.flot.ChartModels;

import br.com.arvus.sws.chart.flot.FlotBaseChart;
import br.com.arvus.sws.chart.flot.FlotConfiguration;
import br.com.arvus.sws.chart.flot.FlotSeriesItem;

import java.util.List;

/**
 * Created by vinicius on 23/04/14.
 */
public final class FlotLineChart extends FlotBaseChart {
    public FlotLineChart(String title) {
        super(title);
    }

    public FlotLineChart(String title, String unit) {
        super(title, unit);
    }

    public FlotLineChart(String title, String unit, List<FlotSeriesItem> series) {
        super(title, unit, series);
    }

    @Override
    protected void setup() {
        FlotConfiguration configuration = new FlotConfiguration();
        configuration.setName("lines");

        //mostrar as linhas
        FlotConfiguration subConfiguration = new FlotConfiguration();
        subConfiguration.setName("show");
        subConfiguration.setValue("true");
        configuration.addSubConfiguration(subConfiguration);

        //alinhar ao centro do valor de X
        subConfiguration = new FlotConfiguration();
        subConfiguration.setName("align");
        subConfiguration.setValue("\"center\"");
        configuration.addSubConfiguration(subConfiguration);


        this.addConfiguration(configuration);

    }

}
