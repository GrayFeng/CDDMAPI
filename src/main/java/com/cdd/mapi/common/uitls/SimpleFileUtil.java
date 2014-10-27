package com.cdd.mapi.common.uitls;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.util.IOUtils;

public class SimpleFileUtil {
	static Logger logger = LoggerFactory.getLogger(SimpleFileUtil.class);

	public static String detectFileStype(byte[] bytes) throws Exception {
		MagicMatch mm = Magic.getMagicMatch(bytes);
		String mime = mm.getMimeType();
		if (mime == null) {
			throw new RuntimeException("can't find find mime type");
		}
		return mime.substring(mime.indexOf("/") + 1, mime.length());
	}

	public static String genRandomFileName(String fileStyle) {
		if (!fileStyle.startsWith(".")) {
			fileStyle = "." + fileStyle;
		}
		String sourceStr = "0123456789abcdefghijklmnopqrstuvwxyz";
		String fileName = String.valueOf(System.currentTimeMillis());
		return fileName + "_" + RandomStringUtils.random(4, sourceStr.toCharArray()) + fileStyle;
	}

	public static byte[] boBin(InputStream is) {
		byte[] content = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream out = null;
		try {
			bis = new BufferedInputStream(is, 1024);
			byte[] bytes = new byte[1024];
			int len;
			out = new ByteArrayOutputStream();
			while ((len = bis.read(bytes)) > 0) {
				out.write(bytes, 0, len);
			}
			bis.close();
			content = out.toByteArray();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		} finally {
			IOUtils.close(is);
			IOUtils.close(out);
		}

		return content;
	}
}
