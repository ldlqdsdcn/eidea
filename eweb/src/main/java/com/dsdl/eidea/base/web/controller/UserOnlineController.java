package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.dsdl.eidea.base.web.annotation.PrivilegesControl;
import com.dsdl.eidea.base.web.content.UserOnlineContent;
import com.dsdl.eidea.base.web.def.ReturnType;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * Created by 刘大磊 on 2017/1/3 14:28.
 */
@Controller
@RequestMapping("/base/userOnline")
public class UserOnlineController {
    private static final String URI = "sys_user";

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @PrivilegesControl(operator = OperatorDef.VIEW, returnType = ReturnType.JSP)
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/online/online");
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.VIEW)
    public ApiResult<Set<UserSessionBo>> list() {
        Set<UserSessionBo> userSessionBoSet = UserOnlineContent.getOnlineUsers();
        return ApiResult.success(userSessionBoSet);
    }

}
