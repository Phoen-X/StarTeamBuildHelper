package ua.sitronics.AutoBuilder.Excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Phoen-X
 * Date: 23.11.12 21:42
 */
public class RowProcessor
{
    HSSFRow row;

    public RowProcessor(HSSFRow row)
    {
        this.row = row;
    }

    public String getCell(int index)
    {
        try
        {
            HSSFCell cell = row.getCell(index);
            return cell.getStringCellValue();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public Date getDateCell(int index)
    {
        try
        {
            HSSFCell cell = row.getCell(index);
            return cell.getDateCellValue();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
