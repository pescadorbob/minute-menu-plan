<style>

h1, h2, h3, h4, h5, h6, form, p, ul, li {
    color: #000000 !important;
    list-style: none !important;
}

.winterButton ul {
    margin-bottom: 10px;
}</style>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>A Note from ${party}</h3>
            </div>
            <p>${note}</p>
        </div>
    </div>
</div>
<g:render template="/mail/compliance" model="['tag':'note']" />