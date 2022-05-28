package com.recipemanager.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

	/**
	 * Bean to map models
	 *
	 * @return new ModelMapper instance
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
