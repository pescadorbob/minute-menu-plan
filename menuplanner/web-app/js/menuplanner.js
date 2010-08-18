function scaleImageSize(){
          var imgH = jQuery('.scaleImageSize img').height();
          var imgW = jQuery('.scaleImageSize img').width();
          var divH = jQuery('.scaleImageSize').height();
          var divW = jQuery('.scaleImageSize').width();
          var imgRatio = imgH / imgW
          var divRatio = divH / divW
          if (imgRatio > divRatio)
          {
              jQuery('.scaleImageSize img').attr('height', divH);
              imgW = jQuery('.scaleImageSize img').width();
              var marginLeft = (divW - imgW) / 2
              jQuery('.scaleImageSize img').css('margin-left', marginLeft);
          }
          else {
              jQuery('.scaleImageSize img').attr('width', divW);
              imgH = jQuery('.scaleImageSize img').height();
              var marginTop = (divH - imgH) / 2
              jQuery('.scaleImageSize img').css('margin-top', marginTop);
          }
}
