/**
 * 관제 Map.js
 */


(function(W,D){
	W.$carLocCntrlMap = W.$carLocCntrlMap || {};
	
	$(document).ready(function(){
		//HTML5 map geolocation
		//기본좌표
		var x_coor = 33.450701;
		var y_coor = 126.570667;
		var level = 3;	
		
		var browser = navigator.appName;
		
		if(browser == "Netscape"){
			x_coor = 37.4950952443;
			y_coor = 127.1224273159;
			$carLocCntrlMap.map.ui.drawMap("gMapArea",x_coor, y_coor,level);			
		}else{
			if(navigator.geolocation){
				navigator.geolocation.getCurrentPosition(function(position) {
					x_coor = position.coords.latitude;
					y_coor = position.coords.longitude;
					/*x_coor = 37.4950952443;
					y_coor = 127.1224273159;*/
					
					$carLocCntrlMap.map.ui.drawMap("gMapArea",x_coor, y_coor,level);
				});
			}else{
				$carLocCntrlMap.map.ui.drawMap("gMapArea",x_coor, y_coor,level);
			}
		}
		
		/*$carLocCntrlMap.searchCntrlList("all");*/
		setTimeout('$carLocCntrlMap.searchCntrlList("all")',1000);
		
		$("#searchWord").on('keypress', function(e) {
			if (e.which == 13) {
				$carLocCntrlMap.searchCntrlList($("#searchTarget").val());
			}
		});
	});
	
	$carLocCntrlMap = {
			map : {
				ui :  $map.ui,
				ajax : $map.ajax,
				event : $map.event,
				util : $map.util
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
			searchCntrlList : function(type){
				
				$carLocCntrlMap.map.ui.removeAllMarker();
				$carLocCntrlMap.map.ui.removeAllOverLay();
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
				
				$carLocCntrlMap.searchWord = searchWord;
				$carLocCntrlMap.searchLocation = searchLocation;
				$carLocCntrlMap.searchTarget = searchTarget;
				$carLocCntrlMap.type = type;
				
				var indexPage = "1";
				var pageShowRow = "5";
				this.map.ajax.searchCntrlList(callObject,type,searchWord,searchTarget,searchLocation,indexPage,pageShowRow);
			},
			
			
			/**
			 * @name : searchCntrIndexList
			 * @description : 검색결과에 대한 리스트 반환
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param status,result
			 */
			searchCntrIndexList : function(type,index){
				$carLocCntrlMap.map.ui.removeAllMarker();
				$carLocCntrlMap.map.ui.removeAllOverLay();
				
				var searchWord = $carLocCntrlMap.searchWord;
				var searchLocation = $carLocCntrlMap.searchLocation; //검색 위치
				var searchTarget = $carLocCntrlMap.searchTarget; //검색 대상 : 차량/이동보조원/사용자
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
				this.map.ajax.searchCntrlList(callObject,type,searchWord,searchTarget,searchLocation,indexPage,pageShowRow);
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
								+ "<td><a href='javascript:$carLocCntrlMap.map.ui.moveCenter("+result[i].X+","+result[i].Y+","+Number(Number(indexPlus) + Number(i))+");' class='btn04'>위치관제</a></td>"+
							"</tr>";
					
					//오버레이 정보 뺌
					/*var popUpHtml = "";
					
					popUpHtml += '<div class="infoLayer" style="z-index:20000;top:-300px;left:-190px;">';
					popUpHtml += 	'<div class="rela">';
					popUpHtml += 		'<p class="subj">운행차량 정보('+result[i].CAR_NO+')</p>';
					popUpHtml += 		'<a href="javascript:void(0)" class="btnClose" onclick="$carLocCntrlMap.map.ui.closeOverLay('+i+');"><img src="/img/ico/ico_close01.png" /></a>';
					popUpHtml += 		'<table class="layerTable">';
					popUpHtml += 			'<colgroup>';
					popUpHtml += 				'<col width="60" />';
					popUpHtml += 				'<col width="" />';
					popUpHtml += 				'<col width="60" />';
					popUpHtml += 				'<col width="" />';
					popUpHtml += 				'</colgroup>';
					popUpHtml += 				'<tr>';
					popUpHtml += 					'<th>차량위치</th>';
					popUpHtml += 					'<td colspan="3"><input type="text" name="" value="'+result[i].FULLNAME+'"class="inp" readOnly /></td>';
					popUpHtml += 				'</tr>';
					popUpHtml += 				'<tr>';
					popUpHtml += 					'<th>위치좌표</th>';
					popUpHtml += 					'<td><input type="text" name="" class="inp" value="'+result[i].X+'" readOnly /></td>';
					popUpHtml += 					'<th>&nbsp;</th>';
					popUpHtml += 					'<td><input type="text" name="" class="inp" value="'+result[i].Y+'" readOnly /></td>';
					popUpHtml += 				'</tr>';
					popUpHtml += 				'<tr>';
					popUpHtml += 					'<th>자동차번호</th>';
					popUpHtml += 					'<td colspan="2"><input type="text" name="" class="inp" value="'+result[i].CAR_NO+'" readOnly /></td>';
					popUpHtml +=					'<td><a href="javascript:$carLocCntrlMap.moveVision(\''+result[i].CAR_NO+'\' ,\''+i+'\');" class="btn02">실시간 관제</a></td>';
					popUpHtml += 				'</tr>';
					popUpHtml += 				'<tr>';
					popUpHtml += 					'<th>운전자명</th>';
					popUpHtml += 					'<td><input type="text" name="" class="inp" value="'+result[i].NAME+'" readOnly /></td>';
					popUpHtml += 					'<th>연락처</th>';
					popUpHtml += 					'<td><input type="text" name="" class="inp" value="'+result[i].PHONE+'" readOnly /></td>';
					popUpHtml += 				'</tr>';
					popUpHtml += 			'</table>';
					popUpHtml += 	'</div>';
					popUpHtml += '</div>';*/
					//
					
					//info정보 뺌
					//var iwContent = '<div style="padding:5px;">'+result[i].CAR_NO+'</div>';
					
					//마커생성 뺌
					//$carLocCntrlMap.map.ui.createMarker(result[i].X,result[i].Y);
					//$carLocCntrlMap.map.ui.contentPopup(popUpHtml,i);
					//$carLocCntrlMap.map.ui.createInfoWindow(result[i].X,result[i].Y,iwContent,i);
				
					/*if(i==0){
						$carLocCntrlMap.map.ui.moveCenter(result[i].X,result[i].Y,i);
					}else{
						$carLocCntrlMap.map.ui.closeOverLay(i);
					}*/
				}
				
				
				//마커정보 생성
				//markerMap
				//carList
				//supportList
				//userList
				var carList = markerMap.carList;
				var supportList = markerMap.supportList;
				var userList = markerMap.userList;
				
				$carLocCntrlMap.gridCarList(carList);
				
				
				$("#gpsTable").html(html);
				if(result.length >0){
					$carLocCntrlMap.map.ui.moveCenter(result[0].X,result[0].Y,indexPlus);
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
						
						$carLocCntrlMap.searchCntrIndexList(type,page);
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
					popUpHtml += 		'<a href="javascript:void(0)" class="btnClose" onclick="$carLocCntrlMap.map.ui.closeOverLay('+i+');"><img src="/img/ico/ico_close01.png" /></a>';
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
					popUpHtml +=					'<td><a href="javascript:$carLocCntrlMap.moveVision(\''+carList[i].CAR_NO+'\' ,\''+i+'\');" class="btn02">실시간 관제</a></td>';
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
					$carLocCntrlMap.map.ui.createMarker(carList[i].X,carList[i].Y);
					$carLocCntrlMap.map.ui.contentPopup(popUpHtml,i);
					$carLocCntrlMap.map.ui.createInfoWindow(carList[i].X,carList[i].Y,iwContent,i);
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
				$carLocCntrlMap.map.ui.moveCenter(x_coor,y_coor,idx);
			},
			
			/**
			 * @name : moveVision
			 * @description : 자동차 이동
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param 특정차량 실시간 관제
			 */
			moveVision : function(carNo,idx){
				$carLocCntrlMap.dirXArray[0] =37.4888521;
				$carLocCntrlMap.dirXArray[1] =37.488123;
				$carLocCntrlMap.dirXArray[2] =37.487563;
				$carLocCntrlMap.dirXArray[3] =37.488197;
				$carLocCntrlMap.dirXArray[4] =37.489191;
				$carLocCntrlMap.dirXArray[5] =37.490391;
				$carLocCntrlMap.dirXArray[6] =37.492413;
				$carLocCntrlMap.dirXArray[7] =37.494226;
				$carLocCntrlMap.dirXArray[8] =37.495388;
				
				$carLocCntrlMap.dirYArray[0] =127.1027984;
				$carLocCntrlMap.dirYArray[1] =127.102341;
				$carLocCntrlMap.dirYArray[2] =127.102516;
				$carLocCntrlMap.dirYArray[3] =127.104637;
				$carLocCntrlMap.dirYArray[4] =127.107888;
				$carLocCntrlMap.dirYArray[5] =127.111632;
				$carLocCntrlMap.dirYArray[6] =127.118231;
				$carLocCntrlMap.dirYArray[7] =127.122096;
				$carLocCntrlMap.dirYArray[8] =127.124012;
				
				$carLocCntrlMap.map.ui.closeOverLay(idx);
				
				
				if(carNo == "02수2222"){
					$carLocCntrlMap.moveCar(idx);
					$carLocCntrlMap.intervalMove = setInterval(function(){$carLocCntrlMap.moveCar(idx);},3000);
				}
				
			},
			
			/**
			 * @name : moveCar
			 * @description : 마커 이동
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param 
			 */
			moveCar : function(idx){
				var x_coor = $carLocCntrlMap.dirXArray.shift();
				var y_coor = $carLocCntrlMap.dirYArray.shift();
				
				var markerPosition  = new daum.maps.LatLng(x_coor, y_coor); 
				var marker = new daum.maps.Marker({
					position : markerPosition,
				});
				var moveLatLon = new daum.maps.LatLng(x_coor,y_coor)
				$carLocCntrlMap.map.ui.mapDispArea.setCenter(moveLatLon);
				
				$carLocCntrlMap.map.ui.markerList[idx].setMap(null);
				$carLocCntrlMap.map.ui.markerList[idx] = marker;
				marker.setMap($carLocCntrlMap.map.ui.mapDispArea);
				if($carLocCntrlMap.dirXArray.length == 2){
					alert("5 분뒤 도착합니다.");
				}else if($carLocCntrlMap.dirXArray.length == 0){
					clearInterval($carLocCntrlMap.intervalMove);
					alert("도착 하였습니다.");
				}
				
				
			},
			
			
			/**
			 * @name : writeAddrInfo
			 * @description : xy 좌표에 대한 주소 값 배정 $carLocCntrlMap.map.util.coord2addr 컬백 함수
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param status,result
			 */
			writeAddrInfo : function(status,result){
				if(status == "OK"){
					/*result.code1;
					result.code2;
					result.code3;
					result.fullName;
					result.name0;
					result.name1;
					result.name2;
					result.name3;*/
					
				}
			},
			
			/**
			 * @name : writeAddrDetailInfo
			 * @description : xy 좌표에 대한 주소 값 배정 $carLocCntrlMap.map.util.coord2addr 컬백 함수
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param status,result
			 */
			writeAddrDetailInfo : function(status,result){
				if(status == "OK"){
					//result[0];
					//bcode : 1171010700
					
					return result[0].fullName;
				}
			},
			
			/**
			 * @name : writeCoordInfo
			 * @description : 주소로 좌표 가져오기 $carLocCntrlMap.map.util.addr2coord 컬백 함수
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param status,result
			 */
			writeCoordInfo : function(status,result){
				if(status =="OK"){
					//lat
					//lng
					//$carLocCntrlMap.map.util.coord2addr(result.addr[0].lat,result.addr[0].lng,$carLocCntrlMap.writeAddrInfo);
				}
			},

	}
	
}(window, document));