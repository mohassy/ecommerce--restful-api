package com.techtrader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techtrader.config.RsaKeyProperties;
import com.techtrader.helper.Condition;
import com.techtrader.helper.ListedStatus;
import com.techtrader.helper.Role;
import com.techtrader.model.Cart;
import com.techtrader.model.Device;
import com.techtrader.model.Listing;
import com.techtrader.model.Trader;
import com.techtrader.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;


@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class TechtraderApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TechtraderApiApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(TraderRepository traderRepository, DeviceRepository deviceRepository,
                                        CartRepository cartRepository, PasswordEncoder encoder,
                                        ListingRepository listingRepository, TransactionRepository transactionRepository) {
        return args -> {

            /* Create a trader*/

            Trader trader = Trader.builder()
                            .username("mohassy").email("msiadhas@gmail.com")
                            .firstName("Mohammed").lastName("Hassy").roles(List.of(Role.TRADER))
                            .password(encoder.encode("password"))
                            .build();
            traderRepository.save(trader);

            /* Create devices*/

            try{

                /* Create devices*/
                List<Device>  jsonDevices;
                ObjectMapper mapper = new ObjectMapper();
                File devicesJsonFile = new File("src/main/resources/devices.json");
                jsonDevices= mapper.readValue(devicesJsonFile, new TypeReference<List<Device>>() {});
                jsonDevices
                        .parallelStream()
                        .forEach(device ->{
                            device.setStock((int)(Math.random() * 50) + 5);
                            device.setCondition(Condition.NEW);
                            deviceRepository.save(device);
                        });


                /* Create Listings*/

                jsonDevices
                        .parallelStream()
                        .map(device -> Listing.builder()
                                .device(device)
                                .trader(trader)
                                .listedStatus(ListedStatus.AVAILABLE)
                                .build())
                        .forEach(listing -> listingRepository.save(listing));

                /* Create a Cart */

                Cart cart = Cart.builder()
                        .trader(trader)
                        .devices(Set.of(jsonDevices.get(0), jsonDevices.get(1)))
                        .build();
                cartRepository.save(cart);


            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        };
    }
}
