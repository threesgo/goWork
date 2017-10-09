package com.yunwang.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class MyIOUtils extends IOUtils {
	public static String fileToString(String path) {
		try {
			return fileToString(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public static String fileToString(InputStream input) {
		BufferedReader reader = null;
        InputStreamReader inputFileReader = null;
        String tempString = null;
        StringBuffer buf = new StringBuffer();
		try {
			inputFileReader = new InputStreamReader(input, "utf-8");
            reader = new BufferedReader(inputFileReader);
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	buf.append(tempString+" ");
            }
            //String result = buf.substring(0, buf.length()-System.getProperty("line.separator").length());
            reader.close();
			return buf.toString();
		} catch (IOException e) {

		} finally {
		}
		return null;
	}

	public static void writeToPath(String data, String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream out;
			out = new FileOutputStream(file);
			write(data, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @date 2017-5-24
	 * @author YBF
	 * @param locationPattern
	 *            正则匹配
	 * @return
	 * @throws IOException
	 *             <p>
	 *             通过spring获取系统资源
	 *             </p>
	 */
	public static Resource[] getSysResources(String locationPattern)
			throws IOException {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return resolver.getResources(locationPattern);
	}

	public static Resource getClassResource(String classpath) {
		Resource resource = new ClassPathResource(classpath);
		return resource;
	}

	public static InputStream getClassResourceAsStream(String classpath) {
		try {
			return getClassResource(classpath).getInputStream();
		} catch (Exception e) {
			return null;
		}
	}
}
