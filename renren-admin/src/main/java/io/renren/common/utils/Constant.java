/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.common.utils;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2016-11-15
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /** 数据权限过滤 */
	public static final String SQL_FILTER = "sql_filter";
	/** 数据已经存在 */
	public static final String EXIST="exist";
	/** 文件格式不正确 */
	public static final String FILE_ERROR="fileError";
	//borrow借 |贷loan|
	/**收入凭证**/
	public  static final String IN_SECOND_CODE_BORROW="112201";
	public  static final String IN_SECOND_NAME_BORROW="应收账款";
	public  static final String IN_SECOND_CODE_LOAN="600101";
	public  static final String IN_SECOND_NAME_LOAN="主营业务收入-运费";
	/**收支凭证**/
	//收入
	public  static final String IN_IN_OUT_SECOND_CODE_BORROW="";
	public  static final String IN_IN_OUT_SECOND_NAME_BORROW="";
	public  static final String IN_IN_OUT_SECOND_CODE_LOAN="";
	public  static final String IN_IN_OUT_SECOND_NAME_LOAN="";
	//支出
	public  static final String OUT_IN_OUT_SECOND_CODE_BORROW="";
	public  static final String OUT_IN_OUT_SECOND_NAME_BORROW="";
	public  static final String OUT_IN_OUT_SECOND_CODE_LOAN="";
	public  static final String OUT_IN_OUT_SECOND_NAME_LOAN="";
	//老板
	public  static final String BOSS_IN_OUT_SECOND_CODE_BORROW="";
	public  static final String BOSS_IN_OUT_SECOND_NAME_BORROW="";
	public  static final String BOSS_IN_OUT_SECOND_CODE_LOAN="";
	public  static final String BOSS_IN_OUT_SECOND_NAME_LOAN="";
	//面单
	public  static final String BILL_IN_OUT_SECOND_CODE_BORROW="";
	public  static final String BILL_IN_OUT_SECOND_NAME_BORROW="";
	public  static final String BILL_IN_OUT_SECOND_CODE_LOAN="";
	public  static final String BILL_IN_OUT_SECOND_NAME_LOAN="";
	
	/**收款凭证**/
	private  static final String DAY_IN_SECOND_CODE="";
	private  static final String DAY_IN_OUT_SECOND_NAME="";
	


	/**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
