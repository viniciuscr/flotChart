package br.com.arvus.sws.chart.flot;

import org.apache.commons.lang.WordUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vinicius on 14/04/14.
 */
public final class FlotSeriesItem{

    private String seriesName;

    private FlotSeriesItem(){}

    protected static enum xTypes{STRING, DATE, NUMBER }

    private  xTypes xType;

    private List<FlotSeriesPoint<Serializable, Number>> points;

    private FlotConfiguration configuration;

    public FlotSeriesItem(String seriesName){
        this.seriesName     = seriesName;
        this.points         = new ArrayList<FlotSeriesPoint<Serializable, Number>>();
        this.configuration  = new FlotConfiguration();
    }

    public String getSeriesName() {
        return WordUtils.capitalize(seriesName.toLowerCase());
    }

    public void addSeries(String string, Number value) {
        this.points.add(new FlotSeriesPoint<Serializable, Number>("\""+string+"\"", value));
        this.xType = xTypes.STRING;
    }

    public void addSeries(Date date, Number value) {
        this.points.add(new FlotSeriesPoint<Serializable, Number>(date.getTime(), value));
        this.xType = xTypes.DATE;
    }

    public void addSeries(Number number, Number value) {
        this.points.add(new FlotSeriesPoint<Serializable, Number>(number, value));
        this.xType = xTypes.NUMBER;
    }

    public String getJson(){
        StringBuilder points = new StringBuilder("[ ");
        for (int i = 0; i < this.points.size(); i++) {
            points.append("[ " + this.points.get(i).getIndex() + "," + this.points.get(i).getValue() + " ]");
            if(i < this.points.size() -1){
                points.append(",");
            }
        }
        points.append(" ]");

        if(this.configuration.getConfigurations() != null && !this.configuration.getConfigurations().isEmpty())
            return "{ label: \""+ this.seriesName + "\" , data: " + points.toString() + ", "+this.jsonConfigurations()+" }";
        else
            return "{ label: \""+ this.seriesName + "\" , data: " + points.toString() + " }";
    }


    private String jsonConfigurations() {

        StringBuilder jsonConfigs = new StringBuilder("");
//        jsonConfigs.append("{ ");
        for (int i = 0; i < this.getConfigurations().size(); i++) {
            jsonConfigs.append(this.getConfigurations().get(i).getJson());

            if(i < this.getConfigurations().size() -1){
                jsonConfigs.append(",");
            }
        }
 //       jsonConfigs.append(" }");

        return jsonConfigs.toString();
    }

    public void addConfiguration(FlotConfiguration configuration){
        this.configuration.addSubConfiguration(configuration);
    }

    protected void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    protected List<FlotSeriesPoint<Serializable, Number>> getPoints(){
        return  this.points;
    }

    protected xTypes getxType() {
        return xType;
    }

    public List<FlotConfiguration> getConfigurations() {
        return configuration.getConfigurations();
    }


}
