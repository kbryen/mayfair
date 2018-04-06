package main.java.report;

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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;

/**
 *
 * @author Kian
 */
public abstract class XlsReport
{

    private final HSSFWorkbook workBook;
    private Component loggingComponent;

    private final Map<String, HSSFCellStyle> styles = new HashMap();
    public static final String BOLD = "bold";
    public static final String ITALIC = "italic";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String NUMBER = "number";

    public XlsReport(HSSFWorkbook workBook)
    {
        this.workBook = workBook;
        createStyles();
    }

    private void createStyles()
    {
        // Bold
        HSSFFont bold = getWorkbook().createFont();
        bold.setBold(true);
        HSSFCellStyle boldStyle = getWorkbook().createCellStyle();
        boldStyle.setFont(bold);
        styles.put(BOLD, boldStyle);

        // Italic
        HSSFFont italic = getWorkbook().createFont();
        italic.setItalic(true);
        HSSFCellStyle italicStyle = getWorkbook().createCellStyle();
        italicStyle.setFont(italic);
        styles.put(ITALIC, italicStyle);

        // Left
        HSSFCellStyle leftStyle = getWorkbook().createCellStyle();
        leftStyle.setAlignment(CellStyle.VERTICAL_TOP);
        leftStyle.setAlignment(CellStyle.ALIGN_LEFT);
        styles.put(LEFT, leftStyle);

        // Bold Left
        HSSFCellStyle boldLeftStyle = getWorkbook().createCellStyle();
        boldLeftStyle.setAlignment(CellStyle.VERTICAL_TOP);
        boldLeftStyle.setAlignment(CellStyle.ALIGN_LEFT);
        boldLeftStyle.setFont(bold);
        styles.put(BOLD + LEFT, boldLeftStyle);

        // Right
        HSSFCellStyle rightStyle = getWorkbook().createCellStyle();
        rightStyle.setAlignment(CellStyle.VERTICAL_TOP);
        rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        styles.put(RIGHT, rightStyle);

        // Bold Right
        HSSFCellStyle boldRightStyle = getWorkbook().createCellStyle();
        boldRightStyle.setAlignment(CellStyle.VERTICAL_TOP);
        boldRightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        boldRightStyle.setFont(bold);
        styles.put(BOLD + RIGHT, boldRightStyle);
        
        // Number
        HSSFCellStyle numberStyle = workBook.createCellStyle();
//        numberStyle.setDataFormat(BuiltinFormats.getBuiltinFormat("0"));
        styles.put(NUMBER, numberStyle);
        
        // Bold Number
        HSSFCellStyle boldNumberStyle = workBook.createCellStyle();
//        boldNumberStyle.setDataFormat(0);
        boldRightStyle.setFont(bold);
        styles.put(BOLD + NUMBER, boldNumberStyle);
    }

    public void setLoggingComponent(Component loggingComponent)
    {
        this.loggingComponent = loggingComponent;
    }

    public HSSFCellStyle getStyle(String style)
    {
        return styles.get(style);
    }

    public HSSFWorkbook getWorkbook()
    {
        return workBook;
    }

    public void centreCell(HSSFCell cell)
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
        HSSFSheet sheet = getWorkbook().createSheet("Colour Chart");
        HSSFRow row = sheet.createRow(0);
        int i = 0;
        for (IndexedColors value : IndexedColors.values())
        {
            HSSFCell cell = row.createCell(i++);
            HSSFCellStyle style = getWorkbook().createCellStyle();
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
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
            outputMessage(loggingComponent, "<html> <b>Dispatch note created successfully.</b> \n<html> <i> " + filename + " </i>", "Dispatch Note Created", INFORMATION_MESSAGE);
        }
        catch (IOException e)
        {
            outputMessage(loggingComponent, "<html> Error while creating dispatch note, please try again.\n<html> <i> If error continues to happen please contact Kian. </i>", "Error", ERROR_MESSAGE);
            outputMessage(loggingComponent, e.getLocalizedMessage(), "Error", ERROR_MESSAGE);
        }
    }

    public static void outputMessage(Component component, String title, String message, int type)
    {
        JOptionPane.showMessageDialog(component, title, message, type);
    }

    public static HSSFWorkbook getHSSFWorkbook(String file)
    {
        if (file != null)
        {
            try (InputStream inp = new FileInputStream(file))
            {
                HSSFWorkbook workbook;
                Workbook wb = WorkbookFactory.create(inp);
                workbook = (HSSFWorkbook) wb;
                return workbook;
            }
            catch (IOException | InvalidFormatException ex)
            {
                return new HSSFWorkbook();
            }
        }
        else
        {
            return new HSSFWorkbook();
        }
    }
}
