package com.hexagonal.sample.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.hexagonal.sample")
class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule adaptersShouldNotDependOnEachOther =
            noClasses()
                    .that().resideInAPackage("..adapters.in..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..adapters.out..");

    @ArchTest
    static final ArchRule inAdaptersShouldNotDependOnCoreImpl =
            noClasses()
                    .that().resideInAPackage("..adapters..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..core.application.impl..");

    @ArchTest
    static final ArchRule coreShouldNotDependOnAdapters =
            noClasses()
                    .that().resideInAPackage("..core..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..adapters..");

    @ArchTest
    static final ArchRule coreShouldNotDependOnOutPorts =
            noClasses()
                    .that().resideInAPackage("..core.application..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..adapters..");

    @ArchTest
    static final ArchRule domainShouldNotDependOnAnything =
            noClasses()
                    .that().resideInAPackage("..core.domain..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "..adapters..",
                            "..ports..",
                            "..core.application.."
                    );

    @ArchTest
    static final ArchRule controllersShouldOnlyDependOnInPorts =
            noClasses()
                    .that().resideInAPackage("..adapters.in..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..core.application.impl..");

    @ArchTest
    static final ArchRule outAdaptersShouldOnlyDependOnOutPorts =
            noClasses()
                    .that().resideInAPackage("..adapters.out..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..adapters.in..");
}