package com.ruoyi.xdata.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xdata.mapper.XdataModelMapper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.xdata.domain.XdataModel;
import com.ruoyi.xdata.service.IXdataModelService;

/**
 * 模型Service业务层处理
 * 
 * @author yepanpan
 * @date 2020-12-29
 */
@Service
public class XdataModelServiceImpl implements IXdataModelService 
{
    @Autowired
    private XdataModelMapper xdataModelMapper;

    /**
     * 查询模型
     * 
     * @param id 模型ID
     * @return 模型
     */
    @Override
    public XdataModel selectXdataModelById(Long id)
    {
        return xdataModelMapper.selectXdataModelById(id);
    }

    /**
     * 查询模型列表
     * 
     * @param xdataModel 模型
     * @return 模型
     */
    @Override
    public List<XdataModel> selectXdataModelList(XdataModel xdataModel)
    {
        return xdataModelMapper.selectXdataModelList(xdataModel);
    }

    /**
     * 新增模型
     * 
     * @param xdataModel 模型
     * @return 结果
     */
    @Override
    public int insertXdataModel(XdataModel xdataModel)
    {
    	
        return xdataModelMapper.insertXdataModel(xdataModel);
    }

    /**
     * 修改模型
     * 
     * @param xdataModel 模型
     * @return 结果
     */
    @Override
    public int updateXdataModel(XdataModel xdataModel)
    {
        return xdataModelMapper.updateXdataModel(xdataModel);
    }

    /**
     * 批量删除模型
     * 
     * @param ids 需要删除的模型ID
     * @return 结果
     */
    @Override
    public int deleteXdataModelByIds(Long[] ids)
    {
        return xdataModelMapper.deleteXdataModelByIds(ids);
    }

    /**
     * 删除模型信息
     * 
     * @param id 模型ID
     * @return 结果
     */
    @Override
    public int deleteXdataModelById(Long id)
    {
        return xdataModelMapper.deleteXdataModelById(id);
    }
    

    /**
     * 查询模型数据
     * 
     * @param xdataModel 模型
     * @return 模型集合
     */
    @Override
    public PageInfo<Map> selectXdataModelData(XdataModel xdataModel){
    	XdataModel model = xdataModelMapper.selectXdataModelById(xdataModel.getId());
    	if(model == null) {
    		throw new CustomException("非法请求");
    	}
    	model.setParams(xdataModel.getParams());
		model.setSearchValue(xdataModel.getSearchValue());
		List<Map> list = xdataModelMapper.selectXdataModelData(model);
		PageInfo<Map> page = new PageInfo(list);
		page.setTotal(xdataModelMapper.countXdataModelData(model));
		return page;
    }
    
    /**
     * 修改模型数据
     * 
     * @param xdataModel 模型， id, params{id,field,value}
     * @return 结果
     */
    @Override
    public int updateXdataModelData(XdataModel xdataModel) {
    	XdataModel model = xdataModelMapper.selectXdataModelById(xdataModel.getId());
    	if(model != null) {
    		model.setParams(xdataModel.getParams());
    		return xdataModelMapper.updateXdataModelData(model);
    	}
    	return 0;
    }
}
