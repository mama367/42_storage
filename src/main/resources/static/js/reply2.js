	
	// ************** js 업데이트시에 쿠키 및 캐시값 초기화 진행 **************
	
	// 즉시실행함수
	// reply 매개변수 : pid, r_contents, replyer 포함하는 Json형태가 됩니다.
	let replyFunc = (() => {
		// 댓글 등록
		function register(reply, callback){
			$.ajax({
				url: "/reply2/new",
				type: "post",
				data: JSON.stringify(reply),	// 문자열(텍스트) 변환 ==> 직렬화작업
				contentType: "application/json; charset=utf8",	// 직렬화 윗줄과 세트
				success: function(result){
					callback(result);
				},
				error: ()=>{alert("요청실패1!!")}
			});
		}
		
		// 댓글 삭제
		function remove(rno, callback){
			$.ajax({
				url: "/reply2/"+rno,
				type: "delete",
				success: function(result){
					callback(result);
				},
				error: ()=>{alert("2요청실패2!!")}
			});
		}
		
		// 댓글 조회
		function read(rno, callback){
			$.ajax({
				url: "/reply2/"+rno,
				type: "get",
				success: function(result){
					// (result) => {} 람다식으로도 가능
					callback(result);
				},
				error: ()=>{alert("2요청실패3!!")}
			});
		}
		
		// 댓글 수정
		function update(reply, callback){
			$.ajax({
				url: "/reply2/"+reply.rno,
				type: "put",
				data: JSON.stringify(reply),	// 문자열(텍스트) 변환 ==> 직렬화작업
				contentType: "application/json; charset=utf8",	// 직렬화 윗줄과 세트
				success: function(result){
					// (result) => {} 람다식으로도 가능
					callback(result);
				},
				error: ()=>{alert("2요청실패4!!")}
			});
		}
		
		// 특정게시글에 대한 댓글조회 
/*		function getList(pid, callback){ // 페이징처리 이전코딩
			$.ajax({
				url: "/winia/replies/list/"+pid,
				type: "get",
				success: function(result){
					// (result) => {} 람다식으로도 가능
					console.log(result);
					callback(result);
				},
				error: ()=>{alert("요청실패!!")}
			});
		} */
		function getList(param, callback){
			let pid = param.pid;
			let viewPage = param.viewPage;
			console.log(pid);
			$.ajax({
				url: "/reply2/list/"+pid+"/"+viewPage,
				type: "get",
				success: function(result){
					// (result) => {} 람다식으로도 가능
					console.log(result);
					callback(result);
				},
				error: ()=>{alert("2요청실패5!!")}
			});
		}
		
		// 댓글 시간/날짜 표시 - 24시간 지난것은 날짜로, 24시간 이내는 시간으로
		function showDateTime(replyTime){	// replyTime 댓글등록 시간
			// 현재시간 구함
			let now = new Date();
			
			// 현재시간과 댓글 등록시간의 갭 차이
			let gap = now.getTime() - replyTime;
			// 댓글 등록시간을 Date객체로 생성
			let rDate = new Date(replyTime);
			
			// 갭이 24시간 이상이면 날짜로 표시, 24시간 이내이면 시간으로 표시
			if(gap <  (1000 * 60 * 60 * 24)){		// 24시간 이내 => 1000(1초) * 60(1분) * 60(1시간) * 24(24시간)
				let hh = rDate.getHours();
				let mi = rDate.getMinutes();
				let ss = rDate.getSeconds();
			
				// split() 배열을 문자열로 쪼개기, join()은 배열을 문자열로 합치기 // [09,":",03,":",23] ==> "09:03:23" // join('') 구분자 없이 합치겟다.
				return [(hh >= 10 ? '' : '0')+hh, ":", (mi >= 10 ? '' : '0')+mi, ":", (ss >= 10 ? '' : '0')+ss].join('');
				// "손흥민/박지성/차범근".split('/')
				// "손흥민/박지성/차범근" ==> [손흥민, 박지성, 차범근]
				
			} else {	// 24시간 이상
				let yy = rDate.getFullYear();	// 년도 FullYear 4자리로 가져옴
				let mm = rDate.getMonth() + 1;	// getMonth()는 +1을 해줘야함 0~11까지 표기됌
				let dd = rDate.getDate();
				
				return [yy, "-", (mm >= 10 ? '' : '0')+mm, "-", (dd >= 10 ? '' : '0')+dd].join('');
			}
		} // showDateTime funciton
		
		return { 		// replyFunc의 리턴
			register:register,
			remove:remove,
			read:read,
			update:update,
			getList:getList,
			showDateTime:showDateTime
		}
	})(); // replyFunc