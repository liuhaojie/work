package cn.maven.medol;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private ServerDao dao;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	
	@RequestMapping(value="addServers",method = RequestMethod.GET)
	public String updateServers(Model model,@RequestParam("id") int id,@RequestParam("gameID") int gameID,@RequestParam("gameName" )String gameName){
		dao = new ServerDao();
		int row = dao.update(id, gameID, gameName);
		String result = null;
		if(row != -1){
			try {
				System.out.println("HomeController   updateServers");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			selectServers(gameID);
			result = "添加数据成功！！";
		}else{
			result = "添加数据失败。。";
		}
		model.addAttribute("update_result", result);
		return "";
	}

	@RequestMapping(value = "/serverInfos", method = RequestMethod.GET)
	public String getServers(Model model, @RequestParam("gameID") int gameID) {
		Object obj = MemcacheManger.getCache("liuhaojie" + gameID);
		if (obj != null) {
			System.out.println("+++++++++++");
			model.addAttribute("gameList", obj);
		} else {
			System.out.println("-----------");
			dao = new ServerDao();
			List<ServerInfo> servers = selectServers(gameID);
			model.addAttribute("gameList", servers);
		}
		return "gameInfos";
	}
	
	private List<ServerInfo> selectServers(int gameID) {
		// TODO Auto-generated method stub
		List<ServerInfo> list = dao.queryServersByGameId(gameID);
		for(ServerInfo serverInfo : list){
			
			try {
				String name = encodStr(serverInfo.getGameName(), "ISO8859-1");
				serverInfo.setGameName(name);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		MemcacheManger.setCache("liuhaojie" + gameID, 10000, list);
		return list;
	}


	private String encodStr(String gameName, String encode)throws Exception {
		// TODO Auto-generated method stub
		return new String(gameName.getBytes(encode), "UTF-8");
	}
}
