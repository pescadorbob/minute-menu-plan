var crossImagePath
function setCrossImagePath(deleteImagePath){
    crossImagePath=deleteImagePath
}

jQuery(function() {
    jQuery(".menuContainer").sortable({
        update: function(event, ui) {
            if (jQuery("h3", jQuery(ui.item)).hasClass("recipeName")) {
                var htmlString = "<div style='clear:both'><input type='hidden' value='" + jQuery("input[name='menuItemId']", jQuery(ui.item)).val() + "' name='mealItems." + jQuery(this).attr("rel") + "'> <img src='" + crossImagePath + "' alt='' style='display:none;' align='left' class='deleteImage'><span>" + jQuery("h3", jQuery(ui.item)).text() + "</span></div>"
                jQuery(ui.item).remove();
                jQuery(this).children().last().before(htmlString);
                jQuery(this).children().last().remove();
                jQuery(this).append('<div class="farji" style="display:none;clear:both"></div>');
                bindHoverAndClick();
            } else {
                jQuery(ui.item).find("input").attr("name", "mealItems." + jQuery(this).attr("rel"))
            }
            if (jQuery(this).children().last().show()[0].offsetTop > 50) {
                jQuery(this).addClass("downArrow")
            } else {
                jQuery(this).removeClass("downArrow")
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

    bindHoverAndClick();
    bindSortableToSearchItems()

    
})
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

