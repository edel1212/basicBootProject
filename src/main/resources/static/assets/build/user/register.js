"use strict";
class Register {
    constructor() {
        // 저장 Flag
        this.registerFlag = false;
        // 등록 요청
        this.registerUser = () => {
            if (!(this.name instanceof HTMLInputElement)
                || !(this.email instanceof HTMLInputElement)
                || !(this.password instanceof HTMLInputElement)
                || !(this.passwordChek instanceof HTMLInputElement))
                return;
            const inputArr = [this.email, this.name, this.password, this.passwordChek];
            // Validation Check
            for (let item of inputArr) {
                if (item.value?.trim())
                    continue;
                let elemName = "";
                if (item.id === "name") {
                    elemName = "이름";
                }
                else if (item.id === "userId") {
                    elemName = "이메일";
                }
                else if (item.id === "userPw") {
                    elemName = "페스워드";
                }
                alert(`${elemName}을(를) 입력해 주세요`);
                item.focus();
                return;
            } //for
            // 이메일 확인
            this.emailValidationCheck();
            // 비밀번호 확인
            this.pwValidationCheck();
            // 모든 값 충족 확인
            if (!this.registerFlag)
                return;
            const registerParam = {
                email: this.email.value,
                password: this.password.value,
                name: this.name.value
            };
            // 로딩화면
            const loading = document.querySelector("#js-preloader");
            if (loading instanceof HTMLElement) {
                loading.className = "js-preloader";
            }
            fetch("/user", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(registerParam)
            }).then(res => res.json())
                .then(result => {
                console.log(result);
                if (result.state_cd === 200) {
                    alert(`5분 안에 ${result.state_msg}로 전달된 인증 이메일 링크를 확인해 주세요.`);
                    location.href = "/user/login";
                }
                else {
                    alert("잘못된 접근입니다. 관리자에게 문의해주세요");
                }
            }).catch(error => {
                console.log(error);
            }).finally(() => {
                if (loading instanceof HTMLElement) {
                    loading.className = "js-preloader loaded";
                }
            });
        };
        // 이메일 Validation Check
        this.emailValidationCheck = async () => {
            this.registerFlag = false;
            if (!(this.email instanceof HTMLInputElement))
                return;
            const email = this.email.value;
            const regexCheck = this.emailRegexCheck(email);
            if (!regexCheck && email?.trim()) {
                alert("이메일을 확인해 주세요");
                return;
            } //if
            await fetch(`/user?email=${email}`)
                .then(res => res.text())
                .then(result => {
                if (result === '1') {
                    alert("이미 사용중인 이메일입니다.");
                    return;
                }
                this.registerFlag = true;
            }).catch(error => {
                console.log(error);
            });
        };
        // 비밀번호 Validation Check
        this.pwValidationCheck = async () => {
            this.registerFlag = false;
            if (!(this.password instanceof HTMLInputElement))
                return;
            if (!(this.passwordChek instanceof HTMLInputElement))
                return;
            if (!this.password.value?.trim() || !this.passwordChek?.value.trim()) {
                alert("패스워드를 입력해주세요.");
                return;
            }
            if (this.password.value !== this.passwordChek.value) {
                alert("비밀번호가 서로 다릅니다.");
                return;
            } //if
            this.registerFlag = true;
        };
        this.name = document.querySelector("#name");
        this.email = document.querySelector("#userId");
        this.password = document.querySelector("#userPw");
        this.passwordChek = document.querySelector("#pwCheck");
        // Validation Check
        // 이메일 체크
        this.email?.addEventListener("focusout", this.emailValidationCheck);
        // 비밀번호 확인 체크
        this.passwordChek?.addEventListener("focusout", this.pwValidationCheck);
        // 등록 버튼
        this.registerBtn = document.querySelector("#registerBtn");
        this.registerBtn?.addEventListener("click", this.registerUser);
        // Goole
        this.registerGoogleBtn = document.querySelector("#googleBtn");
        // Naver
        this.registerNaverleBtn = document.querySelector("#naverBtn");
    }
    // 이메일 정규식 체크
    emailRegexCheck(email) {
        let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");
        return regex.test(email);
    }
}
// init
(function () {
    const register = new Register();
})();
//# sourceMappingURL=register.js.map