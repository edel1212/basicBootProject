
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
                                <div class="image moiveImg" style="background: url(https://image.tmdb.org/t/p/original${item.poster_path}) center">                                    
                                </div>
                            </div>
                            <div class="col-lg-6 align-self-center">
                                <div class="content">
                                    <span class="info">${item.original_title}</span>
                                    <h4>${item.title}</h4>
                                    <div class="row">
                                        <div class="col-6">
                                            <i class="fa fa-clock"></i>
                                            <span class="list">${item.release_date}</span>
                                        </div>
                                        <div class="col-6">
                                            <i class="fa fa-map"></i>
                                            <span class="list">${item.popularity}</span>
                                        </div>
                                    </div>
                                    <p class="overviewSection">${item.overview}</p>
                                    <div class="main-button" style="text-align:center">
                                        <a href="reservation.html">Make a Reservation</a>
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
                innerHTML += `<li data-page="${i}" class=${i === moviePagingObject.page ?  "active" : ""} ><a href="#">${i}</a></li>`
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

        let targetTyp;
        if (target.nodeName ==='I'){
            targetTyp = target.parentElement?.parentElement?.dataset.page;            
        } else if(target.nodeName ==='LI'){
            targetTyp = target.dataset.page;            
        } else {
            targetTyp = target.parentElement?.dataset.page;            
        } // if ~ else

        const searchParam: SearchParam = {
            page : Number(targetTyp),
        }
        // dycjd
        this.getMovieList(searchParam);
    }

}

// init
(function(){
    const movieList = new MovieList();
})();
