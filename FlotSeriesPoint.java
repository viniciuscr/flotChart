package br.com.arvus.sws.chart.flot;

import java.io.Serializable;

/**
 *
 * @param <I> index of series
 * @param <V> value of series
 * @author Vinícius CR
 */
public final class FlotSeriesPoint<I extends Serializable, V extends Serializable>  implements Serializable{


    private static final long serialVersionUID = -6798439164237359390L;
    private I index;

    private V value;

    private FlotSeriesPoint() {}

    public FlotSeriesPoint(I index, V value) {
        this.index = index;
        this.value = value;
    }

    public I getIndex() {
        return index;
    }

    public void setIndex(I index) {
        this.index = index;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
