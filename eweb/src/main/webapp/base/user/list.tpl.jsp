<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div   ng-controller="listCtrl">
    <div class="page-title">
        <h3>
            <div class="row">
                <div class="col-lg-4"><%--用户信息--%><eidea:label key="menu.user"/></div>
                <div class="col-lg-8  text-right">
                    <a href="#/edit" class="btn  btn-primary btn-sm" ng-show="canAdd"><eidea:label key="common.button.create"/><%--新建--%></a>
                    <button type="button" class="btn  btn-primary btn-sm"  id="search_but" data-toggle="modal"
                        data-target="#searchModal"><eidea:label key="common.button.search"/><%--查找--%></button>
                    <button type="button" class="btn  btn-primary btn-sm" ng-click="deleteRecord()" ng-disabled="!canDelete()" ng-show="canDel"><eidea:label key="common.button.delete"/><%--删除--%></button>
                </div>
            </div>
        </h3>
    </div>


    <div class="row-fluid">
        <div class="span12">
            <table  class="table table-hover table-striped table-condensed">
                <thead>
                <tr>
                    <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"
                               ng-model="delFlag"></th>
                    <th>id</th>
                    <th><eidea:label key="loginlog.label.loginname"/></th>
                    <th><eidea:label key="user.username"/></th>
                    <th><eidea:label key="operator.column.init"/></th>
                    <th><eidea:label key="linkman.column.email"/></th>
                    <th><eidea:label key="customer.column.telephone"/></th>
                    <th><eidea:label key="base.whetherEffective"/></th>
                    <th><eidea:label key="common.button.edit"/><%--编辑--%></th>
                </tr>
                </thead>
                <tbody>

                <tr ng-repeat="user in modelList track by $index" ng-class-even="success">
                    <td>
                        <input type="checkbox" ng-model="user.delFlag">
                    </td>
                    <td>{{(bigCurrentPage-1)*itemsPerPage+$index+1}}</td>
                    <td>
                        {{user.username}}
                    </td>
                    <td>
                        {{user.name}}
                    </td>
                    <td>
                        {{user.init}}
                    </td>
                    <td>
                        {{user.email}}
                    </td>
                    <td>
                        {{user.telephone}}
                    </td>
                    <td>
                        {{user.isactive}}
                    </td>
                    <td>
                        <a class="btn btn-primary btn-xs" href="#/edit?id={{user.id}}"><eidea:label key="common.button.edit"/><%--编辑--%></a>
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
