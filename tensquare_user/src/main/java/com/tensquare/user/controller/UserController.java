package com.tensquare.user.controller;

import java.util.HashMap;
import java.util.Map;
import entity.StatusMessage;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;
import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private JwtUtil jwtUtil;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK, StatusMessage.Success,userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,StatusMessage.Success,userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,StatusMessage.Success,  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,StatusMessage.Success,userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(value="/register/{code}",method=RequestMethod.POST)
	public Result register( @RequestBody User user ,@PathVariable String code){
		userService.add(user,code);
		return new Result(true,StatusCode.OK,StatusMessage.Success);
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,StatusMessage.Success);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		Claims claims=(Claims) request.getAttribute("admin_claims");
		if(claims==null){
			return new Result(true,StatusCode.ACCESSERROR,StatusMessage.Jurisdiction_NO);
		}
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,StatusMessage.Success);
	}

	/**
	 * 发送短信验证码
	 * @param mobile
	 */
	@RequestMapping(value="/sendsms/{mobile}",method=RequestMethod.POST)
	public Result sendsms(@PathVariable String mobile ){
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,StatusMessage.Success);
	}

	/**
	 * 用户登陆
	 * @param mobile
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Result login(String mobile,String password){
		User user = userService.findByMobileAndPassword(mobile,password);
		if(user!=null){
				String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");
				Map map=new HashMap();
				map.put("token",token);
				map.put("name",user.getNickname());//昵称
				map.put("avatar",user.getAvatar());//头像
				return new Result(true,StatusCode.OK,StatusMessage.Success,map);
		}else{
			return new Result(false,StatusCode.LOGINERROR,StatusMessage.Fail);
		}
	}

	/**
	 * 增加粉丝数
	 * @param userid
	 * @param x
	 */
	@RequestMapping(value="/incfans/{userid}/{x}",method= RequestMethod.POST)
	public void incFanscount(@PathVariable String userid,@PathVariable int x){
		userService.incFanscount(userid,x);
	}

	/**
	 * 增加关注数
	 * @param userid
	 * @param x
	 */
	@RequestMapping(value="/incfollow/{userid}/{x}",method= RequestMethod.POST)
	public void incFollowcount(@PathVariable String userid,@PathVariable int x){
		userService.incFollowcount(userid,x);
	}
}
