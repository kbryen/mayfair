/*
 * Mayfair Stock Control.
 *
 */
package main.java.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static main.java.MayfairConstants.STOCK_REPORTS_DIR;
import static main.java.MayfairConstants.STOCK_REPORT_TEMPLATE;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author kian_bryen
 */
public class AvailableStockReport extends XlsxReport
{

    private final String outputDir = STOCK_REPORTS_DIR;
    private final String sheetName = "Stock Report";

    public AvailableStockReport()
    {
        super(XlsxReport.getXSSFWorkbook(STOCK_REPORT_TEMPLATE));
    }

    public void populateWorkbook()
    {
        XSSFSheet sheet = getWorkbook().getSheet(sheetName);
    }

    public String getFilename()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate);
        return outputDir + "Stock Report " + date + ".xlsx";
    }
}
