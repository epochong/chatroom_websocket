<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="gb2312" %>
<!DOCTYPE html>
<html>


<!-- Mirrored from admindesigns.com/demos/absolute/1.1/admin_forms-validation.html by HTTrack Website Copier/3.x [XR&CO'2014], Thu, 06 Aug 2015 02:56:15 GMT -->
<head>
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">

    <title> 欢迎使用智能客服系统 </title>

    <link rel="stylesheet" type="text/css" href="/assets/skin/default_skin/css/theme.css">
    <link rel="stylesheet" type="text/css" href="/assets/admin-tools/admin-forms/css/admin-forms.css">
    <link rel="shortcut icon" href="/assets/img/favicon.ico">
</head>
<script language="javascript">
    window.onload=function() {
        if (document.getElementById("faqValid").value == 1) {
            document.getElementById("cValid").checked = true;
        }
    }

    function bindCheckBoxToInput(){
        var check = document.getElementById("cValid").checked;
        if (check) {
            document.getElementById("faqValid").value = 1;
        } else {
            document.getElementById("faqValid").value = 0;
        }
    }

    function searchFaq(){
        window.location.href = "/robot/search?keyWords=" + document.getElementById("keywords").value;
    }
</script>

<body class="admin-validation-page" data-spy="scroll" data-target="#nav-spy" data-offset="200">
<div id="main">
    <section id="content_wrapper">
