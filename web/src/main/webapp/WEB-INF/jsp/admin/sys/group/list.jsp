<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li <c:if test="${empty type}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/group">
                <i class="icon-table"></i>
                所有分组列表
            </a>
        </li>
        <c:forEach items="${types}" var="t">
        <li <c:if test="${t eq type}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/group/list/${t}">
                <i class="icon-table"></i>
                ${t.info}分组列表
            </a>
        </li>
        </c:forEach>

    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <div class="btn-group first">
                    <a class="btn btn-custom dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-file"></i>
                        新&nbsp;增
                        <i class="caret"></i>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn" href="${ctx}/admin/sys/group/create/user">
                                <i class="icon-file"></i>
                                用户组
                            </a>
                        </li>
                        <li>
                            <a class="btn" href="${ctx}/admin/sys/group/create/organization">
                                <i class="icon-file"></i>
                                组织机构组
                            </a>
                        </li>
                    </ul>
                </div>
                <a id="update" class="btn btn-update">
                    <i class="icon-edit"></i>
                    修改
                </a>
                <a class="btn btn-batch-delete">
                    <i class="icon-trash"></i>
                    删除
                </a>

                <div class="btn-group last">
                    <a class="btn btn-custom dropdown-toggle" data-toggle="dropdown">
                        <span class="icon-wrench"></span>
                        更多操作
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn status-show">
                                <span class="icon-pencil"></span>
                                选中有效
                            </a>
                        </li>
                        <li>
                            <a class="btn status-hide">
                                <span class="icon-pencil"></span>
                                选中无效
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="btn default-true"
                               data-toggle="tooltip" data-placement="right" title="只有用户组有默认">
                                <span class="icon-pencil"></span>
                                设置为默认组
                            </a>
                        </li>
                        <li>
                            <a class="btn default-false">
                                <span class="icon-pencil"></span>
                                取消默认组
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="span8">
            <%@include file="searchForm.jsp"%>
        </div>
    </div>
    <%@include file="listTable.jsp"%>

</div>

<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        var tableId = "table";
        $.btn.initChangeStatus(
                "${ctx}/admin/sys/group/changeStatus",
                tableId,
                {
                    btns : [".status-show", '.status-hide'],
                    titles : ["选中有效", "选中无效"],
                    messages : ["使选中的数据有效？", "使选中的数据无效？"],
                    status : ["true", "false"]
                }
        );

        $.btn.initChangeStatus(
                "${ctx}/admin/sys/group/changeDefaultGroup",
                tableId,
                {
                    btns : [".default-true", '.default-false'],
                    titles : ["设置为默认组", "取消默认组"],
                    messages : ["把选中的数据设置为默认组吗？<br/><span class='text-warning'>只有用户组可以设置为默认组！</span>", "把选中的数据取消默认组吗？"],
                    status : ["true", "false"]
                }
        );

    });

</script>