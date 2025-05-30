package jp.ken.ec.domain.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem newItem) {
        for (CartItem item : items) {
            if (item.getProductId().equals(newItem.getProductId())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        items.add(newItem);
    }
    
    //カート内商品の総数を返すメソッド
    public int  getTotalQuantity() {
    	int total = 0;
    	for(CartItem item : items) {
    		total+= item.getQuantity();
    		
    	}
    	return total;
    }
    
    // カート内のすべてのアイテムを削除する
    public void clear_cart() {
    	items.clear();
    }
    
    //選択商品IDのアイテム削除
    public void remove_item(Long product_id) {
    	Iterator<CartItem> iterator =  items.iterator();
    	while(iterator.hasNext()) {
    		CartItem item = iterator.next();
    		if(item.getProductId().equals(product_id)) {
    			iterator.remove();
    			return;
    		}
    	}
    	
    }
    
    //カート内商品の合計金額計算
    public double getTotal_amount() {
    	double total = 0;
    	for(CartItem item :items) {
    		total += item.getQuantity()*item.getProduct_form().getPrice();
    	}
    	
    	return total;
    }
    
    
}

