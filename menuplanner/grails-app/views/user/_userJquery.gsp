<script type="text/javascript">
    jQuery(document).ready(function() {
        /* REMOVE IMAGE: function to be executed when removeRecipeImage is Clicked... */
        jQuery('#removeRecipeImage').click(function() {
            jQuery('#userImage').attr('src', '${resource(dir: 'images', file: 'no-img.gif')}')
            jQuery('#selectUserImagePath').val("");
        })
        /* jQuery for Image Upload. */
        jQuery('#selectUserImage').uploadify({
            'uploader': "${resource(dir:'js/jquery.uploadify-v2.1.0', file:'uploadify.swf')}",
            'script': "${createLink(controller:'image', action:'uploadImage')}",
            'auto': true,
            'buttonImg': "${resource(dir:'js/jquery.uploadify-v2.1.0', file:'browser.jpg')}",
            'width': 130,
            onComplete: function(event, queId, fileObj, response, data) {
                jQuery('#selectUserImagePath').val(response);
                jQuery('#photo').html('<img id="userImage" border="0" style="text-align:center;" src="${createLink(action:'imageByPath', controller:'image')}?imagePath=' + response + '&noImage=no-img.gif"/>')
                 jQuery('.scaleImageSize img').load(function() {
                    scaleImageSize();
                });
            }
        });
    })
    function ChangePassword() {
        jQuery('.passwordSection').show()
        jQuery('input[name="password"]').val('')
        jQuery('input[name="confirmPassword"]').val('')
        jQuery('#btnChangePassword').hide()
    }
    jQuery('#chk_Coach').click(function() {
        if (jQuery('#directorsList').is(':hidden')) {
            jQuery('#directorsList').show()
        } else {
            jQuery('#directorsList').hide()
        }
    })

    jQuery(function() {
        if (jQuery('#chk_Coach').attr('checked')) {
            jQuery('#directorsList').show()
        }
    });
    jQuery('#chk_Subscriber').click(function() {
        if (jQuery('#coachesList').is(':hidden')) {
            jQuery('#coachesList').show()
        } else {
            jQuery('#coachesList').hide()
        }
    })

    jQuery(function() {
        if (jQuery('#chk_Subscriber').attr('checked')) {
            jQuery('#coachesList').show()
        }
    });

</script>