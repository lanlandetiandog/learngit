package com.jkgroup.kingkaid.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

/**
 * 文件访问客户端
 * 
 * @author pan
 * @createDate 2015-04-13
 */
public class FileClient {

	/**
	 * 文件服务位置
	 */
	private static final String fileServer = PropertiesUtil.getFileUri();

	/**
	 * 保存文件
	 * 
	 * @param inputStream
	 * @param filePath
	 * @param fileName
	 */
	public static void createFile(InputStream inputStream, String filePath,
			String fileName) {
		OutputStream os = null;
		try {
			FileSystemManager fsManager = VFS.getManager();
			FileObject fileObject = fsManager.resolveFile(fileServer + "/"
					+ filePath);
			if (!fileObject.exists()) {
				fileObject.createFolder();
			}
			os = fileObject.resolveFile(fileName).getContent()
					.getOutputStream();
			IOUtils.copy(inputStream, os);
		} catch (FileSystemException e) {
			filePath = null;
			e.printStackTrace();
		} catch (IOException e) {
			filePath = null;
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @param fileName
	 */
	public static boolean deleteFile(String filePath, String fileName) {
		boolean result = false;
		FileObject fileObject = null;
		FileObject smallFileObject = null;
		try {
			FileSystemManager fsManager = VFS.getManager();
			fileObject = fsManager.resolveFile(fileServer + "/" + filePath
					+ "/" + fileName);
			smallFileObject = fsManager.resolveFile(fileServer + "/" + filePath
					+ "/" + fileName);
			if (fileObject.exists()) {
				result = fileObject.delete();
			}
			if (smallFileObject.exists()) {
				result = smallFileObject.delete();
			}
		} catch (FileSystemException e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (fileObject != null) {
				try {
					fileObject.close();
				} catch (FileSystemException e) {
					e.printStackTrace();
				}
			}
			if (smallFileObject != null) {
				try {
					smallFileObject.close();
				} catch (FileSystemException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 获取文件
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static InputStream getFileContent(String filePath, String fileName) {
		InputStream in = null;
		try {
			FileSystemManager fsManager = VFS.getManager();
			FileObject fileObject = fsManager.resolveFile(fileServer + "/"
					+ filePath + "/" + fileName);
			if (fileObject.exists()) {
				in = fileObject.getContent().getInputStream();
			}
		} catch (FileSystemException e) {
			e.printStackTrace();
		}
		return in;
	}
}
