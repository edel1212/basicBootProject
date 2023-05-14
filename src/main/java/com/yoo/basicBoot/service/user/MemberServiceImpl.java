package com.yoo.basicBoot.service.user;

import com.yoo.basicBoot.common.MemberState;
import com.yoo.basicBoot.common.Roles;
import com.yoo.basicBoot.common.redis.RedisUtil;
import com.yoo.basicBoot.dto.user.MemberDTO;
import com.yoo.basicBoot.entity.user.Member;
import com.yoo.basicBoot.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    // Member Repository
    private final MemberRepository memberRepository;

    // Password Encoder
    private final PasswordEncoder passwordEncoder;

    // Mail
    private final JavaMailSender javaMailSender;

    // Redis
    private final RedisUtil redisUtil;

    @Override
    public String registerMember(MemberDTO memberDTO) {
        // 소설 회원가입이 아님
        memberDTO.setFromSocial(false);
        // password Encode
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        // 권한 부여 Default USER
        memberDTO.setRole(Roles.USER.toString());
        // 계정 상태 이메일 인증 필요
        memberDTO.setState(MemberState.E.toString());
        Member member = this.dtoToEntity(memberDTO);
        memberRepository.save(member);
        sendVerificationMail(memberDTO);
        return member.getEmail();
    }

    @Override
    public Long findMemberByEmail(String email) {
        return memberRepository.countByEmail(email);
    }

    @Override
    public boolean sendVerificationMail(MemberDTO memberDTO) {

        if(memberDTO == null || memberDTO.getEmail() == null) return false;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        String uuid = UUID.randomUUID().toString();
        //  대상 Email
        simpleMailMessage.setTo(memberDTO.getEmail());
        // 제목
        simpleMailMessage.setSubject("회원가입을 원하시면 해당 링크를 눌러주세요.");
        // 내용
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://localhost:8080/user/check");
        stringBuilder.append("?email=");
        stringBuilder.append(memberDTO.getEmail());
        stringBuilder.append("&uuid=");
        stringBuilder.append(uuid);
        simpleMailMessage.setText(stringBuilder.toString());
        // 전송
        javaMailSender.send(simpleMailMessage);
        redisUtil.setDataExpire("edel1212@naver.com", uuid, 30);

        return true;
    }

    @Override
    public boolean checkVerification(String email, String uuid) {

        if(email == null || uuid == null) return false;

        String uuidData = String.valueOf(redisUtil.getData(email));

        if(uuid.equals(uuidData)) return false;

        Member member = memberRepository.findByEmail(email,false);
        MemberDTO memberDTO = this.entityToDTO(member);
        memberDTO.setState(MemberState.S.toString());
        memberRepository.save(this.dtoToEntity(memberDTO));

        return true;
    }

}
