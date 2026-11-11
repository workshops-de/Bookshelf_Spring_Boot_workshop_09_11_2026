package de.workshops.bookshelf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ApplicationInformationRestController {

    private final ApplicationInformation applicationInformation;

    public ApplicationInformationRestController(ApplicationInformation applicationInformation) {
        this.applicationInformation = applicationInformation;
    }

    @GetMapping("/name")
    public String getName() {
        return applicationInformation.getName();
    }
}
