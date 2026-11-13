package de.workshops.bookshelf;

import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/app")
public class ApplicationInformationRestController {

    private final ApplicationInformation applicationInformation;

    private final ObjectMapper objectMapper;

    private final AuthorityAuthorizationManager<Object> adminManager = AuthorityAuthorizationManager.hasRole("ADMIN");

    public ApplicationInformationRestController(ApplicationInformation applicationInformation, ObjectMapper objectMapper) {
        this.applicationInformation = applicationInformation;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/name")
    public String getName() {
        return applicationInformation.getName();
    }

    @GetMapping("/settings")
    public ObjectNode getSettings(Authentication authentication) {
        boolean isAdmin = adminManager.authorize(() -> authentication, null).isGranted();

        ObjectNode settings = objectMapper.createObjectNode();
        settings.put("admin", isAdmin);
        return settings;
    }
}
