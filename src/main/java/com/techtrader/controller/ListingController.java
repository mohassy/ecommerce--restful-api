package com.techtrader.controller;

import com.techtrader.model.Device;
import com.techtrader.model.Listing;
import com.techtrader.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/list")
@CrossOrigin(origins = "*") // TODO: 2023-05-07  remove in production
public class ListingController {

    private final ListService listService;

    @Autowired
    public ListingController(ListService listService) {
        this.listService = listService;
    }

    @GetMapping
    public List<Listing> getListed(Principal principal) {
        return listService.getListings(principal);
    }

    @PostMapping
    public String listDevice(Principal principal, @RequestBody Device device) {
        return listService.listDevice(principal, device);
    }

    @PutMapping
    public String updateDevice(Principal principal, @RequestBody Device device) {
        return listService.updateDevice(principal, device);
    }

    @DeleteMapping("/{id}")
    public String deleteDevice(Principal principal, @PathVariable Long id) {
        return listService.deleteDevice(principal, id);
    }
}
