package com.shopping.admin.category;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopping.admin.repository.CategoryRepository;
import com.shopping.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;
	
//	@Test
//	public void testCreateRootCategory() {
//		Category category = new Category("Kids");
//		Category saveCategory = categoryRepository.save(category);
//	}
	
	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(6);
		Category subcategory = new Category("Tops", parent);
		Category save = categoryRepository.save(subcategory);
		
	}

}
