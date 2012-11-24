package ua.sitronics.AutoBuilder;

import ua.sitronics.AutoBuilder.Ant.AntRunner;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 23.11.12 17:13
 */
public class ProjectBuilder
{
	private String projectName;
    private File projectDir;
    private File buildDir = new File("C:\\1");

    public ProjectBuilder(String projectName, File projectDir)
    {
        this.projectName = projectName;
        this.projectDir = projectDir;
    }

    public File build() throws IOException
	{
		File moduleDir = new File(projectDir, projectName);
		AntRunner antRunner = new AntRunner(moduleDir, buildDir);
		File build = antRunner.runBuild();
		System.out.println("Build Created. File: " + build.getAbsolutePath());
		return build;
	}
}
