package com.iee.webbase.modelConvertDemo;

import org.springframework.beans.BeanUtils;

public class UserDTO {
    private String username;
    private Integer age;

    public String getUsername() {
            return username;
    }

    public void setUsername(String username) {
            this.username = username;
    }

    public int getAge() {
            return age;
    }

    public void setAge(int age) {
            this.age = age;
    }


    public User convertToUser(){
            UserDTOConvert userDTOConvert = new UserDTOConvert();
            User convert = userDTOConvert.doForward(this);
            return convert;
    }

    public UserDTO convertFor(User user){
            UserDTOConvert userDTOConvert = new UserDTOConvert();
            UserDTO convert = userDTOConvert.doBackward(user);
            return convert;
    }

    private static class UserDTOConvert implements Converter<UserDTO, User> {
            @Override
            public User doForward(UserDTO userDTO) {
                    User user = new User();
                    BeanUtils.copyProperties(userDTO,user);
                    return user;
            }

            @Override
            public UserDTO doBackward(User user) {
                    if(1 != 1){
                        //如果需要则可以抛出了一个断言异常，而不是业务异常，这段代码告诉代码的调用者，
                        // 这个方法不是准你调用的，如果你调用，我就”断言”你调用错误了。
                        throw new AssertionError("不支持逆向转化方法!");
                    }
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(user,userDTO);
                    return userDTO;
            }
    }

}
