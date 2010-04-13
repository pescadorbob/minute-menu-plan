<%@ page import="grails.converters.JSON" %>
<script type="text/javascript">
        $(function() {


            $("#${id}").tagInput("${createLink(controller:controller,action:action)}", {
                classes: {
                    tokenList: "token-input-list-facebook",
                    token: "token-input-token-facebook",
                    tokenDelete: "token-input-delete-token-facebook",
                    selectedToken: "token-input-selected-token-facebook",
                    highlightedToken: "token-input-highlighted-token-facebook",
                    dropdown: "token-input-dropdown-facebook",
                    dropdownItem: "token-input-dropdown-item-facebook",
                    dropdownItem2: "token-input-dropdown-item2-facebook",
                    selectedDropdownItem: "token-input-selected-dropdown-item-facebook",
                    inputToken: "token-input-input-token-facebook"
                },
                tokenLimit: "${tokenLimit}" ,
                prePopulated:"${prePopulated as JSON}"
            });

        });
    </script>

<input type="text" id="${id}" class="${htmlClass}" name="${name}" disabled="disabled"/>
