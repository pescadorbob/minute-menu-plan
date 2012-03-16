var crossImagePath
function setCrossImagePath(deleteImagePath) {
    crossImagePath = deleteImagePath
}
var weeks
var mealCosts = []

$(function() {
    weeks = jQuery(".menuContainer")
    setTimeout("bindSortableToWeeks()", 100);
    setTimeout("bindHoverAndClick()", 200);
    setTimeout("bindTotals()", 300);
//    setTimeout("bindSortableToSearchItems()", 250)
})
function totalMenu(intMenuIndex,menuItem){
    mealCosts[intMenuIndex] = 0
    $(menuItem).find(".meal-item").each(
        function(intItemIndex,mealItem){
            $(mealItem).find("p.item-cost").each(
                function(intItemCostIndex,itemCost){
                    //multiply by 1 to turn it into a number
                    var costString = itemCost.innerHTML.trim()
                    if(costString.indexOf('$')==0) costString = costString.substring(1);
                    var cost = costString*1
                    mealCosts[intMenuIndex] += cost
                })
        })
    $(menuItem).find("span.day-total").text("$" + mealCosts[intMenuIndex])
}
function bindTotals() {
    jQuery(".menuContainer").each(
        function(intMenuIndex,menuItem){
            totalMenu(intMenuIndex,menuItem)
        })
}
function bindTotals1() {
    jQuery(".menuContainer").each(
        function(intMenuIndex,menuItem){
            totalMenu(intMenuIndex,menuItem)
        })
}
function bindHoverAndClick() {
    jQuery(".menuContainer>div").unbind();
    jQuery(".menuContainer>div .deleteImage").unbind();

    jQuery(".menuContainer>div").hover(function() {
        jQuery("img", jQuery(this)).css("display", "block");
    }, function() {
        jQuery("img", jQuery(this)).css("display", "none");
    })

    jQuery(".menuContainer>div .deleteImage").click(function() {
        jQuery(this).parent().remove();
        setTimeout("bindTotals1()", 900);
        
    })
}

function bindSortableToWeeks() {
    var counter=0
    setTimeout(bindMenuContainer, 100)
    function bindMenuContainer() {
//        console.debug("hello" + counter)
        jQuery(weeks[counter]).sortable({
            update: function(event, ui) {
                if (jQuery("span", jQuery(ui.item)).hasClass("recipe-name")) {
                    var htmlString = "<div class='meal-item' style='clear:both'><input type='hidden' value='"
                            + jQuery("input[name='menuItemId']", jQuery(ui.item)).val()
                            + "' name='mealItems." + jQuery(this).attr("rel") + "'> <img src='" +
                     crossImagePath + "' alt='' style='display:none;' align='left' class='deleteImage'><span>"
                     if(jQuery("span.price",jQuery(ui.item)).text()!=""){
                         htmlString += "<p class='item-ellipsis'>"
                     } else {
                         htmlString += "<p class='item-ellipsis-full'>"
                     }
                     htmlString += jQuery("span.recipe-name", jQuery(ui.item)).text() + "</p>"
                     if(jQuery("span.price",jQuery(ui.item)).text()!=""){
                         htmlString += "<p class='item-cost'>"
                         var priceString = jQuery("span.price", jQuery(ui.item)).text()
                         if(priceString.indexOf('$')==0) priceString = priceString.substring(1)
                        htmlString += priceString + "</p>" ;
                     }
                     htmlString += "</span></div>"
                    jQuery(ui.item).remove();
                    jQuery(this).children().last().before(htmlString);
                    var total = jQuery(this).find('div.day-total').remove();

                    jQuery(this).children().last().before(total);
                    jQuery(this).children().last().remove();
                    jQuery(this).append('<div class="farji" style="display:none;clear:both"/>');
                    bindHoverAndClick();
                } else {
                    jQuery(ui.item).find("input").attr("name", "mealItems." + jQuery(this).attr("rel"))
                }
                jQuery('.farji', jQuery(this)).hide()
            },
            over:function(event, ui) {
                jQuery(ui.sender).css("overflow", "visible")
                jQuery(this).css("overflow", "visible")
                jQuery(this).addClass("myHover")
                jQuery(this).css("background-color", "#EEEEEE")
            },
            out:function(event, ui) {
                jQuery(this).removeClass("myHover")
                jQuery(this).css("overflow", "hidden")
                jQuery(this).css("background-color", "")
            },
            stop:function(event, ui) {
                jQuery(".menuContainer").css("overflow", "hidden")
            },
            opacity:0.6,
            tolerance: 'pointer',
            helper:'clone',
            cursorAt: {top: 15,left: 5},
            revert: true,
            scrollSensitivity: 40,
            connectWith: '.menuContainer',
            forcePlaceholderSize:true,
            placeholder:"ui-state-highlight"
        });

        counter=counter+1
        if(counter<42){
            setTimeout(bindMenuContainer, 100)            
        }
    }

}



function bindSortableToSearchItems() {
    jQuery(".resultContainer").sortable({
        over:function(event, ui) {
            if (jQuery(ui.helper).hasClass('draggableItem')) {
                ui.helper = jQuery(ui.helper)
                        .css("width", "auto")
                        .css("height", "auto")
                        .removeClass('draggableItem')
                        .html(jQuery("span.recipe-name", jQuery(ui.helper)).text())
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
            jQuery(ui.item).remove()
        },
        opacity:0.6,
        tolerance: 'pointer',
        helper: 'clone',
        cursorAt: {top: 15,left: 5},
        revert: true,
        scrollSensitivity: 40 ,
        connectWith: '.menuContainer',
        zIndex:10001,
        forcePlaceholderSize:true,
        placeholder:"ui-state-highlight"
    });
}