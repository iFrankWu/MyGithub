#
# 删除分类脚本
#
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE `catalog_category_entity` ;
TRUNCATE TABLE `catalog_category_entity_datetime`;
TRUNCATE TABLE `catalog_category_entity_decimal`;
TRUNCATE TABLE `catalog_category_entity_int`;
TRUNCATE TABLE `catalog_category_entity_text`;
TRUNCATE TABLE `catalog_category_entity_varchar`;
TRUNCATE TABLE `catalog_category_flat_store_1`;
TRUNCATE TABLE `catalog_category_product`;
TRUNCATE TABLE `catalog_category_product_index`;

TRUNCATE TABLE `catalog_category_product_index_enbl_idx`;
TRUNCATE TABLE `catalog_category_product_index_enbl_tmp`;
TRUNCATE TABLE `catalog_category_product_index_enbl_tmp`;

TRUNCATE TABLE `catalog_category_product_index_tmp`;

TRUNCATE TABLE `catalog_compare_item`;
#这一行是系统属性千万不能删
#TRUNCATE TABLE `catalog_eav_attribute`;


INSERT INTO `catalog_category_entity` (`entity_id`, `entity_type_id`, `attribute_set_id`, `parent_id`, `created_at`, `updated_at`, `path`, `position`, `level`, `children_count`) VALUES
(1, 3, 0, 0, '2011-12-06 03:52:57', '2011-12-06 03:52:57', '1', 0, 0, 1),
(2, 3, 3, 1, '2011-12-06 03:52:57', '2011-12-06 03:52:57', '1/2', 1, 1, 0);


INSERT INTO `catalog_category_entity_varchar` (`value_id`, `entity_type_id`, `attribute_id`, `store_id`, `entity_id`, `value`) VALUES
(1, 3, 33, 0, 1, 'Root Catalog'),
(2, 3, 33, 1, 1, 'Root Catalog'),
(3, 3, 35, 1, 1, 'root-catalog'),
(4, 3, 33, 0, 2, 'Default Category'),
(5, 3, 33, 1, 2, 'Default Category'),
(6, 3, 41, 1, 2, 'PRODUCTS'),
(7, 3, 35, 1, 2, 'default-category');


INSERT INTO `catalog_category_entity_int` (`value_id`, `entity_type_id`, `attribute_id`, `store_id`, `entity_id`, `value`) VALUES
(1, 3, 59, 0, 1, 1),
(2, 3, 59, 1, 1, 1),
(3, 3, 34, 0, 2, 1),
(4, 3, 59, 0, 2, 1),
(5, 3, 34, 1, 2, 1),
(6, 3, 59, 1, 2, 1);

INSERT INTO `catalog_category_entity_text` (`value_id`, `entity_type_id`, `attribute_id`, `store_id`, `entity_id`, `value`) VALUES
(1, 3, 57, 0, 1, NULL),
(2, 3, 57, 1, 1, NULL),
(3, 3, 57, 0, 2, NULL),
(4, 3, 57, 1, 2, NULL);


SET FOREIGN_KEY_CHECKS=1;



#
# 删除产品
#
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE `cataloginventory_stock_item` ;
TRUNCATE TABLE `cataloginventory_stock_status` ;
TRUNCATE TABLE `cataloginventory_stock_status_idx` ;

#打折规则
TRUNCATE TABLE `catalogrule_group_website`;
TRUNCATE TABLE `catalogrule_product`;
TRUNCATE TABLE `catalogrule_product_price` ;

TRUNCATE TABLE `catalogrule` ;

#产品搜索
TRUNCATE TABLE `catalogsearch_fulltext` ;

#清空搜索表
TRUNCATE TABLE `catalogsearch_query` ;
TRUNCATE TABLE catalogsearch_result;


#清空产品相关的表
TRUNCATE TABLE catalog_product_bundle_option;
TRUNCATE TABLE catalog_product_bundle_option_value;
TRUNCATE TABLE catalog_product_bundle_price_index;
TRUNCATE TABLE catalog_product_bundle_selection;
TRUNCATE TABLE catalog_product_bundle_selection_price;
TRUNCATE TABLE catalog_product_bundle_stock_index;
TRUNCATE TABLE catalog_product_enabled_index;
TRUNCATE TABLE catalog_product_entity;
TRUNCATE TABLE catalog_product_entity_datetime;
TRUNCATE TABLE catalog_product_entity_decimal;
TRUNCATE TABLE catalog_product_entity_gallery;
TRUNCATE TABLE catalog_product_entity_int;
TRUNCATE TABLE catalog_product_entity_media_gallery;
TRUNCATE TABLE catalog_product_entity_media_gallery_value;
TRUNCATE TABLE catalog_product_entity_text;
TRUNCATE TABLE catalog_product_entity_tier_price;
TRUNCATE TABLE catalog_product_entity_varchar;
TRUNCATE TABLE catalog_product_flat_1;
TRUNCATE TABLE catalog_product_index_eav;
TRUNCATE TABLE catalog_product_index_eav_decimal;
TRUNCATE TABLE catalog_product_index_eav_decimal_idx;
TRUNCATE TABLE catalog_product_index_eav_decimal_tmp;
TRUNCATE TABLE catalog_product_index_eav_idx;
TRUNCATE TABLE catalog_product_index_eav_tmp;
TRUNCATE TABLE catalog_product_index_price;
TRUNCATE TABLE catalog_product_index_price_bundle_idx;
TRUNCATE TABLE catalog_product_index_price_bundle_opt_idx;
TRUNCATE TABLE catalog_product_index_price_bundle_opt_tmp;
TRUNCATE TABLE catalog_product_index_price_bundle_sel_idx;
TRUNCATE TABLE catalog_product_index_price_bundle_sel_tmp;
TRUNCATE TABLE catalog_product_index_price_bundle_tmp;
TRUNCATE TABLE catalog_product_index_price_cfg_opt_agr_idx;
TRUNCATE TABLE catalog_product_index_price_cfg_opt_agr_tmp;
TRUNCATE TABLE catalog_product_index_price_cfg_opt_idx;
TRUNCATE TABLE catalog_product_index_price_cfg_opt_tmp;
TRUNCATE TABLE catalog_product_index_price_downlod_idx;
TRUNCATE TABLE catalog_product_index_price_downlod_tmp;
TRUNCATE TABLE catalog_product_index_price_final_idx;
TRUNCATE TABLE catalog_product_index_price_final_tmp;
TRUNCATE TABLE catalog_product_index_price_idx;
TRUNCATE TABLE catalog_product_index_price_opt_agr_idx;
TRUNCATE TABLE catalog_product_index_price_opt_agr_tmp;
TRUNCATE TABLE catalog_product_index_price_opt_idx;
TRUNCATE TABLE catalog_product_index_price_opt_tmp;
TRUNCATE TABLE catalog_product_index_price_tmp;
TRUNCATE TABLE catalog_product_index_tier_price;
TRUNCATE TABLE catalog_product_index_website;
TRUNCATE TABLE catalog_product_link;
#TRUNCATE TABLE catalog_product_link_attribute;
TRUNCATE TABLE catalog_product_link_attribute_decimal;
TRUNCATE TABLE catalog_product_link_attribute_int;
TRUNCATE TABLE catalog_product_link_attribute_varchar;
#TRUNCATE TABLE catalog_product_link_type;
TRUNCATE TABLE catalog_product_option;
TRUNCATE TABLE catalog_product_option_price;
TRUNCATE TABLE catalog_product_option_title;
TRUNCATE TABLE catalog_product_option_type_price;
TRUNCATE TABLE catalog_product_option_type_title;
TRUNCATE TABLE catalog_product_option_type_value;
TRUNCATE TABLE catalog_product_relation;
TRUNCATE TABLE catalog_product_super_attribute;
TRUNCATE TABLE catalog_product_super_attribute_label;
TRUNCATE TABLE catalog_product_super_attribute_pricing;
TRUNCATE TABLE catalog_product_super_link;
TRUNCATE TABLE catalog_product_website;
SET FOREIGN_KEY_CHECKS=1;

#清空日志表
TRUNCATE TABLE `log_customer` ;
TRUNCATE TABLE `log_quote` ;
TRUNCATE TABLE `log_summary` ;
TRUNCATE TABLE `log_url` ;
TRUNCATE TABLE `log_url_info` ;
TRUNCATE TABLE `log_visitor` ;
TRUNCATE TABLE `log_visitor_info` ;

#情况url重写规则表
#TRUNCATE TABLE `core_url_rewrite` ;

#删除客户信息
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE `customer_address_entity`;
TRUNCATE TABLE `customer_address_entity_datetime`;
TRUNCATE TABLE `customer_address_entity_decimal`;
TRUNCATE TABLE `customer_address_entity_int`;
TRUNCATE TABLE `customer_address_entity_text`;
TRUNCATE TABLE `customer_address_entity_varchar`;
TRUNCATE TABLE `customer_eav_attribute_website`;
TRUNCATE TABLE `customer_entity`;

TRUNCATE TABLE `customer_entity_datetime`;
TRUNCATE TABLE `customer_entity_decimal`;
TRUNCATE TABLE `customer_entity_int`;
TRUNCATE TABLE `customer_entity_text`;
TRUNCATE TABLE `customer_entity_varchar`;
 


SET FOREIGN_KEY_CHECKS=1;

#删除索引时间表
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE `index_event`;
TRUNCATE TABLE index_process_event;




#删除评论信息
TRUNCATE TABLE review;
TRUNCATE TABLE review_detail;
TRUNCATE TABLE review_entity_summary;
TRUNCATE TABLE review_store;

#删除订单信息

#TRUNCATE TABLE `ordertickets_chat`;
#TRUNCATE TABLE `ordertickets_ticket`;

#删除销售信息
TRUNCATE TABLE salesrule;
TRUNCATE TABLE salesrule_coupon;
TRUNCATE TABLE salesrule_coupon_usage;
TRUNCATE TABLE salesrule_customer;
TRUNCATE TABLE salesrule_label;
TRUNCATE TABLE salesrule_product_attribute;
TRUNCATE TABLE sales_bestsellers_aggregated_daily;
TRUNCATE TABLE sales_bestsellers_aggregated_monthly;

TRUNCATE TABLE sales_billing_agreement;
TRUNCATE TABLE sales_billing_agreement_order;

TRUNCATE TABLE sales_flat_creditmemo;
TRUNCATE TABLE sales_flat_creditmemo_comment;
TRUNCATE TABLE sales_flat_creditmemo_grid;
TRUNCATE TABLE sales_flat_creditmemo_item;

TRUNCATE TABLE `sales_flat_invoice`;
TRUNCATE TABLE `sales_flat_invoice_comment`;
TRUNCATE TABLE sales_flat_invoice_grid;
TRUNCATE TABLE `sales_flat_invoice_item`;

TRUNCATE TABLE `sales_flat_order`;
TRUNCATE TABLE `sales_flat_order_address`;
TRUNCATE TABLE `sales_flat_order_grid`;
TRUNCATE TABLE `sales_flat_order_item`;
TRUNCATE TABLE `sales_flat_order_payment`;
TRUNCATE TABLE `sales_flat_order_status_history`;

TRUNCATE TABLE `sales_flat_quote`;
TRUNCATE TABLE `sales_flat_quote_address`;
TRUNCATE TABLE `sales_flat_quote_address_item`;
TRUNCATE TABLE `sales_flat_quote_item`;
TRUNCATE TABLE `sales_flat_quote_item_option`;
TRUNCATE TABLE `sales_flat_quote_payment`;
TRUNCATE TABLE `sales_flat_quote_shipping_rate`;

TRUNCATE TABLE `sales_flat_shipment`;
TRUNCATE TABLE `sales_flat_shipment_comment`;
TRUNCATE TABLE `sales_flat_shipment_grid`;
TRUNCATE TABLE `sales_flat_shipment_item`;
TRUNCATE TABLE `sales_flat_shipment_track`;

TRUNCATE TABLE sales_invoiced_aggregated_order;
TRUNCATE TABLE sales_order_aggregated_created;


TRUNCATE TABLE `sales_payment_transaction` ;

TRUNCATE TABLE sales_recurring_profile;
TRUNCATE TABLE sales_recurring_profile_order;
TRUNCATE TABLE sales_refunded_aggregated;
TRUNCATE TABLE sales_shipping_aggregated_order;

TRUNCATE TABLE sendfriend_log;
SET FOREIGN_KEY_CHECKS=1;
