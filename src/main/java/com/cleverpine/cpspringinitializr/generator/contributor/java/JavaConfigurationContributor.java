package com.cleverpine.cpspringinitializr.generator.contributor.java;

import com.cleverpine.cpspringinitializr.generator.customizer.java.JavaConfigurationCustomizer;
import io.spring.initializr.generator.language.java.JavaSourceCode;
import io.spring.initializr.generator.language.java.JavaSourceCodeWriter;
import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.util.LambdaSafe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.logMajorStep;

@RequiredArgsConstructor
public class JavaConfigurationContributor implements ProjectContributor {

    private final JavaSourceCode sourceCode = new JavaSourceCode();
    private final JavaSourceCodeWriter sourceWriter;
    private final ProjectDescription projectDescription;
    private final ObjectProvider<JavaConfigurationCustomizer> customizers;


    @Override
    public void contribute(Path projectRoot) throws IOException {
        logMajorStep("Generating Java Configurations...");
        // TODO: Refactor this class to be reusable for other types of configuration classes/dependencies
        var applicationName = "CPLoggingConfig";
        var packageName = projectDescription.getPackageName() + ".config";
        var compilationUnit = sourceCode.createCompilationUnit(packageName, applicationName);
        var javaTypeDeclaration = compilationUnit.createTypeDeclaration(applicationName);
        this.customizeJavaDeclarationType(javaTypeDeclaration);
        var buildSystem = projectDescription.getBuildSystem();
        var language = projectDescription.getLanguage();
        var sourceStructure = buildSystem.getMainSource(projectRoot, language);
        this.sourceWriter.writeTo(sourceStructure, sourceCode);
    }

    private void customizeJavaDeclarationType(JavaTypeDeclaration javaTypeDeclaration) {
        List<JavaConfigurationCustomizer> javaConfigurationCustomizers = this.customizers.orderedStream().toList();
        LambdaSafe.callbacks(JavaConfigurationCustomizer.class, javaConfigurationCustomizers, javaTypeDeclaration)
                .invoke((javaConfigurationCustomizer) ->
                        javaConfigurationCustomizer.customize(javaTypeDeclaration, projectDescription.getPackageName()));
    }
}
