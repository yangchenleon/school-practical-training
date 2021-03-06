package me.maxct.asset.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.Property;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.PageDO;
import me.maxct.asset.dto.PropertyDO;
import me.maxct.asset.interceptor.AuthCheck;
import me.maxct.asset.service.PropertyService;

/**
 * @author imaxct
 * 2019-03-30 13:49
 */
@RestController
@RequestMapping("/Prop")
public class PropertyController {
    private final PropertyService propertyService;

    @AuthCheck
    @GetMapping("/get")
    public Msg getPropertyDetail(@RequestParam("id") String propertyId,
                                 HttpServletRequest request) {

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        return propertyService.getPropertyById(propertyId);
    }

    @AuthCheck
    @PostMapping("/list")
    public Msg listProperty(@RequestBody PageDO pageDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        Assert.isTrue(pageDO.getPageNo() >= 0 && pageDO.getSize() > 0, "分页参数设置错误");
        return propertyService.list(pageDO.getPageNo(), pageDO.getSize());
    }

    @AuthCheck
    @GetMapping("/getMy")
    public Msg getMyProp(HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        return propertyService.getByUserId(user.getId());
    }

    @AuthCheck
    @GetMapping("/getDep")
    public Msg getMyDepProp(HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        return propertyService.getByDepId(user.getDepId());
    }

    @AuthCheck
    @GetMapping("/getById")
    public Msg getPropertyBrief(@RequestParam("id") Long id, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        return propertyService.getPropertyById(id);
    }

    @AuthCheck
    @PostMapping("/add")
    public Msg addProperty(@RequestBody List<PropertyDO> properties, HttpServletRequest request) {
        Assert.notEmpty(properties, "参数错误");

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        List<Property> list = new ArrayList<>();
        for (PropertyDO propertyDO : properties) {
            Property property = new Property();
            property.setDepId(propertyDO.getDepId());
            property.setName(propertyDO.getName());
            list.add(property);
        }

        return propertyService.addProperty(list);
    }

    @AuthCheck
    @PostMapping("/save")
    public Msg updateProperty(@RequestBody PropertyDO propertyDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        Assert.notNull(propertyDO.getId(), "参数错误");
        Assert.isTrue((!StringUtils.isEmpty(propertyDO.getName())) || propertyDO.getDepId() != null,
            "参数错误");

        return propertyService.save(propertyDO);
    }

    @AuthCheck
    @PostMapping("/search")
    public Msg search(@RequestBody PropertyDO propertyDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        Assert.isTrue(!StringUtils.isEmpty(propertyDO.getName()), "参数错误");
        return propertyService.search(propertyDO.getName());
    }

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
}
