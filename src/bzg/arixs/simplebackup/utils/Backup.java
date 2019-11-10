package bzg.arixs.simplebackup.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class Backup {

	private final String error = "There was an error zipping the file!";
	private final String success = "Backup completed successfuly!";
	
	public String backup(String fileToSave, String destination) {
		File savingFile = new File(fileToSave);
		File savingDestination = new File(destination);
		try {
			FileUtils.copyDirectoryToDirectory(savingFile, savingDestination);
		} catch (Exception e) { e.printStackTrace(); return error; }
		
		SimpleDateFormat format = new SimpleDateFormat(" HH mm dd MM yy");
		
		File newName = new File(destination, savingFile.getName() + format.format(new Date()));
		new File(destination , savingFile.getName()).renameTo(newName);
		
		return success;
	}
	
}
