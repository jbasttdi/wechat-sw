package com.desksoft.wechat.common.config;

import org.apache.commons.lang.StringUtils;

import com.desksoft.wechat.common.model._MappingKit;
import com.desksoft.wechat.common.plugin.QuartzPlugin;
import com.desksoft.wechat.common.plugin.quartzJob.DailyCheckJob;
import com.desksoft.wechat.common.plugin.quartzJob.DailyShutOffWaterJob;
import com.desksoft.wechat.common.plugin.quartzJob.MonthInfoJob;
import com.desksoft.wechat.common.plugin.quartzJob.MonthRePayFeeJob;
import com.desksoft.wechat.controller.admin.AdminUserController;
import com.desksoft.wechat.controller.admin.IndexController;
import com.desksoft.wechat.controller.bindingUser.BindingUserController;
import com.desksoft.wechat.controller.bindingUser.MenuScoreController;
import com.desksoft.wechat.controller.bindingUser.SendShutWaterInfoInfoController;
import com.desksoft.wechat.controller.microMsg.MyUserWaterController;
import com.desksoft.wechat.controller.microMsg.RedirectUri;
import com.desksoft.wechat.controller.microMsg.ShareController;
import com.desksoft.wechat.controller.microMsg.WechatMenuController;
import com.desksoft.wechat.controller.microMsg.WechatMsgController;
import com.desksoft.wechat.controller.microMsg.WechatUserController;
import com.desksoft.wechat.controller.microMsg.XyBankPayController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.json.JacksonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

import freemarker.template.TemplateModelException;

/**
 * JfinalWechatConfig 核心部分
 * @author joker
 *
 */
public class MicroMsgJfinalConfig extends JFinalConfig {

	private String CONTEXT_PATH = "";
	
	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * @param pro 生产环境配置文件
	 * @param dev 开发环境配置文件
	 */
	public void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		}
		catch (Exception e) {
			PropKit.use(dev);
		}
	}
	
	@Override
	public void configConstant(Constants me) {
		
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		loadProp("jfinal_config_pro.txt", "jfinal_config.txt");
		//PropKit.use("jfinal_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setEncoding("utf-8");
		//me.setViewType(ViewType.JSP);
		
		
		// ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
		ApiConfigKit.setDevMode(me.getDevMode());
		me.setBaseViewPath("/pages");
		me.setJsonFactory(new JacksonFactory());
		me.setError404View("/pages/admin/admin-404.html");
		
	}
	
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class,"/index");	// 第三个参数为该Controller的视图存放路径
		me.add("/admin", AdminUserController.class);			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
		me.add("/bindingUser",BindingUserController.class,"/user");
		me.add("/usedWater",MyUserWaterController.class,"/user");
		me.add("/shutOffWater",SendShutWaterInfoInfoController.class);
		me.add("/myScore", MenuScoreController.class,"/user");
		
		//微信部分
		me.add("/pay",XyBankPayController.class);
		me.add("/msg", WechatMsgController.class);
		me.add("/api", WechatMenuController.class, "/api");
		//me.add("/payi", WechatPayController.class);
		me.add("/oauth2",RedirectUri.class);
		me.add("/user",WechatUserController.class);
		me.add("/jssdk",ShareController.class,"_front");
		
	}
	
	public static C3p0Plugin createC3p0Plugin() {
		return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		
		// 配置C3p0数据库连接池插件
		C3p0Plugin C3p0Plugin = createC3p0Plugin();
		me.add(C3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(C3p0Plugin);
		me.add(arp);
		
		// 所有配置在 MappingKit 中搞定
		_MappingKit.mapping(arp);
		
		//定时任务 用于对账  ,月中账单，月底欠费
		//DailyCheckJob.class,MonthInfoJob.class,MonthRePayFeeJob.class
		QuartzPlugin quartzPlugin =  new QuartzPlugin(MonthInfoJob.class,MonthRePayFeeJob.class,DailyShutOffWaterJob.class,DailyCheckJob.class);
		quartzPlugin.start();
		//me.add(quartzPlugin);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new GlobalInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	
	/**
	 * 配置全局变量
	 */
	public void afterJFinalStart(){
		
		//fm 的全局变量
		if(StringUtils.isNotBlank(CONTEXT_PATH)){
			return; 
		}
		String devType = PropKit.get("devType");
		if(StringUtils.isBlank(devType)){
			return;
		}
		if("dev".equalsIgnoreCase(devType))
			CONTEXT_PATH = PropKit.get("CONTEXT_PATH_DEV");
		else if("test".equalsIgnoreCase(devType))
			CONTEXT_PATH = PropKit.get("CONTEXT_PATH_TEST");
		else if("online".equalsIgnoreCase(devType))
			CONTEXT_PATH = PropKit.get("CONTEXT_PATH_ONLINE");
	    try {
	    	//System.out.println(JFinal.me().getContextPath());
	    	//JFinal.me().getContextPath()
			FreeMarkerRender.getConfiguration().setSharedVariable("CONTEXT_PATH",CONTEXT_PATH );
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
	 }
	
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	/*public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 5);
	}*/

}
