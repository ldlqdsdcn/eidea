package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.web.annotation.PrivilegesControl;
import com.dsdl.eidea.base.web.def.ReturnType;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.def.RelOperDef;
import com.dsdl.eidea.core.def.SearchDataTypeDef;
import com.dsdl.eidea.core.def.SearchPageFieldInputType;
import com.dsdl.eidea.core.def.SearchPageType;
import com.dsdl.eidea.core.entity.bo.KeyValue;
import com.dsdl.eidea.core.entity.bo.SearchBo;
import com.dsdl.eidea.core.entity.bo.SearchColumnBo;
import com.dsdl.eidea.core.service.SearchService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.dsdl.eidea.util.StringUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.googlecode.genericdao.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/17 13:41.
 */
@Controller
@RequestMapping("/core/search")
public class SearchController {
    private static final String URI = "core_search";
    @Autowired
    private SearchService searchService;

    @PrivilegesControl(operator = OperatorDef.VIEW, returnType = ReturnType.JSP)
    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/core/search/search");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<List<SearchBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        List<SearchBo> searchBoList = searchService.findList(search);
        return ApiResult.success(searchBoList);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<SearchBo> get(Integer id) {
        SearchBo searchBo = null;
        if (id == null) {
            searchBo = new SearchBo();
            searchBo.setIsactive("N");
        } else {
            searchBo = searchService.getSearchBo(id);
            List<SearchColumnBo> searchColumnBoLists = searchBo.getSearchColumnBoList();
            for (SearchColumnBo searchColumnBo : searchColumnBoLists) {
                mapperRelOperator(searchColumnBo);
            }
        }
        return ApiResult.success(searchBo);
    }

    @PrivilegesControl(operator = {OperatorDef.ADD, OperatorDef.UPDATE})
    @RequestMapping(value = "/addOneColumn", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<SearchColumnBo> addNewColumn() {
        SearchColumnBo searchColumnBo = new SearchColumnBo();
        mapperRelOperator(searchColumnBo);
        return ApiResult.success(searchColumnBo);
    }

    private void mapperRelOperator(SearchColumnBo searchColumnBo) {
        List<KeyValue> keyValueList = new ArrayList<>();

        String relOper = searchColumnBo.getRelOper();
        int[] ids = searchService.getRelOpersForOperStr(relOper);
        RelOperDef[] relOperDefs = RelOperDef.values();
        for (RelOperDef relOperDef : relOperDefs) {
            KeyValue keyValue = new KeyValue();
            keyValue.setKey(relOperDef.getKey());
            keyValue.setDesc(relOperDef.getDesc());
            for (int id : ids) {
                if (id == relOperDef.getKey()) {
                    keyValue.setChecked(true);
                    break;
                }
            }
            keyValueList.add(keyValue);
        }
        searchColumnBo.setRelOperList(keyValueList);
    }

    /**
     * @param searchBo
     * @return
     */
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.ADD)
    public ApiResult<SearchBo> saveForCreated(@RequestBody @Validated SearchBo searchBo) {
        searchBo = searchService.saveSearchBo(searchBo);
        return get(searchBo.getId());
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.UPDATE)
    public ApiResult<SearchBo> saveForUpdated(@RequestBody @Validated SearchBo searchBo,HttpSession session) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        if(searchBo.getId()==null){
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),resource.getMessage("common.primary_key.isempty"));
        }
        searchBo = searchService.saveSearchBo(searchBo);
        return get(searchBo.getId());
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.DELETE)
    public ApiResult<List<SearchBo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        if (ids == null || ids.length == 0) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        searchService.deleteSearches(ids);
        return list(session);
    }

    @RequestMapping(value = "/getSelectList", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<String> getSelectList() {
        SearchPageType[] searchPageTypes = SearchPageType.values();
        JsonObject listObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (SearchPageType columnDataType : searchPageTypes) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", columnDataType.getKey());
            jsonObject.addProperty("desc", columnDataType.getDesc());
            jsonArray.add(jsonObject);
        }
        listObject.add("searchPageType", jsonArray);
        jsonArray = new JsonArray();
        for (RelOperDef relOperDef : RelOperDef.values()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", relOperDef.getKey());
            jsonObject.addProperty("desc", relOperDef.getDesc());
            jsonArray.add(jsonObject);
        }
        listObject.add("relOper", jsonArray);
        jsonArray = new JsonArray();
        for (SearchPageFieldInputType searchPageFieldInputType : SearchPageFieldInputType.values()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", searchPageFieldInputType.getKey());
            jsonObject.addProperty("desc", searchPageFieldInputType.getDesc());
            jsonArray.add(jsonObject);
        }
        listObject.add("searchPageFieldInput", jsonArray);
        jsonArray = new JsonArray();
        for (SearchDataTypeDef searchDataTypeDef : SearchDataTypeDef.values()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", searchDataTypeDef.getKey());
            jsonObject.addProperty("desc", searchDataTypeDef.getDesc());
            jsonArray.add(jsonObject);
        }
        listObject.add("searchDataType", jsonArray);
        return ApiResult.success(listObject.toString());
    }
}
