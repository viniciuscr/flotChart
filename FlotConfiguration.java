package br.com.arvus.sws.chart.flot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinicius on 14/04/14.
 */
public class FlotConfiguration {

    String name;
    String value;

    List<FlotConfiguration> configurations;

    public List<FlotConfiguration> getConfigurations() {
        return configurations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value          = value;
        this.configurations = null;
    }

    public String getJson() {
        String json = "";

        if (this.value != null && this.configurations == null) {
            json =  this.name + ": " + this.value;
        }else{
            json += "{ ";
            if (this.value == null && this.configurations != null) {
                for (int i = 0; i < this.configurations.size(); i++) {

                    json += this.configurations.get(i).getJson();
                    if(i < this.configurations.size() -1){
                        json += ",";
                    }
                }
            }
            json += " }";

            json = this.name + ": " + json;
        }
        return json;
    }

    public FlotConfiguration getConfiguration(String name){
        for(FlotConfiguration configuration : this.configurations)
            if(configuration.getName().compareTo(name) == 0)
                return configuration;

        return null;
    }

    public void removeConfiguration(String name) {
        for (int i = 0; i < this.configurations.size(); i++) {
            if (this.configurations.get(i).getName().compareTo(name) == 0)
                this.configurations.remove(i);
        }
    }

    public void addSubConfiguration(FlotConfiguration subConfiguration){
        if(this.configurations == null){
            this.configurations = new ArrayList<FlotConfiguration>();
        }
        this.configurations.add(subConfiguration);
        this.value = null;
    }

    public void updateSubConfiguration(FlotConfiguration newConfiguration){
        for(FlotConfiguration oldConfiguration : this.configurations)
            if(oldConfiguration.getName().compareTo(newConfiguration.getName()) == 0){
                removeConfiguration(oldConfiguration.getName());
                break;
            }

        this.addSubConfiguration(newConfiguration);


    }
}
