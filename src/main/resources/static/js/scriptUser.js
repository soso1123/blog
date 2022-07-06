
let index = {
    init: function () {
        $("#btn-save").on("click", ()=> {
            this.save();
        });


    },

    save: function () {
        let data = {
            username:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val()
        };

        // console.log(data);
        //ajax 통신으로 값을 변경
        //ajax 호출 시 default가 비동기 호출
        //통신 성공시 서버가 json을 리텅해 주면 자동으로 자바 오브젝트로 변환해 줌
        $.ajax({
            type:"POST",
            url:"/auth/joinProc",
            data: JSON.stringify(data),
            contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지
            dataType:"json", //요청에 대한 응답이 왔을 때 결과의 타입
            beforeSend: function (jqXHR, settings) {
                let header = $("meta[name='_csrf_header']").attr("content");
                let token = $("meta[name='_csrf']").attr("content");
                jqXHR.setRequestHeader(header, token);
            }
        }).done(function (resp){
            alert("회원가입이 완료되었습니다.");
            // console.log(resp);
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },

}

index.init();


// $("#btn-login").on("click", ()=> {
//     this.login();
// });
//전통적인 로그인 방식
// login: function () {
//     let data = {
//         username:$("#username").val(),
//         password:$("#password").val(),
//     };
//
//     $.ajax({
//         type:"POST",
//         url:"/api/user/login",
//         data: JSON.stringify(data),
//         contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지
//         dataType:"json", //요청에 대한 응답이 왔을 때 결과의 타입
//         beforeSend: function (jqXHR, settings) {
//             let header = $("meta[name='_csrf_header']").attr("content");
//             let token = $("meta[name='_csrf']").attr("content");
//             jqXHR.setRequestHeader(header, token);
//         }
//     }).done(function (resp){
//         alert("로그인이 완료되었습니다.");
//         location.href = "/";
//     }).fail(function (error){
//         alert(JSON.stringify(error));
//     });
// }