
package cn.neusoft.xuxiao.constants;

import java.util.HashMap;
import java.util.Map;


public final class ServiceResponseCode {
	/***************** errorcode.properties中定义的常量   **********************/
	/**
	 * 成功.
	 */
	public static final int OK = 200;
	
	/**
	 * 未登录.
	 */
	public static final int NOT_LOG_IN = 401;
	/**
	 * 用户输入账户或密码有误.
	 */
	public static final int ACCOUNT_OR_PASSWORD_ERROR = 402;
	/**
	 * 无创建操作权限.
	 */
	public static final int NO_CREATE_PERMISSION = 403;
	/**
	 * 未找到文档.
	 */
	public static final int NOT_FOUND_DOCUMENT = 404;
	/**
	 * 查询数据为空.
	 */
	public static final int EMPTY_QUERY_PARAMETER = 408;
	/**
	 * 请求参数错误.
	 */
	public static final int ERROR_REQUEST_PARAMETER = 409;
	/**
	 * 找不到相关服务.
	 */
	public static final int NOT_FOUND_SERVICE = 410;
	/**
	 * 无删除文件权限.
	 */
	public static final int NO_DELETE_FILE_PERMISSION = 413;
	/**
	 * 无下载文件权限.
	 */
	public static final int NO_DOWNLOAD_FILES_PERMISSION = 418;
	/**
	 * 无修改权限.
	 */
	public static final int NO_UPDATE_PERMISSION = 420;
	/***************** 非errorcode.properties中定义的常量 start  **********************/
	/**
	 * 服务器异常,只能用在全局异常捕获出,其他地方禁止使用,日志输出该状态码后会发送监控邮件.
	 */
	public static final int SERVER_ERROR = 500;
	/**
	 * 服务器内部异常,非接口调用异常(内部使用不暴露给用户),而是定时邮件,异步消息等异常,日志输出该状态码会发送监控邮件.
	 */
	public static final int SERVER_INNER_ERROR = 501;
	/**
	 * 服务器接口调用异常,该异常被全局异常捕获后,输出该日志该状态码后会发送监控邮件.
	 */
	public static final int SERVER_INTERFACE_ERROR = 502;
	/**
	 * 业务异常.
	 */
	public static final int BUSINESS_EXCEPTION = 201;
	/**
	 * 已经报名
	 */
	public static final int HAVEJOIN_EXCEPTION = 202;
	/**
	 * 无查看记录权限.
	 */
	public static final int NO_VIEW_RECORD_PERMISSION = 424;
	/***************** 非errorcode.properties中定义的常量 end  **********************/
	
	/**
	 * 状态码数值与中文对应map
	 */
	public static final Map<Integer, String> STATUS_CODE_MAP = new HashMap<Integer, String>(){
		/**
		 * serialVersionUID:序列化版本号.
		 * @since JDK 1.7
		*/
		private static final long serialVersionUID = 6969714571816404982L;
		{
			put(OK, "成功.");
			put(NOT_LOG_IN, "未登录.");
			put(ACCOUNT_OR_PASSWORD_ERROR, "用户输入账户或密码有误.");
			put(NO_CREATE_PERMISSION, "无创建操作权限.");
			put(NOT_FOUND_DOCUMENT, "未找到文档.");
			put(EMPTY_QUERY_PARAMETER, "查询数据为空.");
			put(ERROR_REQUEST_PARAMETER, "请求参数错误.");
			put(NOT_FOUND_SERVICE, "找不到相关服务.");
			put(NO_DELETE_FILE_PERMISSION, "无删除文件权限.");
			put(NO_DOWNLOAD_FILES_PERMISSION, "无下载文件权限.");
			put(NO_UPDATE_PERMISSION, "无修改权限.");
			put(SERVER_ERROR, "服务器异常.");
			put(SERVER_INNER_ERROR, "服务器内部异常.");
			put(SERVER_INTERFACE_ERROR, "服务器接口调用异常.");
			put(BUSINESS_EXCEPTION, "业务异常.");
			put(NO_VIEW_RECORD_PERMISSION, "无查看记录权限.");
		}
	};
}