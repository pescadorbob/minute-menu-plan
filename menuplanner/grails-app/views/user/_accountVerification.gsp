Hi ${party?.name}<br/><br/>

Thank you for registering at ${createLink(uri:'/', absolute: true)}. Your account has been created and needs to be activated before you can use it.
To activate the account click on the following link:<br/><br/>

<g:link action="verify" controller="user" absolute="true" params="[token: token]">Confirm Email</g:link>
<br/><br/>

After activation you may login to
<g:link action="list" controller="recipe" absolute="true">
    ${createLink(action: 'list', controller: 'recipe', absolute: true)}
</g:link>
<br/>
<br/>
-- Admin


<g:render template="/mail/compliance" model="['tag':'account-verification']"/>