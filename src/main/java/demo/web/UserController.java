package demo.web;

import com.zhukai.spring.integration.commons.Session;
import com.zhukai.spring.integration.commons.annotation.*;
import com.zhukai.spring.integration.commons.constant.RequestType;
import com.zhukai.spring.integration.commons.response.Response;
import com.zhukai.spring.integration.commons.response.ResponseBuilder;
import com.zhukai.spring.integration.commons.response.ResponseCodeEnums;
import com.zhukai.spring.integration.context.WebContext;
import demo.domain.UserRepository;
import demo.domain.entity.UserBean;

import java.util.List;

/**
 * Created by zhukai on 17-2-8.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestType.POST)
    public Response login(@RequestAttribute("username") String username, @RequestAttribute("password") String password) {
        List<UserBean> userBeans = userRepository.findByUsernameAndPassword(username, password);
        if (userBeans != null && !userBeans.isEmpty()) {
            WebContext.getSession().setAttribute("loginUser", userBeans.get(0));
            return ResponseBuilder.build(ResponseCodeEnums.SUCCESS);
        } else {
            return ResponseBuilder.build(ResponseCodeEnums.LOGIN_ERROR);
        }
    }

    @RequestMapping("/checkUsername")
    public boolean checkUsername(@RequestParam("username") String username) {
        return !userRepository.exists(new Object[]{"username", username});
    }

    @RequestMapping(value = "/register", method = RequestType.POST)
    public Response register(@RequestBody UserBean userBean) {
        if (userRepository.save(userBean)) {
            return ResponseBuilder.build(ResponseCodeEnums.SUCCESS, "注册成功");
        }
        return ResponseBuilder.build(ResponseCodeEnums.DB_ERROR);
    }

    @RequestMapping("/resetMoney")
    public Boolean resetAllUserMoney() {
        return userRepository.resetMoney();
    }

    @RequestMapping("/getAll")
    public List<UserBean> getAllUsersExcludeAdmin() {
        return userRepository.getAllUsersExcludeAdmin(0);
    }

    @RequestMapping("/getRoleUser")
    public List<UserBean> getRoleUsers(@RequestParam("roleName") String roleName) {
        return userRepository.findAll(new Object[]{"role.roleName", roleName});
    }

    @RequestMapping("/getLoginName")
    public String getLoginName(Session session) {
        UserBean loginUser = (UserBean) session.getAttribute("loginUser");
        return loginUser.getUsername();
    }

}