package ua.sitronics.AutoBuilder.CI;

import org.apache.poi.hssf.usermodel.*;
import sun.plugin.dom.exception.WrongDocumentException;
import ua.sitronics.AutoBuilder.Exception.ComponentNotFoundException;

import java.io.*;
import java.util.*;

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

    // start positions
    private int mainStarRow = 1;
    private int historyStartRow = 1;

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

    public CIListItem getComponentForName(String component) throws ComponentNotFoundException
    {
        int rowNum = 1;
        HSSFRow row;
        while ((row = mainSheet.getRow(rowNum++)) != null)
        {
            CIListItem item = new CIListItem(row);
            if (item.getComponentName().equalsIgnoreCase(component))
                return item;
        }
        throw new ComponentNotFoundException(component);
    }


    public ArrayList<HistoryEntry> getHistory(String componentName)
    {
        ArrayList<HistoryEntry> history = new ArrayList<HistoryEntry>(500);
        for(int rowNum = historyStartRow; rowNum < historySheet.getLastRowNum(); rowNum++)
        {
            HSSFRow row = historySheet.getRow(rowNum);
            if(row == null || row.getCell(HistoryMap.COMPONENT) == null)
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

    private void addHistoryInfo(String component, double version, String changes, String who)
    {
        HistoryEntry entry = new HistoryEntry().setComponent(component).setNewVersion(version)
                                               .setChanges(changes).setPerson(who);
        // setting default values
        entry.setDate(new Date(System.currentTimeMillis()));
        int rowNum = historySheet.getLastRowNum();
        HSSFRow row = historySheet.createRow(rowNum + 1);

        HSSFCell cell = row.createCell(0); // date
        cell.setCellValue(entry.getDate());

        cell = row.createCell(1); //component
        cell.setCellValue(entry.getComponent());

        cell = row.createCell(2); // person who did changes
        cell.setCellValue(entry.getPerson());

        cell = row.createCell(3); // Version
        cell.setCellValue(entry.getNewVersion().toString().replace(",", "."));

        cell = row.createCell(4);              // Changes
        cell.setCellValue(entry.getChanges());
    }

    public double indentVersion(String component, String changes, String person) throws ComponentNotFoundException
    {
        CIListItem mainEntry = getComponentForName(component);

        Double newVersion = mainEntry.getVersion() + 0.01;

        addHistoryInfo(component, newVersion, changes, person);

        int componentRow = getComponentRowNum(component);

        HSSFRow componentMainRow = mainSheet.getRow(componentRow);

        componentMainRow.getCell(MainSheetMap.VERSION).setCellValue(newVersion.toString().replace(",", "."));

        return newVersion;
    }

    private int getComponentRowNum(String component) throws ComponentNotFoundException
    {
        int rowNum = mainStarRow;
        HSSFRow row;
        while ((row = mainSheet.getRow(rowNum)) != null)
        {
            if(row.getCell(MainSheetMap.COMPONENT).getStringCellValue().equalsIgnoreCase(component))
                return rowNum;
            rowNum++;
        }

        throw new ComponentNotFoundException(component);
    }
}
