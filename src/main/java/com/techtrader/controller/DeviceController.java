package com.techtrader.controller;


import com.techtrader.model.Device;
import com.techtrader.service.DeviceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/{device_id}")
    public Device getDevice(@PathVariable Long device_id) {
        return deviceService.getDevice(device_id);
    }

    @GetMapping("page/{page}")
    public List<Device> getDevicesByPage(@PathVariable int page) {
        return deviceService.searchDeviceByPage(page);
    }

    @GetMapping("search/{keyword}/{page}")
    public List<Device> getSearch(@PathVariable String keyword, @PathVariable int page) {
        return deviceService.searchByKeywords(keyword, page);
    }

    @GetMapping("price_between/{page}/{min}/{max}")
    public List<Device> getByPriceBetween(@PathVariable int page, @PathVariable double min, @PathVariable double max) {
        return deviceService.searchByPriceBetween(page, min, max);
    }


}
