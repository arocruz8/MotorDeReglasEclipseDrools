package com.arocruz8.spring.drools.api;

import java.io.IOException;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolConfig {

	//configuración de las reglas
	//variable permite llamar al patron factory para crear las reglas 
	private KieServices kieServices = KieServices.Factory.get();
	
	/*
	 * Metodo permite acceder al archivo drl que contiene las reglas del negocio
	 * */
	private KieFileSystem getKieFileSystem() throws IOException {
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write(ResourceFactory.newClassPathResource("offer.drl"));
		return kieFileSystem;
	}

	/**
	 * Llama a la interfaz KieContainer la cual permite acceder al contenido 
	 * que esta dentro del archivo de las reglas. A parte de eso el Un KieModule
	 * es un contenedor de todos los recursos necesarios para definir un conjunto
	 * de KieBases como un pom.xml que define su ReleaseId, un archivo kmodule.xml 
	 * que declara los nombres y configuraciones de KieBases junto con todas las 
	 * KieSession que se pueden crear a partir de ellos y todos los otros archivos 
	 * necesarios para construir las propias KieBases 
	 * */
	@Bean
	public KieContainer getKieContainer() throws IOException {
		System.out.println("Container created...");
		getKieRepository();
		KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
		kb.buildAll();
		KieModule kieModule = kb.getKieModule();
		KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
		return kContainer;
	}

	/**
	 * El KieRepository actúa como un repositorio para todos los KieModules disponibles
	 * */
	private void getKieRepository() {
		final KieRepository kieRepository = kieServices.getRepository();
		kieRepository.addKieModule(new KieModule() {
			public ReleaseId getReleaseId() {
				return kieRepository.getDefaultReleaseId();
			}
		});
	}

	/**
	 * La KieSession permite que la aplicación establezca una conversación iterativa
	 * con el motor, donde el estado de la sesión se mantiene a través de las invocaciones. 
	 * El proceso de razonamiento puede activarse varias veces para el mismo conjunto de datos. 
	 * Sin embargo, una vez que la aplicación termina de usar la sesión, debe llamar al método 
	 * dispose () para liberar los recursos y la memoria usada.
	 * */
	@Bean
	public KieSession getKieSession() throws IOException {
		System.out.println("session created...");
		return getKieContainer().newKieSession();
	}

}
