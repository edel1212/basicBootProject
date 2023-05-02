/**
 * 영화 글 작성
 */
interface RegisterMovie{
    // 영화 정보를 가져옴
    getMovieInfo(event:MouseEvent):void;
    getMovieDetailInfo(event:MouseEvent):void;
}

type MovieDetailInfo = {
    popularity?    : string;    // 인기점수
    original_title?: string;    // 오리지널 타이틀
    overview?      : string;    // 요약
    poster_path?   : string;    // 포스터
    backdrop_path? : string;    // 배경이미지
    title?         : string;    // 제목
    release_date?  : string;    // 개봉일
    id?            : number;    // ID
    genre_ids?     : number[];  // 장르ID
    genre?         : string;    // 장르변환값
    comment?       : string;    // 코멘트
    mno?           : number;    // 글번호
    reply_cnt?     : number;    // 댓글개수
    mod_date?      : string     // 수정일자
    genres?        : {id:number; name:string}[]; // 영화ID 조회 시 장르
}

type MovieSearchResult = {
    keyword?        : string;               // 검색어
    page            : number;               // 현재페이지
    results         : MovieDetailInfo[];    // 목록
    total_pages?    : number;               // 총 페이지수
}

class RegisterMovieImpl implements RegisterMovie{

    // 스크롤 옵션
    private readonly options = {
        root: document.querySelector("#serchBox"),
        rootMargin: '0px',
        threshold: 0.5
    }; 

    // 영화 검색버튼
    private readonly findMovieBtn;
    
    // 조회결과
    private readonly searchResultElem;

    // 조회 결과
    private movieSearchResult:MovieSearchResult | undefined;

    // 조회 결과 상세
    private movieDetailInfo:MovieDetailInfo | undefined;
    
    // 장르목록
    private genreLst :any | undefined;
    
    private registerBtn;

    constructor(){

        // 메인베너에서 선택하여 들어왔을 경우
        this.selectedMainBanner();  

        // 영화 검색
        this.findMovieBtn = document.querySelector("#findMovieBtn");
        this.findMovieBtn?.addEventListener("click",this.getMovieInfo);
               
        // 조회된 결과 Click
        this.searchResultElem = document.querySelector("#movieLst");        
        this.searchResultElem?.addEventListener("click",this.getMovieDetailInfo);

        //장르 값 설정
        this.getGenre();        

        // 등록 버튼
        this.registerBtn = document.querySelector("#registerBtn");
        this.registerBtn?.addEventListener("click",this.registerMovie);        

    }// constructor

    // 메인 배너에서 선택하여 영화에 접근했을 경우
    private selectedMainBanner = ()=>{
        const urlParams = new URLSearchParams(location.search);
        if(!urlParams.has('id')) return;
        const movieId = urlParams.get('id');    
        fetch(`https://api.themoviedb.org/3/movie/${movieId}?api_key=a3af7d97effb973e78c5fb1fd7787b13&language=ko-KR`)
        .then(res=>res.json())
        .then(obj => {            
            if(!obj){
                alert("관리자이게 문의해 주세요");
                return;
            }
            this.setupMovieDetail(obj);
        }).catch(error =>{
            console.log(error);
        })
    }

    // 영화 정보를 가져옴
    public getMovieInfo = (event: Event)=> {
        const  movieNameElem = document.querySelector("input[name='movieName']");
        if(movieNameElem instanceof HTMLInputElement){
            const movieName = movieNameElem?.value;
            if(!movieName) return;
            fetch(`https://api.themoviedb.org/3/search/movie?api_key=a3af7d97effb973e78c5fb1fd7787b13&language=ko-KR&page=1&query=${movieName}`)
            .then(res => res.json())
            .then(result => {                        

                // 초기화
                this.refreshSearchBox();
                const searchBox = document.querySelector("#serchBox");
                if(searchBox instanceof HTMLElement){
                    searchBox.style.height = "300px";                       
                }//if
                 
                // 결과 Draw
                this.drawMoiveList(result.results);
                
                // 조회결과 업데이트
                this.movieSearchResult = {
                    keyword : movieName,
                    page : result.page,
                    results : result.results,
                    total_pages : result.total_pages,
                }                

            }).then(addEvent=>{
                // 스크롤 이벤트 추가
                const lastTr = document.querySelector("#movieLst tr:last-child");
                if(!lastTr) return;
                this.observer.observe(lastTr);               
            }).catch(error => {
                console.log(error);
            })
        }//if        
    }

    // 영화 조회결과 Draw
    private drawMoiveList(objArr:MovieDetailInfo[]){
        let liHtml = "";        
        for(const item of objArr){                    
            liHtml += `<tr data-id="${item.id}">`;
            liHtml += `<td class="backTd" style="background-image: url( 'https://image.tmdb.org/t/p/w200/${item.backdrop_path}' )"></td>`;
            liHtml += `<td class="movieTitle">${item.title}</td>`;
            liHtml += `<td>${item.release_date}</td>`;
            liHtml += `</tr>`;                                                            
        }//for   
        document.querySelector("#movieLst tbody")?.insertAdjacentHTML("beforeend",liHtml);
    }

    // 영화 목록 스크롤 조회
    private observer = new IntersectionObserver((entries, observer) => {
        entries.forEach(async entry => {
          if (entry.isIntersecting) {
            // 가장 마지막 리스트 아이템인지 확인
            if(!this.movieSearchResult) return;
            let keyword     = this.movieSearchResult.keyword || "";
            let page        = this.movieSearchResult.page;
            let lastPage    = this.movieSearchResult.total_pages;
            let tmpArr: MovieDetailInfo[]  = this.movieSearchResult?.results;      
            
            // 더이상 페이징이 필요없음
            if(page === lastPage) return;
            await this.callApi(page, keyword, tmpArr);
          }
        });
      }, this.options);
    
    // Api Call - 영화 목록  
    private async callApi(page:number, keyword:string, tmpArr: MovieDetailInfo[]){
        fetch(`https://api.themoviedb.org/3/search/movie?api_key=a3af7d97effb973e78c5fb1fd7787b13&language=ko-KR&page=${page+1}&query=${keyword}`)
            .then(res => res.json())
            .then(result => {                             
                const newArr = tmpArr?.concat(result.results);
                // 조회결과 업데이트
                this.movieSearchResult!.page = result.page;
                this.movieSearchResult!.results = newArr;
                        
                // 결과 Draw
                this.drawMoiveList(result.results);                 
                // 옵저버 종료
                this.observer.disconnect();
            }).then(addEvent=>{
                //새로운 마지막 tr 등록
                const lastTr = document.querySelector("#movieLst tr:last-child");
                if(!lastTr) return;                                
                this.observer.observe(lastTr);
            })
            .catch(error => {
                console.log(error);
            })
    }

    // Api Call - 영화 장르 종류 Set
    private getGenre():void{
        fetch(`https://api.themoviedb.org/3/genre/movie/list?api_key=a3af7d97effb973e78c5fb1fd7787b13&language=ko-KR`)
        .then(res => res.json())
        .then(result => {                             
           this.genreLst = result;
        }).catch(error => {
            console.log(error);
        })       
    }
    

    // 조회 목록 클릭 이벤트
    public getMovieDetailInfo = (event : Event)=>{
        const clickedElement= event.target;
    
        if( !(clickedElement instanceof HTMLElement)) return;

        const clickedElementName = clickedElement.nodeName;

        if(clickedElementName !== "TD") return;

        const targetElem = clickedElement.parentElement;
        
        const obj = this.movieSearchResult?.results.find(i=>{
            return i.id == targetElem?.dataset.id
        }) ;

        if(!obj){
            alert("관리자이게 문의해 주세요");
            return;
        }
        this.setupMovieDetail(obj);
    }

    private setupMovieDetail = (obj:MovieDetailInfo)=>{
        // 장르 변환
        let genrePare:string[] = [];
        if(obj?.genre_ids){
            for(let i = 0 ; i < obj.genre_ids.length ; i++){
                obj.genre_ids[i]
                for(let j = 0 ; j < this.genreLst.genres.length ; j++){
                    if(this.genreLst.genres[j].id !== obj.genre_ids[i]) continue;
                    genrePare.push(this.genreLst.genres[j].name);
                }//for
            }//for
        } else {                
            genrePare = obj.genres?.map((item)=>item.name) || [];
        }    
        
        // 데이터 세팅
        this.movieDetailInfo = {
            original_title: obj?.original_title,
            popularity    : obj?.popularity,
            overview      : obj?.overview,
            poster_path   : obj?.poster_path,
            backdrop_path : obj?.backdrop_path,
            title         : obj?.title,
            release_date  : obj?.release_date,
            id            : obj?.id,
            genre         : genrePare.join(","),            
        }

        // UI 세팅
        this.drawMoiveDetail(this.movieDetailInfo);
        
        // 초기화
        this.refreshSearchBox();

        // 상세정보 UI
        const oberviewWrap = document.querySelector("#oberviewWrap");
        if(oberviewWrap instanceof HTMLElement){
            oberviewWrap.style.display = "block";
        }

    }

    // 선택된 상세정보를 작성
    private drawMoiveDetail(movieDetailInfo : MovieDetailInfo){
        // 장르
        const genres  = document.querySelector("#genres");
        // 인기점수
        const popularity = document.querySelector("#popularity");
        // 개봉일        
        const releaseDate = document.querySelector("#releaseDate");
        // 요약
        const overview = document.querySelector("#overview");
        // 오리지널 타이틀
        const originalTitle = document.querySelector("#originalTitle");
        // 포스터 이미지
        const posterImg = document.querySelector("#posterImg");

        if(genres instanceof HTMLInputElement){
            genres.value = movieDetailInfo.genre || "장르를 찾을 수 없습니다.";
        }
        if(popularity instanceof HTMLInputElement){
            popularity.value = movieDetailInfo.popularity || "0.0";
        }
        if(releaseDate instanceof HTMLInputElement){
            releaseDate.value = movieDetailInfo.release_date || "개봉일자를 찾을 수 없습니다."; 
        }
        if(overview instanceof HTMLTextAreaElement){
            overview.value = movieDetailInfo.overview || ""; 
        }
        if(originalTitle instanceof HTMLInputElement){
            originalTitle.value = movieDetailInfo.original_title || ""; 
        }
        if(posterImg instanceof HTMLImageElement){
            posterImg.src = `https://image.tmdb.org/t/p/w500/${movieDetailInfo.poster_path}`;             
        }
        
    }

    // 검색창 UI 및 데이터 초기화
    private refreshSearchBox = ()=>{

        // 데이터 초기화
        this.movieSearchResult = {
            keyword        : '' ,       // 검색어
            page           : 0  ,       // 현재페이지
            results        : [] ,       // 목록
            total_pages    : 0  ,       // 총 페이지수
        }

        // 초기화
        const searchBox = document.querySelector("#serchBox");
        if(searchBox instanceof HTMLElement){
            searchBox.style.height = "0px";                               
        }//if
        const searchBoxTbody = document.querySelector("#serchBox tbody");
        if(searchBoxTbody instanceof HTMLElement){
            searchBoxTbody.innerHTML = "";
        }
        // 상세정보 UI
        const oberviewWrap = document.querySelector("#oberviewWrap");
        if(oberviewWrap instanceof HTMLElement){
            oberviewWrap.style.display = "none";
        }      
    }

    // 등록 이벤트
    private registerMovie = (event : Event)=>{

        const commnet = document.querySelector("#comment");
        
        if(!this.movieDetailInfo) return;

        if(commnet instanceof HTMLTextAreaElement){            
            this.movieDetailInfo!.comment = commnet.value ;
        }

        fetch("/movie/register",{
            method : "POST",
            headers : {
                "Content-Type": "application/json",
            },
            body : JSON.stringify(this.movieDetailInfo)
        })
        .then((response) => response.json())
        .then((data) => {
            location.href = "/movie/list";
        })
        .catch((error) => console.log(error));
    }

}

// init
(function(){
    const registerMovie = new RegisterMovieImpl();
})();



