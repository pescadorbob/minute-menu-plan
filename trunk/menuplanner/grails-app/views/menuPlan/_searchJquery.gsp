<script type="text/javascript">

    function removeSearchOption(fieldName) {
        jQuery('#' + fieldName + 'Row').hide();
        jQuery('[value^=' + fieldName + ']').remove();
        document.getElementById('searchForm').onsubmit();
    }

    function submitSearchForm(element, fieldName, fieldValue) {
        jQuery('[value^=' + fieldName + ']').remove();
        var html = '<input type="hidden" name="q" value="' + fieldName + ':' + fieldValue + '" />';
        jQuery('#searchParams').append(html);
        jQuery('#' + fieldName + 'Row').show()
        if (element) {
            jQuery('#' + fieldName + 'Row td:eq(1)').html(jQuery(element).text())
        } else {
        }
        document.getElementById('searchForm').onsubmit();
        return false;
    }
    function submitSearchFormBySelect() {
        var fieldValue = '*' + jQuery('[name=qSelect] :selected').text() + '*';
        var fieldName = 'categoriesString';
        if (fieldValue == '*(Select One)*') {
            jQuery('[value^=categoriesString]').remove();
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
</script>