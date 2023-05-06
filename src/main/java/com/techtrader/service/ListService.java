package com.techtrader.service;

import com.techtrader.helper.ListedStatus;
import com.techtrader.model.Device;
import com.techtrader.model.Listing;
import com.techtrader.model.Trader;
import com.techtrader.repository.DeviceRepository;
import com.techtrader.repository.ListingRepository;
import com.techtrader.repository.TraderRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ListService {
    private final ListingRepository listingRepository;
    private final TraderRepository traderRepository;
    private final DeviceRepository deviceRepository;

    public ListService(ListingRepository listingRepository, TraderRepository traderRepository, DeviceRepository deviceRepository) {
        this.listingRepository = listingRepository;
        this.traderRepository = traderRepository;
        this.deviceRepository = deviceRepository;
    }

    public String listDevice(Principal authentication, Device device) {
        Trader trader = traderRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NoSuchElementException("Trader: " + authentication.getName() + " doesn't exist"));

        try {
            deviceRepository.save(device);
            listingRepository.save(Listing
                    .builder()
                    .trader(trader)
                    .device(device)
                    .listedStatus(ListedStatus.AVAILABLE)
                    .build());
            return "success";
        } catch (Exception e) {
            return "error cold not list device";
        }
    }

    public String updateDevice(Principal principal, Device device) {
        try {
            Trader trader = traderRepository.findByUsername(principal.getName()).orElseThrow();
            Optional<Listing> listing = listingRepository.findByTraderIdAndDeviceId(trader.getId(), device.getId());
            if (listing.isPresent()) {

                deviceRepository.save(device);
                return "success";
            } else {
                return "Error, device doesn't belong to trader";
            }

        } catch (NoSuchElementException e) {
            return "device with this id: " + device.getId() + " doesn't exist";
        }

    }


    public String deleteDevice(Principal principal, Long id) {
        try {
            Trader trader = traderRepository.findByUsername(principal.getName()).orElseThrow();
            Optional<Listing> listing = listingRepository.findByTraderIdAndDeviceId(trader.getId(), id);
            if (listing.isPresent()) {
                deviceRepository.deleteById(id);
                return "success";
            } else {
                return "Error, device doesn't belong to trader";
            }

        } catch (NoSuchElementException e) {
            return "device with this id: " + id + " doesn't exist";
        }

    }

    public List<Listing> getListings(Principal principal) {
        return listingRepository.findAllByTraderUsername(principal.getName()).orElseThrow(() -> new NoSuchElementException("User: " + principal.getName() + " has no listings"));
    }
}
