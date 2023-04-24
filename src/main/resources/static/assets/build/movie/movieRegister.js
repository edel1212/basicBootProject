"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
class RegisterMovieImpl {
    constructor() {
        var _a;
        // 스크롤 옵션
        this.options = {
            root: document.querySelector("#serchBox"),
            rootMargin: '0px',
            threshold: 0.5
        };
        // 영화 정보를 가져옴
        this.getMovieInfo = (event) => {
            const movieNameElem = document.querySelector("input[name='movieName']");
            if (movieNameElem instanceof HTMLInputElement) {
                const movieName = movieNameElem === null || movieNameElem === void 0 ? void 0 : movieNameElem.value;
                if (!movieName)
                    return;
                fetch(`https://api.themoviedb.org/3/search/movie?api_key=a3af7d97effb973e78c5fb1fd7787b13&language=ko-KR&page=1&query=${movieName}`)
                    .then(res => res.json())
                    .then(result => {
                    // 초기화
                    const searchBox = document.querySelector("#serchBox");
                    if (searchBox instanceof HTMLElement) {
                        searchBox.style.height = "0px";
                        searchBox.style.height = "300px";
                    } //if
                    const searchBoxTbody = document.querySelector("#serchBox tbody");
                    if (searchBoxTbody instanceof HTMLElement) {
                        searchBoxTbody.innerHTML = "";
                    }
                    // 결과 Draw
                    this.drawMoiveList(result.results);
                    // 조회결과 업데이트
                    this.movieSearchResult = {
                        keyword: movieName,
                        page: result.page,
                        results: result.results,
                        total_pages: result.total_pages,
                    };
                }).then(addEvent => {
                    const lastTr = document.querySelector("#movieLst tr:last-child");
                    if (!lastTr)
                        return;
                    this.observer.observe(lastTr);
                }).catch(error => {
                    console.log(error);
                });
            } //if        
        };
        this.observer = new IntersectionObserver((entries, observer) => {
            entries.forEach((entry) => __awaiter(this, void 0, void 0, function* () {
                var _a;
                if (entry.isIntersecting) {
                    // 가장 마지막 리스트 아이템인지 확인
                    if (!this.movieSearchResult)
                        return;
                    let keyword = this.movieSearchResult.keyword || "";
                    let page = this.movieSearchResult.page;
                    let lastPage = this.movieSearchResult.total_pages;
                    let tmpArr = (_a = this.movieSearchResult) === null || _a === void 0 ? void 0 : _a.results;
                    // 더이상 페이징이 필요없음
                    if (page === lastPage)
                        return;
                    yield this.callApi(page, keyword, tmpArr);
                }
            }));
        }, this.options);
        // 영화 검색
        this.findMovieBtn = document.querySelector("#findMovieBtn");
        (_a = this.findMovieBtn) === null || _a === void 0 ? void 0 : _a.addEventListener("click", this.getMovieInfo);
        // 영화 검색 결과 스크롤 이벤트
        this.findMovieLstElem = document.querySelector("#serchBox");
    } // constructor
    // 영화 조회결과 Draw
    drawMoiveList(objArr) {
        var _a;
        let liHtml = "";
        for (const item of objArr) {
            liHtml += `<tr>`;
            liHtml += `<td class="backTd" style="background-image: url( 'https://image.tmdb.org/t/p/w200/${item.backdrop_path}' )"></td>`;
            liHtml += `<td class="movieTitle">${item.title}</td>`;
            liHtml += `<td>${item.release_date}</td>`;
            liHtml += `</tr>`;
        } //for   
        (_a = document.querySelector("#movieLst tbody")) === null || _a === void 0 ? void 0 : _a.insertAdjacentHTML("beforeend", liHtml);
    }
    callApi(page, keyword, tmpArr) {
        return __awaiter(this, void 0, void 0, function* () {
            fetch(`https://api.themoviedb.org/3/search/movie?api_key=a3af7d97effb973e78c5fb1fd7787b13&language=ko-KR&page=${page + 1}&query=${keyword}`)
                .then(res => res.json())
                .then(result => {
                const newArr = tmpArr === null || tmpArr === void 0 ? void 0 : tmpArr.concat(result.results);
                // 조회결과 업데이트
                this.movieSearchResult.page = result.page;
                this.movieSearchResult.results = newArr;
                // 결과 Draw
                this.drawMoiveList(result.results);
                // 옵저버 종료
                this.observer.disconnect();
            }).then(addEvent => {
                //새로운 마지막 tr 등록
                const lastTr = document.querySelector("#movieLst tr:last-child");
                if (!lastTr)
                    return;
                this.observer.observe(lastTr);
            })
                .catch(error => {
                console.log(error);
            });
        });
    }
}
// init
(function () {
    const registerMovie = new RegisterMovieImpl();
})();
//# sourceMappingURL=movieRegister.js.map