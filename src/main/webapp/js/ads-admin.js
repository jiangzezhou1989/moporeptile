$(document).ready(function () {
 
        $('#AdsTableContainer').jtable({
            title: 'App List',
            paging: true,
            sorting: true,
			columnResizable: true, 
            defaultSorting: 'orderNum DESC',
            actions: {
                listAction: '/ads/list',
                deleteAction: '/ads/delete',
                updateAction: '/ads/update',
                createAction: '/ads/create'
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
					options:'/ads/appids',
					width:"20%"
                },                
                ad: {
                    title: '应用图标地址',
                    inputClass: 'validate[required]',
					display:function(data) {
						if (data.record) {
							return '<img src="'+data.record.ad+'" width="64" height="64" />';
						}						
					},
					width:"35%"
                },     
				orderNum: {
	                    title: '排序',
	                    defaultValue:"0",
						inputClass: 'validate[required]',
						width:"15%"
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
            }
        }); 	
       $('#AdsTableContainer').jtable('load');
		
    });

