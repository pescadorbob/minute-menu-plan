window.onload = preventRatingClickEvent;
function preventRatingClickEvent() {
    $(".rating a").click(function(e) {
        e.preventDefault()
    })
}
jQuery(document).ready(function() {
    jQuery('#showPrintInstructions').hide();
    $("#printButton").tooltip({events: {
        input: "focus mouseenter"
    },
        effect:'slide',
        sticky:true
    }).dynamic({ bottom: { direction: 'down', bounce: false } })
});

function showPrintInstruction() {
    jQuery('#showPrintInstructions').show()
    return false;
}
function showNutritionFacts(){
    jQuery('#nutri-info').show();
    jQuery('#nutritional-information').hide();
}
function hideNutritionFacts(){
    jQuery('#nutri-info').hide();
    jQuery('#nutritional-information').show();
}

