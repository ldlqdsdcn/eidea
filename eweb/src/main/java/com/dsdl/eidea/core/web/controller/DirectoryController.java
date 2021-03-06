package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.DirectoryBo;
import com.dsdl.eidea.base.service.DirectoryService;
import com.dsdl.eidea.base.web.annotation.PrivilegesControl;
import com.dsdl.eidea.base.web.def.ReturnType;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by admin on 2016/12/19.
 */
@Controller
@RequestMapping("/base/directory")
public class DirectoryController {
    private static final String URI = "sys_directory";
    @Autowired
    private DirectoryService directoryService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @PrivilegesControl(operator = OperatorDef.VIEW, returnType = ReturnType.JSP)
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/directory/directory");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<List<DirectoryBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        List<DirectoryBo> directoryBoList = directoryService.findDirectory(search);
        return ApiResult.success(directoryBoList);
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.DELETE)
    public ApiResult<List<DirectoryBo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        if (ids == null) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        directoryService.deleteDirectoryById(ids);
        return list(session);
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.ADD)
    public ApiResult<DirectoryBo> saveForCreated(@RequestBody DirectoryBo directoryBo,HttpSession session) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        if (directoryBo.isCreated()) {
            if (directoryService.findExistId(directoryBo.getId())) ;
            {
                return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.connection.point"));
            }
        }
        if (directoryBo.getIsactive() == null) {
            directoryBo.setIsactive("N");
        }
        directoryService.save(directoryBo);
        return get(directoryBo.getId(),session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.UPDATE)
    public ApiResult<DirectoryBo> saveForUpdated(@RequestBody DirectoryBo directoryBo,HttpSession session) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        if(directoryBo.getId()==null){
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),resource.getMessage("common.primary_key.isempty"));
        }
        if (directoryBo.getIsactive() == null) {
            directoryBo.setIsactive("N");
        }
        directoryService.save(directoryBo);
        return get(directoryBo.getId(),session);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<DirectoryBo> get(Integer id,HttpSession session) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        DirectoryBo directoryBo = null;
        if (id == null) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),resource.getMessage("client.msg.primary_key_validation"));
        } else {
            directoryBo = directoryService.getDirectoryBo(id);
        }
        return ApiResult.success(directoryBo);

    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.ADD)
    public ApiResult<DirectoryBo> create() {
        DirectoryBo directoryBo = new DirectoryBo();
        directoryBo.setCreated(true);
        directoryBo.setIsactive("N");
        return ApiResult.success(directoryBo);
    }

}
