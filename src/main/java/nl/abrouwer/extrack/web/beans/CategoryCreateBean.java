package nl.abrouwer.extrack.web.beans;

import nl.abrouwer.extrack.domain.model.TransactionCategoryType;

public class CategoryCreateBean
{
	private String categoryId;
	private String description;
	private TransactionCategoryType type;
	private boolean active = true;
	private String username;
	
	public CategoryCreateBean()
	{
		
	}

	public String getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(String categoryId)
	{
		this.categoryId = categoryId;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public TransactionCategoryType getType()
	{
		return type;
	}

	public void setType(TransactionCategoryType type)
	{
		this.type = type;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
}
