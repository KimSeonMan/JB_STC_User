/**
 * 운행 이용 현황 통계 js
 */
(function(W,D){
	
	W.$carLocUseStats = W.$carLocUseStats || {};
	
	$(document).ready(function(){
		
	});
	
	$carLocUseStats.ui = {
			
			list : new Array(),
			
			gridStatsList : function(statsList){
				var html = "<table class='listTable'><tbody>";
				html +="<tr>" +
						"<th>선택</th>" +
						"<th>운행일자</th>" +
						"<th>접수건수</th>" +
						"<th>배차건수</th>" +
						"<th>취소건수</th>" +
						"</tr>";
				for(var i = 0; i < statsList.length; i ++){
					html +="<tr>";
					var rowDate = statsList[i].SEARCHDATE;//날짜
					var allocCnt = statsList[i].allocCnt;//배차
					var cancelCnt = statsList[i].cancelCnt;//취소
					var failCnt = statsList[i].failCnt;//배차실패
					var receiptCnt =statsList[i].receiptCnt;//접수
					
					html +="<td><input type='checkbox'/></td>"
					html +="<td>"+rowDate+"</td>"
					html +="<td>"+receiptCnt+"</td>"
					html +="<td>"+allocCnt+"</td>"
					html +="<td>"+cancelCnt+"</td>"
					html +="</tr>";
				}
				html +="</tbody></table>";
				$("#listTable").html(html);
				
			},
			
			gridPaging : function(totalCount,pageSize){
				
			},
			
			//이용자현황
			showGraph1 : function(){
				$("#graph1").show();
				$carLocUseStats.ajax.getToDayUseStatList();
			},
			
			//원그래프
			gridGraph1 : function(cancelCnt,receiptCnt,allocCnt,failCnt){
				
				var total = Number(cancelCnt)+Number(receiptCnt)+Number(allocCnt)+Number(failCnt);
				
				var cancelPer = (cancelCnt/total)*100;
				var receiptPer = (receiptCnt/total)*100;
				var allocPer = (allocCnt/total)*100;
				var failPer = (failCnt/total)*100;
				
				$('#mainCharts01').highcharts({
					chart: {
						margin : [ 0, 0, 30, 0 ],
						type: 'pie'
					},
					/*colors: ['#FF0303','#030BFF', '#F6FF03','#847B72'],*/
					title: {text: ''},
					tooltip: {
						enabled : false,
						pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
					},
					legend: { verticalAlign: 'bottom', y: 20 },
					plotOptions: {
						pie: {
							allowPointSelect:false,
							cursor: 'pointer',
							dataLabels: {
								enabled: false
							},
							innerSize: 250,
							showInLegend: true
						},
						series : {
							allowPointSelect : false
						}
					},
					series: [{
						name: "이용자 현황",
						colorByPoint: true, 
						data: [{
							name: "취소:"+Math.round((cancelCnt/total)*100)+"%("+cancelCnt+"명)",
							y: cancelPer
						}, {
							name: "대기:"+Math.round((receiptCnt/total)*100)+"%("+receiptCnt+"명)",
							y: receiptPer 
						},{
							name: "배차완료:"+Math.round((allocCnt/total)*100)+"%("+allocCnt+"명)",
							y: allocPer 
						},{
							name: "배차실패:"+Math.round((failCnt/total)*100)+"%("+failCnt+"명)",
							y: failPer 
						}]
					}]
				});
			},
			
			
			//배차현황
			showGraph2 : function(){
				$("#graph2").show();
				$carLocUseStats.ajax.getToDayAllocDataList();
			},
			
			//막대그래프
			gridGraph2 : function(timeArray){
				var timeCategories = new Array();
				var totalCntArray = new Array();
				var allocCntArray = new Array();
				for(i = 0; i < timeArray.length ; i ++){
					timeCategories.push(timeArray[i].timeRange);
					totalCntArray.push(timeArray[i].totalCnt);
					allocCntArray.push(timeArray[i].allocCnt);
				}
				
				 $('#mainCharts02').highcharts({
						chart: {zoomType: 'xy'},
						colors: ['#498ed2', '#f2863c'], tooltip: { enabled: false },
						title: {text: ''},
						subtitle: {text: ''},
						xAxis: [{
							categories: timeCategories,
							crosshair: true,
							title: {
								text: ''
							}
						}],
						yAxis: [{ // Primary yAxis
							labels: { 
								enabled: false
							},
							title: {
								text: '',
								style: {
									color: Highcharts.getOptions().colors[1]
								}
							}
						}, { // Secondary yAxis
							title: {
								text: '',
								style: {
									color: Highcharts.getOptions().colors[0]
								}
							},
							labels: {
								enabled: true
							}
						}],
						tooltip: {
							shared: true
						},
						legend: { verticalAlign: 'bottom', y: 20 },
						credits: {  enabled: false },
						series: [{
							name: '운행대수',
							type: 'column',
							yAxis: 1,
							data: allocCntArray,
							tooltip: {
								valueSuffix: ' 건'
							}

						}, {
							name: '접수 건수',
							type: 'spline',
							yAxis: 1,
							data: totalCntArray,
							tooltip: {
								valueSuffix: ' 건'
							}
						}]
					}); 
			},
			
			searchUseStat : function(){
				var startDate = $("#ST_DT").val();
				var endDate = $("#EN_DT").val();
				if(startDate =="" || endDate ==""){
					alert("검색 날짜를 지정 하여 주세요.");
				}else{
					$carLocUseStats.ajax.selectUseStatsList(startDate,endDate);
				}
				
			},
			
			downLoadExcel : function(){
				var url = "/ServiceAPI/stats/statsExcelDown.download";
				var target = $("body");
				
				target.prepend("<form id='excelDownloadForm'></form>");
				target = $("#excelDownloadForm");
				target.attr("method", "post");
		        target.attr("style", "top:-3333333333px;");
		        target.attr("action", url);
		        target.append("<input type='hidden' id='startDate' name='startDate' value='"+$("#ST_DT").val()+"'>");
		        target.append("<input type='hidden' id='endDate' name='endDate' value='"+$("#EN_DT").val()+"'>");
				
		        $('#excelDownloadForm').submit();
		        $('#excelDownloadForm').remove();
		        
			}
	},
	
	
	$carLocUseStats.event = {
			
	},
	
	$carLocUseStats.ajax = {
			selectUseStatsList : function(startDate,endDate){
				var statsList = new tscp.service.selectUseStatsList.api();
				statsList.addParam("startDate",startDate);
				statsList.addParam("endDate",endDate);
				statsList.request({
					method : "POST",
					url : contextPath + "/ServiceAPI/stats/selectUseStatsList.json",
					async : true,
				});
			},
			
			
			
			//이용자현황
			getToDayUseStatList : function(){
				var useStatList = new tscp.service.getToDayUseStatList.api();
				useStatList.addParam("searchDate","2017-03-02");
				//useStatList.addParam("searchDate",$carLocUseStats.util.format(new Date(), 'yyyy-MM-dd'));
				useStatList.request({
					method : "POST",
					url : contextPath + "/ServiceAPI/stats/getToDayUseStatList.json",
					async : true,
				});
			},
			
			//일일배차
			getToDayAllocDataList : function(){
				var allocDataList = new tscp.service.getToDayAllocDataList.api();
				
				allocDataList.addParam("searchDate","2017-03-02");
				//allocDataList.addParam.addParam("searchDate",$carLocUseStats.util.format(new Date(), 'yyyy-MM-dd'));
				allocDataList.request({
					method : "POST",
					url : contextPath + "/ServiceAPI/stats/getToDayAllocDataList.json",
					async : true,
				});
				
			}
			
			
			
			
	},
	
	$carLocUseStats.util = {
			format : function date2str(x, y) {
			    var z = {
			        M: x.getMonth() + 1,
			        d: x.getDate(),
			        h: x.getHours(),
			        m: x.getMinutes(),
			        s: x.getSeconds()
			    };
			    y = y.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
			        return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-2)
			    });

			    return y.replace(/(y+)/g, function(v) {
			        return x.getFullYear().toString().slice(-v.length)
			    });
			}
	},

	
	/** *********  ********* */
	(function() {
		$class("tscp.service.selectUseStatsList.api").extend($d.api.ajaxAPI).define(
				{
					onSuccess : function(status, res, options) {
						if (res.errCd == "0") {
							$carLocUseStats.ui.list = res.result.list;
							$carLocUseStats.ui.gridStatsList(res.result.list);
						} else if (res.errCd == "-401") {

						} else {

						}
					},
					onFail : function(status) {
					}
				});
	}());
	/** *********  ********* */
	
	
	(function() {
		$class("tscp.service.getToDayUseStatList.api").extend($d.api.ajaxAPI).define(
				{
					onSuccess : function(status, res, options) {
						if (res.errCd == "0") {
							var list = res.result.list;
							var cancelCnt = 0;
							var receiptCnt = 0;
							var allocCnt = 0;
							var failCnt = 0;
							
							for(var i = 0; i < list.length; i ++){
								if(list[i].ALLOC_STAT_CD =="00"){
									//취소
									cancelCnt = list[i].CNT;
								}else if(list[i].ALLOC_STAT_CD =="10"){
									//접수
									receiptCnt = list[i].CNT;
								}else if(list[i].ALLOC_STAT_CD =="20"){
									//배차
									allocCnt = list[i].CNT;
								}else if(list[i].ALLOC_STAT_CD =="30"){
									//배차실패
									failCnt = list[i].CNT;
								}
							}
							$carLocUseStats.ui.gridGraph1(cancelCnt,receiptCnt,allocCnt,failCnt);
						} else if (res.errCd == "-401") {

						} else {

						}
					},
					onFail : function(status) {
					}
				});
	}());
	
	
	(function() {
		$class("tscp.service.getToDayAllocDataList.api").extend($d.api.ajaxAPI).define(
				{
					onSuccess : function(status, res, options) {
						if (res.errCd == "0") {
							var list = res.result.list;
							
							var timeArray = new Array();
							
							for(var i=0; i < 8; i++ ){
								var object = new Object();
								if(i == 0 ){
									object.startTime = 0;
									object.endTime = 3;
								}else{
									object.startTime = timeArray[i-1].endTime;
									object.endTime = Number(timeArray[i-1].endTime) + 3;
								}
								
								object.timeRange = object.startTime + "~" + object.endTime;  
								object.totalCnt = 0;
								object.allocCnt = 0;
								
								timeArray.push(object);
							}
							
							for(var i = 0; i < list.length; i ++){
								var searchDate = list[i].SEARCHDATE;
								
								if(searchDate < 3){
									timeArray[0].totalCnt = Number(timeArray[0].totalCnt) + Number(list[i].CNT);
									if(list[i].ALLOC_STAT_CD == "20"){
										timeArray[0].allocCnt = Number(timeArray[0].allocCnt) + Number(list[i].CNT);  
									}
								}else if(searchDate < 6){
									timeArray[1].totalCnt = Number(timeArray[1].totalCnt) + Number(list[i].CNT);
									if(list[i].ALLOC_STAT_CD == "20"){
										timeArray[1].allocCnt = Number(timeArray[1].allocCnt) + Number(list[i].CNT);  
									}
								}else if(searchDate < 9){
									timeArray[2].totalCnt = Number(timeArray[2].totalCnt) + Number(list[i].CNT);
									if(list[i].ALLOC_STAT_CD == "20"){
										timeArray[2].allocCnt = Number(timeArray[2].allocCnt) + Number(list[i].CNT);  
									}
								}else if(searchDate < 12){
									timeArray[3].totalCnt = Number(timeArray[3].totalCnt) + Number(list[i].CNT);
									if(list[i].ALLOC_STAT_CD == "20"){
										timeArray[3].allocCnt = Number(timeArray[3].allocCnt) + Number(list[i].CNT);  
									}
								}else if(searchDate < 15){
									timeArray[4].totalCnt = Number(timeArray[4].totalCnt) + Number(list[i].CNT);
									if(list[i].ALLOC_STAT_CD == "20"){
										timeArray[4].allocCnt = Number(timeArray[4].allocCnt) + Number(list[i].CNT);  
									}
								}else if(searchDate < 18){
									timeArray[5].totalCnt = Number(timeArray[5].totalCnt) + Number(list[i].CNT);
									if(list[i].ALLOC_STAT_CD == "20"){
										timeArray[5].allocCnt = Number(timeArray[5].allocCnt) + Number(list[i].CNT);  
									}
								}else if(searchDate < 21){
									timeArray[6].totalCnt = Number(timeArray[6].totalCnt) + Number(list[i].CNT);
									if(list[i].ALLOC_STAT_CD == "20"){
										timeArray[6].allocCnt = Number(timeArray[6].allocCnt) + Number(list[i].CNT);  
									}
								}else if(searchDate < 24){
									timeArray[7].totalCnt = Number(timeArray[7].totalCnt) + Number(list[i].CNT);
									if(list[i].ALLOC_STAT_CD == "20"){
										timeArray[7].allocCnt = Number(timeArray[7].allocCnt) + Number(list[i].CNT);  
									}
								}
								
							}
							
							$carLocUseStats.ui.gridGraph2(timeArray);
						} else if (res.errCd == "-401") {

						} else {

						}
					},
					onFail : function(status) {
					}
				});
	}());
	
	
	
	
	
}(window, document));