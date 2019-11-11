package bzg.arixs.simplebackup.utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class FileSystem {
	
	private File dir, folder, file, type, defaultDestination;
	
	public FileSystem() {
		dir = new File(System.getProperty("user.home")+"\\AppData\\Roaming\\SimpleBackup\\");
		folder = new File(dir, "\\lastSavedFolder.bzg");
		file = new File(dir, "\\lastSavedFile.bzg");
		type = new File(dir, "\\lastSaved.bzg");
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
			type.createNewFile();
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
	
	public void setLastSaved(boolean b) {
		try {
			PrintWriter pw = new PrintWriter(type);
			if(b) {
				pw.write("t");
			} else {
				pw.write("f");
			}
			pw.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public boolean getType() {
		String fileContent = null;
		try {
			BufferedReader bs = new BufferedReader(new FileReader(type));
			fileContent = bs.readLine();
			bs.close();
		} catch (Exception e) { e.printStackTrace(); }
		
		if(fileContent.equals("t")) return true;
		return false;
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
	
//	public void deleteFile(File file) {
//		String[] entries = file.list();
//		for(String currentFileName: entries) {
//			File currentFile = new File(file.getPath() , currentFileName);
//			currentFile.delete();
//		}
//		file.delete();
//	}
	
}
