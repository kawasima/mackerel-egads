package net.unit8.mackerel;

import com.yahoo.egads.control.ProcessableObject;
import com.yahoo.egads.control.ProcessableObjectFactory;
import com.yahoo.egads.data.TimeSeries;
import com.yahoo.egads.utilities.InputProcessor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mackerel.io")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        MackerelApiService apiService = retrofit.create(MackerelApiService.class);
        Call<HostMetrics> call = apiService.getHostMetrics(
                System.getProperty("host.id"),
                System.getProperty("api.key"),
                "loadavg5",
                0l,
                System.currentTimeMillis() / 1000);

        HostMetrics metrics = call.execute().body();
        Properties properties = new Properties();
        properties.put("MAX_ANOMALY_TIME_AGO", "999999999");
        properties.put("AGGREGATION", "1");
        properties.put("OP_TYPE", "DETECT_ANOMALY");
        properties.put("TS_MODEL", "OlympicModel");
        properties.put("AD_MODEL", "ExtremeLowDensityModel");
        properties.put("OUTPUT", "GUI");
        properties.put("BASE_WINDOWS", "168");
        properties.put("PERIOD", "-1");
        properties.put("NUM_WEEKS", "3");
        properties.put("NUM_TO_DROP", "0");
        properties.put("DYNAMIC_PARAMETERS", "0");
        properties.put("TIME_SHIFTS", "0");
        new MetricsInputProcessor(metrics.getMetrics())
                .processInput(properties);
    }

    public static class MetricsInputProcessor implements InputProcessor {
        private final List<HostMetric> metrics;
        public MetricsInputProcessor(List<HostMetric> metrics) {
            this.metrics = metrics;
        }
        @Override
        public void processInput(Properties properties) throws Exception {
            TimeSeries ts = new TimeSeries();
            metrics.forEach(m -> {
                        try {
                            ts.append(m.getTime(), m.getValue().floatValue());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
            });

            ProcessableObject po = ProcessableObjectFactory.create(ts, properties);
            po.process();
        }
    }

}
