package com.dsdl.eidea.devs.strategy;

import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.util.FreeMarkerHelper;
import com.dsdl.eidea.util.DateTimeHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/1/13 15:22.
 */
public class ControllerGenerateStrategy {
    private GenModelDto model;
    private TableMetaDataBo tableMetaDataBo;

    public ControllerGenerateStrategy(GenModelDto genModelDto,TableMetaDataBo tableMetaDataBo)
    {
        this.model=genModelDto;
        this.tableMetaDataBo=tableMetaDataBo;
    }

  public void generateController()
  {
      Date date=new Date();
      Map<String,Object> root = new HashMap();
      String interfacepackage=model.getBasePackage()+"."+model.getModule()+".web.controller";
      String modelpackage=model.getBasePackage()+"."+model.getModule()+".entity.po";
      String datetime= DateTimeHelper.formatDateTime(date);
      root.put("packagename", interfacepackage);
      root.put("modelpackage", modelpackage);
      root.put("basePackage",model.getBasePackage());
      root.put("module",model.getModule());
      root.put("model",model.getModelName());
      root.put("datetime", datetime);
      root.put("lineList",model.getIncludeModelList());
      root.put("pkClass",tableMetaDataBo.getPkClass());
      root.put("memPaging",!model.isPagingByDb());
      //gc.setTime(date);
      try
      {
          FreeMarkerHelper.getInstance().outFile("controller/controller.ftl",root,this.model.getOutputPath().getAbsolutePath()+"/src/main/java/"+interfacepackage.replace(".", "/")+"/"+model.getModelName()+"Controller.java");
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
  }
}
