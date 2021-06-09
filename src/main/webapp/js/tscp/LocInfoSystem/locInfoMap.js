/**
 * 관제 Map.js
 */


(function(W,D){
	W.$locInfoMap = W.$locInfoMap || {};
	
	$(document).ready(function(){
		alert("locInfoMap.js");
		//HTML5 map geolocation
		//기본좌표
		var x_coor = 33.450701;
		var y_coor = 126.570667;
		var level = 3;	
		
		var browser = navigator.appName;
		
		if(browser == "Netscape"){
			x_coor = 37.4950952443;
			y_coor = 127.1224273159;
			$locInfoMap.map.ui.drawMap("map_canvas",x_coor, y_coor,level);			
		}else{
			if(navigator.geolocation){
				navigator.geolocation.getCurrentPosition(function(position) {
					x_coor = position.coords.latitude;
					y_coor = position.coords.longitude;
					/*x_coor = 37.4950952443;
					y_coor = 127.1224273159;*/
					
					$locInfoMap.map.ui.drawMap("map_canvas",x_coor, y_coor,level);
				});
			}else{
				$locInfoMap.map.ui.drawMap("map_canvas",x_coor, y_coor,level);
			}
		}
		
		/*$carLocCntrlMap.searchCntrlList("all");*/
		setTimeout('$locInfoMap.searchlocInfoList()',1000);
		
		$("#searchWord").on('keypress', function(e) {
			if (e.which == 13) {
				$carLocCntrlMap.searchCntrlList($("#searchTarget").val());
			}
		});
	});

	$locInfoMap = {
			map : {
				ui :  $locmap.ui,
				ajax : $locmap.ajax,
				event : $locmap.event,
				util : $locmap.util
			},
			
			
			dirXArray : new Array(),
			dirYArray : new Array(),
			intervalMove : null,
			searchWord : null,
			searchLocation : null, //검색 위치
			searchTarget : null,//검색 대상 : 차량/이동보조원/사용자
			type : null,
			
			/**
			 * @name : searchCntrList
			 * @description : 검색결과에 대한 리스트 반환
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param status,result
			 */
			searchlocInfoList : function(type){
				
				$locInfoMap.map.ui.removeAllMarker();
				$locInfoMap.map.ui.removeAllOverLay();
				//무조건 검색 애는 1페이지로
				var searchWord = $("#searchWord").val();
				var searchLocation = $("#searchLocation").val(); //검색 위치
				var searchTarget = $("#searchTarget").val(); //검색 대상 : 차량/이동보조원/사용자
				var callObject = this;
				
				
				
				if(searchTarget == "all"){
					if(type == "all"){
						var liList = $("#selectResultTabs").find("li");
						for(var i = 0; i < liList.length; i++){
							if(liList.hasClass("on")){
								type = liList.attr("id");
							}
						}
					}
				}else{
					type = searchTarget;
				}
				
				$locInfoMap.searchWord = searchWord;
				$locInfoMap.searchLocation = searchLocation;
				$locInfoMap.searchTarget = searchTarget;
				$locInfoMap.type = type;
				
				var indexPage = "1";
				var pageShowRow = "5";
				this.map.ajax.searchlocInfoList(callObject,type,searchWord,searchTarget,searchLocation,indexPage,pageShowRow);
			},
			
			
			/**
			 * @name : searchCntrIndexList
			 * @description : 검색결과에 대한 리스트 반환
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param status,result
			 */
			searchCntrIndexList : function(type,index){
				$locInfoMap.map.ui.removeAllMarker();
				$locInfoMap.map.ui.removeAllOverLay();
				
				var searchWord = $locInfoMap.searchWord;
				var searchLocation = $locInfoMap.searchLocation; //검색 위치
				var searchTarget = $locInfoMap.searchTarget; //검색 대상 : 차량/이동보조원/사용자
				var callObject = this;
				
				if(searchTarget == "all"){
					if(type == "all"){
						var liList = $("#selectResultTabs").find("li");
						for(var i = 0; i < liList.length; i++){
							if(liList.hasClass("on")){
								type = liList.attr("id");
							}
						}
					}
				}else{
					type = searchTarget;
				}
				
		
				
				var indexPage = index;
				var pageShowRow = "5";
				this.map.ajax.searchlocInfoList(callObject,type,searchWord,searchTarget,searchLocation,indexPage,pageShowRow);
			},
			
			
			
			/**
			 * @name : gridSearchResult
			 * @description : 검색해온결과값 그리기
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param status,result
			 */
			gridSearchResult : function(result,allCount,currentPageIndex,type,markerMap){
				$("#resultText").text("검색결과 : 총 " +allCount +"건");
				$("#resultText").show();
				
				var indexPlus = (currentPageIndex-1) * 5;
				var html = "";
				
				html ="<tr>"+
							"<th>운전자</th>"+
							"<th>차량번호</th>"+
							"<th>운행여부</th>"+
							"<th>상세보기</th>"+
						"</tr>";
							
				for(var i = 0; i < result.length; i++){
					//리스트 정보
					html +="<tr><td>" +result[i].NAME+"</td>" +
								"<td>" + result[i].CAR_NO+"</td>"
								+"<td>" +"대기"+"</td>" 
								+ "<td><a href='javascript:$locInfoMap.map.ui.moveCenter("+result[i].X+","+result[i].Y+","+Number(Number(indexPlus) + Number(i))+");' class='btn04'>위치관제</a></td>"+
							"</tr>";
					
				}
				
				
				//마커정보 생성
				//markerMap
				//carList
				//supportList
				//userList
				var carList = markerMap.carList;
				var supportList = markerMap.supportList;
				var userList = markerMap.userList;
				
				$locInfoMap.gridCarList(carList);
				
				
				$("#gpsTable").html(html);
				if(result.length >0){
					$locInfoMap.map.ui.moveCenter(result[0].X,result[0].Y,indexPlus);
				}
				
				var pageSize = 5;
				var totalPage = Math.ceil(allCount/pageSize);
				$("#pageList").paging({
					current : currentPageIndex,
					max : totalPage,
					itemClass : 'page',
					itemCurrent : 'current',
					format : "{0}",
					next : '&gt;',
					prev : '&lt;',
					first : '&lt;&lt;',
					last : '&gt;&gt;',
					onclick : function(e, page) { // 페이지 선택 시
						
						$locInfoMap.searchCntrIndexList(type,page);
					}	
				});
			},
			
			
			/**
			 * @name : gridCarList
			 * @description : 자동차 마커 그리기
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param carList
			 */
			gridCarList : function(carList){
				
				for(var i =0; i < carList.length; i++){
					//오버레이 정보 뺌
					var popUpHtml = "";
					
					popUpHtml += '<div class="infoLayer" style="z-index:20000;top:-300px;left:-190px;">';
					popUpHtml += 	'<div class="rela">';
					popUpHtml += 		'<p class="subj">운행차량 정보('+carList[i].CAR_NO+')</p>';
					popUpHtml += 		'<a href="javascript:void(0)" class="btnClose" onclick="$locInfoMap.map.ui.closeOverLay('+i+');"><img src="/img/ico/ico_close01.png" /></a>';
					popUpHtml += 		'<table class="layerTable">';
					popUpHtml += 			'<colgroup>';
					popUpHtml += 				'<col width="60" />';
					popUpHtml += 				'<col width="" />';
					popUpHtml += 				'<col width="60" />';
					popUpHtml += 				'<col width="" />';
					popUpHtml += 				'</colgroup>';
					popUpHtml += 				'<tr>';
					popUpHtml += 					'<th>차량위치</th>';
					popUpHtml += 					'<td colspan="3"><input type="text" name="" value="'+carList[i].FULLNAME+'"class="inp" readOnly /></td>';
					popUpHtml += 				'</tr>';
					popUpHtml += 				'<tr>';
					popUpHtml += 					'<th>위치좌표</th>';
					popUpHtml += 					'<td><input type="text" name="" class="inp" value="'+carList[i].X+'" readOnly /></td>';
					popUpHtml += 					'<th>&nbsp;</th>';
					popUpHtml += 					'<td><input type="text" name="" class="inp" value="'+carList[i].Y+'" readOnly /></td>';
					popUpHtml += 				'</tr>';
					popUpHtml += 				'<tr>';
					popUpHtml += 					'<th>자동차번호</th>';
					popUpHtml += 					'<td colspan="2"><input type="text" name="" class="inp" value="'+carList[i].CAR_NO+'" readOnly /></td>';
					popUpHtml +=					'<td><a href="javascript:$locInfoMap.moveVision(\''+carList[i].CAR_NO+'\' ,\''+i+'\');" class="btn02">실시간 관제</a></td>';
					popUpHtml += 				'</tr>';
					popUpHtml += 				'<tr>';
					popUpHtml += 					'<th>운전자명</th>';
					popUpHtml += 					'<td><input type="text" name="" class="inp" value="'+carList[i].NAME+'" readOnly /></td>';
					popUpHtml += 					'<th>연락처</th>';
					popUpHtml += 					'<td><input type="text" name="" class="inp" value="'+carList[i].PHONE+'" readOnly /></td>';
					popUpHtml += 				'</tr>';
					popUpHtml += 			'</table>';
					popUpHtml += 	'</div>';
					popUpHtml += '</div>';
					
					var iwContent = '<div style="padding:5px;">'+carList[i].CAR_NO+'</div>';
					
					//마커생성 뺌
					$locInfoMap.map.ui.createMarker(carList[i].X,carList[i].Y);
					$locInfoMap.map.ui.contentPopup(popUpHtml,i);
					$locInfoMap.map.ui.createInfoWindow(carList[i].X,carList[i].Y,iwContent,i);
				}
			},
			
			
			/**
			 * @name : moveCenter
			 * @description : 해당 좌표로 이동
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param x_coor,y_coor
			 */
			moveCenter : function(x_coor,y_coor,idx){
				$locInfoMap.map.ui.moveCenter(x_coor,y_coor,idx);
			},
			
			

	}
	
}(window, document));