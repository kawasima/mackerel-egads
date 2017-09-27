package net.unit8.mackerel;

import java.io.Serializable;
import java.util.List;

public class HostMetrics implements Serializable {
    private List<HostMetric> metrics;

    public List<HostMetric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<HostMetric> metrics) {
        this.metrics = metrics;
    }
}
