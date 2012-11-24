package ua.sitronics.AutoBuilder.Exception;

/**
 * Created by IntelliJ IDEA.
 * User: Phoen-X
 * Date: 24.11.12 14:47
 */
public class ComponentNotFoundException extends Exception
{
    public ComponentNotFoundException(String componentName)
    {
        super(String.format("Component with name '%s' was not found", componentName));
    }
}
