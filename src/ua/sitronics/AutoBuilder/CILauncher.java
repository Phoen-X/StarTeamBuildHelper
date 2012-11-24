package ua.sitronics.AutoBuilder;

import ua.sitronics.AutoBuilder.CI.CIListItem;
import ua.sitronics.AutoBuilder.CI.CIListProcessor;
import ua.sitronics.AutoBuilder.Exception.ComponentNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Phoen-X
 * Date: 23.11.12 21:08
 */
public class CILauncher
{

    public static void main(String[] args) throws IOException, ComponentNotFoundException
    {
        CIListProcessor processor = new CIListProcessor(new File("D:/VM/Temp/CI List.xls"));

        ArrayList<CIListItem> components = processor.getComponents();

        for (CIListItem component : components)
        {
            component.setHistory(processor.getHistory(component.getComponentName()));
        }

        double newVersion = processor.indentVersion("timm2txt", "test changes", "I did");
        processor.saveBook();
    }
}
