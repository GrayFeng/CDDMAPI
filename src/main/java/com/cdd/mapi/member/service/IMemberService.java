package com.cdd.mapi.member.service;

import java.util.List;
import java.util.Map;

import com.cdd.mapi.common.enums.EScoreRuleType;
import com.cdd.mapi.common.pojo.MemberVO;
import com.cdd.mapi.pojo.Member;
import com.cdd.mapi.pojo.MemberRelation;
import com.cdd.mapi.pojo.PrivateLetter;
import com.cdd.mapi.pojo.PrivateLetterVO;

/**
 * CDDMAPI
 * @date 2014-10-27 下午8:07:56
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public interface IMemberService {

	public void addMember(Member member);
	
	public Member login(String loginId,String password,String ip);
	
	public Member getMemberByLoginId(String loginId);
	
	public Member getMemberByName(String name);
	
	public Member getMemberById(Integer id);
	
	public Member getMemberByUID(String uid);
	
	public void updateMemberDeviceFlag(Member member);
	
	public void updateMember(Member member,String uid);
	
	public void deleteMemberPhoto(Integer memberId);
	
	public Integer addMemberScore(Integer memberId,EScoreRuleType type);
	
	public Integer signIn(Member member);
	
	public MemberVO transformMember(Member member);
	
	public void attention(MemberRelation memberRelation);
	
	public void unAttention(MemberRelation memberRelation);
	
	public Map<String,Long> getMemberStatistics(Integer memberId);
	
	public Integer getFansCount(Integer memberId);
	
	public Integer getIdolCount(Integer memberId);
	
	public List<MemberVO> getFansList(Integer memberId,Integer pageNum);
	
	public List<MemberVO> getIdolList(Integer memberId,Integer pageNum);
	
	public List<PrivateLetterVO> getPrivateLetterList(Integer memberId,Integer pageNum);
	
	public void sendPrivateMessage(PrivateLetter letter);
	
	public Integer getMemberRelation(Integer memberId,Integer otherMemberId);
	
}
