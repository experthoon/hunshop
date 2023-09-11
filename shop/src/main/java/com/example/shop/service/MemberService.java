package com.example.shop.service;

import com.example.shop.entity.Member;
import com.example.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // 로직을 처리하다가 에러가 발생하였다면, 변경된 데이터를 로직을 수행하기 이전 상태로 콜백 시켜줍니다.
@RequiredArgsConstructor // 의존성 주입
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){

        validateDuplicateMember(member);
        return memberRepository.save(member);

    }

    private void validateDuplicateMember(Member member) { //이미 가입된 회원의 경우 예외 발생.

        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
