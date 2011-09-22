<ul>
<g:each in="${pendingSubscriptions}" var="subscription">
    <li><div class="requirement"><h3>${subscription.subscribedProductOffering.name}</h3>
  <ul>
  <g:each in="${subscription.requirements}" var="contributionRequirement">
      <li><g:link controller="${contributionRequirement.requires.controllerName}"
              action="${contributionRequirement.requires.actionName}">
        ${contributionRequirement.requires.description}</g:link></li>


  </g:each>
    </ul>
  </div></li>

</g:each>
</ul>