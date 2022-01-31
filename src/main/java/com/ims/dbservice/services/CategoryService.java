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

    public List<Category> getAllCategories(){
        log.info("Getting all categories");
        return categoryRepository.findAll();
    }

    public Category getCategoryByCategoryName(String categoryName){
        log.info("Finding category with Name {}", categoryName);
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(categoryName);
        if (categoryOptional.isPresent())
            return categoryOptional.get();
        throw new CategoryDoesNotExistException("Category with name "+ categoryName + " does not exist");
    }

    public void addNewCategory(Category category){
        log.info("Adding new category: {}", category);
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryOptional.isPresent()){
            throw new CategoryAlreadyExistsException("Category with name " + category.getCategoryName() + " already exists");
        }
        categoryRepository.save(categoryOptional.get());
    }

    @Transactional
    public void updateCategory(String name, CategoryDTO categoryDTO){
        log.info("Extracting category details");
        String categoryName = categoryDTO.getCategoryName();
        String description = categoryDTO.getDescription();

        log.info("Finding category with name {}", name);

        Category category = categoryRepository.findByCategoryName(name)
                .orElseThrow(() -> new CategoryDoesNotExistException(
                        "Category with name " + name + " does not exist"
                ));

        log.info("Updating category");
        if (categoryName != null && !categoryName.isEmpty() && !categoryName.equals(category.getCategoryName())){
            category.setCategoryName(categoryName);
        }

        if (description != null && !description.isEmpty() && !description.equals(category.getDescription())){
            category.setDescription(description);
        }
    }


}
