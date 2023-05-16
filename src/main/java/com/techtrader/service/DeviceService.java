package com.techtrader.service;

import com.techtrader.model.Device;
import com.techtrader.repository.DeviceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final int pageSize = 12;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device getDevice(Long device_id) {
        return deviceRepository.findById(device_id).orElseThrow(() -> new NoSuchElementException("device: " + device_id + " doesn't exist!"));
    }

    public List<Device> searchDeviceByPage(int page) {
        return deviceRepository.findAll(PageRequest.of(page, pageSize)).toList();
    }

    public List<Device> searchByKeywords(String keyword, int page) {
        return deviceRepository.findAllByNameContainingOrDeviceTypeContainingOrSpecsContaining(keyword, keyword, keyword, PageRequest.of(page, pageSize));
    }


    public List<Device> searchByPriceBetween(int page, double min, double max) {
        return deviceRepository.findAllByPriceBetweenOrderByPrice(min, max, PageRequest.of(page, pageSize));
    }

    public List<Device> getDevicesByType(String type, int page) {
        return deviceRepository.findAllByType(type, PageRequest.of(page, pageSize)).orElseThrow();
    }
}
