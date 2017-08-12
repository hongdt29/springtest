package summer.service;

import java.util.List;

import summer.db.entity.Mcategory;
import summer.db.entity.Mfloor;
import summer.db.entity.Mtag;

public interface ITagService {
	public List<Mtag> getAllTags();
}
