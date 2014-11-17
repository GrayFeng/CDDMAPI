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
import com.cdd.mapi.common.pojo.Page;
import com.cdd.mapi.member.dao.IMemberDao;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.City;
import com.cdd.mapi.pojo.Member;
import com.cdd.mapi.pojo.MemberLevel;
import com.cdd.mapi.pojo.MemberRelation;
import com.cdd.mapi.pojo.PrivateLetter;
import com.cdd.mapi.pojo.PrivateLetterListSearch;
import com.cdd.mapi.pojo.PrivateLetterVO;
import com.google.common.collect.Lists;
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
		Map<String,Long> statisticsMap = getMemberStatistics(member.getId());
		if(statisticsMap != null){
			memberVO.setFansCount(statisticsMap.get("fansCount"));
			memberVO.setIdolCount(statisticsMap.get("idolCount"));
			memberVO.setDynamicInfoCount(statisticsMap.get("dynamicInfoCount"));
			memberVO.setSubjectCount(statisticsMap.get("subjectCount"));
		}
		return memberVO;
	}

	@Override
	public void attention(MemberRelation memberRelation) {
		memberDao.attention(memberRelation);
	}

	@Override
	public void unAttention(MemberRelation memberRelation) {
		memberDao.unAttention(memberRelation);
	}

	@Override
	public Map<String, Long> getMemberStatistics(Integer memberId) {
		return memberDao.getMemberStatistics(memberId);
	}

	@Override
	public Integer getFansCount(Integer memberId) {
		return memberDao.getFansCount(memberId);
	}

	@Override
	public Integer getIdolCount(Integer memberId) {
		return memberDao.getIdolCount(memberId);
	}

	@Override
	public List<MemberVO> getFansList(Integer memberId,Integer pageNum) {
		List<MemberVO> list = null;
		Integer prizeCount = memberDao.getFansCount(memberId);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			page.setNumber(pageNum == null ? 1 : pageNum);
			if(page.getTotalPages() < page.getNumber()){
				return list;
			}
			Map<String,Object> paramsMap = Maps.newHashMap();
			paramsMap.put("startNum", page.getStartNum());
			paramsMap.put("size", page.getSize());
			paramsMap.put("memberId", memberId);
			List<Member> memberList = memberDao.getFansList(paramsMap);
			if(memberList != null && !memberList.isEmpty()){
				list = Lists.newArrayList();
				for(Member member : memberList){
					list.add(transformMember(member));
				}
			}
		}
		return list;
	}

	@Override
	public List<MemberVO> getIdolList(Integer memberId,Integer pageNum) {
		List<MemberVO> list = null;
		Integer prizeCount = memberDao.getIdolCount(memberId);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			page.setNumber(pageNum == null ? 1 : pageNum);
			if(page.getTotalPages() < page.getNumber()){
				return list;
			}
			Map<String,Object> paramsMap = Maps.newHashMap();
			paramsMap.put("startNum", page.getStartNum());
			paramsMap.put("size", page.getSize());
			paramsMap.put("memberId", memberId);
			List<Member> memberList = memberDao.getIdolList(paramsMap);
			if(memberList != null && !memberList.isEmpty()){
				list = Lists.newArrayList();
				for(Member member : memberList){
					list.add(transformMember(member));
				}
			}
		}
		return list;
	}

	@Override
	public void sendPrivateMessage(PrivateLetter letter) {
		memberDao.sendPrivateMessage(letter);
	}

	@Override
	public List<PrivateLetterVO> getPrivateLetterList(Integer memberId,
			Integer pageNum) {
		List<PrivateLetterVO> list = null;
		Integer prizeCount = memberDao.getPrivateLetterCount(memberId);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			pageNum = pageNum == null ? 1 : pageNum;
			page.setNumber(pageNum);
			PrivateLetterListSearch search = new PrivateLetterListSearch();
			search.setStartNum(page.getStartNum());
			search.setSize(page.getSize());
			search.setMemberId(memberId);
			if(page.getTotalPages() < pageNum){
				return list;
			}
			list = memberDao.getPrivateLetterList(search);
		}
		return list;
	}

	@Override
	public Integer getMemberRelation(Integer memberId, Integer otherMemberId) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("memberId", memberId);
		paramMap.put("otherMemberId", otherMemberId);
		Map<String,Object> resultMap = memberDao.getMemberRelation(paramMap);
		if(resultMap != null){
			Long fanState = (Long)resultMap.get("fanState");
			Long idolState = (Long)resultMap.get("idolState");
			if(fanState != null && idolState != null){
				if(fanState == 1 && idolState == 1){
					return 1;
				}else if(fanState == 1){
					return 2;
				}else if(idolState == 1){
					return 0;
				}
			}
		}
		return 3;
	}

	@Override
	public List<MemberVO> getOtherMemberFansList(Integer otherMemberId,
			Integer memberId, Integer pageNum) {
		List<MemberVO> list = null;
		Integer prizeCount = memberDao.getFansCount(memberId);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			page.setNumber(pageNum == null ? 1 : pageNum);
			if(page.getTotalPages() < page.getNumber()){
				return list;
			}
			Map<String,Object> paramsMap = Maps.newHashMap();
			paramsMap.put("startNum", page.getStartNum());
			paramsMap.put("size", page.getSize());
			paramsMap.put("memberId", memberId);
			paramsMap.put("otherMemberId", otherMemberId);
			List<Member> memberList = memberDao.getOtherMemberFansList(paramsMap);
			if(memberList != null && !memberList.isEmpty()){
				list = Lists.newArrayList();
				for(Member member : memberList){
					list.add(transformMember(member));
				}
			}
		}
		return list;
	}

	@Override
	public List<MemberVO> getOtherMemberIdolList(Integer otherMemberId,
			Integer memberId, Integer pageNum) {
		List<MemberVO> list = null;
		Integer prizeCount = memberDao.getIdolCount(memberId);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			page.setNumber(pageNum == null ? 1 : pageNum);
			if(page.getTotalPages() < page.getNumber()){
				return list;
			}
			Map<String,Object> paramsMap = Maps.newHashMap();
			paramsMap.put("startNum", page.getStartNum());
			paramsMap.put("size", page.getSize());
			paramsMap.put("memberId", memberId);
			paramsMap.put("otherMemberId", otherMemberId);
			List<Member> memberList = memberDao.getOtherMemberIdolList(paramsMap);
			if(memberList != null && !memberList.isEmpty()){
				list = Lists.newArrayList();
				for(Member member : memberList){
					list.add(transformMember(member));
				}
			}
		}
		return list;
	}

}
