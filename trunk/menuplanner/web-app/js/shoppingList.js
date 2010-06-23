function attachPaginationLinkEvents() {
    showModalLoader();
    return false;
}
function showModalLoader() {
    $("#timeoutMessage").hide()
    $("#initialMessage").show()
    $("#modalLoader").show()
    setTimeout("modalAfterTimeout()", 5000)
}

function modalAfterTimeout() {
    if ($("#modalLoader:visible").length > 0) {
        $("#initialMessage").hide()
        $("#timeoutMessage").show()
        setTimeout("messageSent()", 1000)
    }
}

function messageSent() {
    jQuery('#emailShoppingList').hide()
    $("#modalLoader").hide()
}