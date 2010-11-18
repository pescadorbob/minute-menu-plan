<g:each in="${item.ingredients}" var="ingredient">
<li>
    <ul>
        <li class="first_clumon">
            %{--<input name="" type="checkbox" value=""/>--}%
        </li>
        <li class="email">${ingredient.ingredient}</li>
        <li>${ingredient.quantity}</li>
    </ul>
</li>    
</g:each>
