<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="robots" content="none" />
<title>E游部署后台</title>
<script src="/js/lib/jquery/jquery.min.js"></script>
<script src="/js/lib/jquery/jquery-lightBox.js"></script>
<script src="/js/lib/jquery/jquery-ui-1.8.16.custom.min.js"></script>
<script src="/js/lib/jtable/jquery.jtable.min.js"></script>
 <link href="/js/lib/jquery/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
 <link href="/js/lib/jquery/jquery.css" rel="stylesheet" type="text/css" media="screen"/>
 <link href="/js/lib/jtable/themes/lightcolor/blue/jtable.css" rel="stylesheet" type="text/css" /> 

<link href="/js/lib/jtable/validationEngine/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/lib/jtable/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="/js/lib/jtable/validationEngine/jquery.validationEngine-en.js"></script>
 
<script src="/js/eyou-app-admin.js"></script>
<script type="text/javascript">
    $(function() {
        $('#gallery a').lightBox();
    });
 </script>
  
  <style type="text/css">
         #EyouAppTableContainer tbody, .jtable tr td{      
          text-overflow:ellipsis; /* for IE */        
          -moz-text-overflow: ellipsis; /* for Firefox,mozilla */       
           overflow:hidden;       
           white-space: nowrap;       
           border:0px;       
           text-align:center       
         }  
         #EyouAppTableContainer table{
            table-layout:fixed;
         }
          /* jQuery lightBox plugin - Gallery style */
    #gallery {
       height:60px;
    }
    #gallery ul { list-style: none; overflow:hidden; }
    #gallery ul li { display: inline; float:left;  text-align:center; background:#3e3e3e;}
    #gallery ul li img {
        border: 5px solid #3e3e3e;
        border-width: 5px 5px 5px;      
    }
    #gallery ul li span { display:block; color:#ffffff; font-size:12px; }
    #gallery ul li span a { color:#ffffff;}
    #gallery ul a:hover img {
        border: 5px solid #fff;
        border-width: 5px 5px 5px;
        color: #fff;
    }
    #gallery ul a:hover { color: #fff; }
  </style>
</head>
<body>     
 
<div id="EyouAppTableContainer"></div>
    </body>
</html>