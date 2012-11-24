package ua.sitronics.AutoBuilder.CI;

import org.apache.poi.hssf.usermodel.HSSFRow;
import ua.sitronics.AutoBuilder.Excel.RowProcessor;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: VVYGULYARNIY
 * Date: 23.11.12
 * Time: 20:42
 */
public class CIListItem
{
    private String componentName;
    private double version;
    private String responsible;
    private String description;
    private String cvsPath;
    private String starTeamPath;
    private String buildFile;
    private String buildFileType;

    private ArrayList<HistoryEntry> history;

    @Override
    public String toString()
    {
        return String.format("%s -> %.2f   %s", componentName, version, description);
    }

    public CIListItem()
    {
    }

    public CIListItem(HSSFRow row)
    {
        RowProcessor rdr = new RowProcessor(row);

        this.setComponentName(rdr.getCell(MainSheetMap.COMPONENT))
                .setVersion(Double.valueOf(rdr.getCell(MainSheetMap.VERSION)))
                .setResponsible(rdr.getCell(MainSheetMap.RESPONSIBLE))
                .setDescription(rdr.getCell(MainSheetMap.DESCRIPTION))
                .setCvsPath(rdr.getCell(MainSheetMap.CVS_PATH))
                .setStarTeamPath(rdr.getCell(MainSheetMap.ST_PATH))
                .setBuildFile(rdr.getCell(MainSheetMap.VERSION_FILE))
                .setBuildFileType(rdr.getCell(MainSheetMap.VERSION_FILE_TYPE));
    }

    public String getComponentName()

    {
        return componentName;
    }

    public CIListItem setComponentName(String componentName)
    {
        this.componentName = componentName;
        return this;
    }

    public double getVersion()
    {
        return version;
    }

    public CIListItem setVersion(double version)
    {
        this.version = version;
        return this;
    }

    public String getBuildFile()
    {
        return buildFile;
    }

    public CIListItem setBuildFile(String buildFile)
    {
        this.buildFile = buildFile;
        return this;
    }

    public String getResponsible()
    {
        return responsible;
    }

    public CIListItem setResponsible(String responsible)
    {
        this.responsible = responsible;
        return this;
    }

    public String getDescription()
    {
        return description;
    }

    public CIListItem setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public String getStarTeamPath()
    {
        return starTeamPath;
    }

    public CIListItem setStarTeamPath(String starTeamPath)
    {
        this.starTeamPath = starTeamPath;
        return this;
    }

    public String getCvsPath()
    {
        return cvsPath;
    }

    public CIListItem setCvsPath(String cvsPath)
    {
        this.cvsPath = cvsPath;
        return this;
    }

    public String getBuildFileType()
    {
        return buildFileType;
    }

    public CIListItem setBuildFileType(String buildFileType)
    {
        this.buildFileType = buildFileType;
        return this;
    }

    public ArrayList<HistoryEntry> getHistory()
    {
        return history;
    }

    public CIListItem setHistory(ArrayList<HistoryEntry> history)
    {
        this.history = history;
        return this;
    }
}
