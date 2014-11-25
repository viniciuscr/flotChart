package br.com.arvus.sws.chart.flot.ChartModels;

import br.com.arvus.sws.chart.flot.FlotBaseChart;
import br.com.arvus.sws.chart.flot.FlotConfiguration;
import br.com.arvus.sws.chart.flot.FlotSeriesItem;

import java.util.List;

/**
 * Created by vinicius on 23/04/14.
 */
public final class FlotBarChart extends FlotBaseChart {
    public FlotBarChart(String title) {
        super(title);
    }

    public FlotBarChart(String title, String unit) {
        super(title, unit);
    }

    public FlotBarChart(String title, String unit, List<FlotSeriesItem> series) {
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
        subConfiguration.setValue("0.03");
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




    }

    private void putBarSideBySide(){
        for (Integer i = 0; i < this.getSeries().size(); i++) {

            FlotConfiguration configuration = new FlotConfiguration();
            configuration.setName("bars");

            FlotConfiguration subConfiguration = new FlotConfiguration();
            subConfiguration.setName("order");
            subConfiguration.setValue(String.valueOf(i + 1));
            configuration.addSubConfiguration(subConfiguration);
            this.getSeries().get(i).addConfiguration(configuration);
        }

    }

    public String plot(){
        this.putBarSideBySide();
        return super.plot();
    }


}
