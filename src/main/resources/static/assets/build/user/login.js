"use strict";
class Login {
    constructor() {
        // 로그인 이벤트
        this.loginEvent = () => {
            const loginForm = document.querySelector("#loginForm");
            if (!(loginForm instanceof HTMLFormElement))
                return;
            const email = loginForm.querySelector("input[name='email']");
            const password = loginForm.querySelector("input[name='password']");
            const remember = document.querySelector("#customCheck");
            if (!(email instanceof HTMLInputElement && password instanceof HTMLInputElement
                && remember instanceof HTMLInputElement))
                return;
            if (!email.value?.trim()) {
                alert("이메일을 입력해주세요");
                email.focus();
                return;
            }
            if (!password.value?.trim()) {
                alert("이메일을 입력해주세요");
                password.focus();
                return;
            }
            fetch("/user/login", {
                method: "post",
                cache: "no-cache",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }, body: new URLSearchParams({
                    username: email.value,
                    password: password.value,
                    "remember-me": String(remember.checked)
                })
            }).then(res => res.json())
                .then(result => {
                console.log(result);
            }).catch(error => {
                console.log(error);
            });
        };
        this.loginBtn = document.querySelector("#loginBtn");
        this.loginBtn?.addEventListener("click", this.loginEvent);
    }
}
// init
(function () {
    const login = new Login();
})();
//# sourceMappingURL=login.js.map