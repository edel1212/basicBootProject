class Login{
    // 일반 로그인
    private loginBtn;

    // Google Login
    private googleBtn;

    private checkEmail;
    private checkUUID;

    constructor(){
        // 일반 로그인 버트
        this.loginBtn = document.querySelector("#loginBtn");
        this.loginBtn?.addEventListener("click",this.loginEvent);

        // Google 로그인 버튼
        this.googleBtn = document.querySelector("#googleBtn");

        // 이메일 인증 값
        const urlParams = new URL(location.href).searchParams;
        this.checkEmail = urlParams.get('email');
        this.checkUUID  = urlParams.get('uuid');
        
        // 이메일 인증으로 들어올 경우
        if(this.checkEmail && this.checkUUID){
            this.emailAuthentication();
        }//if
    }

    // 로그인 이벤트
    private loginEvent = ()=>{
        const loginForm = document.querySelector("#loginForm")
        if(!(loginForm instanceof HTMLFormElement)) return;

        const email    = loginForm.querySelector("input[name='email']");
        const password = loginForm.querySelector("input[name='password']");
        const remember = document.querySelector("#customCheck");

        if(!(email instanceof HTMLInputElement && password instanceof HTMLInputElement
            && remember instanceof HTMLInputElement)) return;

        if(!email.value?.trim()){
            alert("이메일을 입력해주세요");
            email.focus();
            return;
        }
        if(!password.value?.trim()){
            alert("이메일을 입력해주세요");
            password.focus();
            return;
        }
   
        fetch("/user/login",{
             method : "post",
             cache : "no-cache",
            headers : {
                "Content-Type": "application/x-www-form-urlencoded",        
                "remember-me" : String(remember.checked)
            }, body : new URLSearchParams({
                username :  email.value,
                password :  password.value
            })
        }).then(res => res.json())
        .then(result =>{
            if(result.stateCd === 200){
                location.href = "/";
            } else {
                alert(`${result.stateMsg}`);
            }// if - else
        }).catch(error =>{
            console.log(error);
        })
    }

    // 이메일 인증
    private emailAuthentication = ()=>{

        // 로딩 화면
        const loading = document.querySelector("#js-preloader");
        if(loading instanceof HTMLElement){
            loading.className = "js-preloader";
            loading.style.visibility = "visible";
            loading.style.opacity = "1";
            loading.style.pointerEvents = "auto";
        }

        fetch("/user/auth",{
            method : "put"
            ,headers : {
                "Content-Type": "application/json",
            },
            body : JSON.stringify({
                email : this.checkEmail,
                uuid : this.checkUUID
            })
        }).then(res => res.json())
        .then(result=>{
            alert(`${result.state_msg}`);
        }).catch(error =>{
            console.log(error);
        }).finally(()=>{
            if(loading instanceof HTMLElement){
                loading.className = "js-preloader loaded";
                loading.style.visibility = "none";
                loading.style.opacity = "0";
                loading.style.pointerEvents = "none";
            }
        })

    }
}

// init
(function(){
    const login = new Login();
})();