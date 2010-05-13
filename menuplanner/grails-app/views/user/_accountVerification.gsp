Hi ${user.name}<br/><br/>

Thank you for registering at MinuteMenu. Your account is created and must be activated before you can use it.
To activate the account click on the following link or copy-paste it in your browser:<br/><br/>

<g:link action="verify" controller="user" absolute="true" params="[token: token]">${token}</g:link>
<br/><br/>

After activation you may login to <g:link action="list" controller="recipe" absolute="true"
    params="[token: token]">${createLink(action: 'list', controller: 'recipe', absolute: true)}</g:link>
using the following username and password:<br/><br/>

Username: ${user.username}<br/>
Password: ${user.password}
