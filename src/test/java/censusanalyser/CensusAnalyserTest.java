    package censusanalyser;

    import com.google.gson.Gson;
    import org.junit.Assert;
    import org.junit.Test;
    import org.junit.rules.ExpectedException;

    public class CensusAnalyserTest {

        private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
        private static final String WRONG_EXTENSION_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
        private static final String INDIA_STATE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
        private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

        @Test
        public void givenIndianCensusCSVFileReturnsCorrectRecords() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
                Assert.assertEquals(29,numOfRecords);
            } catch (CensusAnalyserException e) { }
        }

        @Test
        public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CensusAnalyserException.class);
                censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
            } catch (CensusAnalyserException e) {
                Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
            }
        }

        @Test
        public void givenIndiaCensusData_WrongExtensionType_ShouldThrowException() {
            String getExtensionType = "";
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CensusAnalyserException.class);
                getExtensionType = WRONG_EXTENSION_FILE_PATH.substring(WRONG_CSV_FILE_PATH.length() - 3);
                censusAnalyser.loadIndiaCensusData(WRONG_EXTENSION_FILE_PATH);

            } catch (CensusAnalyserException e) {
                Assert.assertEquals("csv", getExtensionType);
            }
        }

        @Test
        public void givenIndiaCensusData_WrongDelimeter_ShouldThrowException() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CensusAnalyserException.class);
                censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            } catch (CensusAnalyserException e) {
                Assert.assertNotEquals(CensusAnalyserException.ExceptionType.CENSUS_DELIMITER_PROBLEM, e.type);
            }
        }

        @Test
        public void givenIndiaCensusData_CsvHeader_ShouldThrowException() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CensusAnalyserException.class);
                censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            } catch (CensusAnalyserException e) {
                Assert.assertNotEquals(CensusAnalyserException.ExceptionType.CENSUS_HEADER_PROBLEM, e.type);
            }
        }

        @Test
        public void givenIndianStateCSV_ShouldReturnExactCount() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
                int numOfStateCode = censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
                Assert.assertEquals(29, numOfStateCode);
            } catch (CensusAnalyserException e) {
            }
        }

        @Test
        public void givenIndianStateCSV_WithWrongFile_ShouldThrowException() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CensusAnalyserException.class);
                censusAnalyser.loadIndianStateCode(WRONG_CSV_FILE_PATH);
            } catch (CensusAnalyserException e) {
                Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            }
        }

        @Test
        public void givenIndianStateCSV_WrongExtensionType_ShouldThrowException() {
            String getExtensionType = "";
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CensusAnalyserException.class);
                getExtensionType = WRONG_EXTENSION_FILE_PATH.substring(WRONG_CSV_FILE_PATH.length() - 3);
                censusAnalyser.loadIndianStateCode(WRONG_EXTENSION_FILE_PATH);

            } catch (CensusAnalyserException e) {
                Assert.assertEquals("csv", getExtensionType);
            }
        }

        @Test
        public void givenIndianStateCSV_WrongDelimeter_ShouldThrowException() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CensusAnalyserException.class);
                censusAnalyser.loadIndianStateCode(INDIA_CENSUS_CSV_FILE_PATH);
            } catch (CensusAnalyserException e) {
                Assert.assertNotEquals(CensusAnalyserException.ExceptionType.CENSUS_DELIMITER_PROBLEM, e.type);
            }
        }

        @Test
        public void givenIndianStateCSV_CsvHeader_ShouldThrowException() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CensusAnalyserException.class);
                censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
            } catch (CensusAnalyserException e) {
                Assert.assertNotEquals(CensusAnalyserException.ExceptionType.CENSUS_HEADER_PROBLEM, e.type);
            }
        }
        
        @Test
        public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
                String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
                IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
                Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            } catch (CensusAnalyserException e) {
            }
        }

        @Test
        public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
                String sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
                IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
                Assert.assertEquals(607688, censusCSV[0].population);
            } catch (CensusAnalyserException e) {
            }
        }

        @Test
        public void givenUSCensusData_ShouldReturnCorrectRecords() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                int censusDataCnt = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
                Assert.assertEquals(5,censusDataCnt);
            } catch (CensusAnalyserException e) {}
        }

    }
