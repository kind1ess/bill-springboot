package top.kindless.billtest.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.kindless.billtest.config.properties.KindlessProperties;
import top.kindless.billtest.exception.*;
import top.kindless.billtest.model.entity.User;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.model.vo.UserVo;
import top.kindless.billtest.repository.UserRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContext;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.security.token.AuthToken;
import top.kindless.billtest.service.UserService;
import top.kindless.billtest.utils.UUIDUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final KindlessProperties kindlessProperties;

//    @Autowired
//    @Qualifier("myCacheManager")
//    RedisCacheManager redisCacheManager;
    private final RedisTemplate<String,Object> redisTemplate;

    public UserServiceImpl(UserRepository userRepository,
                           KindlessProperties kindlessProperties,
                           RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.kindlessProperties = kindlessProperties;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public AuthToken login(LoginParams loginParams) {
//        String password = userRepository.findPasswordByAccount(loginParams.getAccount());
        log.info(loginParams.getAccount()+"尝试登录");
        User user = userRepository.findByAccount(loginParams.getAccount());
        if (user == null){
            throw new LoginParamsErrorException("用户名或密码错误");
        }
        if (!user.getPassword().equals(loginParams.getPassword())){
            throw new LoginParamsErrorException("用户名或密码错误");
        }
        return buildToken(user);
    }

    @Override
    public void logout() {
        String message = "未登录，不能注销";
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        String token = authentication.getToken();
        if (token == null){
            throw new UnAuthorizedException(message);
        }
        redisTemplate.delete(token);
        log.info(authentication.getUser().getAccount()+"退出登录");
    }

    @Override
    public void signUp(User user) {
        if (isExist(user.getAccount())){
            throw new AccountAlreadyExistException("账号不可用，该用户已存在！");
        }
        else {
            user.setId(UUIDUtils.generateUserUUID());
            user.setCreditScore(80);
            userRepository.save(user);
            log.info(user.getAccount()+"注册成功");
        }
    }

    @Override
    @Cacheable(key = "#id")
    public User findUserById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()){
            throw new BadRequestException("不存在该用户").setErrorData(id);
        }
        return optionalUser.get();
    }

    @Override
    public UserVo getUserProfile() {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        User user = authentication.getUser();
        return new UserVo(
                user.getId(),
                user.getAccount(),
                user.getPassword(),
                user.getTelephone(),
                user.getAddress()
        );
    }

    @Override
    @CachePut(key = "#user.id")
    public User updateUser(User user) {
        log.info(user.toString());
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        User authenticationUser = authentication.getUser();
        if (!authenticationUser.getId().equals(user.getId())){
            throw new ForbiddenException("禁止修改其他用户的数据");
        }
        user.setCreditScore(authenticationUser.getCreditScore());
        String token = authentication.getToken();
        redisTemplate.opsForValue().set(token,user,kindlessProperties.getTokenExpiredIn(),TimeUnit.SECONDS);
        return userRepository.saveAndFlush(user);
    }

    private AuthToken buildToken(User user){
        //生成token
        String token = UUIDUtils.generateToken();
        Long timeout = kindlessProperties.getTokenExpiredIn();
        //将token缓存起来,并设置缓存时间
        redisTemplate.opsForValue().set(token,user,timeout,TimeUnit.SECONDS);
        //缓存，设置缓存时间
//        redisTemplate.opsForValue().set(userId,token,kindlessProperties.getTokenExpiredIn(), TimeUnit.SECONDS);
        return new AuthToken(token,timeout,user.getId());
    }

    /**
     * 通过用户id判断是否存在
     * @param findAble 可以是用户id也可以是账号
     * @return 如果查询出来有一个不为空则返回true
     */
    private boolean isExist(String findAble){
        Optional<User> userOptional = userRepository.findById(findAble);
        User user = userRepository.findByAccount(findAble);
        return userOptional.isPresent()||user!=null;
    }
}
