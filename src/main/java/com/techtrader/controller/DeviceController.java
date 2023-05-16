package com.techtrader.controller;


import com.techtrader.model.Device;
import com.techtrader.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
@CrossOrigin // TODO: 2023-05-07  remove in production
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/{device_id}")
    public Device getDevice(@PathVariable Long device_id) {
        Device device = deviceService.getDevice(device_id);
        return device;
    }

    @GetMapping("/type/{type}/{page}")
    public List<Device> getDevicesByType(@PathVariable String type, @PathVariable int page){
        return deviceService.getDevicesByType(type, page);
    }
    @GetMapping("/page/{page}")
    public List<Device> getDevicesBy(@PathVariable int page) {
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
