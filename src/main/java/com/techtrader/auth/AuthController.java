package com.techtrader.auth;
import com.techtrader.helper.User;
import com.techtrader.model.Trader;
import com.techtrader.service.TraderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // TODO: 2023-05-13 remove line 
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;
    private final TraderService traderService;


    public AuthController(TokenService tokenService, TraderService traderService) {
        this.tokenService = tokenService;
        this.traderService = traderService;
    }

    @PostMapping("/login")
    public User token(Authentication authentication){
        LOG.debug("Token requested for user: '{}'", authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted {}", token);
        Trader trader = traderService.getTrader(authentication.getName());
        return User.builder()
                .user(trader)
                .token(token)
                .build();
    }
}
