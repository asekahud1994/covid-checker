package kz.asekahud.service;

/**
 * It's utility class to support printing
 * @author asekahud
 */
public final class PrintService {

    // because we're not using containers like Spring, dependencies must
    // be created and instantiated by objects itself, which breaks one of the SOLID principles
    private final CovidApiResource covidApiResource = new CovidApiResource();

    /**
     * welcome message
     */
    public void printWelcomeMessage() {
        System.out.println("*** welcome to COVID checker ***");
        System.out.println("enter \"exit\" to close app");
    }

    /**
     * goodbye message
     */
    public void printGoodbyeMessage() {
        System.out.println("*** Thanks for using our app!!! ***");
        System.out.println("***   Hope to see you soon!!!   ***");
    }

    public void printCountryInfo(String countryName) {

        var casesResponse = covidApiResource.getCases(countryName);
        var historyResource = covidApiResource.getHistory(countryName);
        var vaccinesResponse = covidApiResource.getVaccines(countryName);

        System.out.printf("Country: %s\n", countryName);
        System.out.printf("Confirmed: %d\n", casesResponse.getConfirmed());
        System.out.printf("Recovered: %d\n", casesResponse.getRecovered());
        System.out.printf("Deaths: %d\n", casesResponse.getDeaths());
        System.out.printf("Vaccinated level in %% of total population: %f\n", vaccinesResponse.getVaccinationRatio());
        System.out.printf("New confirmed cases since last data available: %d\n", historyResource.getNewConfirmed());
    }
}
