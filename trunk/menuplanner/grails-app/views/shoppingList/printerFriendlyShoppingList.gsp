<%@ page import="com.mp.domain.*" %>
<%
  int size = 16;
  String colWidth='30%'
  if(request.size.equals('small')){
    size = 16;
  } else if(request.size.equals('med')){
    size = 20;
  } else if(request.size.equals('large')){
    size = 24;
    colWidth='45%'
  }
%>
<html>
<head>
    <meta name="layout" content="printRecipelayout"/>
    <title>Show Shopping List</title>
    <style type="text/css">
    body {
        color: #000000;
        background: #ffffff;
        font-family: "Times New Roman", Times, serif;
        font-size: <%=size %>px;
      line-height: normal !important;
    }

    h1, h2, h3, h4, h5, h6, form {
        color: #000000 !important;
        font-size: <%=size %>px !important;
      line-height: normal !important;
    }
    
    strong, ul, p {
        font-size: <%=size %>px !important;
      line-height: normal !important;
        color: #000000 !important;
    }

    .alternate {
        background: #fff;
        clear: both;
    }

    div.winter-week {
      width: <%=colWidth %>;
      margin-bottom:10px;
    }
    .shopping h3{
      padding:3px;
    }
    #header {
      height:auto;
    }
      .winter-week div.aisle {
        width:100%;
        background: #eee
      }
      div.winter-week {
        border: #eee solid medium;
        margin:2px;
        vertical-align:middle;
      }
      div.winter-week strong {
        margin:5px;
      }
      
    </style>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="shopping" style="background-color:#eee">
                <h3>Shopping List for: ${shoppingList?.name}</h3>
            </div>
            <g:render template="/shoppingList/showShoppingListData" model="[shoppingList: shoppingList,size:size]"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    window.onload = print()
</script>
</body>
</html>
