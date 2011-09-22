<%@ page import="com.mp.analytics.TestInterval; com.mp.analytics.AppRequest" %>
<table class="gant">
<tr>
	<th >name</th>
	<th >begin</th>
	<th >duration</th>
	<th >
		<table style="width: 200px;">
		<tr>
			<td colspan="2" style="text-align: center; width:120px;">graph</td>
		</tr>
		<tr>
			<td style="text-align: left; width: 50%;">in</td>
			<td style="text-align: right;width: 50%;">out</td>
		</tr>
		</table>
	</th>
    <th>details</th>
</tr>
  <%
    AppRequest r = appRequest
    def intervals = r.calls.sort{a,b ->
      def inTimeCompare = a.inTime <=> b.inTime
      if(inTimeCompare!=0) return inTimeCompare
      b.total <=> a.total
    }
    TestInterval maxInterval = intervals.max {it.total}

  %>
<g:each in="${intervals}" status="i" var="interval">
<tr>
	<td>${interval.name}</td>
	<td>${interval.inTime - maxInterval.inTime }</td>
	<td>${interval.total}</td>
                                   <%
                                     def empty = 100.0d*(double)(interval.inTime-maxInterval.inTime)/(double)maxInterval.total
                                     def fill = 100.0d*(double)interval.total/(double)maxInterval.total 
                                   %>
	<td style="border: solid 1px gray;">
      <div class="gantt" >
      <span class="empty" style="width:${empty}%"></span>
      <span class="fill" style="width:${fill}%"></span>
        </div>
	</td>
    <td>
      <g:link controller="call" action="show" id="${interval.id}">${interval?.details}</g:link>
    </td>
</tr>
  </g:each>
</table>