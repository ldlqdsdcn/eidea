<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="listCtrl">
    <div class="page-title">
        <h3>
            <div class="row">
                <div class="col-lg-4"><%--表信息--%><eidea:label key="table.title"/></div>
                <div class="col-lg-8  text-right">
                    <a href="#/wizard" class="btn  btn-primary btn-sm" ng-show="canAdd"><%--新建向导--%><eidea:label key="common.button.create_wizard"/></a>
                    <%--<a href="#/edit" class="btn  btn-primary btn-sm" ng-show="canAdd">&lt;%&ndash;新建&ndash;%&gt;<eidea:label key="common.button.create"/></a>--%>
                    <button type="button" class="btn  btn-primary btn-sm" id="search_but" data-toggle="modal"
                            data-target="#searchModal"><%--查找--%><eidea:label key="common.button.search"/>
                    </button>
                    <button type="button" class="btn  btn-primary btn-sm" ng-disabled="!canDelete()"
                            ng-click="deleteRecord()" ng-show="canDel"><%--删除--%><eidea:label key="common.button.delete"/>
                    </button>
                </div>
            </div>
        </h3>
    </div>

    <div class="row-fluid">
        <div class="span12">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"
                               ng-model="delFlag"></th>
                    <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                    <th><%--name--%><eidea:label key="datadict.column.name"/></th>
                    <th><%--表名--%><eidea:label key="searchcolumn.column.tableName"/></th>
                    <th><%--Po类--%><eidea:label key="table.column.po"/></th>
                    <th><%--输出日志--%><eidea:label key="table.column_output_log"/></th>
                    <th><%--是否有效--%><eidea:label key="base.whetherEffective"/></th>
                    <th><%--remark--%><eidea:label key="base.remarks"/></th>
                    <th><%--编辑--%><eidea:label key="common.button.edit"/></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="model in currentList track by $index" ng-class-even="success">
                    <td>
                        <input type="checkbox" ng-model="model.delFlag">
                    </td>
                    <td>{{(bigCurrentPage-1)*itemsPerPage+$index+1}}</td>
                    <td>
                        {{model.name}}
                    </td>
                    <td>
                        {{model.tableName}}
                    </td>
                    <td>
                        {{model.poClass}}
                    </td>
                    <td>
                        {{model.outLog}}
                    </td>
                    <td>
                        {{model.isactive}}
                    </td>
                    <td>
                        {{model.remark}}
                    </td>
                    <td>
                        <a class="btn btn-primary btn-xs" href="#/edit?id={{model.id}}"><%--编辑--%><eidea:label key="common.button.edit"/></a>
                    </td>
                </tr>
                </tbody>
            </table>
            <ul uib-pagination boundary-links="true" total-items="bigTotalItems" ng-model="bigCurrentPage"
                max-size="maxSize" first-text="<eidea:label key="common.label.firstpage"/>" previous-text="<eidea:label key="common.label.previouspage"/>" next-text="<eidea:label key="common.label.nextpage"/>" last-text="<eidea:label key="common.label.lastpage"/>"
                class="pagination-sm" boundary-link-numbers="true" rotate="false" items-per-page="itemsPerPage"
                ng-change="pageChanged()"></ul>

        </div>
    </div>
</div>
<%--
<!-- 模态框（Modal） -->
<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    查询
                </h4>
            </div>
            <div class="modal-body">
                在这里添加一些文本
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary">
                    查找
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>--%>
