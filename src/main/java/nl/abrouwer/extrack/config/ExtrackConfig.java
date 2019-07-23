package nl.abrouwer.extrack.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import nl.abrouwer.extrack.service.StorageProperties;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class ExtrackConfig
{

}