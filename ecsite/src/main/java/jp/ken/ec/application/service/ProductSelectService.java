package jp.ken.ec.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.ec.domain.entity.ProductEntity;
import jp.ken.ec.domain.repository.ProductRepository;
import jp.ken.ec.presentation.form.ProductForm;

@Service
public class ProductSelectService {

    private final ModelMapper modelMapper;
    private final ProductRepository product_repository;

    public ProductSelectService(ProductRepository product_repository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.product_repository = product_repository;
    }

    // 商品一覧取得
    public List<ProductForm> get_all_product() throws Exception {
        List<ProductEntity> p_entity = product_repository.get_all_product();
        return convert(p_entity);
    }

    // 商品1件取得
    public ProductForm get_product_id(Long product_id) throws Exception {
        try {
            ProductEntity p_entity = product_repository.get_product_id(product_id);
            return convert_toForm(p_entity);
        } catch (Exception e) {
            throw new Exception("商品が見つかりませんでした。", e);
        }
    }

    // キーワード検索
    public List<ProductForm> search_products(String keyword) throws Exception {
        List<ProductEntity> p_entity;
        if (keyword == null || keyword.trim().isEmpty()) {
            p_entity = product_repository.get_all_product();
        } else {
            p_entity = product_repository.search_product(keyword);
        }
        return convert(p_entity);
    }

    // 商品新規登録
    @Transactional
    public void create_products(ProductForm form) {
        ProductEntity entity = modelMapper.map(form, ProductEntity.class);
        product_repository.save(entity);
    }

    // 商品更新
    @Transactional
    public void update_product(ProductForm form) {
        ProductEntity entity = modelMapper.map(form, ProductEntity.class);
        product_repository.update(entity);
    }

    // 商品削除
    @Transactional
    public void delete_product(Long product_id) {
        product_repository.delete(product_id);
    }

    // --- 変換ヘルパー ---

    private ProductForm convert_toForm(ProductEntity p_entity) {
        return modelMapper.map(p_entity, ProductForm.class);
    }

    private List<ProductForm> convert(List<ProductEntity> entityList) {
        List<ProductForm> formList = new ArrayList<>();
        for (ProductEntity entity : entityList) {
            formList.add(modelMapper.map(entity, ProductForm.class));
        }
        return formList;
    }
}