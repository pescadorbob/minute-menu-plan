<g:if test="${hiddenDirection}">
    <span class="directionRow">
        <span class="optionImages">
            <img class="btnDelete" src="${resource(dir: 'images', file: 'remove1.gif')}" width="11" height="13" hspace="2" vspace="2" border="0" style="cursor: pointer;"/>
            <img class="btnUp" src="${resource(dir: 'images', file: 'arw-up.gif')}" hspace="2" vspace="2" border="0" style="cursor: pointer;"/>
            <img class="btnDown" src="${resource(dir: 'images', file: 'arw-dwn.gif')}" vspace="2" hspace="2" border="0" style="cursor: pointer;"/>
            <img class="btnLeft" src="${resource(dir: 'images', file: 'arw-lft.gif')}" vspace="2" hspace="2" border="0" style="cursor: pointer;"/>
            <img class="btnRight" src="${resource(dir: 'images', file: 'arw-rit.gif')}" vspace="2" hspace="2" border="0" style="cursor: pointer;"/>
        </span>
        <span chass="hiddenDirectionField">
            <input class="D" type="hidden" value="${hiddenDirection}" name="directions"/>
        </span>
        <span class="showDirection"  style="padding-left:10px;">${hiddenDirection}</span>
        <span class="hiddenTextDirection">
            <input class='H' type="hidden" name="hiddenDirections" value="${hiddenDirection}"/>
        </span>
    </span>
</g:if>
