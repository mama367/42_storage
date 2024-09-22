//      infinite: true ,      // 무한반복
//      slidesToShow: 1,     // 보여지는 슬라이드 개수
//      slidesToScroll: 1,   // 넘어가는 슬라이드 개수
//      dots: true,         // 점 네비게이션 표시
//      arrows: true ,     // 화살표 표시
//      fade: false,            // 페이드 효과
//      vertical: false,         // 상하 슬라이드
//      autoplay: false,       //자동스크롤
//      autoplaySpeed: 1000 ,  //자동스크롤 속도
//      pauseOnHover: true, // 마우스 호버하면 슬라이딩 멈춤

$(function(){
    $(".demo-slider").slick({
        infinite: true ,      // 무한반복
        slidesToShow: 4,     // 보여지는 슬라이드 개수
        slidesToScroll: 1  ,   // 넘어가는 슬라이드 개수
        dots: false,         // 점 네비게이션 표시
        arrows: true ,     // 화살표 표시
        fade: false,            // 페이드 효과
        vertical: false,         // 상하 슬라이드
        autoplay: true,       //자동스크롤
        autoplaySpeed: 2000 ,  //자동스크롤 속도
        pauseOnHover: true, // 마우스 호버하면 슬라이딩 멈춤
        // 버튼 커스터마이징
        prevArrow: "<button class='prevBtn'><i class='xi-angle-left'></i></button>",
        nextArrow: "<button class='nextBtn'><i class='xi-angle-right'></i></button>"
    });
});
