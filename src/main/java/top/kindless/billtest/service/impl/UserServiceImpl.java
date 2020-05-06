package top.kindless.billtest.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.kindless.billtest.config.properties.KindlessProperties;
import top.kindless.billtest.exception.AccountAlreadyExistException;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.exception.LoginParamsErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.User;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.repository.UserRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.security.token.AuthToken;
import top.kindless.billtest.service.UserService;
import top.kindless.billtest.utils.UUIDUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
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
        User user = userRepository.findByAccount(loginParams.getAccount());
        if (!user.getPassword().equals(loginParams.getPassword())){
            throw new LoginParamsErrorException("用户名或密码错误");
        }
        return buildToken(user);
    }

    @Override
    public void logout() {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未登录，不能注销");
        }
        String token = authentication.getToken();
        if (token == null){
            throw new UnAuthorizedException("未登录，不能注销");
        }
        redisTemplate.delete(token);
    }

    @Override
    public void signUp(User user) {
        if (userRepository.findByAccount(user.getAccount())!=null){
            throw new AccountAlreadyExistException("账号不可用，该用户已存在！");
        }
        else {
            userRepository.save(user);
        }
    }

    @Override
    public User findUserById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()){
            throw new BadRequestException("不存在该用户").setErrorData(id);
        }
        return optionalUser.get();
    }

    private AuthToken buildToken(User user){
        //生成token
        String token = UUIDUtils.generateToken();
        Long timeOut = kindlessProperties.getTokenExpiredIn();
        //将token缓存起来,并设置缓存时间
        redisTemplate.opsForValue().set(token,user,timeOut,TimeUnit.SECONDS);
        AuthToken authToken = new AuthToken();
        authToken.setUserId(user.getId());
        authToken.setToken(token);
        System.out.println(timeOut);
        authToken.setExpiredIn(timeOut);
        //缓存，设置缓存时间
//        redisTemplate.opsForValue().set(userId,token,kindlessProperties.getTokenExpiredIn(), TimeUnit.SECONDS);
        return authToken;
    }
}
