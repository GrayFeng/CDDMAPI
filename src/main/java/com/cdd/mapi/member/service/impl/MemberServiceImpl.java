package com.cdd.mapi.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdd.mapi.member.dao.IMemberDao;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.Member;

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

}
