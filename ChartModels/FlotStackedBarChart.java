package br.com.arvus.sws.chart.flot.ChartModels;

import br.com.arvus.sws.chart.flot.FlotBaseChart;
import br.com.arvus.sws.chart.flot.FlotConfiguration;
import br.com.arvus.sws.chart.flot.FlotSeriesItem;

import java.util.List;

/**
 * Created by vinicius on 23/04/14.
 */
public final class FlotStackedBarChart extends FlotBaseChart {
    public FlotStackedBarChart(String title) {
        super(title);
    }

    public FlotStackedBarChart(String title, String unit) {
        super(title, unit);
    }

    public FlotStackedBarChart(String title, String unit, List<FlotSeriesItem> series) {
        super(title, unit, series);
    }

    @Override
    protected void setup() {
        FlotConfiguration configuration = new FlotConfiguration();
        configuration.setName("bars");

        //mostrar as barras
        FlotConfiguration subConfiguration = new FlotConfiguration();
        subConfiguration.setName("show");
        subConfiguration.setValue("true");
        configuration.addSubConfiguration(subConfiguration);

        //alinhar barras ao centro do valor de X
        subConfiguration = new FlotConfiguration();
        subConfiguration.setName("align");
        subConfiguration.setValue("\"center\"");
        configuration.addSubConfiguration(subConfiguration);

        //largura das barras
        subConfiguration = new FlotConfiguration();
        subConfiguration.setName("barWidth");
        subConfiguration.setValue("12*24*60*60*10");
        configuration.addSubConfiguration(subConfiguration);

        //cores sólidas
        subConfiguration = new FlotConfiguration();
        subConfiguration.setName("fillColor");
        configuration.addSubConfiguration(subConfiguration);

        FlotConfiguration subSubConfiguration = new FlotConfiguration();
        subSubConfiguration.setName("colors");
        subSubConfiguration.setValue("[{ opacity: 1  }, {  opacity: 0.8  }]");
        subConfiguration.addSubConfiguration(subSubConfiguration);

        this.addConfiguration(configuration);

       this.addConfiguration(configuration);


        configuration = new FlotConfiguration();
        configuration.setName("series");

        //barras empilhadas
        subConfiguration = new FlotConfiguration();
        subConfiguration.setName("stack");
        subConfiguration.setValue("true");
        configuration.addSubConfiguration(subConfiguration);

        this.addConfiguration(configuration);




    }

}
