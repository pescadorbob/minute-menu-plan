<script type="text/javascript">

    function removeSearchOption(fieldName) {
        jQuery('#' + fieldName + 'Row').hide();
        jQuery('[value^=' + fieldName + ']').remove();
        document.getElementById('searchForm').onsubmit();
    }
    function removeSearchOptionFromCategoryList(element, fieldName, fieldValue) {
        jQuery(element).parent().hide();
        jQuery('[value$=' + fieldValue + ']').remove();
        document.getElementById('searchForm').onsubmit();
    }

    function submitSearchForm(element, fieldName, fieldValue) {
        if (fieldName == 'subCategoriesString') {
            jQuery('[value$=' + fieldValue + ']').remove();
        } else {
            jQuery('[value^=' + fieldName + ']').remove();
        }
        var html = '<input type="hidden" name="query" value="' + fieldName + ':' + fieldValue + '" />';
        jQuery('#searchParams').append(html);
        if (fieldName == 'subCategoriesString') {
            if (element) {
                var categoryHtml = "<li><img onclick=\"removeSearchOptionFromCategoryList(this,'subCategoriesString','" + fieldValue + "')\" src=\"${resource(dir: 'images', file: 'delete-icon.jpg')}\"/>Category: &nbsp; &nbsp; &nbsp;" + fieldValue + " </li>"
                jQuery('#categoryList').append(categoryHtml)
            }
        } else {
            jQuery('#' + fieldName + 'Row').show()
            if (element) {
                jQuery('#' + fieldName + 'Row td:eq(1)').html(jQuery(element).text())
            }
        }
        document.getElementById('searchForm').onsubmit();
        return false;
    }

    function submitSearchFormBySelect() {
        var fieldValue = '*' + jQuery('[name=qSelect] :selected').text() + '*';
        var fieldName = 'subCategoriesString';
        if (fieldValue == '*(Select One)*') {
            jQuery('[value^=subCategoriesString]').remove();
            fieldName = 'name';
            fieldValue = '*';
        }
        submitSearchForm(null, fieldName, fieldValue);
    }

    function defineSearchDomainType(element, fieldName, fieldValue) {
        jQuery('#' + fieldName + 'Row').show()
        jQuery('#' + fieldName + 'Row td:eq(1)').html(jQuery(element).text())
        jQuery('input[name=searchByDomainName]').attr('value', fieldValue)
        document.getElementById('searchForm').onsubmit();
    }
    function noDomainSpecified(fieldName, defaultFieldValue) {
        jQuery('#' + fieldName + 'Row').hide()
        jQuery('input[name=searchByDomainName]').attr('value', defaultFieldValue)
        document.getElementById('searchForm').onsubmit();
    }

    function showSubCategoryDiv(divId) {
        $(".categoryMenu").hide()
        jQuery('#category' + divId).show()
    }
    function showSubCategoryDivMenuPlan(divId) {
        $(".categoryMenuPlan").hide()
        jQuery('#category' + divId).show()
    }
</script>