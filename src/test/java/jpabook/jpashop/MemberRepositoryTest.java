package jpabook.jpashop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest  {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional //@Transactional 이 테스트에 있으면 끝나면 데이터를 롤백해버린다.
    @Rollback(value = false)
    public void testMember()throws Exception{
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when

        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then

        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember.getId()).isEqualTo(member.getId());

    }


}