package git.tmou.shirodemo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import git.tmou.shirodemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
