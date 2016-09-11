/*==============================================================*/
/* dbms name:      mysql 5.0                                    */
/* created on:     2016-6-29 18:28:57   www.roncoo.com          */
/*==============================================================*/


drop table if exists rp_account;

drop table if exists rp_account_history;

drop table if exists rp_pay_product;

drop table if exists rp_pay_way;

drop table if exists rp_sett_daily_collect;

drop table if exists rp_sett_record;

drop table if exists rp_sett_record_annex;

drop table if exists rp_user_bank_account;

drop table if exists rp_user_info;

drop table if exists rp_user_pay_config;

drop table if exists rp_user_pay_info;

drop table if exists rp_account_check_batch;

drop table if exists rp_account_check_mistake;

drop table if exists rp_account_check_mistake_scratch_pool;

drop table if exists rp_notify_record;

drop table if exists rp_notify_record_log;

drop table if exists rp_refund_record;

drop table if exists rp_trade_payment_order;

drop table if exists rp_trade_payment_record;

drop table if exists seq_table;

/*==============================================================*/
/* table: rp_account                                            */
/*==============================================================*/
create table rp_account
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   account_no           varchar(50) not null,
   balance              decimal(20,6) not null,
   unbalance            decimal(20,6) not null,
   security_money       decimal(20,6) not null,
   status               varchar(36) not null,
   total_income         decimal(20,6) not null,
   total_expend         decimal(20,6) not null,
   today_income         decimal(20,6) not null,
   today_expend         decimal(20,6) not null,
   account_type         varchar(50) not null,
   sett_amount          decimal(20,6) not null,
   user_no              varchar(50),
   primary key (id)
);

alter table rp_account comment '资金账户表';

/*==============================================================*/
/* table: rp_account_history                                    */
/*==============================================================*/
create table rp_account_history
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   account_no           varchar(50) not null,
   amount               decimal(20,6) not null,
   balance              decimal(20,6) not null,
   fund_direction       varchar(36) not null,
   is_allow_sett        varchar(36) not null,
   is_complete_sett     varchar(36) not null,
   request_no           varchar(36) not null,
   bank_trx_no          varchar(30),
   trx_type             varchar(36) not null,
   risk_day             int,
   user_no              varchar(50),
   primary key (id)
);

alter table rp_account_history comment '资金账户流水表';

/*==============================================================*/
/* table: rp_pay_product                                        */
/*==============================================================*/
create table rp_pay_product
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   status               varchar(36) not null,
   product_code         varchar(50) not null comment '支付产品编号',
   product_name         varchar(200) not null comment '支付产品名称',
   audit_status         varchar(45),
   primary key (id)
);

alter table rp_pay_product comment '支付产品表';

/*==============================================================*/
/* table: rp_pay_way                                            */
/*==============================================================*/
create table rp_pay_way
(
   id                   varchar(50) not null comment 'id',
   version              bigint not null default 0 comment 'version',
   create_time          datetime not null comment '创建时间',
   edit_time            datetime comment '修改时间',
   pay_way_code         varchar(50) not null comment '支付方式编号',
   pay_way_name         varchar(100) not null comment '支付方式名称',
   pay_type_code        varchar(50) not null comment '支付类型编号',
   pay_type_name        varchar(100) not null comment '支付类型名称',
   pay_product_code     varchar(50) comment '支付产品编号',
   status               varchar(36) not null comment '状态(100:正常状态,101非正常状态)',
   sorts                int default 1000 comment '排序(倒序排序,默认值1000)',
   pay_rate             double not null comment '商户支付费率',
   primary key (id)
);

alter table rp_pay_way comment '支付方式';

/*==============================================================*/
/* table: rp_sett_daily_collect                                 */
/*==============================================================*/
create table rp_sett_daily_collect
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '版本号',
   create_time          datetime not null comment '创建时间',
   edit_time            datetime not null comment '修改时间',
   account_no           varchar(20) not null comment '账户编号',
   user_name            varchar(200) comment '用户姓名',
   collect_date         date not null comment '汇总日期',
   collect_type         varchar(50) not null comment '汇总类型(参考枚举:settdailycollecttypeenum)',
   total_amount         decimal(24,10) not null comment '交易总金额',
   total_count          int not null comment '交易总笔数',
   sett_status          varchar(50) not null comment '结算状态(参考枚举:settdailycollectstatusenum)',
   remark               varchar(300) comment '备注',
   risk_day             int comment '风险预存期天数',
   primary key (id)
);

alter table rp_sett_daily_collect comment '每日待结算汇总';

/*==============================================================*/
/* table: rp_sett_record                                        */
/*==============================================================*/
create table rp_sett_record
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '版本号',
   create_time          datetime not null comment '创建时间',
   edit_time            datetime not null comment '修改时间',
   sett_mode            varchar(50) comment '结算发起方式(参考settmodetypeenum)',
   account_no           varchar(20) not null comment '账户编号',
   user_no              varchar(20) comment '用户编号',
   user_name            varchar(200) comment '用户姓名',
   user_type            varchar(50) comment '用户类型',
   sett_date            date comment '结算日期',
   bank_code            varchar(20) comment '银行编码',
   bank_name            varchar(100) comment '银行名称',
   bank_account_name    varchar(60) comment '开户名',
   bank_account_no      varchar(20) comment '开户账户',
   bank_account_type    varchar(50) comment '开户账户',
   country              varchar(200) comment '开户行所在国家',
   province             varchar(50) comment '开户行所在省份',
   city                 varchar(50) comment '开户行所在城市',
   areas                varchar(50) comment '开户行所在区',
   bank_account_address varchar(300) comment '开户行全称',
   mobile_no            varchar(20) comment '收款人手机号',
   sett_amount          decimal(24,10) comment '结算金额',
   sett_fee             decimal(16,6) comment '结算手续费',
   remit_amount         decimal(16,2) comment '结算打款金额',
   sett_status          varchar(50) comment '结算状态(参考枚举:settrecordstatusenum)',
   remit_confirm_time   datetime comment '打款确认时间',
   remark               varchar(200) comment '描述',
   remit_remark         varchar(200) comment '打款备注',
   operator_loginname   varchar(50) comment '操作员登录名',
   operator_realname    varchar(50) comment '操作员姓名',
   primary key (id)
);

alter table rp_sett_record comment '结算记录';

/*==============================================================*/
/* table: rp_sett_record_annex                                  */
/*==============================================================*/
create table rp_sett_record_annex
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   is_delete            varchar(36) not null,
   annex_name           varchar(200),
   annex_address        varchar(500) not null,
   settlement_id        varchar(50) not null,
   primary key (id)
);

/*==============================================================*/
/* table: rp_user_bank_account                                  */
/*==============================================================*/
create table rp_user_bank_account
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   status               varchar(36) not null,
   user_no              varchar(50) not null,
   bank_name            varchar(200) not null,
   bank_code            varchar(50) not null,
   bank_account_name    varchar(100) not null,
   bank_account_no      varchar(36) not null,
   card_type            varchar(36) not null,
   card_no              varchar(36) not null,
   mobile_no            varchar(50) not null,
   is_default           varchar(36),
   province             varchar(20),
   city                 varchar(20),
   areas                varchar(20),
   street               varchar(300),
   bank_account_type    varchar(36) not null,
   primary key (id)
);

alter table rp_user_bank_account comment '用户银行账户表';

/*==============================================================*/
/* table: rp_user_info                                          */
/*==============================================================*/
create table rp_user_info
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   status               varchar(36) not null,
   user_no              varchar(50),
   user_name            varchar(100),
   account_no           varchar(50) not null,
   primary key (id),
   unique key ak_key_2 (account_no)
);

alter table rp_user_info comment '该表用来存放用户的基本信息';

/*==============================================================*/
/* table: rp_user_pay_config                                    */
/*==============================================================*/
create table rp_user_pay_config
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   status               varchar(36) not null,
   audit_status         varchar(45),
   is_auto_sett         varchar(36) not null default 'no',
   product_code         varchar(50) not null comment '支付产品编号',
   product_name         varchar(200) not null comment '支付产品名称',
   user_no              varchar(50),
   user_name            varchar(100),
   risk_day             int,
   pay_key              varchar(50),
   fund_into_type       varchar(50),
   pay_secret           varchar(50),
   primary key (id)
);

alter table rp_user_pay_config comment '支付设置表';

/*==============================================================*/
/* table: rp_user_pay_info                                      */
/*==============================================================*/
create table rp_user_pay_info
(
   id_                  varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   status               varchar(36) not null,
   app_id               varchar(50) not null,
   app_sectet           varchar(100),
   merchant_id          varchar(50),
   app_type             varchar(50),
   user_no              varchar(50),
   user_name            varchar(100),
   partner_key          varchar(100),
   pay_way_code         varchar(50) not null comment '支付方式编号',
   pay_way_name         varchar(100) not null comment '支付方式名称',
   primary key (id_)
);

alter table rp_user_pay_info comment '该表用来存放用户开通的第三方支付信息';


create table rp_account_check_batch
(
   id                   varchar(50) not null,
   version              int unsigned not null,
   create_time          datetime not null,
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   status               varchar(30) not null,
   remark               varchar(500),
   batch_no             varchar(30) not null,
   bill_date            date not null,
   bill_type            varchar(30),
   handle_status        varchar(10),
   bank_type            varchar(30),
   mistake_count        int(8),
   unhandle_mistake_count int(8),
   trade_count          int(8),
   bank_trade_count     int(8),
   trade_amount         decimal(20,6),
   bank_trade_amount    decimal(20,6),
   refund_amount        decimal(20,6),
   bank_refund_amount   decimal(20,6),
   bank_fee             decimal(20,6),
   org_check_file_path  varchar(500),
   release_check_file_path varchar(500),
   release_status       varchar(15),
   check_fail_msg       varchar(300),
   bank_err_msg         varchar(300),
   primary key (id)
);

alter table rp_account_check_batch comment '对账批次表 rp_account_check_batch';

create table rp_account_check_mistake
(
   id                   varchar(50) not null,
   version              int unsigned not null,
   create_time          datetime not null,
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   status               varchar(30),
   remark               varchar(500),
   account_check_batch_no varchar(50) not null,
   bill_date            date not null,
   bank_type            varchar(30) not null,
   order_time           datetime,
   merchant_name        varchar(100),
   merchant_no          varchar(50),
   order_no             varchar(40),
   trade_time           datetime,
   trx_no               varchar(20),
   order_amount         decimal(20,6),
   refund_amount        decimal(20,6),
   trade_status         varchar(30),
   fee                  decimal(20,6),
   bank_trade_time      datetime,
   bank_order_no        varchar(40),
   bank_trx_no          varchar(40),
   bank_trade_status    varchar(30),
   bank_amount          decimal(20,6),
   bank_refund_amount   decimal(20,6),
   bank_fee             decimal(20,6),
   err_type             varchar(30) not null,
   handle_status        varchar(10) not null,
   handle_value         varchar(1000),
   handle_remark        varchar(1000),
   operator_name        varchar(100),
   operator_account_no  varchar(50),
   primary key (id)
);

alter table rp_account_check_mistake comment '对账差错表 rp_account_check_mistake';

create table rp_account_check_mistake_scratch_pool
(
   id                   varchar(50) not null,
   version              int unsigned not null,
   create_time          datetime not null,
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   product_name         varchar(50) comment '商品名称',
   merchant_order_no    varchar(30) not null comment '商户订单号',
   trx_no               char(20) not null comment '支付流水号',
   bank_order_no        char(20) comment '银行订单号',
   bank_trx_no          varchar(30) comment '银行流水号',
   order_amount         decimal(20,6) default 0 comment '订单金额',
   plat_income          decimal(20,6) comment '平台收入',
   fee_rate             decimal(20,6) comment '费率',
   plat_cost            decimal(20,6) comment '平台成本',
   plat_profit          decimal(20,6) comment '平台利润',
   status               varchar(30) comment '状态(参考枚举:paymentrecordstatusenum)',
   pay_way_code         varchar(50) comment '支付通道编号',
   pay_way_name         varchar(100) comment '支付通道名称',
   pay_success_time     datetime comment '支付成功时间',
   complete_time        datetime comment '完成时间',
   is_refund            varchar(30) default '101' comment '是否退款(100:是,101:否,默认值为:101)',
   refund_times         smallint default 0 comment '退款次数(默认值为:0)',
   success_refund_amount decimal(20,6) comment '成功退款总金额',
   remark               varchar(500) comment '备注',
   batch_no             varchar(50),
   bill_date            datetime
);

alter table rp_account_check_mistake_scratch_pool comment '差错暂存池';

create table rp_notify_record
(
   id                   varchar(50) not null,
   version              int not null,
   create_time          datetime not null,
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   notify_times         int not null,
   limit_notify_times   int not null,
   url                  varchar(2000) not null,
   merchant_order_no    varchar(50) not null,
   merchant_no          varchar(50) not null,
   status               varchar(50) not null comment '100:成功 101:失败',
   notify_type          varchar(30) comment '通知类型',
   primary key (id),
   key ak_key_2 (merchant_order_no)
);

alter table rp_notify_record comment '通知记录表 rp_notify_record';

create table rp_notify_record_log
(
   id                   varchar(50) not null,
   version              int not null,
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   create_time          datetime not null,
   notify_id            varchar(50) not null,
   request              varchar(2000) not null,
   response             varchar(2000) not null,
   merchant_no          varchar(50) not null,
   merchant_order_no    varchar(50) not null comment '商户订单号',
   http_status          varchar(50) not null comment 'http状态',
   primary key (id)
);

alter table rp_notify_record_log comment '通知记录日志表 rp_notify_record_log';

create table rp_refund_record
(
   id                   varchar(50) not null comment 'id',
   version              int not null comment '版本号',
   create_time          datetime comment '创建时间',
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   org_merchant_order_no varchar(50) comment '原商户订单号',
   org_trx_no           varchar(50) comment '原支付流水号',
   org_bank_order_no    varchar(50) comment '原银行订单号',
   org_bank_trx_no      varchar(50) comment '原银行流水号',
   merchant_name        varchar(100) comment '商家名称',
   merchant_no          varchar(100) not null comment '商家编号',
   org_product_name     varchar(60) comment '原商品名称',
   org_biz_type         varchar(30) comment '原业务类型',
   org_payment_type     varchar(30) comment '原支付方式类型',
   refund_amount        decimal(20,6) comment '订单退款金额',
   refund_trx_no        varchar(50) not null comment '退款流水号',
   refund_order_no      varchar(50) not null comment '退款订单号',
   bank_refund_order_no varchar(50) comment '银行退款订单号',
   bank_refund_trx_no   varchar(30) comment '银行退款流水号',
   result_notify_url    varchar(500) comment '退款结果通知url',
   refund_status        varchar(30) comment '退款状态',
   refund_from          varchar(30) comment '退款来源（分发平台）',
   refund_way           varchar(30) comment '退款方式',
   refund_request_time  datetime comment '退款请求时间',
   refund_success_time  datetime comment ' 退款成功时间',
   refund_complete_time datetime comment '退款完成时间',
   request_apply_user_id varchar(50) comment '退款请求,申请人登录名',
   request_apply_user_name varchar(90) comment '退款请求,申请人姓名',
   refund_reason        varchar(500) comment '退款原因',
   remark               varchar(3000) comment '备注',
   primary key (id),
   unique key ak_key_2 (refund_trx_no)
);

alter table rp_refund_record comment '退款记录表';

create table rp_trade_payment_order
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '版本号',
   create_time          datetime not null comment '创建时间',
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   status               varchar(50) comment '状态(参考枚举:orderstatusenum)',
   product_name         varchar(300) comment '商品名称',
   merchant_order_no    varchar(30) not null comment '商户订单号',
   order_amount         decimal(20,6) default 0 comment '订单金额',
   order_from           varchar(30) comment '订单来源',
   merchant_name        varchar(100) comment '商家名称',
   merchant_no          varchar(100) not null comment '商户编号',
   order_time           datetime comment '下单时间',
   order_date           date comment '下单日期',
   order_ip             varchar(50) comment '下单ip(客户端ip,在网关页面获取)',
   order_referer_url    varchar(100) comment '从哪个页面链接过来的(可用于防诈骗)',
   return_url           varchar(600) comment '页面回调通知url',
   notify_url           varchar(600) comment '后台异步通知url',
   cancel_reason        varchar(600) comment '订单撤销原因',
   order_period         smallint comment '订单有效期(单位分钟)',
   expire_time          datetime comment '到期时间',
   pay_way_code         varchar(50) comment '支付方式编号',
   pay_way_name         varchar(100) comment '支付方式名称',
   remark               varchar(200) comment '支付备注',
   trx_type             varchar(30) comment '交易业务类型  ：消费、充值等',
   trx_no               varchar(50) comment '支付流水号',
   pay_type_code        varchar(50) comment '支付类型编号',
   pay_type_name        varchar(100) comment '支付类型名称',
   fund_into_type       varchar(30) comment '资金流入类型',
   is_refund            varchar(30) default '101' comment '是否退款(100:是,101:否,默认值为:101)',
   refund_times         int default 0 comment '退款次数(默认值为:0)',
   success_refund_amount decimal(20,6) comment '成功退款总金额',
   field1               varchar(500),
   field2               varchar(500),
   field3               varchar(500),
   field4               varchar(500),
   field5               varchar(500),
   primary key (id),
   unique key ak_key_2 (merchant_order_no, merchant_no)
);

alter table rp_trade_payment_order comment 'roncoo pay 龙果支付 支付订单表';

create table rp_trade_payment_record
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '版本号',
   create_time          datetime comment '创建时间',
   status               varchar(30) comment '状态(参考枚举:paymentrecordstatusenum)',
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   product_name         varchar(50) comment '商品名称',
   merchant_order_no    varchar(50) not null comment '商户订单号',
   trx_no               varchar(50) not null comment '支付流水号',
   bank_order_no        varchar(50) comment '银行订单号',
   bank_trx_no          varchar(50) comment '银行流水号',
   merchant_name        varchar(300) comment '商家名称',
   merchant_no          varchar(50) not null comment '商家编号',
   payer_user_no        varchar(50) comment '付款人编号',
   payer_name           varchar(60) comment '付款人名称',
   payer_pay_amount     decimal(20,6) default 0 comment '付款方支付金额',
   payer_fee            decimal(20,6) default 0 comment '付款方手续费',
   payer_account_type   varchar(30) comment '付款方账户类型(参考账户类型枚举:accounttypeenum)',
   receiver_user_no     varchar(15) comment '收款人编号',
   receiver_name        varchar(60) comment '收款人名称',
   receiver_pay_amount  decimal(20,6) default 0 comment '收款方支付金额',
   receiver_fee         decimal(20,6) default 0 comment '收款方手续费',
   receiver_account_type varchar(30) comment '收款方账户类型(参考账户类型枚举:accounttypeenum)',
   order_ip             varchar(30) comment '下单ip(客户端ip,从网关中获取)',
   order_referer_url    varchar(100) comment '从哪个页面链接过来的(可用于防诈骗)',
   order_amount         decimal(20,6) default 0 comment '订单金额',
   plat_income          decimal(20,6) comment '平台收入',
   fee_rate             decimal(20,6) comment '费率',
   plat_cost            decimal(20,6) comment '平台成本',
   plat_profit          decimal(20,6) comment '平台利润',
   return_url           varchar(600) comment '页面回调通知url',
   notify_url           varchar(600) comment '后台异步通知url',
   pay_way_code         varchar(50) comment '支付方式编号',
   pay_way_name         varchar(100) comment '支付方式名称',
   pay_success_time     datetime comment '支付成功时间',
   complete_time        datetime comment '完成时间',
   is_refund            varchar(30) default '101' comment '是否退款(100:是,101:否,默认值为:101)',
   refund_times         int default 0 comment '退款次数(默认值为:0)',
   success_refund_amount decimal(20,6) comment '成功退款总金额',
   trx_type             varchar(30) comment '交易业务类型  ：消费、充值等',
   order_from           varchar(30) comment '订单来源',
   pay_type_code        varchar(50) comment '支付类型编号',
   pay_type_name        varchar(100) comment '支付类型名称',
   fund_into_type       varchar(30) comment '资金流入类型',
   remark               varchar(3000) comment '备注',
   field1               varchar(500),
   field2               varchar(500),
   field3               varchar(500),
   field4               varchar(500),
   field5               varchar(500),
   bank_return_msg      varchar(2000) comment '银行返回信息',
   primary key (id),
   unique key ak_key_2 (trx_no)
);

alter table rp_trade_payment_record comment '支付记录表';

CREATE TABLE seq_table (SEQ_NAME varchar(50) NOT NULL, CURRENT_VALUE bigint DEFAULT '1000000002' NOT NULL, INCREMENT smallint DEFAULT '1' NOT NULL, REMARK varchar(100) NOT NULL, PRIMARY KEY (SEQ_NAME)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('ACCOUNT_NO_SEQ', 10000000, 1, '账户--账户编号');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('ACTIVITY_NO_SEQ', 10000006, 1, '活动--活动编号');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('USER_NO_SEQ', 10001113, 1, '用户--用户编号');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('TRX_NO_SEQ', 10000000, 1, '交易—-支付流水号');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('BANK_ORDER_NO_SEQ', 10000000, 1, '交易—-银行订单号');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('RECONCILIATION_BATCH_NO_SEQ', 10000000, 1, '对账—-批次号');

/*==============================================================*/
/* create function                                              */
/*==============================================================*/
CREATE FUNCTION `FUN_SEQ`(SEQ VARCHAR(50)) RETURNS BIGINT(20)
BEGIN
     UPDATE SEQ_TABLE
     SET CURRENT_VALUE = CURRENT_VALUE + INCREMENT
     WHERE  SEQ_NAME=SEQ;
     RETURN FUN_SEQ_CURRENT_VALUE(SEQ);
END;


CREATE FUNCTION `FUN_SEQ_CURRENT_VALUE`(SEQ VARCHAR(50)) RETURNS BIGINT(20)
BEGIN
    DECLARE VALUE INTEGER;
    SET VALUE=0;
    SELECT CURRENT_VALUE INTO VALUE
    FROM SEQ_TABLE 
    WHERE SEQ_NAME=SEQ;
    RETURN VALUE;
END;

CREATE FUNCTION `FUN_SEQ_SET_VALUE`(SEQ VARCHAR(50), VALUE INTEGER) RETURNS BIGINT(20)
BEGIN
     UPDATE SEQ_TABLE 
     SET CURRENT_VALUE=VALUE
     WHERE SEQ_NAME=SEQ;
     RETURN FUN_SEQ_CURRENT_VALUE(SEQ);
END;

CREATE FUNCTION  FUN_NOW()
 RETURNS DATETIME
BEGIN 
RETURN now();
END;


-- 时间函数

CREATE FUNCTION `FUN_DATE_ADD`(STR_DATE VARCHAR(10), STR_INTERVAL INTEGER) RETURNS DATE
BEGIN
     RETURN date_add(STR_DATE, INTERVAL STR_INTERVAL DAY);
END;






-- -----------------------------------------------------------------------------------------------------------------------------------
--                                   注意：该脚本运行在mysql环境下，如果是其他数据库，如有需要请先修改，再执行。                    --
--                                                                                           编写人：沈佳龙   （www.roncoo.com）    --
-- -----------------------------------------------------------------------------------------------------------------------------------

drop table if exists pms_menu;

drop table if exists pms_menu_role;

drop table if exists pms_operator;

drop table if exists pms_operator_log;

drop table if exists pms_permission;

drop table if exists pms_role;

drop table if exists pms_role_operator;

drop table if exists pms_role_permission;

create table pms_menu
(
   id                   bigint not null auto_increment,
   version              bigint not null,
   creater              varchar(50) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20) not null,
   remark               varchar(300),
   is_leaf              varchar(20),
   level                smallint,
   parent_id            bigint not null,
   target_name          varchar(100),
   number               varchar(20),
   name                 varchar(100),
   url                  varchar(100),
   primary key (id)
)auto_increment = 1000;

alter table pms_menu comment '菜单表';


alter table pms_menu comment '菜单表';

create table pms_menu_role
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint,
   creater              varchar(50) comment '创建人',
   create_time          datetime comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20),
   remark               varchar(300),
   role_id              bigint not null,
   menu_id              bigint not null,
   primary key (id),
   key ak_key_2 (role_id, menu_id)
) auto_increment = 1000;

alter table pms_menu_role comment '权限与角色关联表';

create table pms_operator
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint not null,
   creater              varchar(50) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20) not null,
   remark               varchar(300),
   real_name            varchar(50) not null,
   mobile_no            varchar(15) not null,
   login_name           varchar(50) not null,
   login_pwd            varchar(256) not null,
   type                 varchar(20) not null,
   salt                 varchar(50) not null,
   primary key (id),
   key ak_key_2 (login_name)
) auto_increment = 1000;

alter table pms_operator comment '操作员表';

create table pms_operator_log
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint not null,
   creater              varchar(50) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20) not null,
   remark               varchar(300),
   operator_id          bigint not null,
   operator_name        varchar(50) not null,
   operate_type         varchar(50) not null comment '操作类型（1:增加，2:修改，3:删除，4:查询）',
   ip                   varchar(100) not null,
   content              varchar(3000),
   primary key (id)
) auto_increment = 1000;

alter table pms_operator_log comment '权限_操作员操作日志表';

create table pms_permission
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint not null,
   creater              varchar(50) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20) not null,
   remark               varchar(300),
   permission_name      varchar(100) not null,
   permission           varchar(100) not null,
   primary key (id),
   key ak_key_2 (permission),
   key ak_key_3 (permission_name)
) auto_increment = 1000;

alter table pms_permission comment '权限表';

create table pms_role
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint,
   creater              varchar(50) comment '创建人',
   create_time          datetime comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20),
   remark               varchar(300),
   role_code            varchar(20) not null comment '角色类型（1:超级管理员角色，0:普通操作员角色）',
   role_name            varchar(100) not null,
   primary key (id),
   key ak_key_2 (role_name)
) auto_increment = 1000;

alter table pms_role comment '角色表';

create table pms_role_operator
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint not null,
   creater              varchar(50) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20) not null,
   remark               varchar(300),
   role_id              bigint not null,
   operator_id          bigint not null,
   primary key (id),
   key ak_key_2 (role_id, operator_id)
) auto_increment = 1000;

alter table pms_role_operator comment '操作员与角色关联表';

create table pms_role_permission
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint,
   creater              varchar(50) comment '创建人',
   create_time          datetime comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20),
   remark               varchar(300),
   role_id              bigint not null,
   permission_id        bigint not null,
   primary key (id),
   key ak_key_2 (role_id, permission_id)
) auto_increment = 1000;

alter table pms_role_permission comment '权限与角色关联表';





-- ------------------------------step 1  菜单-------------------------------------------------
-- 菜单初始化数据
--  -- 菜单的初始化数据
insert into pms_menu (id,version,status,creater,create_time, editor, edit_time, remark, name, url, number, is_leaf, level, parent_id, target_name) values 
(1,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '权限管理', '##', '001', 'NO', 1, 0, '#'),
(2,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '菜单管理', 'pms/menu/list', '00101', 'YES', 2, 1, 'cdgl'),
(3,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '权限管理', 'pms/permission/list', '00102', 'YES',2, 1, 'qxgl'),
(4,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '角色管理', 'pms/role/list', '00103', 'YES',2, 1, 'jsgl'),
(5,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '操作员管理', 'pms/operator/list', '00104', 'YES',2, 1, 'czygl'),

(10,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '账户管理', '##', '002', 'NO', 1, 0, '#'),
(12,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '账户信息', 'account/list', '00201', 'YES',2, 10, 'zhxx'),
(13,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '账户历史信息', 'account/historyList', '00202', 'YES',2, 10, 'zhlsxx'),


(20,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '用户管理', '##', '003', 'NO', 1, 0, '#'),
(22,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '用户信息', 'user/info/list', '00301', 'YES',2, 20, 'yhxx'),

(30,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '支付管理', '##', '004', 'NO', 1, 0, '#'),
(32,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '支付产品信息', 'pay/product/list', '00401', 'YES',2, 30, 'zfcpgl'),
(33,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '用户支付配置', 'pay/config/list', '00402', 'YES',2, 30, 'yhzfpz'),

(40,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '交易管理', '##', '005', 'NO', 1, 0, '#'),
(42,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '支付订单管理', 'trade/listPaymentOrder', '00501', 'YES',2, 40, 'zfddgl'),
(43,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '支付记录管理', 'trade/listPaymentRecord', '00502', 'YES',2, 40, 'zfjjgl'),

(50,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '结算管理', '##', '006', 'NO', 1, 0, '#'),
(52,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '结算记录管理', 'sett/list', '00601', 'YES',2, 50, 'jsjlgl'),

(60,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '对账管理', '##', '007', 'NO', 1, 0, '#'),
(62,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '对账差错列表', 'reconciliation/list/mistake', '00701', 'YES',2, 60, 'dzcclb'),
(63,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '对账批次列表', 'reconciliation/list/checkbatch', '00702', 'YES',2, 60, 'dzpclb'),
(64,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '对账缓冲池列表', 'reconciliation/list/scratchPool', '00703', 'YES',2, 60, 'dzhcclb');

-- ------------------------------step 2：权限功能点-------------------------------------------------
-- 权限功能点的初始化数据


insert into pms_permission (id,version,status,creater,create_time, editor, edit_time, remark, permission_name, permission) values 
 (1, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-菜单-查看','权限管理-菜单-查看','pms:menu:view'),
 (2, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-菜单-添加','权限管理-菜单-添加','pms:menu:add'),
 (3, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-菜单-查看','权限管理-菜单-修改','pms:menu:edit'),
 (4, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-菜单-删除','权限管理-菜单-删除','pms:menu:delete'),

 (11, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-权限-查看','权限管理-权限-查看','pms:permission:view'),
 (12, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-权限-添加','权限管理-权限-添加','pms:permission:add'),
 (13, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-权限-修改','权限管理-权限-修改','pms:permission:edit'),
 (14, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-权限-删除','权限管理-权限-删除','pms:permission:delete'),

 (21, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-角色-查看','权限管理-角色-查看','pms:role:view'),
 (22, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-角色-添加','权限管理-角色-添加','pms:role:add'),
 (23, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-角色-修改','权限管理-角色-修改','pms:role:edit'),
 (24, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-角色-删除','权限管理-角色-删除','pms:role:delete'),
 (25, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-角色-分配权限','权限管理-角色-分配权限','pms:role:assignpermission'),

 (31, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-操作员-查看','权限管理-操作员-查看','pms:operator:view'),
 (32, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-操作员-添加','权限管理-操作员-添加','pms:operator:add'),
 (33, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-操作员-查看','权限管理-操作员-修改','pms:operator:edit'),
 (34, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-操作员-冻结与解冻','权限管理-操作员-冻结与解冻','pms:operator:changestatus'),
 (35, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-操作员-重置密码','权限管理-操作员-重置密码','pms:operator:resetpwd'),


 (51, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','账户管理-账户-查看','账户管理-账户-查看','account:accountInfo:view'),
 (52, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','账户管理-账户-添加','账户管理-账户-添加','account:accountInfo:add'),
 (53, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','账户管理-账户-查看','账户管理-账户-修改','account:accountInfo:edit'),
 (54, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','账户管理-账户-删除','账户管理-账户-删除','account:accountInfo:delete'),

 (61, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','账户管理-账户历史-查看','账户管理-账户历史-查看','account:accountHistory:view'),

 (71, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','用户管理-用户信息-查看','用户管理-用户信息-查看','user:userInfo:view'),
 (72, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','用户管理-用户信息-添加','用户管理-用户信息-添加','user:userInfo:add'),
 (73, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','用户管理-用户信息-查看','用户管理-用户信息-修改','user:userInfo:edit'),
 (74, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','用户管理-用户信息-删除','用户管理-用户信息-删除','user:userInfo:delete'),

 (81, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付产品-查看','支付管理-支付产品-查看','pay:product:view'),
 (82, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付产品-添加','支付管理-支付产品-添加','pay:product:add'),
 (83, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付产品-查看','支付管理-支付产品-修改','pay:product:edit'),
 (84, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付产品-删除','支付管理-支付产品-删除','pay:product:delete'),

 (85, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付方式-查看','支付管理-支付方式-查看','pay:way:view'),
 (86, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付方式-添加','支付管理-支付方式-添加','pay:way:add'),
 (87, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付方式-查看','支付管理-支付方式-修改','pay:way:edit'),
 (88, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付方式-删除','支付管理-支付方式-删除','pay:way:delete'),

 (91, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付配置-查看','支付管理-支付配置-查看','pay:config:view'),
 (92, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付配置-添加','支付管理-支付配置-添加','pay:config:add'),
 (93, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付配置-查看','支付管理-支付配置-修改','pay:config:edit'),
 (94, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付配置-删除','支付管理-支付配置-删除','pay:config:delete'),

 (101, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-订单-查看','交易管理-订单-查看','trade:order:view'),
 (102, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-订单-添加','交易管理-订单-添加','trade:order:add'),
 (103, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-订单-查看','交易管理-订单-修改','trade:order:edit'),
 (104, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-订单-删除','交易管理-订单-删除','trade:order:delete'),

 (111, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-记录-查看','交易管理-记录-查看','trade:record:view'),
 (112, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-记录-添加','交易管理-记录-添加','trade:record:add'),
 (113, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-记录-查看','交易管理-记录-修改','trade:record:edit'),
 (114, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-记录-删除','交易管理-记录-删除','trade:record:delete'),

 (121, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','结算管理-记录-查看','结算管理-记录-查看','sett:record:view'),
 (122, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','结算管理-记录-添加','结算管理-记录-添加','sett:record:add'),
 (123, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','结算管理-记录-查看','结算管理-记录-修改','sett:record:edit'),
 (124, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','结算管理-记录-删除','结算管理-记录-删除','sett:record:delete'),

 (131, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-差错-查看','对账管理-差错-查看','recon:mistake:view'),
 (132, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-差错-添加','对账管理-差错-添加','recon:mistake:add'),
 (133, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-差错-查看','对账管理-差错-修改','recon:mistake:edit'),
 (134, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-差错-删除','对账管理-差错-删除','recon:mistake:delete'),

 (141, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-批次-查看','对账管理-批次-查看','recon:batch:view'),
 (142, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-批次-添加','对账管理-批次-添加','recon:batch:add'),
 (143, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-批次-查看','对账管理-批次-修改','recon:batch:edit'),
 (144, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-批次-删除','对账管理-批次-删除','recon:batch:delete'),

 (151, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-缓冲池-查看','对账管理-缓冲池-查看','recon:scratchPool:view'),
 (152, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-缓冲池-添加','对账管理-缓冲池-添加','recon:scratchPool:add'),
 (153, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-缓冲池-查看','对账管理-缓冲池-修改','recon:scratchPool:edit'),
 (154, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-缓冲池-删除','对账管理-缓冲池-删除','recon:scratchPool:delete');

-- -----------------------------------step3：操作员--------------------------------------------
-- -- 操作员的初始化数据
--  admin 超级管理员
insert into pms_operator (id,version,status,creater,create_time, editor, edit_time, remark, login_name, login_pwd,real_name,mobile_no,type,salt) 
values (1, 0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '超级管理员', 'admin', 'd3c59d25033dbf980d29554025c23a75','超级管理员', '18620936193', 'ADMIN','8d78869f470951332959580424d4bf4f');

--  guest  游客
insert into pms_operator (id,version,status,creater,create_time, editor, edit_time, remark, login_name, login_pwd,real_name,mobile_no,type,salt) 
values (2, 0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'guest','2016-06-03 11:07:43', '游客', 'guest', '3f0dbf580ee39ec03b632cb33935a363','游客', '18926215592', 'USER','183d9f2f0f2ce760e98427a5603d1c73');

-- ------------------------------------step4：角色-------------------------------------------
-- -- 角色的初始化数据
insert into pms_role (id,version,status,creater,create_time, editor, edit_time, remark, role_code, role_name) 
values (1, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43','超级管理员角色','admin', '超级管理员角色');

insert into pms_role (id,version,status,creater,create_time, editor, edit_time, remark, role_code, role_name) 
values (2, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'guest', '2016-06-03 11:07:43','游客角色','guest', '游客角色');

-- ------------------------------------step5：操作员和角色关联-------------------------------------------
-- -- 操作员与角色关联的初始化数据

--  admin  关联admin 和test两个角色
insert into pms_role_operator (id,version,status,creater,create_time, editor, edit_time, remark,role_id,operator_id) values (1, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',1,1);
insert into pms_role_operator (id,version,status,creater,create_time, editor, edit_time, remark,role_id,operator_id) values (2, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,1);

-- guest  关联游客角色  （游客角色只有查看交易记录的信息）
insert into pms_role_operator (id,version,status,creater,create_time, editor, edit_time, remark,role_id,operator_id) values (3, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,2);

-- -------------------------------------step6：角色和权限关联------------------------------------------
-- -- 角色与用户功能点关联的初始化数据

-- admin（拥有所有的权限点）
insert into pms_role_permission  (role_id, permission_id) select 1,id from PMS_PERMISSION;


-- guest （只有所有的查看权限）
insert into pms_role_permission (version,status,creater,create_time, editor, edit_time, remark,role_id,permission_id) 
values 
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,1),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,11),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,21),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,31),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,41),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,51),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,61),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,71),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,81),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,85),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,91),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,101),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,111),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,121),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,131),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,141),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,151);

-- -------------------------------------step7：角色和菜单关联------------------------------------------
--  角色与信息关联初始化数据
-- admin

insert into pms_menu_role(role_id, menu_id) select 1,id from PMS_MENU;

-- guest  所有的菜单（只有查看权限）
insert into pms_menu_role (role_id, menu_id) select 2,id from PMS_MENU;

-- 2016.8.5 第三方支付信息表增加支付宝线下产品字段
alter table rp_user_pay_info add offline_app_id varchar(50);
alter table rp_user_pay_info add rsa_private_key varchar(100);
alter table rp_user_pay_info add rsa_public_key varchar(100); 

-- 2016.9.5 增加登录信息字段
alter table rp_user_info add mobile varchar(15);
alter table rp_user_info add password varchar(50);
