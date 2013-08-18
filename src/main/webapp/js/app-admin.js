$(document).ready(function () {
 
        $('#AppTableContainer').jtable({
            title: 'App List',
            paging: true,
            sorting: true,
			columnResizable: true, 
            defaultSorting: 'orderNum DESC',
            actions: {
                listAction: '/app/list',
                deleteAction: '/app/delete',
                updateAction: '/app/update',
                createAction: '/app/create'
            },
            fields: {
				oId: {
                    key: true,
                    create: false,
                    edit: false,
                    list: false
		        },
                appid: {
					title:"应用appid",
                    key: false,
					inputClass: 'validate[required]' 
                },
                name: {
                    title: '应用名称',
                    inputClass: 'validate[required]' 
                },
 				type: {
	                    title: '类型',
						inputClass: 'validate[required]',
	                    options: { '0': '美女', '1': '网游', '2': '单机',  '3': '最新'}
	                },
                icon: {
                    title: '应用图标地址',
                    inputClass: 'validate[required]',
					display:function(data) {
						if (data.record) {
							return '<img src="'+data.record.icon+'" width="64" height="64" />';
						}						
					}
                },
                size: {
                    title: 'apk大小',                 
                    inputClass: 'validate[required]' 
                },
 				apk: {
	                    title: '下载地址',                 
	                    inputClass: 'validate[required]',
						display:function(data) {
							if (data.record) {
								return '<a target="_blank" href="'+data.record.apk+'">下载测试</a>';
							}						
						}
	            },
                des: {
                    title: '描述',
                    type:'textarea',
					inputClass: 'validate[required]'                
                },
              	imgs: {
	                    title: '截图',
	                   	type:'textarea',  
						inputClass: 'validate[required]',  
				display:function(data) {
					if (data.record) {
							var ret = '<div id="gallery"><ul>';
							var imageArray =data.record.imgs.split(",");
							
							for(var i=0; i <imageArray.length; i++ ) {
								//ret +='<img src="'+imageArray[i]+'" width="32" height="32" />';
								ret+='<li>';
							    ret+='<a target="_blank" id="jietu'+i+'" href="'+imageArray[i]+'"title="Home" >';
							    ret+='<img src="'+imageArray[i]+'" width="38" height="57" />';
							    ret+="<span>截图</span>";
							    ret+="</a>";
								ret+='</li>';
							}
							ret +="</ul></di>"
							return ret;
						}	
					}              
	            },              
				orderNum: {
	                    title: '排序',
	                    defaultValue:"0",
						inputClass: 'validate[required]'   
            	}
			},
			//Initialize validation logic when a form is created
            formCreated: function (event, data) {
                data.form.validationEngine();
            },
            //Validate form when it is being submitted
            formSubmitting: function (event, data) {
                return data.form.validationEngine('validate');
            },
            //Dispose validation logic when form is closed
            formClosed: function (event, data) {
                data.form.validationEngine('hide');
                data.form.validationEngine('detach');
            },
			recordsLoaded:function(event, data){
				showText();
			},
			
        });
 	
		var ths ='<tr style="height:0;">'+
			  '<th style="width:4%"></th>'+
			  '<th style="width:8%"></th>'+
			  '<th style="width:6%"></th>'+
			  '<th style="width:8%"></th>'+
			  '<th style="width:8%"></th>'+
			  '<th style="width:8%"></th>'+
			  '<th style="width:10%"></th>'+
			  '<th style="width:30%"></th>'+
			   '<th style="width:4%"></th>'+
			  '<th style="width:2%"></th>'+
			   '<th style="width:2%"></th></tr>';
		
        // Re-load records when user click 'load records' button.
       /*
		 *  $('#LoadRecordsButton').click(function (e) {
		 *             e.preventDefault();
		 *             $('#AppTableContainer').jtable('load', {
		 *                 name: $('#name').val(),                 cityId:
		 * $('#cityId').val()             });         });           // Load all
		 * records when page is first shown
		 *         $('#LoadRecordsButton').click();
		 */
		var load = $('#AppTableContainer').jtable('load','{}',function() {
			//在tbody前面加上一行让高度有效
			prependToThread();
			showText();
		});


		function prependToThread() {
			$('#AppTableContainer thead').prepend(ths);
		}
		
		function showText() {
			$('#AppTableContainer td').each(
					function(index, element) {						
						$(element).off("mouseover").on("mouseover",function(){
							this.title=this.innerText;							
						});
					}
			);		
			
		}
    });

