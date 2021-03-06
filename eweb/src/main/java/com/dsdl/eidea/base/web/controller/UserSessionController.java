package com.dsdl.eidea.base.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.googlecode.genericdao.search.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.dsdl.eidea.base.service.UserSessionService;
import com.dsdl.eidea.core.entity.bo.LabelBo;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;

@Controller
@RequestMapping("/base/userSession")
public class UserSessionController {
    private static final String URI = "sys_user_session";
    @Autowired
    private UserSessionService userSessionService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/userSession/userSession");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<List<UserSessionBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        search.addSort(Sort.desc("loginDate"));
        List<UserSessionBo> userSessionList = userSessionService.getUserSessionList(search);
        return ApiResult.success(userSessionList);
    }
}
