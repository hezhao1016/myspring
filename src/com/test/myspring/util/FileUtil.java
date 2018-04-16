package com.test.myspring.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 
 * java文件操作工具类
 * 
 * 先决性条件
 * 
 * log4j.jar ant.jar
 * 
 * 此类包含利用JAVA进行文件的压缩，解压，删除，拷贝操作。部分代码总结了网上的代码，并修正了很多BUG,例如压缩中文问题， 压缩文件中多余空文件问题。
 * 
 * 注意：此类中用到的压缩类ZipEntry等都来自于org.apache.tools包而非java.util。此包在ant.jar中有。
 * 
 */
public class FileUtil {

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static boolean deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				return false;
			}
			file.delete();
			return true;
		} else {
			System.out.println("FileUtil.deleteFile.FileNotFoundException :找不到"
					+ file.getPath() + "文件！");
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		return deleteFile(file);
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @param file
	 */
	public static boolean deleteFileOrDirectory(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
			return true;
		} else {
			System.out
					.println("FileUtil.deleteFileOrDirectory.FileNotFoundException :找不到"
							+ file.getPath() + "文件！");
			return false;
		}
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @param file
	 */
	public static boolean deleteFileOrDirectory(String fileName) {
		File file = new File(fileName);
		return deleteFileOrDirectory(file);
	}

	/**
	 * 复制文件
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static boolean copyFile(File sourceFile, File targetFile)
			throws IOException {
		if (!sourceFile.exists()) {
			System.out.println("FileUtil.copyFile.FileNotFoundException :找不到"
					+ sourceFile.getPath() + "文件！");
			return false;
		}
		if (!targetFile.exists()) {
			System.out.println("FileUtil.copyFile.FileNotFoundException :找不到"
					+ targetFile.getPath() + "文件！");
			return false;
		}
		// 新建文件输入流并对它进行缓冲
		InputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		OutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
		return true;
	}

	/**
	 * 复制文件
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static boolean copyFile(String sourceFileName, String targetFileName)
			throws IOException {
		File sourceFile = new File(sourceFileName);
		File targetFile = new File(targetFileName);
		return copyFile(sourceFile, targetFile);
	}

	/**
	 * 复制文件夹
	 * 
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static boolean copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		boolean flag = true;
		if (file != null) {

			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// 源文件
					File sourceFile = file[i];
					// 目标文件
					File targetFile = new File(
							new File(targetDir).getAbsolutePath()
									+ File.separator + file[i].getName());
					if (!copyFile(sourceFile, targetFile)) {
						flag = false;
					}
				}
				if (file[i].isDirectory()) {
					// 准备复制的源文件夹
					String dir1 = sourceDir + "/" + file[i].getName();
					// 准备复制的目标文件夹
					String dir2 = targetDir + "/" + file[i].getName();
					copyDirectiory(dir1, dir2);
				}
			}
		}
		return flag;
	}

	/**
	 * 复制文本文件
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static boolean copyTextFile(File sourceFile, File targetFile)
			throws IOException {
		if (!sourceFile.exists()) {
			System.out
					.println("FileUtil.copyTextFile.FileNotFoundException :找不到"
							+ sourceFile.getPath() + "文件！");
			return false;
		}
		if (!targetFile.exists()) {
			System.out
					.println("FileUtil.copyTextFile.FileNotFoundException :找不到"
							+ targetFile.getPath() + "文件！");
			return false;
		}
		Reader reader = new FileReader(sourceFile);
		BufferedReader br = new BufferedReader(reader);
		Writer writer = new FileWriter(targetFile);
		BufferedWriter bw = new BufferedWriter(writer);
		StringBuffer sbf = new StringBuffer();
		String line = br.readLine();
		while (line != null) {
			sbf.append(line + "\r\n");// \r\n回车换行(写入文本时可以保持风格不变)
			line = br.readLine();
		}
		bw.flush();// 刷新
		bw.write(sbf.toString());// 写入文件
		br.close();
		reader.close();
		bw.close();
		writer.close();
		return true;
	}

	/**
	 * 复制文本文件
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static boolean copyTextFile(String sourceFileName,
			String targetFileName) throws IOException {
		File sourceFile = new File(sourceFileName);
		File targetFile = new File(targetFileName);
		return copyTextFile(sourceFile, targetFile);
	}

	/**
	 * 通过InputStream读取文件
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param length
	 *            缓冲区大小
	 * @return
	 * @throws IOException
	 */
	public static String readTextByInputStream(File sourceFile, Integer length)
			throws IOException {
		if (!sourceFile.exists()) {
			System.out
					.println("FileUtil.readByInputStream.FileNotFoundException :找不到"
							+ sourceFile.getPath() + "文件！");
			return null;
		}
		if (length == null) {
			length = 1024;
		}
		InputStream is = new FileInputStream(sourceFile);
		byte[] bytes = new byte[length];
		int count = is.read(bytes);
		String result = null;
		while (count != -1) {
			for (byte b : bytes) {
				result += (char) b;
			}
			bytes = new byte[length];
			count = is.read(bytes);
		}
		is.close();
		return result;
	}

	/**
	 * 通过InputStream读取文件
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param length
	 *            缓冲区大小
	 * @return
	 * @throws IOException
	 */
	public static String readTextByInputStream(String sourceFileName,
			Integer length) throws IOException {
		File sourceFile = new File(sourceFileName);
		return readTextByInputStream(sourceFile, length);
	}

	/**
	 * 通过BufferedReader读取文件
	 * 
	 * @param sourceFile
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public static String readTextByBufferedReader(File sourceFile)
			throws IOException {
		if (!sourceFile.exists()) {
			System.out
					.println("FileUtil.readByInputStream.FileNotFoundException :找不到"
							+ sourceFile.getPath() + "文件！");
			return null;
		}
		Reader reader = new FileReader(sourceFile);
		BufferedReader br = new BufferedReader(reader);
		String line = br.readLine();
		String LINE_SEPARATOR = System.getProperty("line.separator");// 代表换行符
		StringBuffer sbf = new StringBuffer();
		while (line != null) {
			sbf.append(line + LINE_SEPARATOR);
			line = br.readLine();
		}
		br.close();
		reader.close();
		return sbf.toString();
	}

	/**
	 * 通过BufferedReader读取文件
	 * 
	 * @param sourceFile
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public static String readTextByBufferedReader(String sourceFileName)
			throws IOException {
		File sourceFile = new File(sourceFileName);
		return readTextByBufferedReader(sourceFile);
	}

	// --------------------------------------------------------------------------------------------------

	private final static int BUFFER_SIZE = 16 * 1024;

	protected static Logger log = Logger.getLogger(FileUtil.class);

	public static void zip(String inputFileName, String outputFileName)
			throws Exception {

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				outputFileName));

		zip(out, new File(inputFileName), "");

		log.debug("压缩完成！");

		out.closeEntry();

		out.close();

	}

	/**
	 * 
	 * 压缩文件
	 * 
	 * @param out
	 *            org.apache.tools.zip.ZipOutputStream
	 * 
	 * @param file
	 *            待压缩的文件
	 * 
	 * @param base
	 *            压缩的根目录
	 */

	private static void zip(ZipOutputStream out, File file, String base)
			throws Exception {

		if (file.isDirectory()) {

			File[] fl = file.listFiles();

			base = base.length() == 0 ? "" : base + File.separator;

			for (int i = 0; i < fl.length; i++) {

				zip(out, fl[i], base + fl[i].getName());

			}

		} else {

			out.putNextEntry(new ZipEntry(base));

			log.debug("添加压缩文件：" + base);

			FileInputStream in = new FileInputStream(file);

			int b;

			while ((b = in.read()) != -1) {

				out.write(b);

			}

			in.close();

		}

	}

	/**
	 * 
	 * 解压zip文件
	 * 
	 * @param zipFileName
	 *            待解压的zip文件路径，例如：c:\\a.zip
	 * 
	 * @param outputDirectory
	 *            解压目标文件夹,例如：c:\\a\
	 */

	public static void unZip(String zipFileName, String outputDirectory)
			throws Exception {

		ZipFile zipFile = new ZipFile(zipFileName);

		try {

			Enumeration<?> e = zipFile.getEntries();

			ZipEntry zipEntry = null;

			createDirectory(outputDirectory, "");

			while (e.hasMoreElements()) {

				zipEntry = (ZipEntry) e.nextElement();

				log.debug("解压：" + zipEntry.getName());

				if (zipEntry.isDirectory()) {

					String name = zipEntry.getName();

					name = name.substring(0, name.length() - 1);

					File f = new File(outputDirectory + File.separator + name);

					f.mkdir();

					log.debug("创建目录：" + outputDirectory + File.separator + name);

				} else {

					String fileName = zipEntry.getName();

					fileName = fileName.replace('\\', '/');

					if (fileName.indexOf("/") != -1) {

						createDirectory(outputDirectory, fileName.substring(0,
								fileName.lastIndexOf("/")));

						fileName = fileName.substring(
								fileName.lastIndexOf("/") + 1,
								fileName.length());

					}

					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());

					f.createNewFile();

					InputStream in = zipFile.getInputStream(zipEntry);

					FileOutputStream out = new FileOutputStream(f);

					byte[] by = new byte[1024];

					int c;

					while ((c = in.read(by)) != -1) {

						out.write(by, 0, c);

					}

					in.close();

					out.close();

				}

			}

		} catch (Exception ex) {

			System.out.println(ex.getMessage());

		} finally {

			zipFile.close();

			log.debug("解压完成！");

		}

	}

	private static void createDirectory(String directory, String subDirectory) {

		String dir[];

		File fl = new File(directory);

		try {

			if (subDirectory == "" && fl.exists() != true) {

				fl.mkdir();

			} else if (subDirectory != "") {

				dir = subDirectory.replace('\\', '/').split("/");

				for (int i = 0; i < dir.length; i++) {

					File subFile = new File(directory + File.separator + dir[i]);

					if (subFile.exists() == false)

						subFile.mkdir();

					directory += File.separator + dir[i];

				}

			}

		} catch (Exception ex) {

			System.out.println(ex.getMessage());

		}

	}

	/**
	 * 
	 * 拷贝文件夹中的所有文件到另外一个文件夹
	 * 
	 * @param srcDirector
	 *            源文件夹
	 * 
	 * @param desDirector
	 *            目标文件夹
	 */

	public static void copyFileWithDirector(String srcDirector,
			String desDirector) throws IOException {

		(new File(desDirector)).mkdirs();

		File[] file = (new File(srcDirector)).listFiles();

		for (int i = 0; i < file.length; i++) {

			if (file[i].isFile()) {

				log.debug("拷贝：" + file[i].getAbsolutePath() + "-->"
						+ desDirector + "/" + file[i].getName());

				FileInputStream input = new FileInputStream(file[i]);

				FileOutputStream output = new FileOutputStream(desDirector
						+ "/" + file[i].getName());

				byte[] b = new byte[1024 * 5];

				int len;

				while ((len = input.read(b)) != -1) {

					output.write(b, 0, len);

				}

				output.flush();

				output.close();

				input.close();

			}

			if (file[i].isDirectory()) {

				log.debug("拷贝：" + file[i].getAbsolutePath() + "-->"
						+ desDirector + "/" + file[i].getName());

				copyFileWithDirector(srcDirector + "/" + file[i].getName(),
						desDirector + "/" + file[i].getName());

			}

		}

	}

	/**
	 * 
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            folderPath 文件夹完整绝对路径
	 */

	public static void delFolder(String folderPath) throws Exception {

		// 删除完里面所有内容

		delAllFile(folderPath);

		String filePath = folderPath;

		filePath = filePath.toString();

		File myFilePath = new File(filePath);

		// 删除空文件夹

		myFilePath.delete();

	}

	/**
	 * 
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 */

	public static boolean delAllFile(String path) throws Exception {

		boolean flag = false;

		File file = new File(path);

		if (!file.exists()) {

			return flag;

		}

		if (!file.isDirectory()) {

			return flag;

		}

		String[] tempList = file.list();

		File temp = null;

		for (int i = 0; i < tempList.length; i++) {

			if (path.endsWith(File.separator)) {

				temp = new File(path + tempList[i]);

			} else {

				temp = new File(path + File.separator + tempList[i]);

			}

			if (temp.isFile()) {

				temp.delete();

			}

			if (temp.isDirectory()) {

				// 先删除文件夹里面的文件

				delAllFile(path + "/" + tempList[i]);

				// 再删除空文件夹

				delFolder(path + "/" + tempList[i]);

				flag = true;

			}

		}

		return flag;

	}

	/**
	 * 功能描述：列出某文件夹及其子文件夹下面的文件，并可根据扩展名过滤
	 * 
	 * @param path
	 *            文件夹
	 */
	public static void list(File path) {
		if (!path.exists()) {
			System.out.println("文件名称不存在!");
		} else {
			if (path.isFile()) {
				if (path.getName().toLowerCase().endsWith(".pdf")
						|| path.getName().toLowerCase().endsWith(".doc")
						|| path.getName().toLowerCase().endsWith(".chm")
						|| path.getName().toLowerCase().endsWith(".html")
						|| path.getName().toLowerCase().endsWith(".htm")) {// 文件格式
					System.out.println(path);
					System.out.println(path.getName());
				}
			} else {
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) {
					list(files[i]);
				}
			}
		}
	}

	/**
	 * 功能描述：拷贝一个目录或者文件到指定路径下，即把源文件拷贝到目标文件路径下
	 * 
	 * @param source
	 *            源文件
	 * @param target
	 *            目标文件路径
	 * @return void
	 */
	public static void copy(File source, File target) {
		File tarpath = new File(target, source.getName());
		if (source.isDirectory()) {
			tarpath.mkdir();
			File[] dir = source.listFiles();
			for (int i = 0; i < dir.length; i++) {
				copy(dir[i], tarpath);
			}
		} else {
			try {
				InputStream is = new FileInputStream(source); // 用于读取文件的原始字节流
				OutputStream os = new FileOutputStream(tarpath); // 用于写入文件的原始字节的流
				byte[] buf = new byte[1024];// 存储读取数据的缓冲区大小
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */

	/*
	 * copy这个方法主要是用来实现文件上传的，实现的原理就是分别打开输入输出流，进行文件拷贝的
	 */
	public void copyFile2(File src, File dist) {
		InputStream in = null;
		BufferedOutputStream out = null;

		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dist),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			try {
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	// ------------------------------------------------------------------------------------------

	/**
	 * 读取文件内容（使用UTF-8编码）
	 * 
	 * @param filePath
	 *            输出文件路径
	 * @return
	 * @throws Exception
	 */
	public static String readFileUTF8(String filePath) throws Exception {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis,
				"UTF-8"));
		String fileContent = "";
		String temp = "";
		while ((temp = br.readLine()) != null) {
			fileContent = fileContent + temp;
		}
		br.close();
		fis.close();
		return fileContent;
	}

	/**
	 * 将文件内容写入文件（使用UTF-8编码）
	 * 
	 * @param content
	 *            文件内容
	 * @param filePath
	 *            输出文件路径
	 * @throws Exception
	 */
	public static void writeFileUTF8(String content, String filePath)
			throws Exception {
		createDir(filePath);
		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,
				"UTF-8"));
		bw.write(content);
		bw.flush();
		bw.close();
		fos.close();
	}

	/**
	 * 写文件
	 * 
	 * @param outputPath
	 *            输出文件路径
	 * @param is
	 *            输入流
	 * @param isApend
	 *            是否追加
	 * @throws IOException
	 */
	public static void writeFile(InputStream is, String outputPath,
			boolean isApend) throws IOException {
		FileInputStream fis = (FileInputStream) is;
		createDir(outputPath);
		FileOutputStream fos = new FileOutputStream(outputPath, isApend);
		byte[] bs = new byte[1024 * 16];
		int len = -1;
		while ((len = fis.read(bs)) != -1) {
			fos.write(bs, 0, len);
		}
		fos.close();
		fis.close();
	}

	/**
	 * copy文件
	 * 
	 * @param is
	 *            输入流
	 * @param outputPath
	 *            输出文件路径
	 * @throws Exception
	 */
	public static void writeFile(InputStream is, String outputPath)
			throws Exception {
		InputStream bis = null;
		OutputStream bos = null;
		createDir(outputPath);
		bis = new BufferedInputStream(is);
		bos = new BufferedOutputStream(new FileOutputStream(outputPath));
		byte[] bs = new byte[1024 * 10];
		int len = -1;
		while ((len = bis.read(bs)) != -1) {
			bos.write(bs, 0, len);
		}
		bos.flush();
		bis.close();
		bos.close();
	}

	/**
	 * 写文件
	 * 
	 * @param outputPath
	 *            输出文件路径
	 * @param inPath
	 *            输入文件路径
	 * @throws IOException
	 */
	public static void writeFile(String inPath, String outputPath,
			boolean isApend) throws IOException {
		if (new File(inPath).exists()) {
			FileInputStream fis = new FileInputStream(inPath);
			writeFile(fis, outputPath, isApend);
		} else {
			System.out.println("文件copy失败，由于源文件不存在!");
		}
	}

	/**
	 * 将字符串写到文件内
	 * 
	 * @param outputPath
	 *            输出文件路径
	 * @param msg
	 *            字符串
	 * @param isApend
	 *            是否追加
	 * @throws IOException
	 */
	public static void writeContent(String msg, String outputPath,
			boolean isApend) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath,
				isApend));
		bw.write(msg);
		bw.flush();
		bw.close();
	}

	/**
	 * 删除文件夹下的所有内容,包括本文件夹
	 * 
	 * @param path
	 *            删除文件路径
	 * @throws IOException
	 */
	public static void delFileOrDerectory(String path) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					File subFile = files[i];
					delFileOrDerectory(subFile.getAbsolutePath());
				}
				file.delete();
			} else {
				file.delete();
			}
		}
	}

	/**
	 * 如果欲写入的文件所在目录不存在，需先创建
	 * 
	 * @param outputPath
	 *            输出文件路径
	 */
	public static void createDir(String outputPath) {
		File outputFile = new File(outputPath);
		File outputDir = outputFile.getParentFile();
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}
	}

}