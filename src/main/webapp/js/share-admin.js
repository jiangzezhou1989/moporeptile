$(document).ready(function () {
 
        $('#ShareTableContainer').jtable({
            title: 'App List',
            paging: true,
            sorting: true,
			columnResizable: true, 
            defaultSorting: 'orderNum DESC',
            actions: {
                listAction: '/share/list',
                deleteAction: '/ads/delete',
                updateAction: '/share/update',
                createAction: '/share/create'
            },
            fields: {
				oId: {
                    key: true,
                    create: false,
                    edit: false,
                    list: false
		        },
                cnt: {
					title:"分享内容",
                    key: false,
					type:'textarea'
                },             
               
				
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
       $('#ShareTableContainer').jtable('load');
		
    });

