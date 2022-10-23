package org.itique.generator.rest.web;

import org.itique.generator.core.NameGenerator;
import org.itique.generator.rest.model.FullName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1/fullName")
public class FullNameController {

    @GetMapping("/random")
    public FullName getRandomFullName() {
        String[] plainName = generateAndSplit(25, 25);
        return new FullName(plainName[0], plainName[1]);

    }

    @GetMapping("/random/sized")
    public FullName getRandomFullNameWithLengths(@RequestParam String nameLength, @RequestParam String surnameLength) {
        String[] plainName = generateAndSplit(Integer.parseInt(nameLength), Integer.parseInt(surnameLength));
        return new FullName(plainName[0], plainName[1]);
    }

    private String[] generateAndSplit(Integer nameLength, Integer surnameLength) {
        NameGenerator nameGenerator = new NameGenerator();
        return nameGenerator
                .generateNameAndSurname(nameLength, surnameLength, false, false)
                .trim()
                .split( " ");
    }

}
