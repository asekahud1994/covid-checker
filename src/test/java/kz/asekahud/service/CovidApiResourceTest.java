package kz.asekahud.service;

import kz.asekahud.model.CasesResponse;
import kz.asekahud.model.HistoryResponse;
import kz.asekahud.model.VaccinesResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author asekahud
 */
public class CovidApiResourceTest {

    private CovidApiResource covidApiResource;
    private String country;

    @BeforeEach
    void setUp() {
        covidApiResource = new CovidApiResource();
        country = "France";
    }

    @Test
    public void testGetCases() {
        CasesResponse casesInfo = covidApiResource.getCases(country);
        Assertions.assertNotNull(casesInfo);
    }

    @Test
    public void testGetVaccines() {
        VaccinesResponse vaccinesInfo = covidApiResource.getVaccines(country);
        Assertions.assertNotNull(vaccinesInfo);
    }

    @Test
    public void testGetHistory() {
        HistoryResponse historyInfo = covidApiResource.getHistory(country);
        Assertions.assertNotNull(historyInfo);
    }
}
