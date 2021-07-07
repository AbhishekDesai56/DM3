package censusanalyser;

public class CSVBuiderException extends Exception {
    enum ExceptionType {
        CENSUS_DELIMITER_PROBLEM, CENSUS_HEADER_PROBLEM, CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE
    }

    CSVBuiderException.ExceptionType type;

    public CSVBuiderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
