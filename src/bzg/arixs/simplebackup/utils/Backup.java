package bzg.arixs.simplebackup.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Backup {

	private final String error = "There was an error zipping the file!";
	private final String success = "Backup completed successfuly!";
	
	private String sourceFolder;
	private List<String> fileList;
	
	public String backup(String source, String outputFolder) {
		fileList = new ArrayList<String>();
		sourceFolder = source;
		byte[] buffer = new byte[1024];
		
		SimpleDateFormat format = new SimpleDateFormat(" HH mm dd MM yy");
		String outputDestination = outputFolder + File.separator;
		String outputName = getFileName(sourceFolder) + format.format(new Date()) + ".zip";
		String output = outputDestination + outputName;
		String src = new File(sourceFolder).getName();
		generateFileList(new File(sourceFolder));
		
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		
		try {
			fos = new FileOutputStream(output);
			zos =  new ZipOutputStream(fos);
			
			FileInputStream in = null;
			
			for(String file: fileList) {
				ZipEntry ze = new ZipEntry(src + File.separator + file);
				zos.putNextEntry(ze);
				try {
					in = new FileInputStream(sourceFolder + File.separator + file);
					int len;
					while((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
				} finally {
					in.close();
				}
			}
			
			zos.closeEntry();
			fileList = null;
		} catch (Exception e) { 
			e.printStackTrace();
			return error; 
		} finally {
			try {
				zos.close();
			} catch (Exception e) {
				return error;
			}
		}
		return success;
	}
	
	private void generateFileList(File node) {
		if(node.isDirectory()) {
			String[] subNote = node.list();
			for(String fileName: subNote) generateFileList(new File(node, fileName));
		}
		if(node.isFile()) {
			fileList.add(generateZipEntry(node.toString()));
		}
	}
	
	private String generateZipEntry(String file) {
        return file.substring(sourceFolder.length() + 1, file.length());
    }
	
	private String getFileName(String name) {
		String tempName = new File(name).getName();
		int endString = tempName.length();
		for(int i=0;i<tempName.length();i++) {
			char c = tempName.charAt(i);
			if(c == '.') endString = i;
		}
		return tempName.substring(0, endString);
	}
	
}
