package io.renren.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
 

/**
 * excle文件上传工具上传后返回路径
 * @author Administrator
 *
 */
public class UploadAndExcelUtil {
	/**
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	
	public static String saveFile(MultipartFile multipartFile,String diskDirPath) throws IOException {
		try {
			String originalFilename = multipartFile.getOriginalFilename();
			String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
			/*if (suffix.equalsIgnoreCase("xls") || suffix.equalsIgnoreCase("xlsx")) {
				diskDirPath = File.separator+"excle";
			} */
			File nf = new File(diskDirPath);
			if (!nf.exists()) {
				nf.mkdirs();
			}
			File newFile = new File(diskDirPath, newFileName);
			multipartFile.transferTo(newFile);
			if (suffix.equalsIgnoreCase("xls") || suffix.equalsIgnoreCase("xlsx")) {
				return diskDirPath + File.separator + newFileName;
			}
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
