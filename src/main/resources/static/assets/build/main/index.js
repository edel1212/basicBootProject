"use strict";
class MainBanner {
    constructor() {
        // 장르 값 설정
        this.getGenre();
        // 메인베너 생성
        this.getLastesMovieList();
    }
    // 메인베너 이미지를 가져옴 - 랜덤 최신영화 3개
    getLastesMovieList() {
        fetch("https://api.themoviedb.org/3/movie/now_playing?api_key=a3af7d97effb973e78c5fb1fd7787b13&language=ko-KR&page=1")
            .then(res => res.json())
            .then(result => {
            // (4
            let movieDetailInfoArr = result.results;
            let randomMovieArr = [];
            while (true) {
                let randomNum = Math.floor(Math.random() * (movieDetailInfoArr.length + 1));
                const chk = randomMovieArr.find(item => item.id === movieDetailInfoArr[randomNum].id);
                if (chk)
                    continue;
                let genrePare = [];
                let genreSize = movieDetailInfoArr[randomNum].genre_ids?.length || 0;
                for (let i = 0; i < genreSize; i++) {
                    for (let j = 0; j < this.genreLst.genres.length; j++) {
                        if (this.genreLst.genres[j].id !== movieDetailInfoArr[randomNum].genre_ids[i])
                            continue;
                        genrePare.push(this.genreLst.genres[j].name);
                    } //for
                } //for
                movieDetailInfoArr[randomNum].genre = genrePare.join(",");
                randomMovieArr.push(movieDetailInfoArr[randomNum]);
                if (randomMovieArr.length === 4)
                    break;
            } // while
            console.log(this.genreLst);
            // 장르 변환
            // UI Draw
            this.drawMainBanner(randomMovieArr);
        }).catch(error => {
            console.log(SpeechSynthesisErrorEvent);
        });
    }
    // 메인 베너를 그림
    drawMainBanner(randomMovieArr) {
        const sliderElem = document.querySelector("#section-1 .slider");
        if (!(sliderElem instanceof HTMLElement))
            return;
        let htmlElem = "";
        for (let i = 0; i < randomMovieArr.length; i++) {
            htmlElem += `<div id="top-banner-${i + 1}" class="banner" style=" background: url('https://image.tmdb.org/t/p/original${randomMovieArr[i].backdrop_path}') no-repeat">
                            <div class="banner-inner-wrapper header-text">
                            <div class="main-caption" style="text-shadow: 0 0 15px #bebebe;">
                                <h2>${randomMovieArr[i].original_title}</h2>
                                <h1>${randomMovieArr[i].title}</h1>
                                <div class="border-button"><a href="javascript:void(0)">Regisger Review</a></div>
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
                                        <h4><span>Relase Date:</span><br><em>${randomMovieArr[i].release_date}</em></h4>
                                        </div>                                                            
                                    </div>
                                    </div>
                                </div>
                                </div>
                            </div>
                            </div>
                        </div>`;
        } //for
        sliderElem.innerHTML = htmlElem;
    }
    // Api Call - 영화 장르 종류 Set
    getGenre() {
        fetch(`https://api.themoviedb.org/3/genre/movie/list?api_key=a3af7d97effb973e78c5fb1fd7787b13&language=ko-KR`)
            .then(res => res.json())
            .then(result => {
            this.genreLst = result;
        }).catch(error => {
            console.log(error);
        });
    }
}
// init
(function () {
    const mainBanner = new MainBanner();
})();
//# sourceMappingURL=index.js.map