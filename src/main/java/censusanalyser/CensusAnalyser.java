package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }  catch (RuntimeException e) {
            CensusAnalyserException.ExceptionType type = null;
            if (e.getMessage().contains("CsvRequiredFieldEmptyException")) {
                type = CensusAnalyserException.ExceptionType.CENSUS_DELIMITER_PROBLEM;
            } else if (e.getMessage().contains("Error capturing CSV header!")) {
                type = CensusAnalyserException.ExceptionType.CENSUS_HEADER_PROBLEM ;
            }
            throw new CensusAnalyserException(e.getMessage(), type);
        } catch (CSVBuiderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCsvIterable = csvBuilder.
                                                            getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            return getCount(stateCsvIterable);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            CensusAnalyserException.ExceptionType type = null;
            if (e.getMessage().contains("CsvRequiredFieldEmptyException")) {
                type = CensusAnalyserException.ExceptionType.CENSUS_DELIMITER_PROBLEM;
            } else if (e.getMessage().contains("Error capturing CSV header!")) {
                type = CensusAnalyserException.ExceptionType.CENSUS_HEADER_PROBLEM;
            }
            throw new CensusAnalyserException(e.getMessage(), type);
        } catch (CSVBuiderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numOfEntries;

    }
}
