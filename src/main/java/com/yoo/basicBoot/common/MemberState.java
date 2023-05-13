package com.yoo.basicBoot.common;

/**
 * 회원계정 상태
 * <p>P : Password 5 회 틀림</p>
 * <p>E : 이메일 승인 필요 - 소셜일 경우 사용 X    </p>
 * <p>S : 이상없음                       </p>
 * */
public enum MemberState {
    P,
    E,
    S,
}
