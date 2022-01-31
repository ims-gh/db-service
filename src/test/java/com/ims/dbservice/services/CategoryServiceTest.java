package com.ims.dbservice.services;

import com.ims.dbservice.dto.CategoryDTO;
import com.ims.dbservice.exceptions.CategoryAlreadyExistsException;
import com.ims.dbservice.exceptions.CategoryDoesNotExistException;
import com.ims.dbservice.models.Category;
import com.ims.dbservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    Category bakingMaterials;
    Category decorTools;

    @BeforeEach
    void setUp() {
        bakingMaterials = new Category(
                "baking materials",
                "for baking");
        decorTools = new Category(
                "decor tools",
                "to decorate cakes");

    }

    @Test
    @DisplayName("should get all categories")
    void getAllCategoriesTest() {
        List<Category> categories = List.of(decorTools, bakingMaterials);
        when(categoryRepository.findAll())
                .thenReturn(categories);

        List<Category> allCategories = categoryService.getAllCategories();
        assertAll(
                () -> assertEquals(2, allCategories.size()),
                () -> assertTrue(categories.get(1).equals(bakingMaterials))
        );
    }

    @Nested
    @DisplayName("should add new category")
    class addNewCategory {
        @Captor
        private ArgumentCaptor<Category> categoryArgumentCaptor;

        @Test
        @DisplayName("given a valid category")
        void addNewCategoryTest() {
            categoryService.addNewCategory(decorTools);
            verify(categoryRepository).save(categoryArgumentCaptor.capture());

            Category capturedDecorTools = categoryArgumentCaptor.getValue();
            assertAll(
                    () -> assertEquals(decorTools, capturedDecorTools),
                    () -> assertEquals("decor tools", capturedDecorTools.getCategoryName())
            );
        }

        @Test
        @DisplayName("given category already exists, throws exception")
        void addNewCategoryThrowsException() {
            Category packaging = new Category(
                    "packaging",
                    "materials used for packaging"
            );
            when(categoryRepository.findByCategoryName("packaging"))
                    .thenReturn(Optional.of(packaging));
            assertThrows(
                    CategoryAlreadyExistsException.class,
                    () -> categoryService.addNewCategory(packaging));
        }
    }

    @Nested
    @DisplayName("should find category by name")
    class getCategoryByName {
        @Test
        @DisplayName("given a valid/existing category")
        void getCategoryByCategoryNameTest() {
            String categoryName = "decor tools";
            when(categoryRepository.findByCategoryName(categoryName))
                    .thenReturn(Optional.of(decorTools));
            Category testCategory = categoryService.getCategoryByCategoryName(categoryName);
            assertEquals(decorTools, testCategory);
        }

        @Test
        @DisplayName("given a non-existent category")
        void getCategoryByNameThrowsError() {
            String name = "machines";
            when(categoryRepository.findByCategoryName(name))
                    .thenReturn(Optional.empty());
            assertThrows(
                    CategoryDoesNotExistException.class,
                    () -> categoryService.getCategoryByCategoryName(name),
                    String.format("Category %s does not exist", name)
            );
        }
    }

    @Nested
    @DisplayName("should update a category")
    class updateCategory {
        // valid category with valid fields
        @Test
        @DisplayName("given valid category and fields")
        void updateCategoryTest() {
            String name = "packaging";
            Category category = new Category(
                    name,
                    "packaging stuff"
            );
            CategoryDTO packaging = new CategoryDTO(
                    "packaging materials",
                    "for packaging products");
            when(categoryRepository.findByCategoryName(name))
                    .thenReturn(Optional.of(category));
            when(categoryRepository.findByCategoryName("packaging materials"))
                    .thenReturn(Optional.empty());
            categoryService.updateCategory(name, packaging);
            assertAll(
                    () -> assertEquals("packaging materials", category.getCategoryName()),
                    () -> assertEquals("for packaging products", category.getDescription())
            );
        }

        // valid category with invalid fields
        @Test
        @DisplayName("given valid category with invalid name")
        void updateCategoryInvalidName() {
            String name = "packaging";
            Category category = new Category(
                    name,
                    "packaging stuff"
            );
            CategoryDTO baking = new CategoryDTO(
                    "baking materials");
            when(categoryRepository.findByCategoryName(name))
                    .thenReturn(Optional.of(category));
            when(categoryRepository.findByCategoryName("baking materials"))
                    .thenReturn(Optional.of(bakingMaterials));
            assertThrows(
                    CategoryAlreadyExistsException.class,
                    () -> categoryService.updateCategory(name, baking)
            );
        }

        @Test
        @DisplayName("given valid category with invalid description")
        void updateCategoryInvalidDescription() {
            String name = "packaging";
            Category category = new Category(
                    name,
                    "packaging stuff"
            );
            CategoryDTO packaging = new CategoryDTO(
                    "packaging",
                    "");
            when(categoryRepository.findByCategoryName(name))
                    .thenReturn(Optional.of(category));
            categoryService.updateCategory(name, packaging);
            assertAll(
                    () -> assertEquals("packaging", category.getCategoryName()),
                    () -> assertNotEquals("", category.getDescription())
            );
        }

        // non-existent category
        @Test
        @DisplayName("given non-existent category")
        void updateCategoryInvalid() {
            String name = "packaging";
            Category category = new Category(
                    name,
                    "packaging stuff"
            );
            CategoryDTO packaging = new CategoryDTO(
                    "packaging materials",
                    "for packaging");
            when(categoryRepository.findByCategoryName(name))
                    .thenReturn(Optional.empty());
            assertThrows(
                    CategoryDoesNotExistException.class,
                    () -> categoryService.updateCategory(name, packaging)
            );
        }
    }

    @Nested
    @DisplayName("should delete category")
    class deleteCategory {

        @Test
        @DisplayName("given category exists")
        void deleteCategoryTest() {
            String name = "decor tools";
            when(categoryRepository.findByCategoryName(name))
                    .thenReturn(Optional.of(decorTools));
            categoryService.deleteCategory(name);
            verify(categoryRepository).delete(decorTools);
        }

        @Test
        @DisplayName("given invalid category")
        void deleteCategoryDoesNotExist() {
            String name = "decor tools";
            when(categoryRepository.findByCategoryName(name))
                    .thenReturn(Optional.empty());
            assertThrows(
                    CategoryDoesNotExistException.class,
                    () -> categoryService.deleteCategory(name)
            );
        }

    }
}