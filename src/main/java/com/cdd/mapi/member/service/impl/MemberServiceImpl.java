package com.cdd.mapi.member.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdd.mapi.base.service.IBaseService;
import com.cdd.mapi.common.Constant;
import com.cdd.mapi.common.cache.MemberCache;
import com.cdd.mapi.common.enums.EScoreRuleType;
import com.cdd.mapi.common.pojo.LoginInfo;
import com.cdd.mapi.common.pojo.MemberVO;
import com.cdd.mapi.member.dao.IMemberDao;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.City;
import com.cdd.mapi.pojo.Member;
import com.cdd.mapi.pojo.MemberLevel;
import com.google.common.collect.Maps;

/**
 * CDDMAPI
 * @date 2014-10-27 下午8:08:36
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Service
public class MemberServiceImpl implements IMemberService {
	
	@Autowired
	private IMemberDao memberDao;
	@Autowired
	private IBaseService baseService;

	@Override
	public void addMember(Member member) {
		memberDao.addMember(member);
	}
	
	@Override
	public Member login(String loginId, String password,String ip) {
		Member member = getMemberByLoginId(loginId);
		if(member != null 
				&& member.getPassword().equals(password)){
			return member;
		}
		return null;
	}

	@Override
	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	@Override
	public Member getMemberByName(String name) {
		return memberDao.getMemberByName(name);
	}

	@Override
	public void updateMemberDeviceFlag(Member member) {
		memberDao.updateMemberDeviceFlag(member);
	}

	@Override
	public void updateMember(Member member,String uid) {
		memberDao.updateMember(member);
		if(StringUtils.isNotEmpty(uid)){
			LoginInfo loginInfo = MemberCache.getInstance().get(uid);
			if(loginInfo != null){
				loginInfo.setMember(member);
				MemberCache.getInstance().add(uid, loginInfo);
			}
		}
	}

	@Override
	public Member getMemberById(Integer id) {
		return memberDao.getMemberById(id);
	}

	@Override
	public Member getMemberByUID(String uid) {
		Member member = null;
		LoginInfo loginInfo = MemberCache.getInstance().get(uid);
		if(loginInfo != null && loginInfo.getMember() != null){
			member = getMemberById(loginInfo.getMember().getId());
		}
		return member;
	}

	@Override
	public void deleteMemberPhoto(Integer memberId) {
		Member member = getMemberById(memberId);
		if(member != null && StringUtils.isNotEmpty(member.getPhoto())){
			File file = new File(Constant.PHOTO_URL_PATH + member.getPhoto()
					.replace(Constant.PHOTO_URL_PATH, ""));
			if(file.exists()){
				file.delete();
			}
		}
	}

	@Override
	public Integer addMemberScore(Integer memberId, EScoreRuleType type) {
		int score = baseService.getScoreByType(type);
		if(score > 0){
			Map<String,Object> paramMap = Maps.newHashMap();
			paramMap.put("id", memberId);
			paramMap.put("score", score);
			memberDao.addMemberScore(paramMap);
			return score;
		}
		return null;
	}

	@Override
	public Integer signIn(Member member) {
		memberDao.signIn(member);
		return addMemberScore(member.getId(), EScoreRuleType.SIGN);
	}
	
	public MemberVO transformMember(Member member){
		MemberVO memberVO = new MemberVO();
		BeanUtils.copyProperties(member, memberVO);
		if(StringUtils.isEmpty(memberVO.getName())){
			memberVO.setName(member.getLoginId());
		}
		memberVO.setIsSignIn(Constant.isSignIn(member.getSignTime()));
		List<MemberLevel> levelList = baseService.getMemberLevelList();
		if(levelList != null){
			for(MemberLevel memberLevel : levelList){
				if(memberLevel.getId().equals(member.getLevelId())){
					memberVO.setLevelName(memberLevel.getName());
					break;
				}
			}
		}
		List<City> cityList = baseService.getCityList();
		if(cityList != null){
			for(City city : cityList){
				if(city.getId().equals(member.getCityId())){
					memberVO.setCityName(city.getName());
					break;
				}
			}
		}
		return memberVO;
	}

}
