<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="listCtrl">
    <div class="page-title">
        <h3>
            <div class="row">
                <div class="col-lg-4"><%--查询条件维护--%><eidea:label key="search.condition"/></div>
                <div class="col-lg-8  text-right">
                    <a href="#/edit" class="btn  btn-primary btn-sm" ng-show="canAdd"><eidea:label key="common.button.create"/><%--新建--%></a>
                    <button type="button" class="btn  btn-primary btn-sm"  id="search_but" data-toggle="modal"
                            data-target="#searchModal"><eidea:label key="common.button.search"/><%--查找--%></button>
                    <button type="button" class="btn  btn-primary btn-sm" ng-disabled="!canDelete()"
                            ng-click="deleteRecord()" ng-show="canDel"><eidea:label key="common.button.delete"/><%--删除--%></button>
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
                    <th><%--名称--%><eidea:label key="datadict.column.name"/></th>
                    <th><%--查询页类型--%><eidea:label key="search.page.type"/></th>
                    <th><%--标识符--%><eidea:label key="base.identifier"/></th>
                    <th><%--是否有效--%><eidea:label key="base.whetherEffective"/></th>
                    <th>remark</th>
                    <th><eidea:label key="common.button.edit"/><%--编辑--%></th>
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
                        {{model.showTypeStr}}
                    </td>
                    <td>
                        {{model.uri}}
                    </td>
                    <td>
                        {{model.isactive}}
                    </td>
                    <td>
                        {{model.remark}}
                    </td>
                    <td>
                        <a class="btn btn-primary btn-xs" href="#/edit?id={{model.id}}"><eidea:label key="common.button.edit"/><%--编辑--%></a>
                    </td>
                </tr>
                <tr ng-show="$scope.isLoading">
                    <td colspan="9">
                        <i class='fa fa-spinner fa-pulse loading'></i>&nbsp;<eidea:message key="load.please.wait"/>
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
