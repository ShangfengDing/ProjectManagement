package com.free4lab.freeRT.action.login;

import com.free4lab.utils.account.NewBaseLandingAction;

public class LandingAction extends NewBaseLandingAction {

    @Override
    protected String getClientId() {
        return "freeproject";
    }

    @Override
    protected String getRedirectUri() {
        return "/account/login";
    }

}
