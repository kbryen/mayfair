/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.report.reports;

import java.awt.Component;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;

/**
 *
 * @author kian_bryen
 */
public interface Report
{

    public void populateWorkbook();

    public void save(String filename, Component loggingComponent);

    public void setReportName(String reportName);

    public String getReportName();

    public String getFilename();

    public Workbook getWorkbook();

    public CellStyle getStyle(String style);

    default public void generate(Component loggingComponent)
    {
        populateWorkbook();
        save(getFilename(), loggingComponent);
    }

    default public void autoSizeColumns(Sheet sheet, int size)
    {
        for (int i = 0; i < size; i++)
        {
            sheet.autoSizeColumn(i);
        }
    }

    default public void centreCell(Cell cell, Workbook workbook)
    {
        CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
    }

}