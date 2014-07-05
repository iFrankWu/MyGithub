SET FOREIGN_KEY_CHECKS=0;

#清空日志表
TRUNCATE TABLE `log_customer` ;
TRUNCATE TABLE `log_quote` ;
TRUNCATE TABLE `log_summary` ;
TRUNCATE TABLE `log_url` ;
TRUNCATE TABLE `log_url_info` ;
TRUNCATE TABLE `log_visitor` ;
TRUNCATE TABLE `log_visitor_info` ;

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
 
 #删除索引时间表
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

TRUNCATE TABLE  wishlist;
TRUNCATE TABLE  wishlist_item;
TRUNCATE TABLE  wishlist_item_option;
TRUNCATE TABLE newsletter_subscriber;
SET FOREIGN_KEY_CHECKS=1;
 