package oving5_dockercompiler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import oving5_dockercompiler.dto.OutputDTO;
import oving5_dockercompiler.model.SourceCode;
import oving5_dockercompiler.services.CompilerService;

@RestController
@RequestMapping("/api/compiler")
@CrossOrigin(origins = "http://localhost:5173")
public class CompilerController {

    @ResponseBody
    @PostMapping("/compile-and-run-in-docker")
    public OutputDTO compile(@RequestBody SourceCode sourceCode) {

        CompilerService compilerService = new CompilerService();
        Logger logger = LoggerFactory.getLogger(CompilerController.class);

        logger.info("<!---------- START\n");

        OutputDTO outputDTO = compilerService.compile(sourceCode);

        logger.info("Compiling completed! OutputDTO: " + outputDTO.getOutput());

        logger.info("END ----------!>\n");

        return outputDTO;
    }
}
