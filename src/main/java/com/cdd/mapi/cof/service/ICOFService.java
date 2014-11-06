package com.cdd.mapi.cof.service;

import java.util.List;

import com.cdd.mapi.pojo.COFReply;
import com.cdd.mapi.pojo.CircleOfFriends;
import com.cdd.mapi.pojo.CofNewsVO;

/**
 * CDDMAPI
 * @date 2014-11-6 下午11:20:09
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public interface ICOFService {
	
	public void addNews(CircleOfFriends cir);
	
	public void addPhotos(List<String> photos, Integer memberId,
			Integer cofId);
	
	public void addReply(COFReply reply);
	
	public List<CofNewsVO> getCofNewsLisst(Integer pageNum,Integer memberId);

}
