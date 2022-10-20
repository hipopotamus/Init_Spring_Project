package initproject.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import initproject.global.p6spy.P6spySqlFormatConfiguration;
import initproject.global.security.authentication.Principal;
import initproject.global.security.authentication.UserAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Configuration
public class AppConfig {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatConfiguration.class.getName());
    }

    @Bean
    public AuditorAware<String> auditorProvider() {

        return new AuditorAware<String>() {

            @Override
            public Optional<String> getCurrentAuditor() {

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    return Optional.of("nonAuthentication");
                }

                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                if (principal == "anonymousUser") {
                    return Optional.of(principal.toString());
                }

                return Optional.of(((Principal) principal).getEmail());
            }
        };
    }
}
