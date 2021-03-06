package censusanalyser;

public class IndiaCensusDAO {
    public String state;
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String stateCode;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
    }
}
