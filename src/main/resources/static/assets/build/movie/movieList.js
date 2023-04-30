"use strict";
// 영화 목록
class MovieList {
    constructor() {
        // 영화 목록 페이징 이벤트
        this.pagingEvent = (event) => {
            event.preventDefault;
            const target = event.target;
            if (!(target instanceof HTMLElement))
                return;
            if (target.nodeName !== 'A')
                return;
            const targetTyp = Number(target.parentElement?.dataset.page);
            const searchParam = {
                page: targetTyp,
                type: this.searchParam.type,
                search_text: this.searchParam.search_text
            };
            // drawMovieList
            this.getMovieList(searchParam);
        };
        // 영화 조회 조건 이벤트
        this.movieSearch = () => {
            // 정렬 조건
            const sortType = document.querySelector("select[name='sortType']");
            if (sortType instanceof HTMLSelectElement) {
                this.searchParam.sortType = sortType.value;
            }
            // 조회 타입
            const moiveType = document.querySelector("select[name='moiveType']");
            if (moiveType instanceof HTMLSelectElement) {
                this.searchParam.type = moiveType.value;
            }
            // 조회 조건
            const movieSearchText = document.querySelector("#searchInput");
            if (movieSearchText instanceof HTMLInputElement) {
                this.searchParam.search_text = movieSearchText.value;
            }
            // 조회
            this.getMovieList(this.searchParam);
        };
        // 조회조건 초기화
        this.searchParam = { page: 1 };
        // 검색 목록 조회
        this.getMovieList(this.searchParam);
        // 영화목록 버튼 Event 추가
        this.moviePageNation = document.querySelector("#moviePageNation");
        this.moviePageNation?.addEventListener("click", this.pagingEvent);
        // 조회버튼 Event 추가
        this.searchBtn = document.querySelector("#searchBtn");
        this.searchBtn?.addEventListener("click", this.movieSearch);
    }
    // 영화 목록
    getMovieList(searchParam) {
        let url = `/movie/?page=${searchParam.page}`;
        if (searchParam.type) {
            url += `&type=${searchParam.type}`;
        }
        if (searchParam.search_text) {
            url += `&searchText=${searchParam.search_text}`;
        }
        if (searchParam.sortType) {
            url += `&searchText=${searchParam.sortType}`;
        }
        fetch(url)
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
                innerHTML += `<li data-page="${i}" class=${i === moviePagingObject.page ? "active" : ""} ><a class="pageNumBtn" href="#">${i}</a></li>`;
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
        // 상세보기 종료
        this.closeModal = (event) => {
            const modalSection = document.querySelector("#open-modal");
            if (!(modalSection instanceof HTMLElement))
                return;
            this.modalHide(modalSection);
        };
        // 영화 상세정보 Draw
        this.drawMovieDtail = (movieDetailInfo) => {
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
            if (!(poster instanceof HTMLElement) || !(originalTitle instanceof HTMLElement) || !(movieNm instanceof HTMLElement)
                || !(releaseDate instanceof HTMLElement) || !(popularity instanceof HTMLElement) || !(writerComment instanceof HTMLElement))
                return;
            poster.style.backgroundImage = `url(https://image.tmdb.org/t/p/original/${movieDetailInfo.backdrop_path})`;
            poster.style.backgroundPosition = "center";
            originalTitle.innerHTML = `${movieDetailInfo.original_title}`;
            movieNm.innerHTML = `${movieDetailInfo.title}`;
            releaseDate.innerHTML = `${movieDetailInfo.release_date}`;
            popularity.innerHTML = `${movieDetailInfo.popularity}`;
            writerComment.innerHTML = `${movieDetailInfo.comment}`;
        };
        // 상세보기
        this.openModal = (event) => {
            const target = event.target;
            if (!(target instanceof HTMLElement))
                return;
            if (target.nodeName !== 'A' && !target.classList.contains("showMovieDetails"))
                return;
            const modalSection = document.querySelector("#open-modal");
            if (!(modalSection instanceof HTMLElement))
                return;
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
            });
            // 댓글 목록
            if (mno)
                this.reply.getReplyList(mno);
        };
        // Modal Control Event
        this.modalHide = (modalSection) => modalSection.style.display = "none";
        this.modalShow = (modalSection) => modalSection.style.display = "flex";
        // 상세정보 초기화
        this.movieDetailInfo = {};
        // 상세보기 Event
        this.openMoiveDetial = document.querySelector("#listWrap");
        this.openMoiveDetial?.addEventListener("click", this.openModal);
        // 상세보기 닫기 Event
        this.closeMoiveDetial = document.querySelector("#modalCloseBtn");
        this.closeMoiveDetial?.addEventListener("click", this.closeModal);
        // 댓글
        this.reply = new Reply();
        this.writeBtn = document.querySelector("#writeBtn");
        this.writeBtn?.addEventListener("click", (e) => {
            this.reply.insertReply(this.movieDetailInfo);
        });
    }
}
class Reply {
    constructor() {
        // 댓글 목록
        this.getReplyList = (mno) => {
            if (!mno) {
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
            });
        };
        // 댓글 Draw
        this.drawReplyList = (movieDetailInfo) => {
            const replySection = document.querySelector("#replySection");
            if (!(replySection instanceof HTMLElement))
                return;
            let HTMLCode = "";
            for (let item of movieDetailInfo) {
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
            } //for
            replySection.innerHTML = HTMLCode;
        };
        // 댓글 등록
        this.insertReply = (movieDetailInfo) => {
            let replyText = "";
            const replyTextBox = document.querySelector("#writeCommentBtn");
            if (replyTextBox instanceof HTMLInputElement) {
                replyText = replyTextBox.value;
            } //if
            const replyObject = {
                mno: Number(movieDetailInfo.mno),
                replier: "회원가입 기능 추가후 수정",
                text: replyText
            };
            fetch(`/movie/replies`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(replyObject)
            })
                .then(res => res.json())
                .then(result => {
                // UI 작성            
                this.getReplyList(result);
            }).catch(error => {
                console.log(error);
            });
        };
        // 댓글 삭제
        this.deleteReply = (event) => {
            const target = event.target;
            if (!(target instanceof HTMLElement && target.classList.contains("replyDelBtn")))
                return;
            const rno = target.dataset.rno;
            const mno = target.dataset.mno || "";
            fetch(`/movie/replies/${rno}`, {
                method: "DELETE"
            })
                .then(res => res.text())
                .then(result => {
                // UI 작성            
                this.getReplyList(mno);
            }).catch(error => {
                console.log(error);
            });
        };
        this.replySection = document.querySelector("#replySection");
        // 댓글삭제 이벤트 추가
        this.replySection?.addEventListener("click", this.deleteReply);
    }
}
// init
(function () {
    const movieList = new MovieList();
    const movieDetails = new MoiveDetails();
})();
//# sourceMappingURL=movieList.js.map