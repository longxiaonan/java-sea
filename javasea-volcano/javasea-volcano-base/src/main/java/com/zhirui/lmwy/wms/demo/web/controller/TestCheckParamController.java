package com.zhirui.lmwy.wms.demo.web.controller;

import com.zhirui.lmwy.wms.demo.web.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
* @ClassName CheckParamController
* @Description 对@RequestParam和@PathVariable的校验需要添加@Validated到类上
* @Author longxn
* @Date 2018/7/9 20:25
*/
@RestController
@Validated
public class TestCheckParamController {

    // http://localhost:8801/checkParamTest?username=1&age=-1
    // 提示: msg": "age必须大于0"
    @GetMapping("checkParamTest")
    public void checkParamTest(@RequestParam @Min(value = 0,message = "age必须大于0") Integer age){
        System.out.println(age + ",.,.");
    }

    // 加@Validated 或@Valid 对具体实体类检验，需要在实体类加相应的注解，如@Size等。在controller上不需要加@Validated注解
    // http://localhost:8801/checkParamTest2?username=1&age=1
    // 提示: msg": "age必须大于0"
    @GetMapping("checkParamTest2")
    public void checkParamTest2(@Valid User age){//@Validated或@Valid都可以
        System.out.println(age + ",.,.");
    }

    // 和上面的方法类似，但是用@RequestParam注解竟然无法参数转换，如果需要在get方法中传对象，用上面的方法。
    // 如果参数转换成功，需要校验User的话，应该是需要在controller上不需要加@Validated注解的
    // http://localhost:8801/checkParamTest2?username=1&age=1
    // 提示: msg": "age必须大于0"
    @GetMapping("checkParamTest3")
    public void checkParamTest3(@RequestParam @Valid User age){//@Validated或@Valid都可以
        System.out.println(age + ",.,.");
    }

    //加@Validated 或@Valid 对具体实体类检验，需要在实体类加相应的注解，如@Size等。在controller上不需要加@Validated注解
    // http://localhost:8801/checkParamTestPost
    // 提示: msg": "姓名长度为3到5之间"
    // 参数:
    // {
    //  "username":"1",
    //  "age":1
    // }
    @PostMapping("checkParamTestPost")
    public void checkParamTestPost(@RequestBody @Valid User user){//@Validated或@Valid都可以
        System.out.println(user);
    }


    // 在controller上需要添加@Validated 注解才能起校验作用， 在方法中添加@Validated 或 @Valid都没用
    // http://localhost:8080/checkParamTestPath/aa
    // msg": "checkParamTestPath.name: 名字长度在3到5之间"
    @GetMapping("checkParamTestPath/{name}")
    public void checkParamTestPath(@NotNull @NotBlank @Size(min=3,max=5, message = "名字长度在3到5之间")@PathVariable String name){
        System.out.println(name);
    }

    // 同checkParamTestPath
    // http://localhost:8801/checkParamTestPath2?username=1&age=-1
    // 提示: msg": "age必须大于0"
    @GetMapping("checkParamTestPath2/{age}")
    public void pathParamTest(@Min(value = 0,message = "age必须大于0") @PathVariable Integer age){
        System.out.println(age + ",.,.");
    }
}
