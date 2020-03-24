package com.ym.book.shop.service;

import com.ym.book.shop.domain.entity.Member;
import com.ym.book.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers =  memberRepository.findByName(member.getName());
        if(findMembers.size()>0){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전제 초회
    public List<Member>findMembers(){
        return memberRepository.findAll();
    }

    //회원 한명 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
