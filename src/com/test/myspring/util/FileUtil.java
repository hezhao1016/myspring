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
 * java�ļ�����������
 * 
 * �Ⱦ�������
 * 
 * log4j.jar ant.jar
 * 
 * �����������JAVA�����ļ���ѹ������ѹ��ɾ�����������������ִ����ܽ������ϵĴ��룬�������˺ܶ�BUG,����ѹ���������⣬ ѹ���ļ��ж�����ļ����⡣
 * 
 * ע�⣺�������õ���ѹ����ZipEntry�ȶ�������org.apache.tools������java.util���˰���ant.jar���С�
 * 
 */
public class FileUtil {

	/**
	 * ɾ���ļ�
	 * 
	 * @param file
	 */
	public static boolean deleteFile(File file) {
		if (file.exists()) { // �ж��ļ��Ƿ����
			if (file.isFile()) { // �ж��Ƿ����ļ�
				file.delete(); // delete()���� ��Ӧ��֪�� ��ɾ������˼;
			} else if (file.isDirectory()) { // �����������һ��Ŀ¼
				return false;
			}
			file.delete();
			return true;
		} else {
			System.out.println("FileUtil.deleteFile.FileNotFoundException :�Ҳ���"
					+ file.getPath() + "�ļ���");
			return false;
		}
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param file
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		return deleteFile(file);
	}

	/**
	 * ɾ���ļ����ļ���
	 * 
	 * @param file
	 */
	public static boolean deleteFileOrDirectory(File file) {
		if (file.exists()) { // �ж��ļ��Ƿ����
			if (file.isFile()) { // �ж��Ƿ����ļ�
				file.delete(); // delete()���� ��Ӧ��֪�� ��ɾ������˼;
			} else if (file.isDirectory()) { // �����������һ��Ŀ¼
				File files[] = file.listFiles(); // ����Ŀ¼�����е��ļ� files[];
				for (int i = 0; i < files.length; i++) { // ����Ŀ¼�����е��ļ�
					deleteFile(files[i]); // ��ÿ���ļ� ������������е���
				}
			}
			file.delete();
			return true;
		} else {
			System.out
					.println("FileUtil.deleteFileOrDirectory.FileNotFoundException :�Ҳ���"
							+ file.getPath() + "�ļ���");
			return false;
		}
	}

	/**
	 * ɾ���ļ����ļ���
	 * 
	 * @param file
	 */
	public static boolean deleteFileOrDirectory(String fileName) {
		File file = new File(fileName);
		return deleteFileOrDirectory(file);
	}

	/**
	 * �����ļ�
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static boolean copyFile(File sourceFile, File targetFile)
			throws IOException {
		if (!sourceFile.exists()) {
			System.out.println("FileUtil.copyFile.FileNotFoundException :�Ҳ���"
					+ sourceFile.getPath() + "�ļ���");
			return false;
		}
		if (!targetFile.exists()) {
			System.out.println("FileUtil.copyFile.FileNotFoundException :�Ҳ���"
					+ targetFile.getPath() + "�ļ���");
			return false;
		}
		// �½��ļ����������������л���
		InputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// �½��ļ���������������л���
		OutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// ��������
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// ˢ�´˻���������
		outBuff.flush();

		// �ر���
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
		return true;
	}

	/**
	 * �����ļ�
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
	 * �����ļ���
	 * 
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static boolean copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		// �½�Ŀ��Ŀ¼
		(new File(targetDir)).mkdirs();
		// ��ȡԴ�ļ��е�ǰ�µ��ļ���Ŀ¼
		File[] file = (new File(sourceDir)).listFiles();
		boolean flag = true;
		if (file != null) {

			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// Դ�ļ�
					File sourceFile = file[i];
					// Ŀ���ļ�
					File targetFile = new File(
							new File(targetDir).getAbsolutePath()
									+ File.separator + file[i].getName());
					if (!copyFile(sourceFile, targetFile)) {
						flag = false;
					}
				}
				if (file[i].isDirectory()) {
					// ׼�����Ƶ�Դ�ļ���
					String dir1 = sourceDir + "/" + file[i].getName();
					// ׼�����Ƶ�Ŀ���ļ���
					String dir2 = targetDir + "/" + file[i].getName();
					copyDirectiory(dir1, dir2);
				}
			}
		}
		return flag;
	}

	/**
	 * �����ı��ļ�
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static boolean copyTextFile(File sourceFile, File targetFile)
			throws IOException {
		if (!sourceFile.exists()) {
			System.out
					.println("FileUtil.copyTextFile.FileNotFoundException :�Ҳ���"
							+ sourceFile.getPath() + "�ļ���");
			return false;
		}
		if (!targetFile.exists()) {
			System.out
					.println("FileUtil.copyTextFile.FileNotFoundException :�Ҳ���"
							+ targetFile.getPath() + "�ļ���");
			return false;
		}
		Reader reader = new FileReader(sourceFile);
		BufferedReader br = new BufferedReader(reader);
		Writer writer = new FileWriter(targetFile);
		BufferedWriter bw = new BufferedWriter(writer);
		StringBuffer sbf = new StringBuffer();
		String line = br.readLine();
		while (line != null) {
			sbf.append(line + "\r\n");// \r\n�س�����(д���ı�ʱ���Ա��ַ�񲻱�)
			line = br.readLine();
		}
		bw.flush();// ˢ��
		bw.write(sbf.toString());// д���ļ�
		br.close();
		reader.close();
		bw.close();
		writer.close();
		return true;
	}

	/**
	 * �����ı��ļ�
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
	 * ͨ��InputStream��ȡ�ļ�
	 * 
	 * @param sourceFile
	 *            Դ�ļ�
	 * @param length
	 *            ��������С
	 * @return
	 * @throws IOException
	 */
	public static String readTextByInputStream(File sourceFile, Integer length)
			throws IOException {
		if (!sourceFile.exists()) {
			System.out
					.println("FileUtil.readByInputStream.FileNotFoundException :�Ҳ���"
							+ sourceFile.getPath() + "�ļ���");
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
	 * ͨ��InputStream��ȡ�ļ�
	 * 
	 * @param sourceFile
	 *            Դ�ļ�
	 * @param length
	 *            ��������С
	 * @return
	 * @throws IOException
	 */
	public static String readTextByInputStream(String sourceFileName,
			Integer length) throws IOException {
		File sourceFile = new File(sourceFileName);
		return readTextByInputStream(sourceFile, length);
	}

	/**
	 * ͨ��BufferedReader��ȡ�ļ�
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
					.println("FileUtil.readByInputStream.FileNotFoundException :�Ҳ���"
							+ sourceFile.getPath() + "�ļ���");
			return null;
		}
		Reader reader = new FileReader(sourceFile);
		BufferedReader br = new BufferedReader(reader);
		String line = br.readLine();
		String LINE_SEPARATOR = System.getProperty("line.separator");// �����з�
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
	 * ͨ��BufferedReader��ȡ�ļ�
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

		log.debug("ѹ����ɣ�");

		out.closeEntry();

		out.close();

	}

	/**
	 * 
	 * ѹ���ļ�
	 * 
	 * @param out
	 *            org.apache.tools.zip.ZipOutputStream
	 * 
	 * @param file
	 *            ��ѹ�����ļ�
	 * 
	 * @param base
	 *            ѹ���ĸ�Ŀ¼
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

			log.debug("���ѹ���ļ���" + base);

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
	 * ��ѹzip�ļ�
	 * 
	 * @param zipFileName
	 *            ����ѹ��zip�ļ�·�������磺c:\\a.zip
	 * 
	 * @param outputDirectory
	 *            ��ѹĿ���ļ���,���磺c:\\a\
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

				log.debug("��ѹ��" + zipEntry.getName());

				if (zipEntry.isDirectory()) {

					String name = zipEntry.getName();

					name = name.substring(0, name.length() - 1);

					File f = new File(outputDirectory + File.separator + name);

					f.mkdir();

					log.debug("����Ŀ¼��" + outputDirectory + File.separator + name);

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

			log.debug("��ѹ��ɣ�");

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
	 * �����ļ����е������ļ�������һ���ļ���
	 * 
	 * @param srcDirector
	 *            Դ�ļ���
	 * 
	 * @param desDirector
	 *            Ŀ���ļ���
	 */

	public static void copyFileWithDirector(String srcDirector,
			String desDirector) throws IOException {

		(new File(desDirector)).mkdirs();

		File[] file = (new File(srcDirector)).listFiles();

		for (int i = 0; i < file.length; i++) {

			if (file[i].isFile()) {

				log.debug("������" + file[i].getAbsolutePath() + "-->"
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

				log.debug("������" + file[i].getAbsolutePath() + "-->"
						+ desDirector + "/" + file[i].getName());

				copyFileWithDirector(srcDirector + "/" + file[i].getName(),
						desDirector + "/" + file[i].getName());

			}

		}

	}

	/**
	 * 
	 * ɾ���ļ���
	 * 
	 * @param folderPath
	 *            folderPath �ļ�����������·��
	 */

	public static void delFolder(String folderPath) throws Exception {

		// ɾ����������������

		delAllFile(folderPath);

		String filePath = folderPath;

		filePath = filePath.toString();

		File myFilePath = new File(filePath);

		// ɾ�����ļ���

		myFilePath.delete();

	}

	/**
	 * 
	 * ɾ��ָ���ļ����������ļ�
	 * 
	 * @param path
	 *            �ļ�����������·��
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

				// ��ɾ���ļ���������ļ�

				delAllFile(path + "/" + tempList[i]);

				// ��ɾ�����ļ���

				delFolder(path + "/" + tempList[i]);

				flag = true;

			}

		}

		return flag;

	}

	/**
	 * �����������г�ĳ�ļ��м������ļ���������ļ������ɸ�����չ������
	 * 
	 * @param path
	 *            �ļ���
	 */
	public static void list(File path) {
		if (!path.exists()) {
			System.out.println("�ļ����Ʋ�����!");
		} else {
			if (path.isFile()) {
				if (path.getName().toLowerCase().endsWith(".pdf")
						|| path.getName().toLowerCase().endsWith(".doc")
						|| path.getName().toLowerCase().endsWith(".chm")
						|| path.getName().toLowerCase().endsWith(".html")
						|| path.getName().toLowerCase().endsWith(".htm")) {// �ļ���ʽ
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
	 * ��������������һ��Ŀ¼�����ļ���ָ��·���£�����Դ�ļ�������Ŀ���ļ�·����
	 * 
	 * @param source
	 *            Դ�ļ�
	 * @param target
	 *            Ŀ���ļ�·��
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
				InputStream is = new FileInputStream(source); // ���ڶ�ȡ�ļ���ԭʼ�ֽ���
				OutputStream os = new FileOutputStream(tarpath); // ����д���ļ���ԭʼ�ֽڵ���
				byte[] buf = new byte[1024];// �洢��ȡ���ݵĻ�������С
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
	 * copy���������Ҫ������ʵ���ļ��ϴ��ģ�ʵ�ֵ�ԭ����Ƿֱ������������������ļ�������
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
	 * ��ȡ�ļ����ݣ�ʹ��UTF-8���룩
	 * 
	 * @param filePath
	 *            ����ļ�·��
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
	 * ���ļ�����д���ļ���ʹ��UTF-8���룩
	 * 
	 * @param content
	 *            �ļ�����
	 * @param filePath
	 *            ����ļ�·��
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
	 * д�ļ�
	 * 
	 * @param outputPath
	 *            ����ļ�·��
	 * @param is
	 *            ������
	 * @param isApend
	 *            �Ƿ�׷��
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
	 * copy�ļ�
	 * 
	 * @param is
	 *            ������
	 * @param outputPath
	 *            ����ļ�·��
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
	 * д�ļ�
	 * 
	 * @param outputPath
	 *            ����ļ�·��
	 * @param inPath
	 *            �����ļ�·��
	 * @throws IOException
	 */
	public static void writeFile(String inPath, String outputPath,
			boolean isApend) throws IOException {
		if (new File(inPath).exists()) {
			FileInputStream fis = new FileInputStream(inPath);
			writeFile(fis, outputPath, isApend);
		} else {
			System.out.println("�ļ�copyʧ�ܣ�����Դ�ļ�������!");
		}
	}

	/**
	 * ���ַ���д���ļ���
	 * 
	 * @param outputPath
	 *            ����ļ�·��
	 * @param msg
	 *            �ַ���
	 * @param isApend
	 *            �Ƿ�׷��
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
	 * ɾ���ļ����µ���������,�������ļ���
	 * 
	 * @param path
	 *            ɾ���ļ�·��
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
	 * �����д����ļ�����Ŀ¼�����ڣ����ȴ���
	 * 
	 * @param outputPath
	 *            ����ļ�·��
	 */
	public static void createDir(String outputPath) {
		File outputFile = new File(outputPath);
		File outputDir = outputFile.getParentFile();
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}
	}

}