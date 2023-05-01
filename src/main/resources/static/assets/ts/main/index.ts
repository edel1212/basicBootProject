class MainBanner{

    // 장르목록
    private genreLst :any | undefined;

    constructor(){
        // 장르 값 설정
        this.getGenre(); 
        // 메인베너 생성
        this.getLastesMovieList()
    }

    // 메인베너 이미지를 가져옴 - 랜덤 최신영화 3개
    private getLastesMovieList(){
        fetch("https://api.themoviedb.org/3/movie/now_playing?api_key=a3af7d97effb973e78c5fb1fd7787b13&language=ko-KR&page=1")
        .then(res=>res.json())
        .then(result=>{            
            // (4
            let movieDetailInfoArr:MovieDetailInfo[] = result.results;
            let randomMovieArr = [];
            while(true){
                let randomNum = Math.floor(Math.random() * (movieDetailInfoArr.length+1));
                const chk = randomMovieArr.find(item=>item.id === movieDetailInfoArr[randomNum].id)
                if(chk) continue;           
                
                let genrePare:string[] = [];
                
                let genreSize:number =  movieDetailInfoArr[randomNum].genre_ids?.length || 0;
                
                for(let i = 0 ; i < genreSize ; i ++){
                    for(let j = 0 ; j < this.genreLst.genres.length ; j++){                        
                        if(this.genreLst.genres[j].id !== movieDetailInfoArr[randomNum].genre_ids![i]) continue;                                            
                        genrePare.push(this.genreLst.genres[j].name);
                    }//for
                }//for
                
                movieDetailInfoArr[randomNum].genre = genrePare.join(",");

                randomMovieArr.push(movieDetailInfoArr[randomNum]);
                if(randomMovieArr.length === 4) break;
            }// while
            
            // UI Draw
            this.drawMainBanner(randomMovieArr);
        }).catch(error=>{
            this.getLastesMovieList();
            console.log(error);
        })
    }

    // 메인 베너를 그림
    private drawMainBanner(randomMovieArr:MovieDetailInfo[]){
        const sliderElem = document.querySelector("#section-1 .slider");
        if(!(sliderElem instanceof HTMLElement)) return;
        let htmlElem = "";
        for(let i = 0 ; i < randomMovieArr.length ; i++){
            htmlElem += `<div id="top-banner-${i+1}" class="banner" style=" background: url('https://image.tmdb.org/t/p/original${randomMovieArr[i].backdrop_path}') no-repeat">
                            <div class="banner-inner-wrapper header-text">
                            <div class="main-caption" style="text-shadow: 0 0 15px #bebebe;">
                                <h2>${randomMovieArr[i].original_title}</h2>
                                <h1>${randomMovieArr[i].title}</h1>
                                <div class="border-button"><a href="/movie/register?id=${randomMovieArr[i].id}">Regisger Review</a></div>
                            </div>
                            <div class="container">
                                <div class="row">
                                <div class="col-lg-12">
                                    <div class="more-info">
                                    <div class="row">
                                        <div class="col-lg-3 col-sm-6 col-6">
                                        <i class="fa fa-film"></i>
                                        <h4><span>Original Title:</span><br>${randomMovieArr[i].original_title}</h4>
                                        </div>  
                                        <div class="col-lg-3 col-sm-6 col-6">
                                        <i class="fa fa-globe"></i>
                                        <h4><span>Genre:</span><br>${randomMovieArr[i].genre}</h4>
                                        </div>  
                                        <div class="col-lg-3 col-sm-6 col-6">
                                        <i class="fa fa-user"></i>
                                        <h4><span>Population:</span><br>${randomMovieArr[i].popularity}</h4>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-6">
                                        <i class="fa fa-clock"></i>
                                        <h4><span>Relase Date:</span><br>${randomMovieArr[i].release_date}</h4>
                                        </div>                                                            
                                    </div>
                                    </div>
                                </div>
                                </div>
                            </div>
                            </div>
                        </div>`
        }//for
        sliderElem.innerHTML = htmlElem;
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
}

// 중간 컨텐드 탑3
class Top3Review{
    constructor(){
        this.getRTop3Review();
    }
    // 영화 목록
    private getRTop3Review(){        
        fetch(`/movie?page=1&sortType=rv`)
            .then(res => res.json())
            .then(result => {                             
                this.drawTop3(result.dto_list);
            }).catch(error => {
                console.log(error);
            })
    }
    private drawTop3(movieDetailInfo:MovieDetailInfo[]){
        const top3Wrap = document.querySelector("#top3Wrap");
        if(!(top3Wrap instanceof HTMLElement)) return;
        for(let i = 0; i < movieDetailInfo.length ; i ++){
            if(i === 3) break;
            let item = movieDetailInfo[i];
            let htmlCode = `<div class="col-lg-12">
                                <div class="item">
                                <div class="row">
                                    <div class="col-lg-4 col-sm-5">
                                    <div class="image">
                                        <img src="https://image.tmdb.org/t/p/original${item.poster_path}" alt="poster">
                                    </div>
                                    </div>
                                    <div class="col-lg-8 col-sm-7">
                                    <div class="right-content">
                                        <h4 >${item.title}</h4>
                                        <span>${item.original_title}</span>
                                        <div class="main-button">
                                        <a href="about.html">View Detail</a>
                                        </div>
                                        <p class="top3Pverview">${item.overview}</p>
                                        <ul class="info">
                                        <li><i class="fa fa-user"></i> ${item.popularity}</li>
                                        <li><i class="fa fa-globe"></i> ${item.genre}</li>
                                        <li><i class="fa fa-clock"></i> ${item.release_date}</li>
                                        </ul>
                                        <div>
                                        <div>RegDate : ${item.mod_date}</div>
                                        <div class="text-button">
                                        <a href="about.html">edel1212@naver.com</a>
                                        </div>
                                        </div>                                        
                                    </div>
                                    </div>
                                </div>
                                </div>
                            </div>`;
            top3Wrap.insertAdjacentHTML("beforeend",htmlCode);
        }//for
        
    }
}

// init
(function(){
    const mainBanner = new MainBanner();    
    const top3Review = new Top3Review();
})();