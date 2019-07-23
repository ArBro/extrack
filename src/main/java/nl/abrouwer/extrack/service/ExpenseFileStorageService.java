package nl.abrouwer.extrack.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import nl.abrouwer.extrack.exception.ExpenseFileNotFoundException;
import nl.abrouwer.extrack.exception.ExpenseFileStorageException;

@Service
public class ExpenseFileStorageService implements StorageService
{
	private final Path rootLocation;

	public ExpenseFileStorageService(@Value("${storage.upload.dir") String uploadDir)
	{
		this.rootLocation = Paths.get(uploadDir);
	}

	@Override
	public void store(MultipartFile file)
	{
		try
		{
			if (file.isEmpty())
			{
				throw new ExpenseFileStorageException("Failed to store empty file " + file.getOriginalFilename());
			}
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		}
		catch (IOException e)
		{
			throw new ExpenseFileStorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}


	@Override
	public Stream<Path> loadAll()
	{
		try
		{
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		}
		catch (IOException e)
		{
			throw new ExpenseFileStorageException("Failed to read stored files", e);
		}

	}


	@Override
	public Path load(String filename)
	{
		return rootLocation.resolve(filename);
	}


	@Override
	public Resource loadAsResource(String filename)
	{
		try
		{
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable())
			{
				return resource;
			}
			else
			{
				throw new ExpenseFileNotFoundException("Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e)
		{
			throw new ExpenseFileNotFoundException("Could not read file: " + filename, e);
		}
	}


	@Override
	public void deleteAll()
	{
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}


	@Override
	public void init()
	{
		try
		{
			Files.createDirectory(rootLocation);
		}
		catch (IOException e)
		{
			throw new ExpenseFileStorageException("Could not initialize storage", e);
		}
	}
}