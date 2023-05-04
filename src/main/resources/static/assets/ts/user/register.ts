class Register{
    private name;
    private email;
    private password;
    private passwordChek;

    private registerBtn;
    private registerGoogleBtn;
    private registerNaverleBtn;

    // 저장 Flag
    private registerFlag = false;

    constructor(){
        this.name         = document.querySelector("#name");
        this.email        = document.querySelector("#userId");
        this.password     = document.querySelector("#userPw");
        this.passwordChek = document.querySelector("#pwCheck");

        // Validation Check
        // 이메일 체크
        this.email?.addEventListener("focusout",this.emailValidationCheck);

        // 등록 버튼
        this.registerBtn        = document.querySelector("#registerBtn");
        this.registerBtn?.addEventListener("click",this.registerUser);
        // Goole
        this.registerGoogleBtn  = document.querySelector("#googleBtn");
        // Naver
        this.registerNaverleBtn = document.querySelector("#naverBtn");
    }

    // 등록 요청
    private registerUser = ()=>{
        if(!(this.name instanceof HTMLInputElement)
                || !(this.email instanceof HTMLInputElement)
                || !(this.password instanceof HTMLInputElement)
                || !(this.passwordChek instanceof HTMLInputElement)) return;
            
            const inputArr = [this.email, this.name, this.password, this.passwordChek]
            
            // Validation Check
            for(let item of inputArr){
                if(item.value?.trim()) continue;
                alert("모든 값을 입력해 주세요");
                item.focus();
                return; 
            }//for

            if(this.password !== this.passwordChek){
                alert("비밀번호가 서로 다릅니다.");
                this.passwordChek.focus();
                return; 
            }//if
            
            // 이메일 확인
            this.emailValidationCheck();

    }

    // 이메일 정규식 체크
    private emailRegexCheck(email:string):boolean{
        let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");
        return regex.test(email);
    }

    // 이메일 Validation Check
    private emailValidationCheck = async ()=>{
        this.registerFlag = false;
        if( !(this.email instanceof HTMLInputElement) ) return;
        const email = this.email.value;
        const regexCheck = this.emailRegexCheck(email);
        if(!regexCheck){
            alert("이메일을 확인해 주세요");            
            return;
        }//if
        await fetch(`/user?email=${email}`)
        .then(res => res.text())
        .then(result =>{
            if(result === '1') {
                alert("이미 사용중인 이메일입니다.");
                return;
            }
            this.registerFlag = true;
        }).catch(error =>{
            console.log(error);
        }) 
        console.log(this.registerFlag);
    }

}

// init
(function(){
    const register = new Register();
})();