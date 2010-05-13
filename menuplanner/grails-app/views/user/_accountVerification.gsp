Hi ${user.name}<br/><br/>

Thank you for registering at Minute Menu Plan. Your account is created and must be activated before you can use it.
To activate the account click on the following link:<br/><br/>

<g:link action="verify" controller="user" absolute="true" params="[token: token]">Confirm Email</g:link>
<br/><br/>

After activation you may login to <g:link action="list" controller="recipe" absolute="true"
    params="[token: token]">${createLink(action: 'list', controller: 'recipe', absolute: true)}</g:link>
using the following username and password:<br/><br/>

Username: ${user.email}<br/>
Password: ${user.password}<br/><br/>

-- Admin


