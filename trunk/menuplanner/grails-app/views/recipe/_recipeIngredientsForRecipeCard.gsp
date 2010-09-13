<g:each in="${items}" var="product" status="i">
    <g:if test="${(i<3)}"><li>${product}</li></g:if>
</g:each>