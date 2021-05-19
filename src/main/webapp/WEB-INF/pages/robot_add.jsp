<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="top.jsp"/>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 添加机器人问题回复 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <form:form action="/robot/add" modelAttribute="robot"  id="admin-form" name="addForm">
                    <div class="panel-body bg-light">
                        <div class="section-divider mt20 mb40">
                            <span> 基本信息 </span>
                        </div>
                        <div class="section" style="text-align: left; margin-bottom: 10px;">
                            <label for="id" >
                                <form:input path="id" type="hidden"/>
                                <label for="id"></label>
                            </label>
                            <div class="col-md-6">
                                <label for="faqValid" class="field prepend-icon">
                                    <form:input path="faqValid" type="hidden" id="faqValid"/>
                                    <input id="cValid" type="checkbox" onclick="bindCheckBoxToInput()"/>
                                    是否生效
                                </label>
                            </div>
                        </div>
                        <div class="section row">
                            <div class="col-md-6">
                                <label for="faq" class="field prepend-icon">
                                    <form:input path="faq" cssClass="gui-input" placeholder="问题"/>
                                    <label for="faq" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label>
                            </div>
                            <div class="col-md-6">
                                <label for="answer" class="field prepend-icon">
                                    <form:input path="answer" cssClass="gui-input" placeholder="回复" />
                                    <label for="answer" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label>
                            </div>
                        </div>
                        <div class="panel-footer text-right">
                            <button type="submit" class="button"> 保存 </button>
                            <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>