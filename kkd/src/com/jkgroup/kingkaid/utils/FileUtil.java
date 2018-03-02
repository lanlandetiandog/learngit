package com.jkgroup.kingkaid.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

	public static void ZipFiles(File zip, String path, List<File> srcFiles)
			throws IOException {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
		FileUtil.ZipFiles(out, path, srcFiles);
		out.close();
	}

	public static void ZipFiles(ZipOutputStream out, String path,
			List<File> srcFiles) {
		path = path.replaceAll("\\*", "/");
		if (!path.endsWith("/")) {
			path += "/";
		}
		byte[] buf = new byte[1024];
		try {
			for (int i = 0; i < srcFiles.size(); i++) {
				if (srcFiles.get(i).isDirectory()) {
					List<File> files = Arrays.asList(srcFiles.get(i).listFiles());
					String srcPath = srcFiles.get(i).getName();
					srcPath = srcPath.replaceAll("\\*", "/");
					if (!srcPath.endsWith("/")) {
						srcPath += "/";
					}
					out.putNextEntry(new ZipEntry(path + srcPath));
					ZipFiles(out, path + srcPath, files);
				} else {
					FileInputStream in = new FileInputStream(srcFiles.get(i));
					out.putNextEntry(new ZipEntry(path + srcFiles.get(i).getName()));
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.closeEntry();
					in.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createDir(String destDirName) {
		File dir = new File(destDirName);
		if(!dir.exists() && dir.mkdirs());
	}
}
