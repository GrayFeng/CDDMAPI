package com.cdd.mapi.cof.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cdd.mapi.common.Constant;
import com.cdd.mapi.common.enums.EEchoCode;
import com.cdd.mapi.common.enums.EUploadType;
import com.cdd.mapi.common.pojo.ImageUploadResult;
import com.cdd.mapi.common.pojo.Result;
import com.cdd.mapi.common.serivce.IUploadService;
import com.cdd.mapi.common.uitls.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * CDDMAPI
 * @date 2014-10-30 下午8:31:16
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/api/cof")
public class COFUploadController {
	
	private Logger log = LoggerFactory.getLogger(COFUploadController.class);
	
	@Autowired
	private IUploadService uploadService;
	
	@RequestMapping(value="photoUpload",method = RequestMethod.POST)
	@ResponseBody
	public String photoUpload(String uid,HttpServletRequest request){
		Result result = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> photoList = multipartRequest.getFiles("files");
		if(photoList != null && !photoList.isEmpty()){
			Map<String,Object> resultMap = Maps.newHashMap();
			List<String> photoUrlList = Lists.newArrayList();
			List<String> failList = Lists.newArrayList();
			for(MultipartFile file : photoList){
				try{
					ImageUploadResult imageUploadResult =  uploadService.processupload(null,file,EUploadType.COF_PHOTO);
					if(imageUploadResult.isSuccess() 
							&& StringUtils.isNotEmpty(imageUploadResult.getUrl())){
						photoUrlList.add(Constant.PHOTO_URL_PATH +"/"+ imageUploadResult.getUrl());
					}else{
						failList.add(file.getOriginalFilename());
					}
				}catch (Exception e) {
					log.error("叮当圈图片上传失败:", e);
					failList.add(file.getOriginalFilename());
				}
			}
			result = Result.getSuccessResult();
			resultMap.put("urls", photoUrlList);
			resultMap.put("failFileNames", failList);
			result.setRe(resultMap);
		}else{
			result = new Result(EEchoCode.ERROR.getCode(), "请选择需要上传的文件");
		}
		return ResultUtil.getJsonString(result);
	}

}
