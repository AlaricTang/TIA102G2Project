package com.ellie.member.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public void deleteMember(Integer memberID) {
        if (repository.existsById(memberID)) {
            repository.deleteByMemberID(memberID);
        }
    }

    public MemberVO getOneMember(Integer memberID) {
        Optional<MemberVO> optional = repository.findById(memberID);
        return optional.orElse(null);
    }

    public List<MemberVO> getAll() {
        return repository.findAll();
    }

//    public List<MemberVO> getAll(Map<String, String[]> map) {
//        return CompositeQuery_Member.getAllC(map, sessionFactory.openSession());
//    }

    public MemberVO findByAcc(String memberAcc) {
        return repository.findByMemberAcc(memberAcc);
    }
}
