package vjp.tag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vjp.tag.domain.vjpTag;
import vjp.tag.mapper.TagMapper;

@Service
public class TagService {

	@Autowired
	private TagMapper tagMapper;
	
	public vjpTag getTagByID(int id) {
		return tagMapper.getTagByID(id);
    }
}
