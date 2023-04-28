"use strict";
// 영화 목록
class MovieList {
    constructor() {
        var _a;
        // 영화 목록 페이징 이벤트
        this.pagingEvent = (event) => {
            var _a;
            event.preventDefault;
            const target = event.target;
            if (!(target instanceof HTMLElement))
                return;
            if (target.nodeName !== 'A')
                return;
            const targetTyp = Number((_a = target.parentElement) === null || _a === void 0 ? void 0 : _a.dataset.page);
            const searchParam = {
                page: targetTyp,
            };
            // drawMovieList
            this.getMovieList(searchParam);
        };
        // 검색 목록 조회
        this.getMovieList({ page: 1 });
        // 영화목록 버튼 Event 추가
        this.moviePageNation = document.querySelector("#moviePageNation");
        (_a = this.moviePageNation) === null || _a === void 0 ? void 0 : _a.addEventListener("click", this.pagingEvent);
    }
    // 영화 목록
    getMovieList(searchParam) {
        fetch(`/movie/?page=${searchParam.page}`)
            .then(res => res.json())
            .then(result => {
            console.log(result);
            // UI 작성
            this.drawMovieList(result);
        }).catch(error => {
            console.log(error);
        });
    }
    // 받아온 결과값으로 UI 생성
    drawMovieList(moviePagingObject) {
        // 목록 Draw
        const listWrap = document.querySelector("#listWrap");
        if (listWrap instanceof HTMLElement) {
            listWrap.innerHTML = "";
            for (let item of moviePagingObject.dto_list) {
                const innerHTML = `<div class="col-lg-6 col-sm-6">
                    <div class="item">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="image moiveImg" style="background: url(https://image.tmdb.org/t/p/original${item.poster_path}) center">                                    
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
                                            <i class="fa fa-map"></i>
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
                listWrap.insertAdjacentHTML("beforeend", innerHTML);
            } //for
        } //if
        // PageNation
        const moviePageNation = document.querySelector("#moviePageNation");
        if (moviePageNation instanceof HTMLUListElement) {
            // 초기화
            moviePageNation.innerHTML = "";
            let innerHTML = '';
            // 이전
            if (moviePagingObject.prev) {
                innerHTML += `<li data-page="${moviePagingObject.start - 1}"><a href="#"><i class="fa fa-arrow-left"></i></a></li>`;
            }
            // 페이지 번호
            for (let i of moviePagingObject.page_list) {
                innerHTML += `<li data-page="${i}" class=${i === moviePagingObject.page ? "active" : ""} ><a href="#">${i}</a></li>`;
            }
            // 다음
            if (moviePagingObject.next) {
                innerHTML += `<li data-page="${moviePagingObject.end + 1}"><a href="#"><i class="fa fa-arrow-right"></i></a></li>`;
            }
            moviePageNation.insertAdjacentHTML("beforeend", innerHTML);
        } //if
    }
}
class MoiveDetails {
    constructor() {
        var _a, _b;
        // 상세보기 종료
        this.closeModal = (event) => {
            const modalSection = document.querySelector("#open-modal");
            if (modalSection instanceof HTMLElement) {
                modalSection.style.display = "none";
            }
        };
        // 상세보기
        this.openModal = (event) => {
            const target = event.target;
            if (!(target instanceof HTMLElement))
                return;
            if (target.nodeName !== 'A' && !target.classList.contains("showMovieDetails"))
                return;
            console.log(target.dataset.mno);
            const modalSection = document.querySelector("#open-modal");
            if (modalSection instanceof HTMLElement) {
                modalSection.style.display = "flex";
            }
        };
        // 상세보기 Event
        this.openMoiveDetial = document.querySelector("#listWrap");
        (_a = this.openMoiveDetial) === null || _a === void 0 ? void 0 : _a.addEventListener("click", this.openModal);
        // 상세보기 닫기 Event
        this.closeMoiveDetial = document.querySelector("#modalCloseBtn");
        (_b = this.closeMoiveDetial) === null || _b === void 0 ? void 0 : _b.addEventListener("click", this.closeModal);
    }
}
// init
(function () {
    const movieList = new MovieList();
    const movieDetails = new MoiveDetails();
})();
//# sourceMappingURL=movieList.js.map