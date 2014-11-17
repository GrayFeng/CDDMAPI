package com.cdd.mapi.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdd.mapi.common.annotation.MyBatisRepository;
import com.cdd.mapi.pojo.Member;
import com.cdd.mapi.pojo.MemberRelation;
import com.cdd.mapi.pojo.PrivateLetter;
import com.cdd.mapi.pojo.PrivateLetterListSearch;
import com.cdd.mapi.pojo.PrivateLetterVO;
/**
 * CDDMAPI
 * @date 2014-10-27 下午8:06:42
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Repository
@MyBatisRepository
public interface IMemberDao {

	public void addMember(Member member);
	
	public Member getMemberByLoginId(String loginId);
	
	public Member getMemberByName(String name);
	
	public Member getMemberById(Integer id);
	
	public void updateMemberDeviceFlag(Member member);
	
	public void updateMember(Member member);
	
	public void addMemberScore(Map<String,Object> paramMap);
	
	public void signIn(Member member);
	
	public void attention(MemberRelation memberRelation);
	
	public void unAttention(MemberRelation memberRelation);
	
	public Map<String,Long> getMemberStatistics(Integer memberId);
	
	public Integer getFansCount(Integer memberId);
	
	public Integer getIdolCount(Integer memberId);
	
	public List<Member> getFansList(Map<String,Object> paramsMap);
	
	public List<Member> getIdolList(Map<String,Object> paramsMap);
	
	public List<Member> getOtherMemberFansList(Map<String,Object> paramsMap);
	
	public List<Member> getOtherMemberIdolList(Map<String,Object> paramsMap);
	
	public Integer getPrivateLetterCount(Integer memberId);
	
	public List<PrivateLetterVO> getPrivateLetterList(PrivateLetterListSearch search);
	
	public void sendPrivateMessage(PrivateLetter letter);
	
	public Map<String,Object> getMemberRelation(Map<String,Object> params);
	
}
