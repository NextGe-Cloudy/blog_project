package site.exception.controller.sys;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import site.exception.model.User;
import site.exception.model.vo.ArticleVo;
import site.exception.service.IArticleService;
import site.exception.service.IUserService;

/**
 * 后台控制器
 * @author Allen
 * 
 */
@Controller
@RequestMapping("/sys")
public class SysController {

	private static final Log logger = LogFactory.getLog(SysController.class);

	@Resource
	private IArticleService articleService;
	@Resource
	private IUserService userService;
	

	@RequestMapping("/login_view")
	public String viewLogin(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		logger.info("request ip ： " + ip);
		return "/sys/login-view";
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String login(User user, HttpSession session) {
		user = userService.findByNameAndPassword(user); 
		if (user != null) {
			// 查询数据库，id 存入 session 中
			session.setAttribute("userid", user.getId());
			// 重定向到提交文章页面
			return "redirect:/sys/post_view";
		}

		// 用户名和密码错误，重新登录
		return "redirect:/sys/login_view";
	}

	/**
	 * 上传博客表单
	 * @return
	 */
	@RequestMapping("/post_view")
	public String viewPost() {
		return "/sys/post-view";
	}

	/**
	 * 发表博客
	 * @return
	 */
	@RequestMapping("/post")
	public String post(HttpServletRequest request, @ModelAttribute ArticleVo vo, HttpSession session) {
		
		if (vo.getFiles() == null) 
			return "/sys/post-view";
		
		articleService.save(vo, (Integer) session.getAttribute("userid"));
		
		return "/sys/post-view";
	}

}
