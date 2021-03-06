package com.dsdl.eidea.devs.strategy;

import com.dsdl.eidea.core.entity.bo.ColumnMetaDataBo;
import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.util.FreeMarkerHelper;
import com.dsdl.eidea.util.DateTimeHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/1/12 16:01.
 */
public class ServiceGenerateStrategy {
    private GenModelDto model;
    private TableMetaDataBo tableMetaDataBo;
    public ServiceGenerateStrategy(GenModelDto genModelDto,TableMetaDataBo tableMetaDataBo)
    {
        this.model=genModelDto;
        this.tableMetaDataBo=tableMetaDataBo;
    }

    public  void generateInterface()
    {
        Map<String,Object> root = new HashMap();
        Date date=new Date();
        String interfacepackage=model.getBasePackage()+"."+model.getModule()+".service";
        String modelpackage=model.getBasePackage()+"."+model.getModule()+".entity.po";
        String datetime= DateTimeHelper.formatDateTime(date);
        root.put("packagename", interfacepackage);
        root.put("modelpackage", modelpackage);
        root.put("datetime", datetime);
        root.put("lineList",model.getIncludeModelList());
        root.put("pkClass",tableMetaDataBo.getPkClass());
        //gc.setTime(date);
        try
        {
            root.put("modelname", model.getModelName());
            FreeMarkerHelper.getInstance().outFile("service/serviceInterface.ftl",root,this.model.getOutputPath().getAbsolutePath()+"/src/main/java/"+interfacepackage.replace(".", "/")+"/"+model.getModelName()+"Service.java");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public   void generateServiceclass()
    {
        Map root = new HashMap();
        Date date=new Date();
        String classpackage=model.getBasePackage()+"."+model.getModule()+".service.impl";
        String modelpackage=model.getBasePackage()+"."+model.getModule()+".entity.po";
        String datetime= DateTimeHelper.formatDateTime(date);
        root.put("packagename", classpackage);
        root.put("modelpackage", modelpackage);
        root.put("datetime", datetime);
        root.put("lineList",model.getIncludeModelList());
        root.put("basePackage",model.getBasePackage());
        root.put("pkClass",tableMetaDataBo.getPkClass());
        try
        {
            root.put("interfacefulldaoname", model.getBasePackage()+"."+model.getModule()+".dao."+model.getModelName()+"Dao");
            //toUpperCase
            root.put("interfacefullservicename", model.getBasePackage()+"."+model.getModule()+".service."+model.getModelName()+"Service");
            root.put("modelname", model.getModelName());
            String bgnChar=model.getModelName().substring(0,1);
            root.put("repositoryname", bgnChar.toLowerCase()+model.getModelName().substring(1)+"Dao");
            String modelname2=model.getModelName().substring(0,1).toLowerCase()+model.getModelName().substring(1);
            System.out.println("modelname2="+modelname2);
            root.put("serviceName", "@Service(\""+modelname2+"Service\")");
            FreeMarkerHelper.getInstance().outFile("service/serviceClass.ftl",root,this.model.getOutputPath().getAbsolutePath()+"/src/main/java/"+classpackage.replace(".", "/")+"/"+model.getModelName()+"ServiceImpl.java");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
