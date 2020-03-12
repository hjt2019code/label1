package com.csdn.label.controller;

import com.csdn.entity.Result;
import com.csdn.entity.StatusCode;
import com.csdn.label.pojo.Label;
import com.csdn.label.service.LabelService;
import com.csdn.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "商标模块相关的接口说明")
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

   /* @RequestMapping(method = RequestMethod.GET)
    public Result findAll()
    {
        return new Result(true, StatusCode.OK,"查询所有成功",labelService.findAll());
    }*/

    @ApiOperation("根据Id查询相关信息")
    @ApiImplicitParam(name="id",value ="商标id",required = true,paramType = "path")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id)
    {
        return new Result(true,StatusCode.OK,"查询一条信息成功",labelService.findById(id));
    }

    @ApiOperation("根据商标添加信息的接口")
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label)
    {
        labelService.add(label);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    @ApiOperation("根据ID更新商标的接口")
//    @ApiImplicitParam(name = "id",value = "商标id",required = true,paramType = "body")
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody Label label)
    {
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @ApiOperation("根据Id删除商标信息的接口")
    @ApiImplicitParam(name = "id",value = "商标id",defaultValue = "4",required = true,paramType = "path",dataType = "String")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id)
    {
        labelService.delete(id);
        return new Result(true,StatusCode.OK,"删除一条数据成功");
    }

    @ApiOperation("查询并分页的接口")
    @RequestMapping(method = RequestMethod.GET)
    public Result findAllToPage()
    {
        Page<Label> allToPage = labelService.findAllToPage();
        return new Result(true,StatusCode.OK,"查询并进行分页成功",allToPage);
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap)
    {
        List<Label> search = labelService.findSearch(searchMap);
     return new Result(true,StatusCode.OK,"模糊查询成功",search);
    }

    @ApiOperation("模糊查询并分页的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = true,dataType = "Integer",paramType = "path"),
            @ApiImplicitParam(name = "pageSize",value = "每页条数",required = true,dataType = "Integer",paramType = "path")
    })
    @RequestMapping(value = "/search/{page}/{pageSize}",method = RequestMethod.POST)
    public Result findAllByPage(@RequestBody Map searchMap,@PathVariable int page,@PathVariable int pageSize)
    {
        Page<Label> pageList = labelService.findAllByPage(searchMap, page, pageSize);
        System.out.println("getTotalElements(): " + pageList.getTotalElements() + "getContent():" + pageList.getContent());
        return new Result(true,StatusCode.OK,"按条件查询并分页成功！",
                new PageResult<>(pageList.getTotalElements(),pageList.getContent()));
    }
}
