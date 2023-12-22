package com.either.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class GsonConfig {

  @Bean
  public Gson getGson() {
    return new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
  }
}
