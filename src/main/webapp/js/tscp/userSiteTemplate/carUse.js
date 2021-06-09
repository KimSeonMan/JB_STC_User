/**
 * 차량이용안내
 *
 */
(function(W, D) {
	$(document).ready(function() {
		//센터데이터 로딩
		var cnter_cd = session.cnter_cd;
		if(cnter_cd == "" || cnter_cd == "null" || cnter_cd == null){
			cnter_cd = 'NAT-1-001';
		}
		$("#cnterCd").val(cnter_cd);
		$carUse.centerList(cnter_cd);
		
		//센터  selectbox 이벤트
		$("#SCH_CNTER_CD").change(function(){
			$carUse.list();
		});
		
	});
	
	$carUse = {
		list : function() {	
			
			var senders = new tscp.carUseList.api();
			var gubn = getQuerystring('gubn');
			senders.addParam("GUBN_TYPE", gubn);
			
			if($("#SCH_CNTER_CD").val() != "") {
				senders.addParam("CNTER_CD", $("#SCH_CNTER_CD").val());
			}else{
				senders.addParam("CNTER_CD", "WDR-2-001");
			}
			
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/main/CarUseList.json",
			});
		},
		centerList : function(str){
			var senders = new tscp.centerList.api();
			//senders.addParam("CNTER_CD",str);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/common/CenterList.json",
			});
		}
	};
	
	/*********** 이용안내 내용 조회 API 호출 Start **********/
	(function() {
		$class("tscp.carUseList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var html = result.contentsInfo.CNTNTS_CTNT
				$("#cnttData").html(html);
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 이용안내 내용 조회 API 호출 End **********/
	
	/*********** 센터목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.centerList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				
				//센터 구분
             	var cnList = "";
             	cnList += "<option value='NAT-1-001'>선택</option>";
             	for(var i = 0; i < result.centerList.length; i++){
						if($("#cnterCd").val() == result.centerList[i].CNTER_CD){
							cnList += "<option value='"+result.centerList[i].CNTER_CD+"' selected='selected'>"+result.centerList[i].CNTER_NM+"</option>";
						} else {
							cnList += "<option value='"+result.centerList[i].CNTER_CD+"'>"+result.centerList[i].CNTER_NM+"</option>";
						}
					}
 				
 				$("#SCH_CNTER_CD").html(cnList);
 				
 				//$carUse.list();
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 센터목록조회 API 호출 End **********/
	
	function getQuerystring(paramName){ 
		var _tempUrl = window.location.search.substring(1); //url에서 처음부터 '?'까지 삭제 
		var _tempArray = _tempUrl.split('&'); // '&'을 기준으로 분리하기 
		for(var i = 0; _tempArray.length; i++) { 
			var _keyValuePair = _tempArray[i].split('='); // '=' 을 기준으로 분리하기
			if(_keyValuePair[0] == paramName){ // _keyValuePair[0] : 파라미터 명
				// _keyValuePair[1] : 파라미터 값
				return _keyValuePair[1]; 
			} 
		} 
	} 
		

	
}(window, document));

