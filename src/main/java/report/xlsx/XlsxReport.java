package main.java.report.xlsx;

import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Kian
 */
public abstract class XlsxReport
{

    private final XSSFWorkbook workBook;
    private Component loggingComponent;
    private String reportName = "Report";

    private final Map<String, XSSFCellStyle> styles = new HashMap();
    public static final String BOLD = "bold";
    public static final String ITALIC = "italic";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String NUMBER = "number";

    public XlsxReport(XSSFWorkbook workBook)
    {
        this.workBook = workBook;
        createStyles();
    }

    private void createStyles()
    {
        // Bold
        XSSFFont bold = getWorkbook().createFont();
        bold.setBold(true);
        XSSFCellStyle boldStyle = getWorkbook().createCellStyle();
        boldStyle.setFont(bold);
        styles.put(BOLD, boldStyle);

        // Italic
        XSSFFont italic = getWorkbook().createFont();
        italic.setItalic(true);
        XSSFCellStyle italicStyle = getWorkbook().createCellStyle();
        italicStyle.setFont(italic);
        styles.put(ITALIC, italicStyle);

        // Left
        XSSFCellStyle leftStyle = getWorkbook().createCellStyle();
        leftStyle.setAlignment(CellStyle.VERTICAL_TOP);
        leftStyle.setAlignment(CellStyle.ALIGN_LEFT);
        styles.put(LEFT, leftStyle);

        // Bold Left
        XSSFCellStyle boldLeftStyle = getWorkbook().createCellStyle();
        boldLeftStyle.setAlignment(CellStyle.VERTICAL_TOP);
        boldLeftStyle.setAlignment(CellStyle.ALIGN_LEFT);
        boldLeftStyle.setFont(bold);
        styles.put(BOLD + LEFT, boldLeftStyle);

        // Right
        XSSFCellStyle rightStyle = getWorkbook().createCellStyle();
        rightStyle.setAlignment(CellStyle.VERTICAL_TOP);
        rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        styles.put(RIGHT, rightStyle);

        // Bold Right
        XSSFCellStyle boldRightStyle = getWorkbook().createCellStyle();
        boldRightStyle.setAlignment(CellStyle.VERTICAL_TOP);
        boldRightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        boldRightStyle.setFont(bold);
        styles.put(BOLD + RIGHT, boldRightStyle);

        // Number
        XSSFCellStyle numberStyle = workBook.createCellStyle();
        numberStyle.setDataFormat(BuiltinFormats.getBuiltinFormat("0"));
        styles.put(NUMBER, numberStyle);

        // Bold Number
        XSSFCellStyle boldNumberStyle = workBook.createCellStyle();
        boldNumberStyle.setDataFormat(BuiltinFormats.getBuiltinFormat("0"));
        boldRightStyle.setFont(bold);
        styles.put(BOLD + NUMBER, boldNumberStyle);
    }

    public void setLoggingComponent(Component loggingComponent)
    {
        this.loggingComponent = loggingComponent;
    }

    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    public String getReportName()
    {
        return reportName;
    }

    public XSSFCellStyle getStyle(String style)
    {
        return styles.get(style);
    }

    public XSSFWorkbook getWorkbook()
    {
        return workBook;
    }

    public void centreCell(XSSFCell cell)
    {
        CellUtil.setAlignment(cell, getWorkbook(), CellStyle.ALIGN_CENTER);
    }

    public void autoSizeColumns(Sheet sheet, int size)
    {
        for (int i = 0; i < size; i++)
        {
            sheet.autoSizeColumn(i);
        }
    }

    public void createColourChart()
    {
        XSSFSheet sheet = getWorkbook().createSheet("Colour Chart");
        XSSFRow row = sheet.createRow(0);
        int i = 0;
        for (IndexedColors value : IndexedColors.values())
        {
            XSSFCell cell = row.createCell(i++);
            XSSFCellStyle style = getWorkbook().createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(value.index);
            cell.setCellValue(value.name());
            cell.setCellStyle(style);
        }
    }

    public void save(String filename)
    {
        try (FileOutputStream fileOut = new FileOutputStream(filename))
        {
            workBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            outputMessage(loggingComponent, "<html> <b>" + reportName + " created successfully.</b> \n<html> <i> " + filename + " </i>", reportName + " Created", INFORMATION_MESSAGE);
        }
        catch (IOException e)
        {
            outputMessage(loggingComponent, "<html> Error while creating " + reportName + ", please try again.\n<html> <i> If error continues to happen please contact Kian. </i>", "Error", ERROR_MESSAGE);
            outputMessage(loggingComponent, e.getLocalizedMessage(), "Message for Kian", ERROR_MESSAGE);
        }
    }

    public static void outputMessage(Component component, String title, String message, int type)
    {
        JOptionPane.showMessageDialog(component, title, message, type);
    }

    public static XSSFWorkbook getXSSFWorkbook(String file)
    {
        if (file != null)
        {
            try (InputStream inp = new FileInputStream(file))
            {
                XSSFWorkbook workbook;
                Workbook wb = WorkbookFactory.create(inp);
                workbook = (XSSFWorkbook) wb;
                return workbook;
            }
            catch (IOException | InvalidFormatException ex)
            {
                return new XSSFWorkbook();
            }
        }
        else
        {
            return new XSSFWorkbook();
        }
    }
}
