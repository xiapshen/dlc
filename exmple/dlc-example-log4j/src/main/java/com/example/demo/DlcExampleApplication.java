package com.example.demo;

import com.happgo.dlc.base.DlcConstants;
import com.happgo.dlc.base.ignite.service.DlcIgniteService;
import com.happygo.dlc.DlcIgniteServicesExporter;
import com.happygo.dlc.ignite.service.DlcIgniteServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/**
 * The type Dlc example application.
 */
@SpringBootApplication
public class DlcExampleApplication extends SpringBootServletInitializer {

	/**
	 * Expoter dlc ignite services exporter.
	 *
	 * @return the dlc ignite services exporter
	 */
	@Bean
	public DlcIgniteServicesExporter expoter() {
		DlcIgniteServicesExporter expoter = new DlcIgniteServicesExporter();
		expoter.setMode(DlcConstants.DEPLOY_NODE_SINGLETON);
		DlcIgniteService service = new DlcIgniteServiceImpl();
		expoter.setService(service);
		expoter.export();
		return expoter;
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(DlcExampleApplication.class, args);
	}
}
