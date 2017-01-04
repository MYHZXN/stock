/**
 * 
 */

function analyse(){
    var val=$('input:radio[name="datecondition"]:checked').val();
    var seasonYear = $("#season_year").val();
    var seasonWhich = $("#season_which").val();
    var yearWhich = $("#year_which").val();
    var startdate = $("#startdate").val();
    var enddate = $("#enddate").val();
    var url="http://localhost:9090/reports/analyseReports";
    	url += "?dateCondition="+val+
    			"&seasonYear="+seasonYear+"&seasonWhich="+seasonWhich+
    			"&yearWhich="+yearWhich+
    			"&startdate="+startdate+"&enddate="+enddate;		
    

	//alert(url);
    $.ajax({
        type : "get",
        url : url,
        dataType : "json",
        data : {},
        error : function() {
        },
        success : function(data) {
        		if(data.code == "200"){
        			mytable.fnClearTable();
        			mytable.fnDestroy();
        			for (var i in data.obj) {
        				$('.dataTables-example').append(
        						'<tr>'+
        						'<td>'+data.obj[i].id+'</td>'+
        						'<td>'+data.obj[i].name+'</td>'+
        						'<td>'+data.obj[i].curstock+'</td>'+
        						'<td>'+data.obj[i].incout+'</td>'+
        						'<td>'+data.obj[i].innum+'</td>'+
        						'<td>'+data.obj[i].inworth/100+'</td>'+
        						'<td>'+data.obj[i].outcout+'</td>'+
        						'<td>'+data.obj[i].outnum+'</td>'+
        						'<td>'+data.obj[i].outworth/100+'</td>'+
        						'<td>'+(data.obj[i].outworth-data.obj[i].inworth)/100+'</td>'+
        						'</tr>');
        			}
        			$('.dataTables-example').dataTable();
        		}else{
        			layer.msg(data.msg);
        		}
          }
    });
}


function showDetails(){
	$.ajax({
        type : "get",
        url : "http://localhost:9090/reports/analyseDetails",
        dataType : "json",
        data : {},
        error : function() {
        },
        success : function(data) {
        		if(data.code == "200"){
        			for (var i in data.obj) {
        				$('.dataTables-example').append(
        						'<tr>'+
        						'<td>'+data.obj[i].id+'</td>'+
        						'<td>'+data.obj[i].date+'</td>'+
        						'<td>'+data.obj[i].type+'</td>'+
        						'<td>'+data.obj[i].goodId+'</td>'+
        						'<td>'+data.obj[i].goodName+'</td>'+
        						'<td>'+data.obj[i].num+'</td>'+
        						'<td>'+data.obj[i].worth/100+'</td>'+
        						'</tr>');
        			}
        			$('.dataTables-example').dataTable({
        				"order": [[ 1, "asc" ]]
        			});
        		}else{
        			layer.msg(data.msg);
        		}
          }
    });
}

