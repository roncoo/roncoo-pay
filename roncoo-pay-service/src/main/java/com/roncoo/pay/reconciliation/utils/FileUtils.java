package com.roncoo.pay.reconciliation.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.roncoo.pay.reconciliation.utils.https.HttpResponse;

/**
 * 
 * @类功能说明： 文件工具类.
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院:www.roncoo.com）
 * @作者：Along.shen
 * @创建时间：2016年5月23日,上午11:33:56.
 * @版本：V1.0
 *
 */

public class FileUtils {
	private static final Log LOG = LogFactory.getLog(FileUtils.class);

	/**
	 * @param response
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static File saveFile(HttpResponse response, File file) throws IOException {

		// 判断父文件是否存在,不存在就创建
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				// 新建文件目录失败，抛异常
				throw new IOException("创建文件(父层文件夹)失败, filepath: " + file.getAbsolutePath());
			}
		}
		// 判断文件是否存在，不存在则创建
		if (!file.exists()) {
			if (!file.createNewFile()) {
				// 新建文件失败，抛异常
				throw new IOException("创建文件失败, filepath: " + file.getAbsolutePath());
			}
		}

		InputStream is = response.getInputStream();
		FileOutputStream fileOut = null;
		FileChannel fileChannel = null;
		try {
			fileOut = new FileOutputStream(file);
			fileChannel = fileOut.getChannel();

			ReadableByteChannel readableChannel = Channels.newChannel(is);
			ByteBuffer buffer = ByteBuffer.allocate(1024 * 32);
			while (true) {
				buffer.clear();
				if (readableChannel.read(buffer) == -1) {
					readableChannel.close();
					break;
				}
				buffer.flip();
				fileChannel.write(buffer);
			}
			return file;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("保存账单文件失败, filepath: " + file.getAbsolutePath());
		} catch (IOException e) {
			throw new IOException("保存账单文件失败, filepath: " + file.getAbsolutePath(), e);
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					LOG.error("保存账单时关闭流失败", e);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LOG.error("保存账单时关闭流失败", e);
				}
			}
			if (fileChannel != null) {
				try {
					fileChannel.close();
				} catch (IOException e) {
					LOG.error("保存账单时关闭流失败", e);
				}
			}
		}
	}

	/**
	 * 解压到指定目录
	 *
	 * @param zipPath
	 * @param descDir
	 * @author isea533
	 */
	public static List<String> unZipFiles(String zipPath, String descDir) throws IOException {
		return unZipFiles(new File(zipPath), descDir);
	}

	/**
	 * 解压文件到指定目录
	 *
	 * @param zipFile
	 * @param descDir
	 * @author isea533
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> unZipFiles(File zipFile, String descDir) throws IOException {
		List<String> result = new ArrayList<String>();
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		Charset charset = Charset.forName("GBK");
		ZipFile zip = new ZipFile(zipFile, charset);
		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
			;
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			result.add(outPath);

			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		return result;
	}

	/**
	 * 解析csv文件 到一个list中 每个单元个为一个String类型记录，每一行为一个list。 再将所有的行放到一个总list中
	 *
	 * @param file
	 *            要解析的cvs文件
	 * @param charsetName
	 *            指定的字符编号
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> readCSVFile(String file, String charsetName) throws IOException {
		if (file == null || !file.contains(".csv")) {
			return null;
		}
		InputStreamReader fr = new InputStreamReader(new FileInputStream(file), charsetName);

		BufferedReader br = new BufferedReader(fr);
		String rec = null;// 一行
		String str;// 一个单元格
		List<List<String>> listFile = new ArrayList<List<String>>();
		try {
			// 读取一行
			while ((rec = br.readLine()) != null) {
				Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
				Matcher mCells = pCells.matcher(rec);
				List<String> cells = new ArrayList<String>();// 每行记录一个list
				// 读取每个单元格
				while (mCells.find()) {
					str = mCells.group();
					str = str.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
					str = str.replaceAll("(?sm)(\"(\"))", "$2");
					cells.add(str);
				}
				listFile.add(cells);
			}
		} catch (Exception e) {
			LOG.error("异常", e);
		} finally {
			if (fr != null) {
				fr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return listFile;
	}
}
