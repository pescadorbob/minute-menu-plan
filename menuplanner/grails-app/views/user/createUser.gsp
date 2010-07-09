<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="layout" content="menu"/>
    <title>Add User</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'user.css')}"/>

    <style type="text/css" media="screen">
    body {
        font-family: 'MyriadProRegular', 'PalatinoRegular', 'Arial';
    }
    </style>
</head>
<body>
<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>One Page Checkout</h3>
            </div>
            <div class="top-shadow">
                <label>&nbsp;</label>
            </div>
            <div class="leftbox clearfix">
                <h2>One Page Checkout</h2>
                <div class="clearfix"><br/>
                    The Minute Menu plan is an onlnie software service.Your credit card will be charged as per
                    the subscription period chose and indicated below.<br/>
                    <br/>
                </div>
                <div id="leftpanelbox">
                    <h1>Your subscription</h1>
                    <div id="subscription">
                        <ul>
                            <li class="head">
                                <ul>
                                    <li class="bigcluman">Subscription</li>
                                    <li>Price</li>
                                    <li>Total</li>
                                </ul>
                            </li>
                            <li class="alternatecolor clearfix">
                                <ul>
                                    <li class="bigcluman">Yearly - 3/2/2010</li>
                                    <li>$50.00</li>
                                    <li>$50.00</li>
                                </ul>
                            </li>
                            <li>
                                <ul>
                                    <li class="bigcluman">Yearly - 3/2/2010</li>
                                    <li>$50.00</li>
                                    <li>$50.00</li>
                                </ul>
                            </li>
                        </ul>
                    </div>

                    <div class="boxDiv">
                        <h1>Profile Information</h1>
                        <ul>
                            <li>
                                <input name="" type="radio" value=""/>
                                Link your account with your facebook account</li>
                            <li>&nbsp; &nbsp;               <input type="button" value="F -Connect"/>
                                <br/>
                            </li>
                            <li>
                                <input name="" type="radio" value=""/>
                                Create a new account. (You can link it later)</li>
                        </ul>
                    </div>
                    <div class="boxDiv">
                        <ul>
                            <li>
                                <label>First Name</label>
                                <span>
                                    <input name="firstName" type="text" class="inpbox "/>
                                </span>
                            </li>
                            <li>
                                <label>Last Name</label>
                                <span>
                                    <input name="lastName" type="text" class="inpbox "/>
                                </span>
                            </li>
                            <li>
                                <label>City</label>
                                <span>
                                    <input name="city" type="text" class="inpbox "/>
                                </span>
                            </li>
                            <li>
                                <label>Email</label>
                                <span>
                                    <input name="email" type="text" class="inpbox "/>
                                </span>
                            </li>
                            <li>
                                <label>Password</label>
                                <span>
                                    <input name="password" type="text" class="inpbox "/>
                                </span>
                            </li>
                            <li>
                                <label>Confirm Password</label>
                                <span>
                                    <input name="confirmPassword" type="text" class="inpbox "/>
                                </span>
                            </li>
                            <li>
                            </li>
                        </ul>
                    </div>
                </div>
                <g:render template="/user/googleCheckout"/>
                <div class="bottom-shadow">
                    <label>&nbsp;</label>
                </div>
            </div>
        </div>
        <!--end wrapper start footer -->
        <div id="footer"></div>
    </div>
</div>
</body>
</html>
