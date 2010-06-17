<%@ page import="com.mp.domain.QuickFill" %>
<html>
<head>
  <meta name="layout" content="menu"/>
  <title>Admin: Quick Fill</title>
  <g:javascript library="ui.core"/>
  <g:javascript library="ui.sortable"/>
  <style type="text/css">
  .ui-sortable-placeholder {
    border: 1px dotted black;
  }

  .ui-sortable-placeholder * {
    visibility: hidden;
  }

  .mealContainer .ui-state-highlight {
    height: 20px !important;
    border: 1px dotted black !important;
    visibility: visible !important;
  }
  </style>
</head>
<body>
<div id="content-wrapper" class="clearfix">
  <div id="left-panel">
    <!--  start left-panel -->
    <div class="headbox">
      <h3>Quick Fill Admin</h3>
    </div>
    <div class="top-shadow">
      <label>&nbsp;</label>
    </div>
    <div class="leftbox clearfix">
      <div id="country-cate">Quick Fill Admin :<input type="button" value="Add New" id="addQuickFillBtn"/>
      </div>
      <g:if test="${flash.message}">
        <div style="font-size:13px;background-color:#99ccff;border:1px solid #6666ff">${flash.message}</div>
      </g:if>
      <g:render template="quickFillList"/>
      <g:form controller="quickFill" action="saveAndUpdateQuickFill" method="post">
        <div id="box-right">
          <div id="usersave"><strong>Name :</strong>
            <input type="text" class="inpbox" name="quickFillName" value="${quickFill.name}"/>
            <input type="hidden" class="inpbox" name="id" value="${quickFill.id}"/>
            &nbsp;
            <input type="submit" value="Save"/>
          </div>
          <div class="box-rating">
            <ul>
              <g:each in="${quickFill.mealItems}" var="meal">
                <li>
                  <h2>${meal.type}</h2>
                </li>
                <li class="bgcolor clearfix mealContainer" rel="${meal.type}">
                  <g:each in="${meal?.items}" var="item">
                    <div><span>${item.name}</span><a href="quickFillAdmin.gsp#">Remove</a>
                      <input type="hidden" name="meal.${meal.type}" value="${item.id}">
                    </div>
                  </g:each>
                </li>
              </g:each>
            </ul>
          </div>
          <!-- paging -->
          <!--end paging-->
        </div>
      </g:form>
    </div>
    <div class="bottom-shadow">
      <label>&nbsp;</label>
    </div>
  </div>
  <script type="text/javascript">
    jQuery(function() {
      jQuery(".mealContainer").sortable({
        update: function(event, ui) {
          if (jQuery("h3", jQuery(ui.item)).hasClass("recipeName")) {
            var htmlString = "<div><span>" + jQuery("h3", jQuery(ui.item)).text() + "</span><a href='#'>Remove</a><input type='hidden' value='" + jQuery("input[name='menuItemId']", jQuery(ui.item)).val() + "' name='meal." + jQuery(this).attr("rel") + "'></div>"
            jQuery(ui.item).after(jQuery(htmlString));
            jQuery(ui.item).remove()
            bindHoverAndClick();
          } else {
            jQuery(ui.item).find("input").attr("name", "meal." + jQuery(this).attr("rel"))
          }
        },
        over:function(event, ui) {
          jQuery(this).css("background-color", "#EEEEEE")
        },
        out:function(event, ui) {
          jQuery(this).css("background-color", "")
        },
        stop:function(event, ui) {
        },
        opacity:0.6,
        tolerance: 'pointer',
        helper:'clone',
        cursorAt: {top: 15,left: 5},
        revert: true,
        scrollSensitivity: 40,
        connectWith: '.mealContainer',
        forcePlaceholderSize:true,
        placeholder:"ui-state-highlight"
      });

      bindHoverAndClick();
      bindSortableToSearchItems()

      $("#addQuickFillBtn").click(function() {
        self.location.href = "${createLink(controller:'quickFill',action:'quickFillAdmin',params:['addNew':true])}"
        return false;
      })

    })

    function bindHoverAndClick() {
      jQuery(".mealContainer a").unbind().click(function() {
        jQuery(this).parent().remove();
        return false;
      })
    }

    function bindSortableToSearchItems() {
      jQuery(".resultContainer").sortable({
        over:function(event, ui) {
          if (jQuery(ui.helper).hasClass('draggableItem')) {
            ui.helper = jQuery(ui.helper)
                    .css("width", "auto")
                    .css("height", "auto")
                    .removeClass('draggableItem')
                    .html(jQuery("h3", jQuery(ui.helper)).text())
          }
        },
        start:function(event, ui) {
          var elemId = jQuery(ui.item).attr("id")
          var elemNo = elemId.split("_")[1]
          if (parseInt(elemNo) == 1) {
            jQuery(this).prepend(jQuery(ui.item).clone().css("opacity", 1).show())
          } else {
            var prevElemNo = parseInt(elemNo) - 1
            jQuery("#draggableSearchItem_" + prevElemNo).after(jQuery(ui.item).clone().css("opacity", 1).show())
          }
        },
        update:function(event, ui) {
          //          jQuery(ui.item).remove()
        },
        opacity:0.6,
        tolerance: 'pointer',
        helper: 'clone',
        cursorAt: {top: 15,left: 5},
        revert: true,
        scrollSensitivity: 40 ,
        connectWith: '.mealContainer',
        zIndex:10001,
        forcePlaceholderSize:true,
        placeholder:"ui-state-highlight"
      });
    }
  </script>
  <!--  end left-panel start right-panel -->

  <g:render template="/menuPlan/search" model='[categoryList:categoryList, itemTotal:itemTotal]'/>

</div>
</body>
</html>
