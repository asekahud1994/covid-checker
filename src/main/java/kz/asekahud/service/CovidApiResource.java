package kz.asekahud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.asekahud.model.CasesResponse;
import kz.asekahud.model.HistoryResponse;
import kz.asekahud.model.VaccinesResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;

/**
 * Service class for applying to external resources
 *
 * @author asekahud
 */
public final class CovidApiResource {

    // External API urls
    private final static String BASE_URL = "https://covid-api.mmediagroup.fr/v1";
    private final static String CASES_URL = BASE_URL + "/cases?country=%s";
    private final static String VACCINES_URL = BASE_URL + "/vaccines?country=%s";
    private final static String HISTORY_URL = BASE_URL + "/history?country=%s&status=confirmed";
    private final ObjectMapper mapper = new ObjectMapper();

    private final HttpClient httpClient;

    public CovidApiResource() {
        // here we can configure our HttpClient
        this.httpClient = HttpClient.newBuilder().build();
    }

    /**
     * API for live cases data
     *
     * @param countryName - requested country name
     */
    public CasesResponse getCases(String countryName) {
        try {
            String casesResponse = this.sendRequest(String.format(CASES_URL, countryName));
            JsonNode node = mapper.readTree(casesResponse).path("All");
            CasesResponse result = new CasesResponse();
            if (!node.isMissingNode()) {
                result.setConfirmed(node.path("confirmed").asInt());
                result.setDeaths(node.path("deaths").asInt());
                result.setRecovered(node.path("recovered").asInt());
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * API for vaccines data
     *
     * @param countryName - requested country name
     */
    public VaccinesResponse getVaccines(String countryName) {
        try {
            String vaccinesResponse = this.sendRequest(String.format(VACCINES_URL, countryName));
            JsonNode node = mapper.readTree(vaccinesResponse).path("All");
            var result = new VaccinesResponse();
            if (!node.isMissingNode()) {
                double peopleVaccinated = node.path("people_vaccinated").asDouble();
                double totalPopulation = node.path("population").asDouble();
                double percentage = (peopleVaccinated / totalPopulation) * 100;
                result.setVaccinationRatio(percentage);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * API for historical cases data
     *
     * @param countryName - requested country name
     */
    public HistoryResponse getHistory(String countryName) {
        try {
            String historyResponse = this.sendRequest(String.format(HISTORY_URL, countryName));
            JsonNode node = mapper.readTree(historyResponse).path("All");
            var result = new HistoryResponse();
            if (!node.isMissingNode() && node.has("dates")) {
                JsonNode dates = node.path("dates");
                Iterator<String> iterator = dates.fieldNames();
                result.setNewConfirmed(dates.path(iterator.next()).asInt() - dates.path(iterator.next()).asInt());
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param url - Resource URL
     * @return body of http response in String
     */
    private String sendRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
