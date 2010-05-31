<li>
    <a href="#" class="menuplan"><span>Menu&nbsp;Plans</span></a>
    <ul><li><nobr>
        <g:each in="${menuPlans}" var="menuPlan">
            <g:link controller="menuPlan" action="show" id="${menuPlan.id}">${menuPlan.name}</g:link>
        </g:each></nobr></li>
        <li><nobr><a>Copy Menu Plan</a><g:link controller="menuPlan" action="create">Add New Menu Plan</g:link></nobr></li>
    </ul>
</li>
