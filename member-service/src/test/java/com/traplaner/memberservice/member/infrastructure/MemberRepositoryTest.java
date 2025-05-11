package com.traplaner.memberservice.member.infrastructure;

import com.traplaner.memberservice.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
//@Transactional
//@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    MemberJpaRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Test DB 테이블 데이터 생성")
    void makeTestTableData() {

        int subscriberCount = 100;
        int randomPara = subscriberCount - 1;
        int favoriteCount = subscriberCount;
        int randomFavoritePara = (subscriberCount / 10) - 1;

        //멤버 100명 생성
        for (int i = 1; i <= subscriberCount; i++) {
            MemberEntity m = MemberEntity.builder()
                    .nickName("테스트" + i)
                    .email("test" + i + "@abc.net")
                    .password(passwordEncoder.encode("1234@qwer"))
                    .loginMethod(Member.LoginMethod.COMMON)
                    .profileImg("47a3e6ea4e1d4377ab87d07c85e1b124.png")
                    .build();
            MemberEntity member = memberRepository.save(m);
        }



    }
}