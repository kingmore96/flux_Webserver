package com.demo.fluxdemo.controller;

import com.demo.fluxdemo.pojo.User;
import com.demo.fluxdemo.repository.UserRepositoty;
import com.demo.fluxdemo.util.CheckUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepositoty userRepositoty;

    public UserController(UserRepositoty userRepositoty) {
        this.userRepositoty = userRepositoty;
    }

    /**
     * 原始的查找所有用户的方法
     * @return
     */
    @GetMapping("/origin")
    public Flux<User> findAll(){
        return userRepositoty.findAll();
    }

    /**
     * 使用Flux流返回数据
     * @return
     */
    @GetMapping(value = "/list",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findAllByStream(){
        return userRepositoty.findAll();
    }


    /**
     * 添加用户，不允许客户自己设置id
     * @param user
     * @return
     */

    @PostMapping("/add")
    public Mono<ResponseEntity<User>> add(@Valid  @RequestBody User user){
        //注意：save方法，如果id有值，就是更新，如果id为空，就是新增，不允许客户设置id
        CheckUtil.checkName(user.getName());
        user.setId(null);
        System.out.println(user.getName());
        System.out.println(user.getAge());
        return userRepositoty.save(user)
                .map(u -> new ResponseEntity<User>(u,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add_2")
    public Mono<User> add2(@RequestBody User user){
        //CheckUtil.checkName(user.getName());
        user.setId(null);
        System.out.println(user.getName());
        System.out.println(user.getAge());
        return userRepositoty.save(user);

    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        //这里不能直接用repository的delete方法，会返回空，不知道是删没删好
        //先去数据库查，然后用flatmap处理一下
        return userRepositoty.findById(id).flatMap(user -> userRepositoty.delete(user)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 删除用户，不用ResponseEntity包裹的
     * @param id
     * @return
     */
    @DeleteMapping("/delete_none/{id}")
    public Mono<Void> delete2(@PathVariable String id){
        return userRepositoty.deleteById(id);
    }




    /**
     * 更新用户信息
     * @param user
     * @param id
     * @return
     */
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<User>> update(@Valid @RequestBody User user,@PathVariable String id){
        CheckUtil.checkName(user.getName());
        return userRepositoty.findById(id).flatMap(u -> {
            u.setAge(user.getAge());
            u.setName(user.getName());
            return this.userRepositoty.save(u);
        }).map(u -> new ResponseEntity<User>(u,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<User>> findById(@PathVariable String id){
        return userRepositoty.findById(id)
                .map(user -> new ResponseEntity<User>(user,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }






}
