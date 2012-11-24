package ua.sitronics.AutoBuilder.CI;

import org.apache.poi.hssf.usermodel.HSSFRow;
import ua.sitronics.AutoBuilder.Excel.RowProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Phoen-X
 * Date: 23.11.12 21:24
 */
public class HistoryEntry
{
    private static final SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");
    private Date date;
    private String component;
    private String person;
    private Double newVersion;
    private String changes;

    @Override
    public String toString()
    {
        return  String.format("%s (%.2f)-> %s", fmt.format(date), newVersion, changes);
    }

    public Date getDate()
    {
        return date;
    }

    public HistoryEntry setDate(Date date)
    {
        this.date = date;
        return this;
    }

    public HistoryEntry()
    {
    }

    public HistoryEntry(HSSFRow row)
    {
        RowProcessor rdr = new RowProcessor(row);
        try
        {
            this.setDate(rdr.getDateCell(HistoryMap.DATE));
        }
        finally
        {
            this.setComponent(rdr.getCell(HistoryMap.COMPONENT))
                .setPerson(rdr.getCell(HistoryMap.CHANGED_BY))
                .setNewVersion(Double.valueOf(rdr.getCell(HistoryMap.VERSION)))
                .setChanges(rdr.getCell(HistoryMap.CHANGES));
        }

    }

    public String getComponent()
    {
        return component;
    }

    public HistoryEntry setComponent(String component)
    {
        this.component = component;
        return this;
    }

    public String getPerson()
    {
        return person;
    }

    public HistoryEntry setPerson(String person)
    {
        this.person = person;
        return this;
    }

    public Double getNewVersion()
    {
        return newVersion;
    }

    public HistoryEntry setNewVersion(Double newVersion)
    {
        this.newVersion = newVersion;
        return this;
    }

    public String getChanges()
    {
        return changes;
    }

    public HistoryEntry setChanges(String changes)
    {
        this.changes = changes;
        return this;
    }
}
