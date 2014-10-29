package com.cdd.mapi.member.service;

import com.cdd.mapi.common.enums.EScoreRuleType;
import com.cdd.mapi.common.pojo.MemberVO;
import com.cdd.mapi.pojo.Member;

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
	
}
