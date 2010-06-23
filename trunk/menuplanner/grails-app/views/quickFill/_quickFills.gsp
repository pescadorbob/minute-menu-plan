<%@ page import="com.mp.domain.QuickFill" %>
<div id="daymenu">
    <ul>
        <li class="first">&nbsp;
        </li>
        <li>
            <ul>
                <li><a href="menuPlan.gsp#">Sunday</a></li>
                <li><a href="menuPlan.gsp#">Monday</a></li>
                <li><a href="menuPlan.gsp#">Tuesday</a></li>
                <li><a href="menuPlan.gsp#">Wednesday</a></li>
                <li><a href="menuPlan.gsp#">Thursday</a></li>
                <li><a href="menuPlan.gsp#">Friday</a></li>
                <li><a href="menuPlan.gsp#">Saturday</a></li>
            </ul>
        </li>
    </ul>
</div>
<div id="countrymenu">
  <g:if test="${params.action in ['edit','create']}">
    <ul>
      <li class="first">&nbsp;</li>
      <li>
        <ul>
          <li><g:select name="day0" from="${QuickFill.list()}" optionKey="id" optionValue="${it}" class="mySelect" noSelection="['':'-Quick Fill-']"/> </li>
          <li><g:select name="day1" from="${QuickFill.list()}" optionKey="id" optionValue="${it}" class="mySelect" noSelection="['':'-Quick Fill-']"/></li>
          <li><g:select name="day2" from="${QuickFill.list()}" optionKey="id" optionValue="${it}" class="mySelect" noSelection="['':'-Quick Fill-']"/></li>
          <li><g:select name="day3" from="${QuickFill.list()}" optionKey="id" optionValue="${it}" class="mySelect" noSelection="['':'-Quick Fill-']"/></li>
          <li><g:select name="day4" from="${QuickFill.list()}" optionKey="id" optionValue="${it}" class="mySelect" noSelection="['':'-Quick Fill-']"/></li>
          <li><g:select name="day5" from="${QuickFill.list()}" optionKey="id" optionValue="${it}" class="mySelect" noSelection="['':'-Quick Fill-']"/></li>
          <li class="last"><g:select name="day6" from="${QuickFill.list()}" optionKey="id" optionValue="${it}" class="mySelect" noSelection="['':'-Quick Fill-']"/></li>
        </ul>
      </li>

    </ul>
    <script type="text/javascript">
      $(function(){
        $(".mySelect").change(function(){
          var thisId=$(this).attr('id')
          var thisVal=$(this).val()
          $.ajax({
            url:"${createLink(controller:'quickFill',action:'getQuickFill')}",
            data:{'id':thisVal},
            dataType:"json",
            success: function(data){
              var jsonData=eval(data);
              $.each(jsonData.mealItems,function(i){
                var htmlString=''
                $.each(this.items,function(j){
                  htmlString += "<div style='clear:both'><input type='hidden' value='"
                          + this.id + "' name='mealItems." + $('li.menuContainer[rel$='+ thisId +']').eq(i).attr("rel")
                          + "'> <img src='" + crossImagePath + "' alt='' style='display:none;' align='left' class='deleteImage'><span>" + this.name + "</span></div>"
                })
                htmlString+='<div class="farji" style="display:none;clear:both"></div>'
                $('li.menuContainer[rel$='+ thisId +']').eq(i).html(htmlString)
              })
              bindHoverAndClick();
            }
          })
        })
      })
    </script>
  </g:if>
</div>
