package com.ellie.member.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_Member; // 引入對應的複合查詢工具類

@Service("memberService")
public class MemberService {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private SessionFactory sessionFactory;

    public void addMember(MemberVO memberVO) {
        repository.save(memberVO);
    }

    public void updateMember(MemberVO memberVO) {
        repository.save(memberVO);
    }

    public void deleteMember(Integer memberId) {
        if (repository.existsById(memberId)) {
            repository.deleteByMemberId(memberId);
        }
    }

    public MemberVO getOneMember(Integer memberId) {
        Optional<MemberVO> optional = repository.findById(memberId);
        return optional.orElse(null);
    }

    public List<MemberVO> getAll() {
        return repository.findAll();
    }

    public List<MemberVO> getAll(Map<String, String[]> map) {
        return CompositeQuery_Member.getAllC(map, sessionFactory.openSession());
    }
}
