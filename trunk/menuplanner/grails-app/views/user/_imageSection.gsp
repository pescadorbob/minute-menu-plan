<div id="leftpanel">
    <div id="photo">
      <img id='userImage' border='0' width='180' height="180" src="${g.createLink(controller: 'image', action: 'imageByPath', params: [imagePath: userCO?.selectUserImagePath, noImage:'photo-pic.png'])}"/>
    </div>
    <input type="hidden" name="selectUserImagePath" id="selectUserImagePath" value="${userCO?.selectUserImagePath}"/>

    <g:render template="/recipe/imageUpload" model="[selectorName:'selectUserImage']"/>

    %{--<ul>--}%
    %{--<li><a href="#">Upload New Photo</a> <a href="#">Remove Photo</a></li>--}%
    %{--<li>Member since March 2010</li>--}%
    %{--<li>--}%
    %{--<h3>Contributed Recipes</h3>--}%
    %{--</li>--}%
    %{--<li><a href="#">Beef & broccoll</a></li>--}%
    %{--<li><a href="#">Lamb Curry</a></li>--}%
    %{--<li><a href="#">Turkey Pie</a></li>--}%
    %{--<li>--}%
    %{--<h3>Favorites</h3>--}%
    %{--</li>--}%
    %{--<li><a href="#">Beef & broccoll</a> <a href="#">remove</a></li>--}%
    %{--<li><a href="#">Lamb Curry</a> <a href="#">remove</a></li>--}%
    %{--<li><a href="#">Turkey Pie</a> <a href="#">remove</a></li>--}%
    %{--</ul>--}%
</div>
