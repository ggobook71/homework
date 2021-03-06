package com.kakao.homework.core.resolver;

import com.kakao.homework.core.anotation.UserInfoResolver;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;

/*
UserInfoArgResolver
Controller resolver -> @UserInfoResolver
*/
public class UserInfoArgResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserInfoResolver.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        SprinklingHeaderDto sprinklingHeaderDto = new SprinklingHeaderDto();
        sprinklingHeaderDto.setUserId(request.getIntHeader("X-USER-ID"));
        sprinklingHeaderDto.setRoomId(request.getHeader("X-ROOM-ID"));
        return sprinklingHeaderDto;
    }
}
