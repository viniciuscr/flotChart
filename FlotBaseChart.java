package br.com.arvus.sws.chart.flot;

import br.com.arvus.sws.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinicius on 14/04/14.
 */
public abstract class FlotBaseChart {

    private String id;
    private List<FlotSeriesItem> series;
    private String title;
    private String unit;
    private FlotConfiguration configuration;
    private String hardCoreConfigs;
    private String tooltipFormat;

    private FlotBaseChart(){}

    protected abstract void setup();

    public FlotBaseChart(String title){
        this.title  = title;
        constructor();
    }

    public FlotBaseChart(String title, String unit){
        this.title  = title;
        constructor();
        this.unit   = unit;
    }

    public FlotBaseChart(String title, String unit,List<FlotSeriesItem> series){
        this.title  = title;
        constructor();
        this.unit   = unit;
        this.series = series;
    }

    private void constructor(){
        this.id             = StringUtils.normalizeStr(this.title).replace(" ","_");
        this.configuration  = new FlotConfiguration();
        this.series         = new ArrayList<FlotSeriesItem>();
        this.unit           = "";
        this.baseSetup();
        this.setup();
    }

    private void baseSetup(){

        //adicionar configs de grid
        FlotConfiguration configuration = new FlotConfiguration();
        configuration.setName("grid");

        //permitir usar hover no grid
        FlotConfiguration subConfiguration = new FlotConfiguration();
        subConfiguration.setName("hoverable");
        subConfiguration.setValue("true");
        configuration.addSubConfiguration(subConfiguration);

        this.addConfiguration(configuration);

        //adicionar xaxis
        configuration = new FlotConfiguration();
        configuration.setName("xaxis");

        //Adicionando meses em português
        subConfiguration = new FlotConfiguration();
        subConfiguration.setName("monthNames");
        subConfiguration.setValue(" [\"Jan\", \"Fev\", \"Mar\", \"Abr\", \"Mai\", \"Jun\", \"Jul\", \"Ago\", \"Set\", \"Out\", \"Nov\", \"Dez\"]");
        configuration.addSubConfiguration(subConfiguration);

        //formato da data
        subConfiguration = new FlotConfiguration();
        subConfiguration.setName("timeformat");
        subConfiguration.setValue("\"%d/%m/%Y\"");
        configuration.addSubConfiguration(subConfiguration);

        this.addConfiguration(configuration);


        //tooltip default
        this.tooltipFormat = "item.series.label + ' <br/> ' + y + ' " + this.unit+"'";
    }

    public String plot (){
        if(this.series.get(0).getxType() == FlotSeriesItem.xTypes.STRING) {
            this.configureXAxisNames();
         //   setAxisAsCategory(); // não funciona direito
        }
        if(this.series.get(0).getxType() == FlotSeriesItem.xTypes.DATE) {
            setAxisAsDate();
        }

        StringBuilder builder = new StringBuilder();
    //    builder.append("$(document).ready(function(){ ");
        builder.append("   $.plot('#").append(this.id).append("', ");
        builder.append(this.jsonSeries());
        builder.append(", ");
        if(this.hardCoreConfigs == null)
            builder.append(this.jsonConfigurations());
        else
            builder.append(this.hardCoreConfigs);
        builder.append(");\r\n");
        //criar tooltips
        builder.append(" $(\"<div id='tooltip'></div>\").css({ position: 'absolute', display: 'none', border: '1px solid #FFF', padding: '2px', 'background-color': '#CCC', opacity: 0.90, 'border-radius': '5px', 'z-index':'1010' }).appendTo('body');");
        builder.append("\r\n");
        //evento para tooltip
        builder.append(" $('#"+this.id+"').bind('plothover', function (event, pos, item) { if (item) { var x = item.datapoint[0].toFixed(2), y = item.datapoint[1].toFixed(2);  $('#tooltip').html("+this.tooltipFormat +").css({top: item.pageY, left: item.pageX+10}).fadeIn(200);} else {$('#tooltip').hide();}}); ");
   //     builder.append("})");

        return builder.toString();
    }

    private void setAxisAsDate() {


        FlotConfiguration configAxis  = new FlotConfiguration();
        configAxis.setName("xaxis");

        FlotConfiguration categoryX = new FlotConfiguration();
        categoryX.setName("mode");
        categoryX.setValue("\"time\"");
        configAxis.addSubConfiguration(categoryX);

        FlotConfiguration timeZone = new FlotConfiguration();
        timeZone.setName("timezone");
        timeZone.setValue("\"browser\"");
        configAxis.addSubConfiguration(timeZone);

//        Devemos setar esta opção em cada gráfico, pois ele "vareia"
//        categoryX = new FlotConfiguration();
//        categoryX.setName("minTickSize");
//        categoryX.setValue("[1, \"day\"]");
//        configAxis.addSubConfiguration(categoryX);
        FlotConfiguration oldConfig = this.configuration.getConfiguration("xaxis");
        if(oldConfig == null)
            this.addConfiguration(configAxis);
        else {
            oldConfig.addSubConfiguration(categoryX);
            oldConfig.addSubConfiguration(timeZone);
            this.configuration.updateSubConfiguration(oldConfig);
        }

    }

    private void setAxisAsCategory() {
        FlotConfiguration categoryX = new FlotConfiguration();
        categoryX.setName("mode");
        categoryX.setValue("\"categories\"");

        FlotConfiguration configAxis  = new FlotConfiguration();
        configAxis.setName("xaxis");
        configAxis.addSubConfiguration(categoryX);

        this.addConfiguration(configAxis);
    }

    public void addConfiguration(FlotConfiguration configuration){
        this.configuration.addSubConfiguration(configuration);
    }

    public FlotConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(FlotConfiguration configuration) {
        this.configuration = configuration;
    }

    private List<FlotConfiguration> getConfigurations() {
        return configuration.getConfigurations();
    }

    private String jsonConfigurations() {
        if(this.hardCoreConfigs != null && !this.hardCoreConfigs.isEmpty())
            return this.hardCoreConfigs;

        if(this.configuration.getConfigurations() == null)
            return "null";

        StringBuilder jsonConfigs = new StringBuilder("");
        jsonConfigs.append("{ ");
        for (int i = 0; i < this.getConfigurations().size(); i++) {
            jsonConfigs.append(this.getConfigurations().get(i).getJson());

            if(i < this.getConfigurations().size() -1){
                jsonConfigs.append(",");
            }
        }
        jsonConfigs.append(" }");

        return jsonConfigs.toString();
    }

    private String jsonSeries() {
        StringBuilder jsonSeries = new StringBuilder();
        jsonSeries.append("[ ");
        for (int i = 0; i < this.series.size(); i++) {

            jsonSeries.append(this.series.get(i).getJson());
            if(i < this.series.size() -1){
                jsonSeries.append(",");
            }
        }
        jsonSeries.append(" ]");
        return jsonSeries.toString();
    }

    private void configureXAxisNames(){

        String ticks = "[";
        for (Integer j = 0; j < this.series.get(0).getPoints().size(); j++) {
            ticks += "[" + j + ", " + this.series.get(0).getPoints().get(j).getIndex() + "]";
            for (int i = 0; i < this.series.size(); i++) {
                this.series.get(i).getPoints().get(j).setIndex(j);
            }

            if(j < this.series.size() -1){
                ticks += ",";
            }
        }
        ticks += "]";

        FlotConfiguration configAxis  = new FlotConfiguration();
        FlotConfiguration configTicks = new FlotConfiguration();
        configTicks.setName("ticks");
        configTicks.setValue(ticks);
        configAxis.setName("xaxis");
        configAxis.addSubConfiguration(configTicks);

        FlotConfiguration oldConfig = this.configuration.getConfiguration("xaxis");
        if(oldConfig == null)
            this.addConfiguration(configAxis);
        else {
            oldConfig.addSubConfiguration(configTicks);
            this.configuration.updateSubConfiguration(oldConfig);
        }


    }

    public void setHardCoreConfigs(String hardCoreConfigs) {
        this.hardCoreConfigs = hardCoreConfigs;
    }

    public List<FlotSeriesItem> getSeries() {
        return series;
    }

    public void setSeries(List<FlotSeriesItem> series) {
        this.series = series;
    }

    public String getTitle() {
        return title;
    }

    public String getUnit() {
        return unit;
    }

    /*
    * Função para criar compatibilidade com o componente WidgetToDashboardPanelComponent
    * */
    public String getChartReplotJQuery(){
        return this.plot();
    }

    /*
    * Função para criar compatibilidade com o componente WidgetToDashboardPanelComponent
    * */
    public String getId() {
        return id;
    }

    public void setTooltipFormat(String tooltipFormat) {
        this.tooltipFormat = tooltipFormat;
    }


}
