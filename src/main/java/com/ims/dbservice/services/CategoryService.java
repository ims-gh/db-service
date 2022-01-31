package com.ims.dbservice.services;

import com.ims.dbservice.dto.CategoryDTO;
import com.ims.dbservice.exceptions.CategoryAlreadyExistsException;
import com.ims.dbservice.exceptions.CategoryDoesNotExistException;
import com.ims.dbservice.models.Category;
import com.ims.dbservice.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        log.info("Getting all categories");
        return categoryRepository.findAll();
    }

    public Category getCategoryByCategoryName(String categoryName) {
        log.info("Finding category with Name {}", categoryName);
        return findCategoryOrThrowError(categoryName);
    }

    public void addNewCategory(Category category) {
        log.info("Adding new category: {}", category);
        throwsErrorIfCategoryExists(category.getCategoryName());
        categoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(String name, CategoryDTO categoryDTO) {
        log.info("Extracting category details");
        String categoryName = categoryDTO.getCategoryName();
        String description = categoryDTO.getDescription();

        log.info("Finding category with name {}", name);
        Category category = findCategoryOrThrowError(name);
        log.info("Updating category");

        if (categoryName != null && !categoryName.isEmpty() && !categoryName.equals(category.getCategoryName())) {
            throwsErrorIfCategoryExists(categoryName);
            category.setCategoryName(categoryName);
        }
        log.info("Name updated successfully");

        if (description != null && !description.isEmpty() && !description.equals(category.getDescription())) {
            category.setDescription(description);
        }
        log.info("Description updated successfully");
    }

    public void deleteCategory(String categoryName){
        log.info("Deleting category {}", categoryName);
        Category category = findCategoryOrThrowError(categoryName);
        categoryRepository.delete(category);
    }

    private Category findCategoryOrThrowError(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                    .orElseThrow(() -> new CategoryDoesNotExistException(categoryName));
    }

    private void throwsErrorIfCategoryExists(String categoryName){
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(categoryName);
        if (categoryOptional.isPresent()) {
            throw new CategoryAlreadyExistsException(categoryName);
        }
    }

}
