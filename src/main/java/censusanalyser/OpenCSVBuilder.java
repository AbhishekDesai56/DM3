package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder<E> implements ICSVBuilder {
    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuiderException {
        return this.getCSVBean(reader, csvClass).iterator();
    }

    @Override
    public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuiderException {
        return this.getCSVBean(reader, csvClass).parse();
    }

    private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CSVBuiderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBeanBuilder.build();
        } catch (IllegalStateException e) {
            throw new CSVBuiderException(e.getMessage(),
                    CSVBuiderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
