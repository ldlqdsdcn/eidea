package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.bo.DirectoryBo;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by admin on 2016/12/19.
 */
public interface DirectoryService {
    List<DirectoryBo> findDirectory(Search search);
    //根据id 删除菜单
    void deleteDirectoryById(Integer[] ids);
    //保存
    void save(DirectoryBo directoryBo);
    //判断url是否存在
    boolean findExistId(Integer id);

    DirectoryBo getDirectoryBo(Integer id);
}
