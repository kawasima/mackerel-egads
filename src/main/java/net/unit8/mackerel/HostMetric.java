package net.unit8.mackerel;

import java.io.Serializable;

public class HostMetric implements Serializable {
    private long time;
    private Double value;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HostMetric{" +
                "time=" + time +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HostMetric that = (HostMetric) o;

        return time == that.time && (value != null ? value.equals(that.value) : that.value == null);
    }

    @Override
    public int hashCode() {
        int result = (int) (time ^ (time >>> 32));
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
