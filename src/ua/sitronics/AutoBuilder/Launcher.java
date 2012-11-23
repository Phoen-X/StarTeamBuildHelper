package ua.sitronics.AutoBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 23.11.12 14:57
 */
public class Launcher
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
        File modulePath = new File(args[0]);
        String moduleName = args[1];

		ProjectBuilder builder = new ProjectBuilder(moduleName, modulePath);
		File build = builder.build();

	}
}
