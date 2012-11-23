package ua.sitronics.AutoBuilder;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import ua.sitronics.Mail.Archiver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: Admin Date: 23.11.12 14:22
 */
public class AntLauncher
{
	private File moduleDir;
	private File antOutputDir;
	private static final String buildFileName = "build.xml";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmm");

	public AntLauncher(File moduleDir, File antOutputDir)
	{
		this.moduleDir = moduleDir;
		this.antOutputDir = antOutputDir;
	}

	public File runBuild() throws IOException
	{
		File buildFile = new File(moduleDir, buildFileName);

		doAnt(buildFile);
		String buildArchiveName = String.format("Build_%s_%s",
												dateFormat.format(new Date(System.currentTimeMillis())),
												moduleDir.getName());

		File archFile = new File(moduleDir.getParent() + File.separatorChar + buildArchiveName + ".zip");
		archiveBuild(archFile, antOutputDir);

		if(antOutputDir.exists())
			cleanupDir(antOutputDir);

		return archFile;
	}

	private void cleanupDir(File dir)
	{
		File[] innerFiles = dir.listFiles();

		for (File file : innerFiles)
		{
				if(file.isDirectory())
				{
					cleanupDir(file);
				}
				else
				{
					cleanupFile(file);
				}
		}

		cleanupFile(dir);
	}

	private void cleanupFile(File file)
	{
		if (!file.delete())
		{
			System.err.println("Cannot clean up file: " + file.getName());
		}
	}

	private void doAnt(File buildFile)
	{
		// source copied from there:
		// http://www.ibm.com/developerworks/websphere/library/techarticles/0502_gawor/0502_gawor.html
		Project project = new Project();
		project.setUserProperty("ant.file", buildFile.getAbsolutePath());
		project.setUserProperty("production.dir", antOutputDir.getAbsolutePath());
		project.init();

		ProjectHelper helper = ProjectHelper.getProjectHelper();
		project.addReference("ant.projectHelper", helper);
		helper.parse(project, buildFile);

		project.executeTarget(project.getDefaultTarget());
	}

	private void archiveBuild(File archFile, File buildDir) throws IOException
	{
		ArrayList<File> files = new ArrayList<File>();
		Collections.addAll(files, buildDir.listFiles());

		Archiver arch = new Archiver(files);
		arch.create(archFile, 5);
	}
}
