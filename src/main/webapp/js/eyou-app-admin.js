$(document).ready(function () {
 
        $('#EyouAppTableContainer').jtable({
            title: 'E游首页部署列表',
            paging: true,
            sorting: true,
			columnResizable: true, 
            defaultSorting: 'orderNum DESC',
            actions: {
                listAction: '/eyou/admin/list',
                deleteAction: '/eyou/admin/delete',
                updateAction: '/eyou/admin/update',
                createAction: '/eyou/admin/create'
            },
			ajaxSettings: {
			    type: 'POST',
			    dataType: 'json'
			},
            fields: {
				oId: {
                    key: true,
                    create: false,
                    edit: false,
                    list: false
		        },
                id: {
					title:"应用appid",
                    key: false, 
					inputClass: 'validate[required]'                 
                },
                title: {
                    title: '应用名称',
					inputClass: 'validate[required]'               
                },
 				type: {
	                    title: '类型',			
	                    options: { '1': 'mrp', '2': 'apk', '3': '网址'}
	                },
                icon: {
                    title: '应用图标地址',
                    width: '8%',
					display:function(data) {
						if (data.record) {
							return '<img src="'+data.record.icon+'" width="64" height="64" />';
						}						
					},
					inputClass: 'validate[required]'
                },              
 				url: {
	                    title: '下载地址',
						display:function(data) {
							if (data.record) {
								return '<a target="_blank" href="'+data.record.apk+'">下载测试</a>';
							}						
						},
						inputClass: 'validate[required]'
						
	            },
				pkg: {
		                title: '包名',	
						inputClass: 'validate[required]'                                    
		        },
                des: {
                    title: '描述',
                    type:'textarea',
					inputClass: 'validate[required]'                
                },              
				orderNum: {
	                    title: '排序',
						inputClass: 'validate[required]',
						defaultValue:"0",               
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
			'<th style="width:6%"></th>'+
			'<th style="width:10%"></th>'+
			'<th style="width:8%"></th>'+
			'<th style="width:6%"></th>'+			 
			'<th style="width:8%"></th>'+
			'<th style="width:8%"></th>'+
			'<th style="width:20%"></th>'+
			'<th style="width:4%"></th>'+
			 '<th style="width:4%"></th>'+
			'<th style="width:2%"></th>'+
			 '</tr>';
				
        // Re-load records when user click 'load records' button.
       /*
		 *  $('#LoadRecordsButton').click(function (e) {
		 *             e.preventDefault();
		 *             $('#EyouAppTableContainer').jtable('load', {
		 *                 name: $('#name').val(),                 cityId:
		 * $('#cityId').val()             });         });           // Load all
		 * records when page is first shown
		 *         $('#LoadRecordsButton').click();
		 */
		var load = $('#EyouAppTableContainer').jtable('load','{}',function() {
			// 在tbody前面加上一行让高度有效
			prependToThread();
			showText();
		});


		function prependToThread() {
			$('#EyouAppTableContainer thead').prepend(ths);
		}
		
		function showText() {
			$('#EyouAppTableContainer td').each(
					function(index, element) {						
						$(element).off("mouseover").on("mouseover",function(){
							this.title=this.innerText;							
						});
					}
			);		
			
		}
    });

