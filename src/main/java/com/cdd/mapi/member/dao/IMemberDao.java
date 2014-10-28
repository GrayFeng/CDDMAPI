package com.cdd.mapi.member.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdd.mapi.common.annotation.MyBatisRepository;
import com.cdd.mapi.pojo.Member;
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
	
}
