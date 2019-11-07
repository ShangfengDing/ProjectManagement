package com.free4lab.freeRT.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.utils.account.AccountUtil;
import com.free4lab.utils.account.NewBaseLoginFilter;

public class LoginFilter extends NewBaseLoginFilter {

    @Override
    protected String getClientId() {
        return Constants.CLIENT_ID;
    }

    @Override
    protected String getRedirectUri() {
        return "/account/login";
    }

    @Override
    protected String getAccessTokenInSession(HttpServletRequest request,
                                             HttpServletResponse response) {
        String accessTokenInSession = (String) request.getSession().getAttribute(Constants.ACCESS_TOKEN);
        System.out.println("LoginFilter中：先checkPermission：accessTokenInSession"+accessTokenInSession);
        return accessTokenInSession;
    }

//    @Override
//    protected boolean checkPermission(HttpServletRequest request,
//                                      HttpServletResponse response) {
//        String accessTokenInSession = getAccessTokenInSession(request, response);
//        if( accessTokenInSession != null && !"".equalsIgnoreCase( accessTokenInSession )
//                && !"-1".equalsIgnoreCase( AccountUtil.getAccessTokenInfo(accessTokenInSession) ) ){
//            return true;
//        }
//        return false;
//    }
}
