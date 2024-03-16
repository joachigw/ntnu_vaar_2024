package oving5_dockercompiler.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import oving5_dockercompiler.dto.OutputDTO;
import oving5_dockercompiler.model.SourceCode;

import java.io.*;
import java.util.Arrays;

@Service
public class CompilerService {
    private final Logger logger = LoggerFactory.getLogger(CompilerService.class);

    public OutputDTO compile(SourceCode sourceCode) {

        OutputDTO outputDTO = null;

        switch (sourceCode.getLanguage()) {
            case "java" -> outputDTO = compileJava(sourceCode);
            case "python" -> outputDTO = compilePython(sourceCode);
            case "c" -> outputDTO = compileC(sourceCode);
            case "cpp" -> outputDTO = compileCPP(sourceCode);
        }

        return outputDTO;
    }

    public OutputDTO compileJava(SourceCode sourceCode) {
        logger.info("Compiling Java...");

        try {
            writeCodeToFile(sourceCode.getSourceCode(), "Java.java");

            String dockerImageName = "java-docker";

            // Build Docker image and read build process log
            Process dockerBuildProcess = buildDockerImage(dockerImageName, "Java").start();

            // Read potential build errors and return error if any are present
            StringBuilder buildErrors = outputLogs(dockerBuildProcess);
            if (!buildErrors.isEmpty()) {
                logger.error("Error encountered while compiling the source code as Java. Adding error to end of output.");
                return new OutputDTO(buildErrors.toString());
            }

            // Run Docker image
            Process dockerRunProcess = runDockerImage(dockerImageName).start();

            // Read output
            StringBuilder output = outputLogs(dockerRunProcess);

            // Read potential error messages
            StringBuilder errors = errorLogs(dockerRunProcess);
            if (!errors.isEmpty()) {
                logger.error("Error encountered while running the Java class file. Adding error to end of output.");
                output.append(errors);
            }

            // Wait for process to finish and remove docker image
            dockerRunProcess.waitFor();
            removeDockerImage(dockerImageName);

            return new OutputDTO(output.toString());

        } catch (IOException | InterruptedException e) {
            logger.error("Error compiling Java: " + e.getMessage());
            return null;
        }
    }


    public OutputDTO compilePython(SourceCode sourceCode) {
        logger.info("Compiling Python...");

        try {
            String dockerImageName = "py-docker";
            String code = sourceCode.getSourceCode().replace("\"", "'");
            writeCodeToFile(code, "python.py");

            // Build Docker image
            Process dockerBuildProcess = buildDockerImage(dockerImageName, "Python").start();

            // Read potential build errors and return error if any are present
            StringBuilder buildErrors = outputLogs(dockerBuildProcess);
            if (!buildErrors.isEmpty()) {
                logger.error("Error encountered while compiling the source code as C. Adding error to end of output.");
                return new OutputDTO(buildErrors.toString());
            }

            // Run Docker image
            Process dockerProcess = runDockerImage(dockerImageName).start();

            // Read output
            StringBuilder output = outputLogs(dockerProcess);

            // Read potential error messages
            StringBuilder errors = errorLogs(dockerProcess);
            if (!errors.isEmpty()) {
                logger.error("Error encountered while compiling and running the Python source code. " +
                        "Adding error to end of output.");
                output.append(errors);
            }

            dockerProcess.waitFor();
            removeDockerImage(dockerImageName);

            return new OutputDTO(output.toString());

        } catch (IOException | InterruptedException e) {
            logger.error("Error compiling Python: " + e.getMessage());
            return null;
        }
    }


    public OutputDTO compileC(SourceCode sourceCode) {

        logger.info("Compiling C...");

        try {
            writeCodeToFile(sourceCode.getSourceCode(), "c.c");

            String dockerImageName = "c-docker";

            // Build Docker image and read build process log
            Process dockerBuildProcess = buildDockerImage(dockerImageName, "C").start();

            // Read potential build errors and return error if any are present
            StringBuilder buildErrors = outputLogs(dockerBuildProcess);
            if (!buildErrors.isEmpty()) {
                logger.error("Error encountered while compiling the source code as C. Adding error to end of output.");
                return new OutputDTO(buildErrors.toString());
            }

            // Run Docker image
            Process dockerRunProcess = runDockerImage(dockerImageName).start();

            // Read output
            StringBuilder output = outputLogs(dockerRunProcess);

            // Read potential error messages
            StringBuilder errors = errorLogs(dockerRunProcess);
            if (!errors.isEmpty()) {
                logger.error("Error encountered while compiling the C source code. Adding error to end of output.");
                output.append(errors);
            }

            // Wait for process to finish and remove docker image
            dockerRunProcess.waitFor();
            removeDockerImage(dockerImageName);

            return new OutputDTO(output.toString());

        } catch (IOException | InterruptedException e) {
            logger.error("Error compiling C: " + e.getMessage());
            return null;
        }
    }


    public OutputDTO compileCPP(SourceCode sourceCode) {
        logger.info("Compiling C++...");

        try {
            writeCodeToFile(sourceCode.getSourceCode(), "cpp.cpp");

            String dockerImageName = "cpp-docker";

            // Build Docker image and read build process log
            Process dockerBuildProcess = buildDockerImage(dockerImageName, "CPP").start();

            // Read potential build errors and return error if any are present
            StringBuilder buildErrors = outputLogs(dockerBuildProcess);
            if (!buildErrors.isEmpty()) {
                logger.error("Error encountered while compiling the source code as C++. Adding error to end of output.");
                return new OutputDTO(buildErrors.toString());
            }

            // Run Docker image
            Process dockerRunProcess = runDockerImage(dockerImageName).start();

            // Read output
            StringBuilder output = outputLogs(dockerRunProcess);

            // Read potential error messages
            StringBuilder errors = errorLogs(dockerRunProcess);
            if (!errors.isEmpty()) {
                logger.error("Error encountered while compiling the C++ source code. Adding error to end of output.");
                output.append(errors);
            }

            // Wait for process to finish and remove docker image
            dockerRunProcess.waitFor();
            removeDockerImage(dockerImageName);

            return new OutputDTO(output.toString());

        } catch (IOException | InterruptedException e) {
            logger.error("Error compiling C++: " + e.getMessage());
            return null;
        }
    }

    private void writeCodeToFile(String sourceCode, String fileName) throws IOException{
        FileWriter writer = new FileWriter("src/backend/resources/docker/" + fileName);
        writer.write(sourceCode);
        writer.close();
    }

    private ProcessBuilder buildDockerImage(String dockerImageName, String language) {
        logger.info("Building docker image...");
        String[] dockerBuild = {"docker", "build", "-t", dockerImageName, "-f", language + ".Dockerfile", "."};
        logger.info("Build::" + Arrays.toString(dockerBuild));
        ProcessBuilder dockerBuildProcessBuilder = new ProcessBuilder(dockerBuild);
        dockerBuildProcessBuilder.directory(new File("./src/backend/resources/docker/"));
        logger.info("Build complete!");
        return dockerBuildProcessBuilder;
    }

    private ProcessBuilder runDockerImage(String dockerImageName) {
        String[] dockerRun = {"docker", "run", "--rm", dockerImageName};
        ProcessBuilder dockerRunProcessBuilder = new ProcessBuilder(dockerRun);
        dockerRunProcessBuilder.directory(new File("./src/backend/resources/docker/"));
        return dockerRunProcessBuilder;
    }
    
    private StringBuilder outputLogs(Process process) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return output;
    }


    private StringBuilder errorLogs(Process process) throws IOException {
        StringBuilder errors = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            errors.append(line).append("\n");
        }
        return errors;
    }

    private void removeDockerImage(String imageName) throws IOException, InterruptedException{
        String[] dockerRemoveImage = {"docker", "image", "rm", imageName};
        ProcessBuilder dockerRemoveImageBuilder = new ProcessBuilder(dockerRemoveImage);
        Process dockerRemove = dockerRemoveImageBuilder.start();
        dockerRemove.waitFor();
    }
}