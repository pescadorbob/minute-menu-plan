<%@ page import="com.mp.domain.*" %>
<html>
<head>
    <meta name="layout" content="printRecipelayout"/>
    <title>Show Shopping List</title>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>Shopping List for: ${shoppingList?.name}</h3>
            </div>

            <div class="clearfix">
                <g:each in="${shoppingList?.weeklyShoppingLists}" var="weeklyShoppingList" status="outer_i">
                    <div class="winter-week clearfix" style="width:406px;">
                        <div class="winterButton"><strong>Week ${weeklyShoppingList?.weekIndex + 1}</strong><p></p></div>
                        <ul>
                            <g:each in="${weeklyShoppingList?.products}" var="product" status="i">
                                <li class="${(i % 2 == 1) ? 'alternatecolor' : ''}">
                                    <ul>
                                        <li class="email">${product?.item}</li>
                                        <li>${product?.quantity}
                                        </li>
                                    </ul>
                                </li>
                            </g:each>
                          <g:each in="${weeklyShoppingList?.groceries}" var="item" status="j">
                            <li class="${(j % 2 == 1) ? 'alternatecolor' : ''}">
                              <ul>
                                <li class="email">${item}</li>
                                <li>&nbsp;</li>
                              </ul>
                            </li>
                          </g:each>
                        </ul>
                    </div>
                </g:each>
                
            </div>
            
        </div>
    </div>
</div>
<script type="text/javascript">
    window.onload = print()
  </script>
</body>
</html>