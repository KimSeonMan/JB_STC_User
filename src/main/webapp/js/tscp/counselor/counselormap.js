/**
 * daum 맵 사용을 위한 js
 */
(function(W, D) {

	W.counselormap = W.$counselormap || {};

	$(document).ready(function() {
	});
		
	$counselormap.ui = {
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
				$counselormap.ui.infoWindowList[idx].open($counselormap.ui.mapDispArea,$counselormap.ui.markerList[idx]);
			});
			
			daum.maps.event.addListener(this.markerList[idx],'mouseout',function(){
				$counselormap.ui.infoWindowList[idx].close();
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
				$counselormap.ui.overLayList[markerIdx].setMap($counselormap.ui.mapDispArea);
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
		
		moveEmrgncyCenter : function(x_coor,y_coor, popUp){
			var markerPosition  = new daum.maps.LatLng(x_coor, y_coor); 
			
			var marker = new daum.maps.Marker({
				position : markerPosition,
			});
			marker.setMap(this.mapDispArea)
			
			for(var i =0; i < this.overLayList.length; i++){
				this.overLayList[i].setMap(null);
			}
			
			var moveLatLon = new daum.maps.LatLng(x_coor,y_coor);
			this.mapDispArea.setCenter(moveLatLon);
			
			var overlay = new daum.maps.CustomOverlay({
				content : popUp,
				map : this.mapDispArea,
				position : new daum.maps.LatLng(x_coor,y_coor)
			});
		
			overlay.setMap(this.mapDispArea);
			
		}
	};

	$counselormap.ajax = {

	};


}(window, document));