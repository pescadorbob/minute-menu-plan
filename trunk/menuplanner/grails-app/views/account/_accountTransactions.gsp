<table>
    <thead>
        <tr>

            <th>${message(code: 'accountTransaction.transactionDate.label', default: 'Date')}</th>

            <th>${message(code: 'accountTransaction.description.label', default: 'Description')}</th>

            <th>${message(code: 'accountTransaction.amount.label', default: 'Amount')}</th>

            <th>${message(code: 'accountTransaction.balance.label', default: 'Balance')}</th>

        </tr>
    </thead>
    <tbody>
    <txn:eachTransaction account="${account}" status="i" var="t" from="${from}" thru="${thru}">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td><g:link controller="accountTransaction" action="show" id="${t.id}"><g:formatDate format="yyyy-MM-dd" date="${t.transactionDate}"/></g:link></td>
        <td>${t.description}</td>
        <td class="currency"><g:formatNumber number="${t.amount}" format="\$###,##0.00" /></td>
        <td class="currency"><txn:balance account="${account}" txn="${t}" format="\$###,##0.00"/></td>
      </tr>
    </txn:eachTransaction>
    </tbody>
</table>
