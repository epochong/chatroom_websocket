<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="top.jsp"/>

<%--<section id="content" class="table-layout animated fadeIn">--%>
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 机器人问题列表 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel  heading-border">
                <div class="panel-menu">
                    <div class="row">
                        <div class="hidden-xs hidden-sm col-md-3" style="width: 100%; margin-bottom: 5px;">
                            <div class="btn-group" style="width: 100%">
                                <input id="keywords" class="gui-input" placeholder="请输入问题关键字" value="${keyWords}" style="width: 93%; margin-right: 3px; float: left;"/>
                                <button type="button" class="btn btn-default light" style="background: #DEDEDE;" onclick="searchFaq()">
                                    查询
                                </button>
                            </div>
                        </div>
                        <div class="hidden-xs hidden-sm col-md-3">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-refresh"></i>
                                </button>
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-trash"></i>
                                </button>
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-plus" onclick="javascript:window.location.href='/robot/to_add';"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body pn">
                    <table id="message-table" class="table admin-form theme-warning tc-checkbox-1">
                        <thead>
                        <tr class="">
                            <th class="hidden-xs">是否生效</th>
                            <th class="hidden-xs">问题</th>
                            <th class="hidden-xs">回复</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list}" var="item">
                        <tr class="message-unread">
                            <td class="hidden-xs">
                                <label class="option block mn">
                                    <c:if test="${item.faqValid==1}">
                                        <input type="checkbox" name="mobileos" value="FR" checked ='checked' disabled>
                                    </c:if>
                                    <c:if test="${item.faqValid==0}">
                                        <input type="checkbox" name="mobileos" value="FR" disabled>
                                    </c:if>
                                    <span class="checkbox mn"></span>
                                </label>
                            </td>
                            <td>${item.faq}</td>
                            <td>${item.answer}</td>
                            <td>
                                <a href="/robot/to_update?id=${item.id}">编辑</a>
                                <a href="/robot/remove?id=${item.id}">删除</a>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
<%--</section>--%>

<jsp:include page="bottom.jsp"/>