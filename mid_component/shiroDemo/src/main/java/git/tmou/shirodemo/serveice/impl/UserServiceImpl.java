package git.tmou.shirodemo.serveice.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import git.tmou.shirodemo.entity.User;
import git.tmou.shirodemo.mapper.UserMapper;
import git.tmou.shirodemo.serveice.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
