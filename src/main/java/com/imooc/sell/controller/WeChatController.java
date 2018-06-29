package com.imooc.sell.controller;

import com.imooc.sell.appException.SellException;
import com.imooc.sell.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    @Autowired
    private WxMpService wxMpService;


    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url = "http://sellasmjne.natapp1.cc/sell/wechat/userInfo";

//        String url = "http://sell.natapp4.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE,
                        URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code，redirectUrl={}",redirectUrl);
        return "redirect:"+redirectUrl;
    }

    //利用code获取访问令牌accessToken
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken ;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            log.error("【微信网页授权异常】{}",e.getMessage());
            throw new SellException(ResultEnum.WECHAT_AUTHORIZE_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("授权获取到的用户openID：{}，回调地址：{}",openId,returnUrl);
        return "redirect:" + returnUrl + "?openid=" + openId;

    }

    //微信测试公众号URL验证
    @GetMapping("/wxMp")
    @ResponseBody
    public String getChecked(@RequestParam("signature") String signature,
                             @RequestParam("timestamp") String timestamp,
                             @RequestParam("nonce") String nonce,
                             @RequestParam("echostr") String encrypt){
        try {
            String token = "mytoken";
            String[] array = new String[] { token, timestamp, nonce };
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            log.info("测试号URL验证方法，返回echostr:{}",hexstr.toString());
            if (hexstr.toString() .equals(signature)){
                return encrypt;
            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new SellException(ResultEnum.WECHAT_AUTHORIZE_ERROR);
        }
    }
}
