type MoviePagingObject = {
    dto_list : MovieDetailInfo[];
    next : boolean;
    prev : boolean;
    page_list : number[];   
    page : number; 
    start : number;
    end : number;
}

type SearchParam = {
    page : number;
}

type ReplyObject = {
    rno?      :number;    // 시퀀스
    mno       :number;    // 영화번호
    text      :string;    // 댓글
    replier   :string;    // 작성자
    mod_date? :string     // 수정일자
}

// 영화 목록
class MovieList{

    // 영화목록 페이징 버튼
    private moviePageNation;

    constructor(){
        // 검색 목록 조회
        this.getMovieList({page:1});

        // 영화목록 버튼 Event 추가
        this.moviePageNation = document.querySelector("#moviePageNation");
        this.moviePageNation?.addEventListener("click",this.pagingEvent);
    }   

    // 영화 목록
    private getMovieList(searchParam:SearchParam){
        fetch(`/movie/?page=${searchParam.page}`)
            .then(res => res.json())
            .then(result => {                             
                console.log(result); 
                // UI 작성
                this.drawMovieList(result);
            }).catch(error => {
                console.log(error);
            })
    }

    // 받아온 결과값으로 UI 생성
    private drawMovieList(moviePagingObject:MoviePagingObject){
        // 목록 Draw
        const listWrap = document.querySelector("#listWrap");
        if(listWrap instanceof HTMLElement){
            listWrap.innerHTML = "";
            for(let item of moviePagingObject.dto_list){
                const innerHTML = 
                `<div class="col-lg-6 col-sm-6">
                    <div class="item">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="image movieImg" style="background: url(https://image.tmdb.org/t/p/original${item.poster_path}) center">                                    
                                </div>
                            </div>
                            <div class="col-lg-6 align-self-center">
                                <div class="content">
                                    <span class="info">${item.original_title}</span>
                                    <h4 class="movieTitle">
                                        ${item.title}
                                        <b class="replyCnt">[${item.reply_cnt}]</b>
                                    </h4>                                    
                                    <div class="row">
                                        <div class="col-6">
                                            <i class="fa fa-clock"></i>
                                            <span class="list">${item.release_date}</span>
                                        </div>
                                        <div class="col-6">
                                            <i class="fa fa-user"></i>
                                            <span class="list">${item.popularity}</span>
                                        </div>
                                    </div>
                                    <p class="overviewSection">${item.overview}</p>
                                    <div class="main-button" style="text-align:center">
                                        <a data-mno="${item.mno}" class="showMovieDetails" href="javascript:void(0)">Make a Reservation</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>`;
                listWrap.insertAdjacentHTML("beforeend",innerHTML);
            }//for
        }//if


        // PageNation
        const moviePageNation = document.querySelector("#moviePageNation");
        if(moviePageNation instanceof HTMLUListElement){
            // 초기화
            moviePageNation.innerHTML = "";
            let innerHTML = '';
            // 이전
            if(moviePagingObject.prev){
                innerHTML += `<li data-page="${moviePagingObject.start -1}"><a href="#"><i class="fa fa-arrow-left"></i></a></li>`;                
            }
            // 페이지 번호
            for(let i of moviePagingObject.page_list){                
                innerHTML += `<li data-page="${i}" class=${i === moviePagingObject.page ?  "active" : ""} ><a class="pageNumBtn" href="#">${i}</a></li>`
            }
            // 다음
            if(moviePagingObject.next){
                innerHTML += `<li data-page="${moviePagingObject.end +1}"><a href="#"><i class="fa fa-arrow-right"></i></a></li>`;                
            }
            moviePageNation.insertAdjacentHTML("beforeend",innerHTML);
        }//if
    }

    // 영화 목록 페이징 이벤트
    private pagingEvent = (event : Event)=>{
        event.preventDefault;
        const target = event.target;
        if( !(target instanceof HTMLElement) ) return;
        
        if (target.nodeName !=='A') return;

        const targetTyp:number = Number(target.parentElement?.dataset.page);            
        
        const searchParam: SearchParam = {
            page : targetTyp,
        }
        // drawMovieList
        this.getMovieList(searchParam);
    }

    //

}

class MoiveDetails{
    // 상세 정보
    private movieDetailInfo:MovieDetailInfo;
    // 상세보기 버튼
    private openMoiveDetial;

    // 상세보기 닫기버튼
    private closeMoiveDetial;

    // Reply 객체
    private reply:Reply;

    // 댓글 버튼 
    private writeBtn;
    
    constructor(){
        // 상세정보 초기화
        this.movieDetailInfo = {};   
        // 상세보기 Event
        this.openMoiveDetial = document.querySelector("#listWrap");
        this.openMoiveDetial?.addEventListener("click",this.openModal);
        
        // 상세보기 닫기 Event
        this.closeMoiveDetial = document.querySelector("#modalCloseBtn");
        this.closeMoiveDetial?.addEventListener("click",this.closeModal);

        // 댓글
        this.reply = new Reply();
        this.writeBtn = document.querySelector("#writeBtn");        
        this.writeBtn?.addEventListener("click",(e)=>{
            this.reply.insertReply(this.movieDetailInfo);
        });            
    }   

    // 상세보기 종료
    private closeModal = (event : Event)=>{
        const modalSection = document.querySelector("#open-modal");
        if(!(modalSection instanceof HTMLElement)) return;
        this.modalHide(modalSection);
    }

    // 영화 상세정보 Draw
    private drawMovieDtail =(movieDetailInfo:MovieDetailInfo)=>{
        // poster path
        const poster = document.querySelector("#open-modal #modalMovieImg");
        // originalTitle
        const originalTitle = document.querySelector("#open-modal #originalTitle");
        // movieNm
        const movieNm = document.querySelector("#open-modal #movieNm");
        // releaseDate
        const releaseDate = document.querySelector("#open-modal #releaseDate");
        // popularity
        const popularity = document.querySelector("#open-modal #popularity");
        // writerComment
        const writerComment = document.querySelector("#open-modal #writerComment");
        
        if( !(poster instanceof HTMLElement)  || !(originalTitle instanceof HTMLElement) || !(movieNm instanceof HTMLElement) 
          || !(releaseDate instanceof HTMLElement)  || !(popularity instanceof HTMLElement) || !(writerComment instanceof HTMLElement)
         ) return;

         poster.style.backgroundImage = `url(https://image.tmdb.org/t/p/original/${movieDetailInfo.backdrop_path})`
         poster.style.backgroundPosition = "center";
         originalTitle.innerHTML = `${movieDetailInfo.original_title}`
         movieNm.innerHTML = `${movieDetailInfo.title}`;
         releaseDate.innerHTML = `${movieDetailInfo.release_date}`;
         popularity.innerHTML = `${movieDetailInfo.popularity}`;
         writerComment.innerHTML = `${movieDetailInfo.comment}`;
    }   

    // 상세보기
    private openModal = (event: Event)=>{
        const target = event.target;
        if(!(target instanceof HTMLElement)) return;

        if(target.nodeName !== 'A' && !target.classList.contains("showMovieDetails") ) return;
        
        const modalSection = document.querySelector("#open-modal");
        if( !(modalSection instanceof HTMLElement)) return;

        // Modal Open
        this.modalShow(modalSection);

        const mno = target.dataset.mno;

        // 상세정보
        fetch(`/movie/${mno}`)
        .then(res => res.json())
        .then(result => {         
            // 상세정보 주입
            this.movieDetailInfo = result;                                
            // UI 작성            
            this.drawMovieDtail(result);            
        }).catch(error => {
            console.log(error);
        })      

        // 댓글 목록
        if(mno) this.reply.getReplyList(mno);
        
    }
    // Modal Control Event
    private modalHide = (modalSection : HTMLElement)=> modalSection.style.display = "none";
    private modalShow = (modalSection : HTMLElement)=> modalSection.style.display = "flex";
}

class Reply{
    private replySection;
    constructor(){
        this.replySection = document.querySelector("#replySection")
        // 댓글삭제 이벤트 추가
        this.replySection?.addEventListener("click",this.deleteReply);
    }

    // 댓글 목록
    public getReplyList = (mno : string)=>{

        if(!mno) {
            throw new Error("관리자에게 문의해주세요!");
        }

        fetch(`/movie/replies/${mno}`)
        .then(res => res.json())
        .then(result => {                             
            console.log(result); 
            // UI 작성            
            this.drawReplyList(result);            
        }).catch(error => {
            console.log(error);
        }) 
    }

    // 댓글 Draw
    private drawReplyList =(movieDetailInfo:ReplyObject[])=>{
        const replySection = document.querySelector("#replySection");
        if(!(replySection instanceof HTMLElement)) return;
        let HTMLCode = "";
        for(let item of movieDetailInfo){
            HTMLCode += `<div class="replies">

                            <div class="replyMainConent">
                                <div class="infoImg">
                                    <span style="width:50px;height: 50px;background-color: rgb(113, 221, 182);display: block;border-radius: 50%;"></span>
                                </div>

                                <div class="replyInfo">
                                    <strong>${item.replier}</strong>
                                    <p class="replyText">${item.text}</p>
                                </div>
                            </div>
                            
                            <div class="replySubContent">
                                <span>${item.mod_date}</span>
                                <div><button data-mno="${item.mno}" data-rno="${item.rno}" class="replyDelBtn">삭제</button></div> 
                            </div>
                                
                        </div> `;            
        }//for
        replySection.innerHTML = HTMLCode;
    }

    // 댓글 등록
    public insertReply = (movieDetailInfo:MovieDetailInfo)=>{
        let replyText = "";
        const replyTextBox = document.querySelector("#writeCommentBtn") ;
        if(replyTextBox instanceof HTMLInputElement){
            replyText = replyTextBox.value;
        }//if
        const replyObject:ReplyObject = {
            mno : Number(movieDetailInfo.mno),
            replier : "회원가입 기능 추가후 수정",
            text : replyText
        }
        fetch(`/movie/replies`,{
            method : "POST",
            headers : {                
                "Content-Type" : "application/json",
            },
            body : JSON.stringify(replyObject)
        })
        .then(res => res.json())
        .then(result => {                                         
            // UI 작성            
            this.getReplyList(result);            
        }).catch(error => {
            console.log(error);
        })      
    }
    
    // 댓글 삭제
    private deleteReply = (event: Event):void=>{
        const target = event.target;
        if( !(target instanceof HTMLElement && target.classList.contains("replyDelBtn"))) return;
        const rno = target.dataset.rno;
        const mno = target.dataset.mno || "" ;
        fetch(`/movie/replies/${rno}`,{
            method : "DELETE"
        })
        .then(res => res.text())
        .then(result => {                                         
            // UI 작성            
            this.getReplyList(mno);            
        }).catch(error => {
            console.log(error);
        })      
    }
}

// init
(function(){
    const movieList = new MovieList();
    const movieDetails = new MoiveDetails();
})();
