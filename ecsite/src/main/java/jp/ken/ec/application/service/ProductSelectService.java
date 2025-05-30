package jp.ken.ec.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.ken.ec.domain.entity.ProductEntity;
import jp.ken.ec.domain.repository.ProductRepository;
import jp.ken.ec.presentation.form.ProductForm;


@Service
public class ProductSelectService {
	private final ModelMapper modelMapper;
	private final ProductRepository product_repository;
	
	public ProductSelectService(ProductRepository product_repository,ModelMapper modelMapper) {
		this.modelMapper=modelMapper;
		this.product_repository = product_repository;
	}
	//商品一覧取得
	public List<ProductForm> get_all_product() throws Exception{
		List<ProductEntity> p_entity = product_repository.get_all_product();
		
		List<ProductForm> p_form_list = convert(p_entity);
		
		return p_form_list;
		
		
	}
	//商品取得商品IDをもとに、1件の商品情報
	public ProductForm get_product_id(Long product_id) throws Exception{
		try {
			ProductEntity p_entity = product_repository.get_product_id(product_id);
			ProductForm p_form = convert_toForm(p_entity);
			
			return p_form;
			
		}catch(Exception e) {
			throw new Exception("商品が見つかりませんでした。",e);
		}
	}
	
	//キーワード検索
	public List<ProductForm> search_products(String keyword) throws Exception {
		List<ProductEntity> p_entity;
		
		if(keyword == null || keyword.trim().isEmpty()) {
			//キーワードが空の場合は全件取得
			p_entity = product_repository.get_all_product();
		}else {
			//商品名にキーワードがある場合。
			p_entity = product_repository.search_product(keyword);
		}
		
		List<ProductForm> p_form_list = convert(p_entity);
		return p_form_list;
		
		
	}
	
	
	// エンティティをフォームに変換するメソッド
	private ProductForm convert_toForm(ProductEntity p_entity) {
		ProductForm form = modelMapper.map(p_entity,ProductForm.class);
		
		return form;
	}
	
	
	
	// エンティティリストをフォームリストに変換する共通メソッド
	private List<ProductForm> convert(List<ProductEntity> entityList){
		
		List<ProductForm> formList = new ArrayList<ProductForm>();
		
		for(ProductEntity entity : entityList) {
			ProductForm form = modelMapper.map(entity,ProductForm.class);
			
			formList.add(form);
		}
		return formList;
	}
	
	
	

}
