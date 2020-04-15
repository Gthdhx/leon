package com.leon.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.leon.enums.ResultStatusCode;
import com.leon.utils.ResultUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class AuthcShiroFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }
        returnTokenInvalid((HttpServletRequest)request, (HttpServletResponse)response);
        return false;
    }

    /**
     * 替代shiro重定向
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    private void returnTokenInvalid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream()));
        out.write(JSONObject.toJSONString(new ResultUtil(ResultStatusCode.INVALID_TOKEN)));
        out.flush();
        out.close();
    }
}
