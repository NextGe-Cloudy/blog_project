package site.exception.sys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import site.exception.front.model.User;
import site.exception.front.model.vo.ArticleVo;
import site.exception.front.service.IArticleService;
import site.exception.front.service.ICategoryService;
import site.exception.front.service.IUserService;

/**
 * 后台控制器
 * 
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
	@Resource
	private ICategoryService categoryService;

	/**
	 * 登录视图
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
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
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(User user, HttpSession session) {
		user = userService.findByNameAndPassword(user);
		if (user != null) {
			// 查询数据库，id 存入 session 中
			session.setAttribute("userid", user.getId());
			// 重定向到提交文章页面
			// return "redirect:/sys/post";
		}

		// 重定向到首页
		return "redirect:/index";
	}

	/**
	 * 上传博客表单视图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String viewPost() {
		return "/sys/post-view";
	}

	/**
	 * 发表博客
	 * 
	 * @return
	 */
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String articlePost(HttpServletRequest request,
			@ModelAttribute ArticleVo vo, HttpSession session) {

		if (vo.getFiles() == null)
			return "redirect:/index";

		articleService.save(vo, (Integer) session.getAttribute("userid"));

		return "redirect:/index";
	}

}