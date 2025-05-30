package jp.ken.ec.domain.entity;

import lombok.Data;

@Data
public class CategoryEntity {
	private Long category_id; //主キー
	private String category_name; //カテゴリ名
}
