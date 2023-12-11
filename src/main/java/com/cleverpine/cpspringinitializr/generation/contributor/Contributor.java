package com.cleverpine.cpspringinitializr.generation.contributor;

import com.cleverpine.cpspringinitializr.customizer.Customizer;
import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.language.java.JavaSourceCode;
import io.spring.initializr.generator.language.java.JavaSourceCodeWriter;
import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.util.LambdaSafe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Contributor implements ProjectContributor {

    private final JavaSourceCode sourceCode;

    private final JavaSourceCodeWriter sourceWriter;

    private final ObjectProvider<Customizer> mainTypeCustomizers;


    @Override
    public void contribute(Path projectRoot) throws IOException {
        String applicationName = "JavaFile";
        String packageName = "com.cleverpine.mando.way";
        var compilationUnit = sourceCode.createCompilationUnit(packageName, applicationName);
        var mainApplicationType = compilationUnit.createTypeDeclaration(applicationName);
        customizeMainApplicationType(mainApplicationType);
        this.sourceWriter.writeTo(
                BuildSystem.forId(MavenBuildSystem.ID).getMainSource(projectRoot, Language.forId("java", "17")),
                sourceCode);
    }

    private void customizeMainApplicationType(JavaTypeDeclaration mainApplicationType) {
        List<Customizer> customizers = this.mainTypeCustomizers.orderedStream()
                .collect(Collectors.toList());
        LambdaSafe.callbacks(Customizer.class, customizers, mainApplicationType)
                .invoke((customizer) -> customizer.customize(mainApplicationType));
    }
}
