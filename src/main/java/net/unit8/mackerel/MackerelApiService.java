package net.unit8.mackerel;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface MackerelApiService {
    /**
     * Post the service metrics to a Mackerel server.
     *
     * @param serviceName    the name of the service
     * @param apiKey         the key of the api
     * @param serviceMetrics the list of metric objects
     * @return Call object
     */
    @POST("/api/v0/services/{serviceName}/tsdb")
    Call<Map<String, String>> postServiceMetrics(
            @Path("serviceName") String serviceName,
            @Header("X-Api-Key") String apiKey,
            @Body List<HostMetric> serviceMetrics);

    @GET("/api/v0/hosts/{hostId}/metrics")
    Call<HostMetrics> getHostMetrics(
            @Path("hostId") String hostId,
            @Header("X-Api-Key") String apiKey,
            @Query("name") String name,
            @Query("from") long from,
            @Query("to") long to
    );
}
