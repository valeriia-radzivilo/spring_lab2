package com.kpi.springboot_laba_2.controller;

import com.kpi.springboot_laba_2.entity.Category;
import com.kpi.springboot_laba_2.entity.Product;
import com.kpi.springboot_laba_2.service.AppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class AppController {

    private AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/")
    public String welcomePage(@RequestParam(required = false) boolean created,
                              Model model) {
        if (created) {
            creatingSample();
            model.addAttribute("created", true);
        }
        return "home";
    }

    @GetMapping("/admin")
    public String adminPage(@RequestParam(name = "category", required = false) Integer categoryId,
                            @RequestParam(name = "deleted", required = false) String typeName,
                            Model model) {
        if (typeName != null) {
            model.addAttribute("typeName", typeName);
        }

        setupLists(categoryId, model);
        return "admin";
    }

    @GetMapping("/admin/delete-category")
    public String deleteCategory(@RequestParam int id,
                                 Model model) {
        Category category = appService.findCategoryById(id);
        appService.deleteCategory(id);
        model.addAttribute("typeName", "category");
        model.addAttribute("name", category.getName());
        return "admin";
    }

    @GetMapping("/admin/delete-product")
    public String deleteProduct(@RequestParam int id,
                                Model model) {
        Product product = appService.findProductById(id);
        appService.deleteProduct(id);
        model.addAttribute("typeName", "product");
        model.addAttribute("name", product.getName());
        return "admin";
    }

    @GetMapping("/admin/edit-product")
    public String editProduct(@RequestParam int id,
                              @RequestParam(required = false) boolean updated,
                              Model model) {
        model.addAttribute("updated", updated);
        Product product = appService.findProductById(id);
        List<Category> categories = appService.findAllCategories();
        Category currentCategory = appService.findCategoryById(product.getCategoryId());
        categories.remove(currentCategory);
        model.addAttribute("currentCategory", currentCategory);
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/admin/edit-product")
    public String postEditProduct(@ModelAttribute("product") Product product) {
        appService.updateProduct(product.getId(), product);
        return "redirect:/admin/edit-product?id=" + product.getId() + "&updated=true";
    }

    @GetMapping("/admin/edit-category")
    public String editCategory(@RequestParam int id,
                               @RequestParam(required = false) boolean updated,
                               Model model) {
        model.addAttribute("updated", updated);


        Category category = appService.findCategoryById(id);
        model.addAttribute("category", category);
        return "edit-category";
    }

    @PostMapping("/admin/edit-category")
    public String postEditCategory(@ModelAttribute("category") Category category) {
        appService.updateCategory(category.getId(), category);
        return "redirect:/admin/edit-category?id=" + category.getId() + "&updated=true";
    }

    @GetMapping("/admin/add-product")
    public String addProductPage(Model model) {
        List<Category> categories = appService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("isAdded", false);
        return "add-product";
    }

    @PostMapping("/admin/add-product")
    public String addProductPost(@RequestParam("name") String name,
                                 @RequestParam("description") String description,
                                 @RequestParam("category") Integer category,
                                 Model model) {

        Product newProduct = new Product(appService.createProductUniqueId(),
                name, description, category);
        appService.createProduct(newProduct);
        model.addAttribute("isAdded", true);
        return "add-product";
    }


    @GetMapping("/admin/add-category")
    public String addCategoryPage(Model model) {
        List<Category> categories = appService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("isAdded", false);
        return "add-category";
    }

    @PostMapping("/admin/add-category")
    public String addCategoryPost(@RequestParam("name") String name,
                                  @RequestParam("category") Integer category,
                                  Model model) {
        Category newCategory = new Category(appService.createCategoryUniqueId(),
                name, category);
        appService.createCategory(newCategory);
        model.addAttribute("isAdded", true);
        return "add-category";
    }

    @GetMapping("/guest")
    public String guestPage(@RequestParam(name = "category", required = false) Integer categoryId,
                            Model model) {
        setupLists(categoryId, model);
        return "guest";
    }


    private void setupLists(Integer categoryId, Model model) {
        List<Category> categories = appService.findAllCategories();
        List<Product> products = appService.findAllProducts();
        if (categoryId != null) {
            categories.removeIf(el -> el.getParentId() != categoryId);
            products.removeIf(el -> el.getCategoryId() != categoryId);
        } else {
            categories.removeIf(el -> el.getParentId() != 0);
            products = new ArrayList<>();
        }

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
    }

    private void creatingSample() {
        Category cameras = new Category(appService.createCategoryUniqueId(), "Cameras", 0);
        Category headphones = new Category(appService.createCategoryUniqueId(), "Headphones", 0);
        Category wirelessHeadphones = new Category(appService.createCategoryUniqueId(), "Wireless headphones", 2);
        Category wireHeadphones = new Category(appService.createCategoryUniqueId(), "Wire headphones", 2);

        Product product1 = new Product(
                appService.createProductUniqueId(),
                "Skullcandy Jib True 2 In-Ear Wireless Earbuds",
                "32 Hr Battery, Microphone, Works with iPhone Android and Bluetooth Devices - Black",
                3);

        Product product2 = new Product(
                appService.createProductUniqueId(),
                "Samsung Galaxy Buds Live, Earbuds",
                "Samsung Galaxy Buds Live, Earbuds w/Active Noise Cancelling (Mystic Black) (Renewed)",
                3);

        Product product3 = new Product(
                appService.createProductUniqueId(),
                "Maxell - 190319 Stereo Headphones",
                "Maxell - 190319 Stereo Headphones - 3.5mm Cord with 6-Foot Length - Soft Padded Ear Cushions",
                4);

        Product product4 = new Product(
                appService.createProductUniqueId(),
                "DaKuan 3 Packs Earphone with Remote Microphone",
                "DaKuan 3 Packs Earphone with Remote Microphone, in Ear Stereo Sound Noise Isolating Tangle Free for Smartphones",
                4);

        Product product5 = new Product(
                appService.createProductUniqueId(),
                "Monoprice - 116150 Modern Retro Over Ear Headphones",
                "Monoprice - 116150 Modern Retro Over Ear Headphones with Ultra-Comfortable Ear Pads Perfect for Mobile Devices, HiFi, and Audio/Video Production Black",
                2);

        Product product6 = new Product(
                appService.createProductUniqueId(),
                "Polaroid IS048 Digital Camera",
                "Small Lightweight Waterproof Instant Sharing 16 MP Digital Portable Handheld Action Camera (Black)",
                1);

        appService.createCategory(cameras);
        appService.createCategory(wireHeadphones);
        appService.createCategory(wirelessHeadphones);
        appService.createCategory(headphones);

        appService.createProduct(product1);
        appService.createProduct(product2);
        appService.createProduct(product3);
        appService.createProduct(product4);
        appService.createProduct(product5);
        appService.createProduct(product6);
    }
}
