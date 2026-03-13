package jp.ken.ec.presentation.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jp.ken.ec.application.service.ProductSelectService;
import jp.ken.ec.domain.repository.CategoryRepository;
import jp.ken.ec.presentation.form.ProductForm;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductSelectService p_select_service;
    private final CategoryRepository categoryRepository;

    public AdminProductController(ProductSelectService p_select_service,
                                   CategoryRepository categoryRepository) {
        this.p_select_service = p_select_service;
        this.categoryRepository = categoryRepository;
    }

    // カテゴリー一覧を全メソッドのModelに自動追加
    @ModelAttribute
    public void addCategories(Model model) throws Exception {
        model.addAttribute("categories", categoryRepository.get_all_categories());
    }

    // 管理者用商品一覧
    @GetMapping
    public String list(Model model) throws Exception {
        model.addAttribute("products", p_select_service.get_all_product());
        return "admin/product_list";
    }

    // 商品新規登録画面
    @GetMapping("/create")
    public String createProduct(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "admin/product_form";
    }

    // 商品新規登録 POST
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("productForm") ProductForm productForm,
                         BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/product_form";
        }
        try {
            p_select_service.create_products(productForm);
            redirectAttributes.addFlashAttribute("success","商品を登録しました");
            return "redirect:/admin/products";
        } catch (Exception e) {
            model.addAttribute("error", "商品登録に失敗しました: " + e.getMessage());
            return "admin/product_form";
        }
    }

    // 商品編集画面
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        try {
            ProductForm form = p_select_service.get_product_id(id);
            model.addAttribute("productForm", form);
        } catch (Exception e) {
            model.addAttribute("error", "商品が見つかりませんでした");
            return "redirect:/admin/products";
        }
        return "admin/product_form";
    }

    // 商品更新 POST
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute("productForm") ProductForm productForm,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/product_form";
        }
        try {
            productForm.setProduct_id(id);
            p_select_service.update_product(productForm);
            return "redirect:/admin/products";
        } catch (Exception e) {
            model.addAttribute("error", "商品更新に失敗しました: " + e.getMessage());
            return "admin/product_form";
        }
    }

    // 商品削除 POST
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        p_select_service.delete_product(id);
        return "redirect:/admin/products";
    }
}