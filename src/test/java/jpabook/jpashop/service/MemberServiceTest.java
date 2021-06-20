package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    //@Rollback(value = false) DB에 실제로 값을 날릴수 있다.
    public void  join()throws Exception{

        //given
        Member member = new Member();
        member.setName("MemberA");
        //when
        Long saveId = memberService.join(member);
        //then

        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(saveId));

    }

    @Test
    @DisplayName("중복_회원_예외")
    public void MemberException() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("MemberB");
        Member member2 = new Member();
        member2.setName("MemberB");

        //when

        memberService.join(member1);
        try{
            memberService.join(member2);
        }catch (IllegalStateException e){
            return;
        }



        //then
        fail("예외가 방생해야 합니다.");


    }
}