package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.entity.bo.ClientBo;
import com.dsdl.eidea.base.entity.po.ClientPo;
import com.dsdl.eidea.base.service.ClientService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/12 17:38 9:48.
 */
@Service
public class ClientServiceImpl implements ClientService {
    @DataAccess(entity = ClientPo.class)
    private CommonDao<ClientPo, Integer> clientDao;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ClientBo> getClientList(ISearch search) {
        List<ClientPo> clientPoList = clientDao.search(search);
        return modelMapper.map(clientPoList, new TypeToken<List<ClientBo>>() {
        }.getType());
    }

    public boolean findExistClient(String no) {
        Search search = new Search();
        search.addFilterEqual("no", no);
        List<ClientPo> clientPoList = clientDao.search(search);
        if (clientPoList.size() > 0) {
            return true;
        }
        return false;
    }

    public ClientBo getClientBo(Integer id) {
        ClientPo clientPo = clientDao.find(id);
        return modelMapper.map(clientPo, ClientBo.class);
    }

    public void save(ClientBo clientBo) {
        ClientPo clientPo = modelMapper.map(clientBo, ClientPo.class);
        clientDao.save(clientPo);
        clientBo.setId(clientPo.getId());
    }

    public void deletes(Integer[] ids) {
        clientDao.removeByIds(ids);
    }

    public List<ClientBo> getClientListForActivated() {
        Search search = new Search();
        search.addFilterEqual("isactive", "Y");
        return modelMapper.map(clientDao.search(search), new TypeToken<List<ClientBo>>() {
        }.getType());
    }
}
