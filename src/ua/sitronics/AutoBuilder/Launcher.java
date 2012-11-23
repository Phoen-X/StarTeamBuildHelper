package ua.sitronics.AutoBuilder;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA. User: Admin Date: 23.11.12 14:57
 */
public class Launcher
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		ProjectBuilder builder = new ProjectBuilder("webvat");
		builder.build();
	}
}
