package nl.abrouwer.extrack.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties
{
	private String uploadDir;


	public String getUploadDir()
	{
		return uploadDir;
	}
}