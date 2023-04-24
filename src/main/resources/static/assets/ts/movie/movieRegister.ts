/**
 * 영화 글 작성
 */
interface RegisterMovie{
    getMovieInfo(event:MouseEvent):void;
}

type MovieDetailInfo = {
    backdrop_path : string;
    title         : string;
    release_date  : string;   
    id?           : number
}

type MovieSearchResult = {
    keyword?        : string;           // 검색어
    page            : number;           // 현재페이지
    results         : MovieDetailInfo[];  // 목록
    total_pages?    : number;           // 총 페이지수
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
    
    // 영화 조회 결과창
    private findMovieLstElem;

    // 조회 결과
    private movieSearchResult:MovieSearchResult | undefined;

    constructor(){
        // 영화 검색
        this.findMovieBtn = document.querySelector("#findMovieBtn");
        this.findMovieBtn?.addEventListener("click",this.getMovieInfo);
        
        // 영화 검색 결과 스크롤 이벤트
        this.findMovieLstElem = document.querySelector("#serchBox");
       
    }// constructor

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
                const searchBox = document.querySelector("#serchBox");
                if(searchBox instanceof HTMLElement){
                    searchBox.style.height = "0px"; 
                    searchBox.style.height = "300px";                       
                }//if
                const searchBoxTbody = document.querySelector("#serchBox tbody");
                if(searchBoxTbody instanceof HTMLElement){
                    searchBoxTbody.innerHTML = "";
                }        

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
            liHtml += `<tr>`;
            liHtml += `<td class="backTd" style="background-image: url( 'https://image.tmdb.org/t/p/w200/${item.backdrop_path}' )"></td>`;
            liHtml += `<td class="movieTitle">${item.title}</td>`;
            liHtml += `<td>${item.release_date}</td>`;
            liHtml += `</tr>`;                                                            
        }//for   
        document.querySelector("#movieLst tbody")?.insertAdjacentHTML("beforeend",liHtml);
    }


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


}



// init
(function(){
    const registerMovie = new RegisterMovieImpl();
})();



