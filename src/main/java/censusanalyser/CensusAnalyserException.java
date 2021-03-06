package censusanalyser;

public class CensusAnalyserException extends Exception {
    enum ExceptionType {
        CENSUS_DELIMITER_PROBLEM, CENSUS_HEADER_PROBLEM, CENSUS_FILE_PROBLEM, NO_CENSUS_DATA, UNABLE_TO_PARSE
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
