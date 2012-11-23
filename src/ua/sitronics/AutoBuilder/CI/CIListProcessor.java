package ua.sitronics.AutoBuilder.CI;

import org.apache.poi.hssf.usermodel.*;
import sun.plugin.dom.exception.WrongDocumentException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by IntelliJ IDEA.
 * User: Phoen-X
 * Date: 23.11.12 20:51
 */
public class CIListProcessor
{
    private File ciFile;
    private HSSFWorkbook wb;
    private HSSFSheet mainSheet;
    private HSSFSheet historySheet;

    //default sheet names
    private String ciMainSheetName = "ci list";
    private String versionSheetName = "versions history";

    public CIListProcessor(File ciFile) throws IOException
    {
        setCiFile(ciFile);
    }

    public CIListProcessor(File ciFile, String ciMainSheetName, String versionSheetName) throws IOException
    {
        this.ciMainSheetName = ciMainSheetName;
        this.versionSheetName = versionSheetName;
        setCiFile(ciFile);
    }

    private void processWorkBook() throws WrongDocumentException, IOException
    {
        wb = new HSSFWorkbook(new FileInputStream(ciFile));
        mainSheet = wb.getSheet(ciMainSheetName);
        historySheet = wb.getSheet(versionSheetName);

        // checking sheets availability
        if (mainSheet == null)
        {
            throw new WrongDocumentException(String.format("CI List main sheet is not found. Check that sheet with " +
                    "name '%s' exists in a file.", ciMainSheetName));
        }

        if (historySheet == null)
        {
            throw new WrongDocumentException(String.format("CI List version sheet is not found. Check that sheet with" +
                    " name '%s' exists in a file.", versionSheetName));
        }
    }

    public ArrayList<CIListItem> getComponents()
    {
        ArrayList<CIListItem> components = new ArrayList<CIListItem>(mainSheet.getLastRowNum());
        int rowNum = 1;
        HSSFRow row;
        while ((row = mainSheet.getRow(rowNum++)) != null)
        {
            components.add(new CIListItem(row));
        }

        return components;
    }


    public ArrayList<HistoryEntry> getHistory(String componentName)
    {
        ArrayList<HistoryEntry> history = new ArrayList<HistoryEntry>(500);
        for(int rowNum = 1; rowNum < historySheet.getLastRowNum(); rowNum++)
        {
            HSSFRow row = historySheet.getRow(rowNum);
            if(row == null || row.getCell(0) == null)
                break;

            try
            {
                HistoryEntry entry = new HistoryEntry(row);
                if(entry.getComponent().equalsIgnoreCase(componentName))
                    history.add(entry);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new WrongDocumentException("Cannot read history row #" + row.getRowNum() + 1);
            }
        }

        Collections.reverse(history);

        return history;
    }


    public File getCiFile()
    {
        return ciFile;
    }

    public void setCiFile(File ciFile) throws IOException
    {
        this.ciFile = ciFile;
        processWorkBook();
    }

    public void saveBook() throws IOException
    {
        FileOutputStream stream = new FileOutputStream(ciFile);
        wb.write(stream);
        stream.flush();
        stream.close();

    }
}
