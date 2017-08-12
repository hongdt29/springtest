package summer.service;

import java.util.List;

import summer.db.entity.Mcategory;
import summer.db.entity.Mcompany;
import summer.db.entity.Mfloor;

public interface ICompanyService {
	public List<Mcompany> getAllCompany();
}
