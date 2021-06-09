/**
 * daum 맵 사용을 위한 js
 */
(function(W, D) {
	/*
	 * <script type="text/javascript"
	 * src="//apis.daum.net/maps/maps3.js?apikey=dd4800ee3b6b6d2739e6e689a5f47370"></script>
	 * <script src="http://maps.google.com/maps/api/js?sensor=true&language=ko"></script>
	 */

	W.$map = W.$map || {};

	$(document).ready(function() {
		/*document.write('<script type="text/javascript" src="//apis.daum.net/maps/maps3.js?apikey=dd4800ee3b6b6d2739e6e689a5f47370&libraries=services"></script>');*/
	});

	$map.ui = {
		mapList : new Array(),
		mapDispArea : null,
		markerList : new Array(),
		overLayList : new Array(),
		infoWindowList : new Array(),
		
		/**
		 * @name : drawMap
		 * @description : 특정 DIV 영역에 daumMap 생성
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param element : 그려질 영역 x_coor : MAP의 x좌표 y_coor : MAP의 Y좌표 level : 줌 레벨
		 */
		drawMap : function(element, x_coor, y_coor, level) {
			var container =document.getElementById(element);
			var options = {
				center : new daum.maps.LatLng(x_coor, y_coor),
				level : level
			};
			this.mapDispArea = new daum.maps.Map(container, options);
		},
		
		/**
		 * @name : createInfoWindow
		 * @description : 머커의 InfoWindow 생성
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param x_coor,y_coor,iwContent,idx
		 */
		createInfoWindow : function(x_coor,y_coor,iwContent,idx){
			var infowindow = new daum.maps.InfoWindow({
				position : new daum.maps.LatLng(x_coor,y_coor),
				content : iwContent
			});
			
			this.infoWindowList.push(infowindow);
			daum.maps.event.addListener(this.markerList[idx],'mouseover',function(){
				$map.ui.infoWindowList[idx].open($map.ui.mapDispArea,$map.ui.markerList[idx]);
			});
			
			daum.maps.event.addListener(this.markerList[idx],'mouseout',function(){
				$map.ui.infoWindowList[idx].close();
			});
			
			
		},
		
		
		/**
		 * @name : createMarker
		 * @description : 해당지역 Marker 생성
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param x_coor,y_coor
		 */
		createMarker : function(x_coor,y_coor){
			var markerPosition  = new daum.maps.LatLng(x_coor, y_coor); 
			var marker = new daum.maps.Marker({
				position : markerPosition,
			});
			marker.setMap(this.mapDispArea)
			this.markerList.push(marker);
		},
		
		/**
		 * @name : contentPopup
		 * @description : 닫기가 가능한 커스텀 오버레이
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param popUp,markerIdx
		 */
		contentPopup : function(popUp,markerIdx){
			var overlay = new daum.maps.CustomOverlay({
				content : popUp,
				map : this.mapDispArea,
				position : this.markerList[markerIdx].getPosition()
			});
			this.overLayList.push(overlay);
			
			daum.maps.event.addListener(this.markerList[markerIdx],'click',function(){
				$map.ui.overLayList[markerIdx].setMap($map.ui.mapDispArea);
			});
		},
		
		
		/**
		 * @name : closeOverlay
		 * @description : 커스텀 오버레이 닫기
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param markerIdx
		 */
		closeOverLay : function(markerIdx){
			this.overLayList[markerIdx].setMap(null);
		},
		
		/**
		 * @name : closeAllOverlay
		 * @description : 커스텀 오버레이 닫기
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param
		 */
		closeAllOverlay : function(){
			for(var i =0; i < this.overLayList.length; i++){
				this.overLayList[i].setMap(null);
			}
		},
		
		/**
		 * @name : removeAllMarker
		 * @description : 마커제거
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param 
		 */
		removeAllMarker : function(){
			for(var i =0; i < this.markerList.length; i++){
				this.markerList[i].setMap(null);
			}
			this.markerList = new Array();
		},
		
		/**
		 * @name : removeAllOverLay
		 * @description : 마커제거
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param 
		 */
		removeAllOverLay : function(){
			for(var i =0; i < this.overLayList.length; i++){
				this.overLayList[i].setMap(null);
			}
			this.overLayList = new Array();
		},
		
		/**
		 * @name : moveCenter
		 * @description : center로 이동
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param x_coor,y_coor,idx
		 */
		moveCenter : function(x_coor,y_coor,idx){
			
			for(var i =0; i < this.overLayList.length; i++){
				this.overLayList[i].setMap(null);
			}
			
			var moveLatLon = new daum.maps.LatLng(x_coor,y_coor);
			this.mapDispArea.setCenter(moveLatLon);
			this.overLayList[Number(idx)].setMap(this.mapDispArea);
			
		},
		
		
		
		/**
		 * @name : reset
		 * @description : Map 초기화
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param element :
		 *            그려질 영역
		 */
		reset : function() {

		}

	};

	$map.event = {

	};
	
	$map.util = {
			geocoder : new daum.maps.services.Geocoder(),
			
			/**
			 * @name : coord2addr
			 * @description : 좌표로 주소 가져오기
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param x_coor,y_coor,objCallback
			 */
			coord2addr : function(x_coor,y_coor,objCallback){
				// 주소-좌표 변환 객체를 생성합니다
				var latlng = new daum.maps.LatLng(x_coor, y_coor);
				this.geocoder.coord2addr(latlng,objCallback);
			},
			
			/**
			 * @name : coord2detailaddr
			 * @description : 좌표로 상세 주소 가져오기
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param addr , objCallback
			 */
			coord2detailaddr : function(x_coor,y_coor,objCallback){
				var coord = new daum.maps.LatLng(x_coor,y_coor);
				this.geocoder.coord2detailaddr(coord, objCallback);
			},
			
	
			/**
			 * @name : addr2coord
			 * @description : 주소로 좌표 가져오기
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param addr , objCallback
			 */
			addr2coord : function(addr,objCallback){
				this.geocoder.addr2coord(addr,objCallback);
			},
			
			
			/**
			 * @name : addr2code
			 * @description : 주소로 좌표 가져오기
			 * @date : 2017. 02. 15
			 * @author : 최재영
			 * @param addr
			 */
			addr2code : function(addr){
				return this.geocoder.addr2coord(addr,function(status,result){
					if(status == "OK"){
						var latlng = new daum.maps.LatLng(result.addr[0].lat,result.addr[0].lng);
						var resultAddr = $map.util.geocoder.coord2addr(latlng,function(status,result){
								if(status == "OK"){
									return result[0].code;
								}
						});
					}
				});
			},
			
	};
	
	$map.ajax = {
		// 구글 경로 찾기 참고
		// https://maps.googleapis.com/maps/api/directions/json?origin=Boston,MA&destination=Concord,MA&waypoints=Charlestown,MA|Lexington,MA&key=YOUR_API_KEY
		// startFrom : 시작 지점
		// endTo : 도착 지점
		// mode : 이용 방법 TRANSIT 대중교통 , DRIVING : 운정경로 , BICYCLE : 자전거, WALKING :
		// 도보

		/**
		 * @name : searchRoute
		 * @description : 경로 정보 가져오기
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param startFrom :
		 *            시작지점 endTo : 도착지점 type : 이용 교통수단 element : 결과 값을 그려줄
		 *            element
		 */
		searchRoute : function(startFrom, endTo, type, element) {
			var tscpSearchRoute = new tscp.service.searchRoute.api();
			tscpSearchRoute.addParam("startFrom", startFrom);
			tscpSearchRoute.addParam("endTo", endTo);
			tscpSearchRoute.addParam("type", type);

			tscpSearchRoute.request({
				method : "POST",
				url : "",
				dataType : "json",
				options : {
					element : element
				}
			});
		},

		/**
		 * @name : 주소 검색 값
		 * @description : 주소 검색 리스트 가져오기
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param addrName :
		 *            검색할 주소 element : 검색결과를 그릴 element
		 */
		searchAddress : function(addrName, element) {
			var tscpSearchAddr = new tscp.service.searchAddr.api();
			tscpSearchAddr.addParam("addrName");

			tscpSearchAddr.request({
				method : "POST",
				url : "",
				dataType : "json",
				options : {
					element : element
				}
			});
		},
		
		/**
		 * @name : searchWordList
		 * @description : 차량 / 이동보조원 / 이동약자 위치관제 검색
		 * @date : 2017. 02. 15
		 * @author : 최재영
		 * @param callObject,searchWord,searchTarget/searchLocation
		 */
		searchCntrlList : function(callObject,type,searchWord,searchTarget,searchLocation,indexPage,pageShowRow){
			
			var util = $map.util;
			//주소 코드값을 이용하여 xy 값을 가져온후 그 안에 있는 코드 값을 이용한다.
			
			var tscpSearchCntrlList = new tscp.service.searchWordList.api();
			if(searchWord != null && searchWord !=""){
				tscpSearchCntrlList.addParam("searchWord",searchWord);
			}
			
			tscpSearchCntrlList.addParam("searchTarget",searchTarget);
			tscpSearchCntrlList.addParam("searchLocation",searchLocation);
			tscpSearchCntrlList.addParam("type",type);
			tscpSearchCntrlList.addParam("indexPage",indexPage);
			tscpSearchCntrlList.addParam("pageShowRow",pageShowRow);
			tscpSearchCntrlList.request({
				method : "POST",
				url : contextPath + "/ServiceAPI/cntrl/searchWordList.json",
				async : true,
				options : {
					callObject : callObject
				}
			});
			
		},
	
	};

	/*********** 경로검색 ********* */
	(function() {
		$class("tscp.service.searchRoute.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") {

				} else if (res.errCd == "-401") {

				} else {

				}
			},
			onFail : function(status) {
			}
		});
	}());
	/** ********* 경로검색 ********* */

	/** ********* 주소 검색 ********* */
	(function() {
		$class("tscp.service.searchAddress.api").extend($d.api.ajaxAPI).define(
				{
					onSuccess : function(status, res, options) {
						if (res.errCd == "0") {

						} else if (res.errCd == "-401") {

						} else {

						}
					},
					onFail : function(status) {
					}
				});
	}());
	/** ********* 주소 검색 ********* */
	
	/** ********* 차량 / 이동보조원 / 이동약자 위치관제 검색 ********* */
	(function() {
		$class("tscp.service.searchWordList.api").extend($d.api.ajaxAPI).define(
				{
					onSuccess : function(status, res, options) {
						if (res.errCd == "0") {
							options.callObject.gridSearchResult(res.result.list,res.result.allCount,res.result.currentPageIndex,res.result.type,res.result.markerMap);
						} else if (res.errCd == "-401") {

						} else {

						}
					},
					onFail : function(status) {
					}
				});
	}());
	/** ********* 차량 / 이동보조원 / 이동약자 위치관제 검색 ********* */
	
	

}(window, document));