package ua.sitronics.AutoBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA. User: Admin Date: 23.11.12 17:13
 */
public class ProjectBuilder
{
	String projectName;

	public ProjectBuilder(String projectName)
	{
		this.projectName = projectName;
	}

	public File build() throws IOException
	{
		File projectDir = new File("C:\\ST Working folder\\MTSU-BSCS6 support\\Sources\\jrm\\app");
		File buildDir = new File("C:\\1");
		File moduleDir = new File(projectDir, "webvat");
		AntLauncher antRunner = new AntLauncher(moduleDir, buildDir);
		File build = antRunner.runBuild();
		System.out.println("Build Created. File: " + build.getAbsolutePath());
		return build;
	}
}
