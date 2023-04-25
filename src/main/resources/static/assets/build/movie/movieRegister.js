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
        var _a, _b;
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
                    this.refreshSearchBox();
                    const searchBox = document.querySelector("#serchBox");
                    if (searchBox instanceof HTMLElement) {
                        searchBox.style.height = "300px";
                    } //if
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
                    // 스크롤 이벤트 추가
                    const lastTr = document.querySelector("#movieLst tr:last-child");
                    if (!lastTr)
                        return;
                    this.observer.observe(lastTr);
                }).catch(error => {
                    console.log(error);
                });
            } //if        
        };
        // 영화 목록 스크롤 조회
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
        // 조회 목록 클릭 이벤트
        this.getMovieDetailInfo = (event) => {
            var _a;
            const clickedElement = event.target;
            if (!(clickedElement instanceof HTMLElement))
                return;
            const clickedElementName = clickedElement.nodeName;
            if (clickedElementName !== "TD")
                return;
            const targetElem = clickedElement.parentElement;
            const obj = (_a = this.movieSearchResult) === null || _a === void 0 ? void 0 : _a.results.find(i => {
                return i.id == (targetElem === null || targetElem === void 0 ? void 0 : targetElem.dataset.id);
            });
            // 장르 변환
            let genrePare = [];
            if (obj === null || obj === void 0 ? void 0 : obj.genre_ids) {
                for (let i = 0; i < obj.genre_ids.length; i++) {
                    obj.genre_ids[i];
                    for (let j = 0; j < this.genreLst.genres.length; j++) {
                        if (this.genreLst.genres[j].id !== obj.genre_ids[i])
                            continue;
                        genrePare.push(this.genreLst.genres[j].name);
                    } //for
                } //for
            }
            // 데이터 세팅
            this.movieDetailInfo = {
                original_title: obj === null || obj === void 0 ? void 0 : obj.original_title,
                popularity: obj === null || obj === void 0 ? void 0 : obj.popularity,
                overview: obj === null || obj === void 0 ? void 0 : obj.overview,
                poster_path: obj === null || obj === void 0 ? void 0 : obj.poster_path,
                backdrop_path: obj === null || obj === void 0 ? void 0 : obj.backdrop_path,
                title: obj === null || obj === void 0 ? void 0 : obj.title,
                release_date: obj === null || obj === void 0 ? void 0 : obj.release_date,
                id: obj === null || obj === void 0 ? void 0 : obj.id,
                genre: genrePare,
            };
            // UI 세팅
            this.drawMoiveDetail(this.movieDetailInfo);
            // 초기화
            this.refreshSearchBox();
            // 상세정보 UI
            const oberviewWrap = document.querySelector("#oberviewWrap");
            if (oberviewWrap instanceof HTMLElement) {
                oberviewWrap.style.display = "block";
            }
        };
        // 검색창 UI 및 데이터 초기화
        this.refreshSearchBox = () => {
            // 데이터 초기화
            this.movieSearchResult = {
                keyword: '',
                page: 0,
                results: [],
                total_pages: 0, // 총 페이지수
            };
            // 초기화
            const searchBox = document.querySelector("#serchBox");
            if (searchBox instanceof HTMLElement) {
                searchBox.style.height = "0px";
            } //if
            const searchBoxTbody = document.querySelector("#serchBox tbody");
            if (searchBoxTbody instanceof HTMLElement) {
                searchBoxTbody.innerHTML = "";
            }
            // 상세정보 UI
            const oberviewWrap = document.querySelector("#oberviewWrap");
            if (oberviewWrap instanceof HTMLElement) {
                oberviewWrap.style.display = "none";
            }
        };
        // 영화 검색
        this.findMovieBtn = document.querySelector("#findMovieBtn");
        (_a = this.findMovieBtn) === null || _a === void 0 ? void 0 : _a.addEventListener("click", this.getMovieInfo);
        // 조회된 결과 Click
        this.searchResultElem = document.querySelector("#movieLst");
        (_b = this.searchResultElem) === null || _b === void 0 ? void 0 : _b.addEventListener("click", this.getMovieDetailInfo);
        //장르 값 설정
        this.getGenre();
    } // constructor
    // 영화 조회결과 Draw
    drawMoiveList(objArr) {
        var _a;
        let liHtml = "";
        for (const item of objArr) {
            liHtml += `<tr data-id="${item.id}">`;
            liHtml += `<td class="backTd" style="background-image: url( 'https://image.tmdb.org/t/p/w200/${item.backdrop_path}' )"></td>`;
            liHtml += `<td class="movieTitle">${item.title}</td>`;
            liHtml += `<td>${item.release_date}</td>`;
            liHtml += `</tr>`;
        } //for   
        (_a = document.querySelector("#movieLst tbody")) === null || _a === void 0 ? void 0 : _a.insertAdjacentHTML("beforeend", liHtml);
    }
    // Api Call - 영화 목록  
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
    // 선택된 상세정보를 작성
    drawMoiveDetail(movieDetailInfo) {
        var _a;
        // 장르
        const genres = document.querySelector("#genres");
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
        if (genres instanceof HTMLInputElement) {
            genres.value = ((_a = movieDetailInfo.genre) === null || _a === void 0 ? void 0 : _a.join(",")) || "장르를 찾을 수 없습니다.";
        }
        if (popularity instanceof HTMLInputElement) {
            popularity.value = movieDetailInfo.popularity || "0.0";
        }
        if (releaseDate instanceof HTMLInputElement) {
            releaseDate.value = movieDetailInfo.release_date || "개봉일자를 찾을 수 없습니다.";
        }
        if (overview instanceof HTMLTextAreaElement) {
            overview.value = movieDetailInfo.overview || "";
        }
        if (originalTitle instanceof HTMLInputElement) {
            originalTitle.value = movieDetailInfo.original_title || "";
        }
        if (posterImg instanceof HTMLImageElement) {
            posterImg.src = `https://image.tmdb.org/t/p/w500/${movieDetailInfo.poster_path}`;
        }
    }
}
// init
(function () {
    const registerMovie = new RegisterMovieImpl();
})();
//# sourceMappingURL=movieRegister.js.map