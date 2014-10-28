package com.cdd.mapi.common.serivce;

import org.springframework.web.multipart.MultipartFile;

import com.cdd.mapi.common.enums.EUploadType;
import com.cdd.mapi.common.pojo.ImageUploadResult;

public interface IUploadService {

	public ImageUploadResult processupload(Integer memberId,MultipartFile file,EUploadType uploadType);

}
