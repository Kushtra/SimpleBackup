package bzg.arixs.simplebackup.utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class FileSystem {
	
	private File dir, folder, file, defaultDestination;
	
	public FileSystem() {
		dir = new File(System.getProperty("user.home")+"\\AppData\\Roaming\\SimpleBackup\\");
		folder = new File(dir, "\\lastSavedFolder.bzg");
		file = new File(dir, "\\lastSavedFile.bzg");
		defaultDestination = new File(dir, "\\Backup");
	}
	
	
	public void open() {
		try {
			Desktop.getDesktop().open(defaultDestination);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void makeFiles() {
		try {
			dir.mkdirs();
			defaultDestination.mkdirs();
			folder.createNewFile();
			file.createNewFile();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public boolean saved() {
		if(dir.exists()) {
			return true;
		} else {
			makeFiles();
			return false;
		}
	}
	
	public void setLastSavedFile(String path) {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.write(path);
			pw.close();
		} catch (Exception e) { e.printStackTrace(); }	
	}
	
	public void setLastSavedFolder(String path) {
		try {
			PrintWriter pw = new PrintWriter(folder);
			pw.write(path);
			pw.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public String getSavedFolder() {
		String lastPath = null;
		try {
			BufferedReader bs = new BufferedReader(new FileReader(folder));
			lastPath = bs.readLine();
			bs.close();
		} catch (Exception e) { e.printStackTrace(); }
		
		if(lastPath == null) {
			return "Error getting folder";
		} else {
			return lastPath;
		}
	}
	
	public String getSavedFile() {
		String lastPath = null;
		try {
			BufferedReader bs = new BufferedReader(new FileReader(file));
			lastPath = bs.readLine();
			bs.close();
		} catch (Exception e) { e.printStackTrace(); }
		
		if(lastPath == null) {
			return "Error getting file";
		} else {
			return lastPath;
		}
	}

}
